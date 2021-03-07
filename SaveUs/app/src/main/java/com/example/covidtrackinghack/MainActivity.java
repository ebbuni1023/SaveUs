package com.example.covidtrackinghack;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    TextView userName, userEmail;
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    String userID2;
    String forQRCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        qrCodeIV = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);// NEED TO CHANGE
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        userID2 = fAuth.getCurrentUser().getUid();
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);

        DocumentReference documentReference = fStore.collection("Symptoms").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                forQRCode = documentSnapshot.getString("Your result:");
                if (forQRCode != null) {
                    dataEdt.setText("https://codd.cs.gsu.edu/~nchau1/gotCovid.png");
                } else dataEdt.setText("https://codd.cs.gsu.edu/~nchau1/gotNoCovid.png");
            }
        });
       DocumentReference documentReference2 = fStore.collection("users").document(userID2);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                String name = "Full Name: " + document.getString("Full Name");
                String email = "Email: " + document.getString("Email");
              //  String res = "  \n\n ~~~~! Your name is: " + document.getString("Full Name") + " !~~~~" ;
               // forQRCode += res;
               // dataEdt.setText(forQRCode);
                userName.setVisibility(View.VISIBLE);
                userEmail.setVisibility(View.VISIBLE);
                userName.setText(name);
                userEmail.setText(email);
            }
        });

        // initializing onclick listener for button.
        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                    Toast.makeText(MainActivity.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {

                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Display display = manager.getDefaultDisplay();

                    Point point = new Point();
                    display.getSize(point);

                    int width = point.x;
                    int height = point.y;

                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen);
                    try {

                        bitmap = qrgEncoder.encodeAsBitmap();

                        qrCodeIV.setImageBitmap(bitmap);
                    } catch (WriterException e) {

                        Log.e("Tag", e.toString());
                    }
                }
            }
        });


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
    }
    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
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
                startActivity(new Intent(getApplicationContext(), Register.class));
                finish();
                break;
            default:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

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