package com.igweze.ebi.journalapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.services.SharedPreferenceService;
import com.igweze.ebi.journalapp.ui.adapters.WriteupAdapter;
import com.igweze.ebi.journalapp.ui.model.UserInfo;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 200;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private SharedPreferenceService mSharedPreferenceService;
    private TextView userName;
    private TextView userEmail;
    private NavigationView mNavigationView;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create google signIn options
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // initialize google signIn client
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // get fireBase auth
        mAuth = FirebaseAuth.getInstance();

        // create shared preference service
        mSharedPreferenceService = new SharedPreferenceService(this);

        final RecyclerView rv = findViewById(R.id.rvWriteupList);

        WriteupListViewModelFactory factory = InjectorUtils.provideListViewModelFactory(this);
        WriteupListViewModel mViewModel = ViewModelProviders.of(this, factory).get(WriteupListViewModel.class);

        mViewModel.getWriteups().observe(this, writeups -> {
            rv.setAdapter(new WriteupAdapter(writeups));
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        });

        // set support action bar with toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // sync drawer toggle with toolbar hamburger icon
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // set navigation listener
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener( v -> {
            // sign out the user
            if (v.getItemId() == R.id.nav_sign_out) {
                signOut();
                return true;
            }
            if (v.getItemId() == R.id.nav_sign_in) {
                login();
                return true;
            }
            return false;
        });

        View headerView = mNavigationView.getHeaderView(0);
        // set user name and email
        userName = headerView.findViewById(R.id.text_user_name);
        userEmail = headerView.findViewById(R.id.text_email_address);

        // hide login button
        showButton(false);

        // check logged in status
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) showButton(true);
        else authenticateWithFirebase(account);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newWriteup: {
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TYPE, EditActivity.TYPE_ADD);
                startActivity(intent);
                return true;
            }
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        closeDrawer();
        super.onBackPressed();
    }

    private void setUserInfoInDrawer() {
        UserInfo userInfo = mSharedPreferenceService.getUserInfo();
        if (userInfo != null) {
            String fullName = userInfo.firstName + " "+ userInfo.lastName;
            userEmail.setText(userInfo.email);
            userName.setText(fullName);
        }
    }

    private void removeProfileInfo() {
        mSharedPreferenceService.removeUser();
        userEmail.setText(" ");
        userName.setText(" ");
    }

    private void signOut() {
        // sign out with google
        mGoogleSignInClient.signOut();
        // sign out with firebase
        mAuth.signOut();
        // close the nav drawer
        closeDrawer();
        // remove profile text views
        removeProfileInfo();
        // show login button
        showButton(true);

        Toast.makeText(this, "Signed out", Toast.LENGTH_LONG).show();
    }


    private void showButton(boolean shouldShow) {
        Menu menu = mNavigationView.getMenu();
        MenuItem signOutMenu = menu.getItem(0);
        MenuItem signInMenu = menu.getItem(1);

        if (shouldShow) {
            signInMenu.setVisible(true);
            signOutMenu.setVisible(false);
        } else {
            signInMenu.setVisible(false);
            signOutMenu.setVisible(true);
        }
    }

    private void login() {
        closeDrawer();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            // get result of signIn task
            GoogleSignInAccount account = task.getResult(ApiException.class);
            authenticateWithFirebase(account);
        } catch (ApiException e) {
            Log.w(TAG, "SignIn failed, signInResult: failed code=" + e.getStatusCode());
        }
    }

    private void authenticateWithFirebase(GoogleSignInAccount account) {
        // hide button and show spinner
        showButton(false);

        // check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // set the user's info on navigation drawer
            setUserInfoInDrawer();
            return;
        }

        // else sign in user with firebase
        Log.d(TAG, "firebase auth with account id: " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        // sign in with google credentials
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "firebase sign in: SUCCESS");
                        // save the user's information.
                        UserInfo userInfo = new UserInfo(account.getGivenName(), account.getFamilyName(), account.getEmail());
                        new SharedPreferenceService(this).setUser(userInfo);
                        // show user profile info
                        setUserInfoInDrawer();
                    } else {
                        Log.w(TAG, "firebase sign in: FAILED");
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseAuthException) {
                        String code = ((FirebaseAuthException) e).getErrorCode();
                        Log.e(TAG, "firebase auth exception code: "+ code);
                    }
                    Log.e(TAG, e.getMessage());
                });
    }
}
