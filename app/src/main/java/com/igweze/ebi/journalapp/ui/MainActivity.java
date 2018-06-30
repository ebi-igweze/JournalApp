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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.igweze.ebi.journalapp.R;
import com.igweze.ebi.journalapp.services.SharedPreferenceService;
import com.igweze.ebi.journalapp.ui.adapters.WriteupAdapter;
import com.igweze.ebi.journalapp.ui.model.UserInfo;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModel;
import com.igweze.ebi.journalapp.ui.model.WriteupListViewModelFactory;
import com.igweze.ebi.journalapp.utilities.InjectorUtils;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private SharedPreferenceService mSharedPreferenceService;
    private TextView userName;
    private TextView userEmail;

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // set navigation listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( v -> {
            // sign out the user
            if (v.getItemId() == R.id.nav_sign_out) {
                signOut();
                return true;
            }
            return false;
        });

        View headerView = navigationView.getHeaderView(0);
        // set user name and email
        userName = headerView.findViewById(R.id.text_user_name);
        userEmail = headerView.findViewById(R.id.text_email_address);

        // set the user's info on navigation drawer
        setUserInfoInDrawer();
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setUserInfoInDrawer() {
        UserInfo userInfo = mSharedPreferenceService.getUserInfo();
        if (userInfo != null) {
            String fullName = userInfo.firstName + " "+ userInfo.lastName;
            userEmail.setText(userInfo.email);
            userName.setText(fullName);
        }
    }

    private void signOut() {
        Intent intent = new Intent(this, SplashActivity.class);
        // sign out with google
        mGoogleSignInClient.signOut();
        // sign out with firebase
        mAuth.signOut();
        Toast.makeText(this, "Signed out", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}
