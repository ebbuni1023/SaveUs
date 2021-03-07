package com.example.covidtrackinghack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import java.util.HashMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Survey extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11, cb12, cb13, cb14;
    Button button_no, button_next;
    DatabaseReference reff;
    String userID;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Check box
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        cb8 = findViewById(R.id.cb8);
        cb9 = findViewById(R.id.cb9);
        cb10 = findViewById(R.id.cb10);
        cb11 = findViewById(R.id.cb11);
        cb12 = findViewById(R.id.cb12);
        cb13 = findViewById(R.id.cb13);
        cb14 = findViewById(R.id.cb14);

        button_no = findViewById(R.id.button_no);
        button_next = findViewById(R.id.button_next);


        StringBuffer res =  new StringBuffer();
        res.append("You have these symptoms: ");

        //database
        reff = FirebaseDatabase.getInstance().getReference().child("Symptoms");


        // start a new activity, which is another question?
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), question2.class));
            }
        });
        // start a new activity, which is another question?
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb1.isChecked()) {
                    res.append(cb1.getText().toString());
                }
                if (cb2.isChecked()) {
                    res.append(", " + cb2.getText().toString());
                }
                if (cb3.isChecked()) {
                    res.append(", " + cb3.getText().toString());
                }
                if (cb4.isChecked()) {
                    res.append(", " + cb4.getText().toString());
                }
                if (cb5.isChecked()) {
                    res.append(", " + cb5.getText().toString());
                }
                if (cb6.isChecked()) {
                    res.append(", " + cb6.getText().toString());
                }
                if (cb7.isChecked()) {
                    res.append(", " + cb7.getText().toString());
                }
                if (cb8.isChecked()) {
                    res.append(", " + cb8.getText().toString());
                }
                if (cb9.isChecked()) {
                    res.append(", " + cb9.getText().toString());
                }if (cb10.isChecked()) {
                    res.append(", " + cb10.getText().toString());
                }
                if (cb11.isChecked()) {
                    res.append(", " + cb11.getText().toString());
                }if (cb12.isChecked()) {
                    res.append(", " + cb12.getText().toString());
                }if (cb13.isChecked()) {
                    res.append(", " + cb13.getText().toString());
                }if (cb14.isChecked()) {
                    res.append(", " + cb14.getText().toString());
                }
                String str = res.toString();
                reff.child("member1").push().setValue(str);

                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Symptoms").document(userID);
                java.util.Map<String, Object> contact = new HashMap<>();
                contact.put("Your result:", str);

                documentReference.set(contact).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                Toast.makeText(Survey.this, res, Toast.LENGTH_SHORT).show();
                if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked() || cb5.isChecked()
                        || cb6.isChecked() || cb7.isChecked() || cb8.isChecked() || cb9.isChecked()|| cb9.isChecked()
                        || cb10.isChecked() || cb11.isChecked() || cb12.isChecked() || cb13.isChecked()
                        || cb14.isChecked()) {
                    startActivity(new Intent(getApplicationContext(), question1.class));
                } else {
                    Toast.makeText(Survey.this, "Please select the symptoms", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.nav_first_fragment:
                startActivity(new Intent(getApplicationContext(), Survey.class));
                break;
            case R.id.nav_second_fragment:
                startActivity(new Intent(getApplicationContext(), Map.class));
                break;
            case R.id.nav_third_fragment:
                startActivity(new Intent(getApplicationContext(), Contact.class));
                break;
            case R.id.nav_feedback:
                startActivity(new Intent(getApplicationContext(), Feedback.class));
                break;
            case R.id.nav_share:
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "CovidTrackingApp: Designed by Bryan and Jiyoung";
                String shareSub = "Share Us";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share Using"));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            default:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
/*
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } */

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }
}