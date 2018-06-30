package com.igweze.ebi.journalapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.services.SharedPreferenceService;
import com.igweze.ebi.journalapp.ui.model.UserInfo;

public class SplashActivity extends AppCompatActivity {
    private static int RC_SIGN_IN = 200;
    private static String TAG = SplashActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private SignInButton mGoogleButton;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // initialize ui components
        mProgressBar = findViewById(R.id.progress_bar);
        mGoogleButton = findViewById(R.id.sign_in_button);

        // set login click listener
        mGoogleButton.setOnClickListener(v -> login());

        // hide login button
        showButton(false);

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

        // check logged in status
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) showButton(true);
        else authenticateWithFirebase(account);
    }

    private void gotoHome(FirebaseUser currentUser) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showButton(boolean shouldShow) {
        if (shouldShow) {
            mGoogleButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        } else {
            mGoogleButton.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void login() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
            gotoHome(currentUser);
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
                     // navigate to the main activity
                     gotoHome(mAuth.getCurrentUser());
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
