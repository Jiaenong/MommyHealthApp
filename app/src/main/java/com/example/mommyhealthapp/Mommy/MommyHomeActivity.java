package com.example.mommyhealthapp.Mommy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.mommyhealthapp.Mommy.ui.SelfCheck.SelfCheckFragment;
import com.example.mommyhealthapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MommyHomeActivity extends AppCompatActivity implements SelfCheckFragment.OnFragmentInteractionListener {
    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mommy_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_self_check, R.id.navigation_pregnancy_record, R.id.navigation_notifications, R.id.navigation_mother_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(tag != null && tag.equals("TAG"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            navController.navigate(R.id.navigation_mother_profile, bundle);
        }
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
