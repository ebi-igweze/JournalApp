package com.igweze.ebi.journalapp.datastore;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.igweze.ebi.journalapp.ui.model.Writeup;
import com.igweze.ebi.journalapp.utilities.AppExecutors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalRepository {
    private static String TAG = JournalRepository.class.getSimpleName();


    // for singleton instance
    private static final Object LOCKER = new Object();
    private static volatile JournalRepository mRepository;

    // firebase user's table reference
    private static String USER_TABLE = "users_table";
    private static String WRITEUP_KEY = "writeups";

    private DatabaseReference ref = null;

    private final WriteupDao mWriteupDao;
    private final AppExecutors mExecutors;

    private JournalRepository(WriteupDao writeupDao, AppExecutors executors) {
        this.mWriteupDao = writeupDao;
        this.mExecutors = executors;
        setupFirebase();
    }

    // return single instance
    public synchronized static JournalRepository getInstance(WriteupDao writeupDao, AppExecutors executors) {
        if (mRepository == null) {
            // prevent access until instance is created
            synchronized (LOCKER) {
                mRepository = new JournalRepository(writeupDao, executors);
                Log.d(TAG, "Created new repository");
            }
        }

        // return instance
        return mRepository;
    }


    private void setupFirebase() {
        // enable offline mode
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // initialize db reference
        setDbReference(FirebaseAuth.getInstance());
        // listen for authentication changes
        FirebaseAuth.getInstance().addAuthStateListener(this::setDbReference);
    }

    private void setDbReference(FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            ref = db.getReference(USER_TABLE).child(user.getUid()).child(WRITEUP_KEY);
            // sync db with firebase
            mExecutors.diskIO().execute(this::syncDbWithFirebase);
        } else {
            ref = null;
            Log.d(TAG, "user logged out");
        }
    }

    private void syncDbWithFirebase() {
        // get updates and insert in map
        List<Writeup> writeupsUpdate = mWriteupDao.getLocalWriteups(Writeup.LOGGED_OUT);
        if (writeupsUpdate != null && writeupsUpdate.size() != 0) {
            Map<String, Object> updates = new HashMap<>();
            for (Writeup w : writeupsUpdate) {
                w.setLoggedOut(Writeup.SINGED_IN);
                String key = w.getId() + "";
                updates.put(key, w);
            }

            ref.updateChildren(updates).addOnSuccessListener(v -> {
                // update status of wrieups
                mExecutors.diskIO().execute(() -> mWriteupDao.updateAll(writeupsUpdate));
            });
        }

        // get pending items to be deleted
        List<Writeup> writeupsDelete = mWriteupDao.getPendingRemovals();
        if (writeupsDelete != null && writeupsDelete.size() != 0) {
            Map<String, Object> deletes = new HashMap<>();
            for (Writeup w : writeupsDelete) {
                String key = w.getId() + "";
                deletes.put(key, null);
            }

            // update server and local db
            ref.updateChildren(deletes).addOnSuccessListener(v ->
                    mExecutors.diskIO().execute(() -> mWriteupDao.deleteAll(writeupsDelete)));
        }


    }

    public LiveData<Writeup> getWriteupById(int id) {
        return mWriteupDao.getWriteupWithId(id);
    }

    public LiveData<List<Writeup>> getWriteups() {
        return mWriteupDao.getWriteups();
    }

    public void addWriteup(Writeup writeup) {
        mExecutors.diskIO().execute(() -> {
            //  check if user is logged out
            boolean isSignedIn = ref != null;
            int loggedOut = isSignedIn ? Writeup.SINGED_IN : Writeup.LOGGED_OUT;
            writeup.setLoggedOut(loggedOut);
            long id = mWriteupDao.insertWriteup(writeup);

            // send update to firebase db
            if (isSignedIn) ref.child(id+"").setValue(writeup);
        });
    }

    public void updateWriteup(Writeup writeup) {
        //  check if user is logged out
        mExecutors.diskIO().execute(() -> {
            boolean isSignedIn = ref != null;
            int loggedOut = isSignedIn ? Writeup.SINGED_IN : Writeup.LOGGED_OUT;
            writeup.setLoggedOut(loggedOut);
            mWriteupDao.updateWriteup(writeup);

            // send update to firebase db
            if (isSignedIn) ref.child(writeup.getId()+"").setValue(writeup);
        });
    }

    public void deleteWriteup(Writeup writeup) {
        mExecutors.diskIO().execute(() ->  {
            boolean isSignedIn = ref != null;
            if (isSignedIn) {
                mWriteupDao.deleteWriteup(writeup);
                ref.child(writeup.getId() + "").removeValue();
            } else {
                writeup.setActive(Writeup.IN_ACTIVE);
                mWriteupDao.updateWriteup(writeup);
            }
        });
    }
}
