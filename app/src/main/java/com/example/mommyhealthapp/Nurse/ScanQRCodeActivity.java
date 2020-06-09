package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.MainActivity;
import com.example.mommyhealthapp.Mommy.KickCounterActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class ScanQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        scannerView.setAspectTolerance(0.5f);
        setContentView(scannerView);

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        if(checkPermission()){
            Toast.makeText(ScanQRCodeActivity.this,"Permission is already granted",Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(ScanQRCodeActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(ScanQRCodeActivity.this,new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]){
        switch (requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(ScanQRCodeActivity.this,"Permission granted",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ScanQRCodeActivity.this,"Permission denied",Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            Log.i("Testing", "test");
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                Log.i("Testing", "test2");
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;

        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if(checkPermission()){
            if(scannerView==null){
                scannerView = new ZXingScannerView(this);
                scannerView.setAspectTolerance(0.5f);
                setContentView(scannerView);
            }
            scannerView.setResultHandler(this);
            scannerView.startCamera();
        }else{
            requestPermission();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        final String mommyId = result.getText();

        mCollectionReference = mFirebaseFirestore.collection("Mommy");
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    Mommy mommy = documentSnapshot.toObject(Mommy.class);
                    if(mommy.getMommyId().equals(mommyId))
                    {
                        check++;
                        Intent intent = new Intent(ScanQRCodeActivity.this, MommyRecordActivity.class);
                        SaveSharedPreference.setMummyId(ScanQRCodeActivity.this, mommy.getMommyId());
                        intent.putExtra("MommyID", mommy.getMommyId());
                        startActivity(intent);
                    }
                }
                if(check == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScanQRCodeActivity.this);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            scannerView.resumeCameraPreview(ScanQRCodeActivity.this);
                            dialogInterface.cancel();
                        }
                    });
                    builder.setCancelable(false);
                    builder.setTitle("Error");
                    builder.setMessage("Invalide QRCode !");
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(ScanQRCodeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(ScanQRCodeActivity.this);
                builder.setTitle("Logout");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaveSharedPreference.clearUser(ScanQRCodeActivity.this);
                        Intent intent = new Intent(ScanQRCodeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setMessage("Are you sure you want to log out?");
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_home, menu);
        return true;
    }
}
