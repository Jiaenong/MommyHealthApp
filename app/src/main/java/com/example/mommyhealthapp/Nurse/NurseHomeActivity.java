package com.example.mommyhealthapp.Nurse;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.mommyhealthapp.Class.MedicalPersonnel;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.Nurse.ui.CreateMother.CreateMotherDetailFragment;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class NurseHomeActivity extends AppCompatActivity implements CreateMotherDetailFragment.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private CircularImageView imageViewProfilePic;
    private TextView textViewMPName;

    private FirebaseFirestore mFirebaseFirestore;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imageViewProfilePic = (CircularImageView)headerView.findViewById(R.id.imageViewProfilePic);
        textViewMPName = (TextView)headerView.findViewById(R.id.textViewMPName);

        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference mCollectionReference = mFirebaseFirestore.collection("MedicalPersonnel");
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    MedicalPersonnel mp = documentSnapshot.toObject(MedicalPersonnel.class);
                    if(documentSnapshot.getId().equals(SaveSharedPreference.getID(NurseHomeActivity.this)))
                    {
                        textViewMPName.setText(mp.getName());
                        if(mp.getImageUrl().equals(""))
                        {
                            imageViewProfilePic.setImageResource(R.drawable.user);
                        }else{
                            Glide.with(NurseHomeActivity.this).load(mp.getImageUrl()).into(imageViewProfilePic);
                        }
                    }
                }
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_create, R.id.nav_search, R.id.nav_nurseprofile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(tag != null && tag.equals("TAG"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("tag", tag);
            navController.navigate(R.id.nav_nurseprofile, bundle);
        }
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(NurseHomeActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(NurseHomeActivity.this);
                        Intent intent = new Intent(NurseHomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setMessage("Are you sure you want to log out?");
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
