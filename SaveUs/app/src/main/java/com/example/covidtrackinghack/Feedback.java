package com.example.covidtrackinghack;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Feedback extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private RatingBar ratingBar;
    private TextView tvRateCount,tvRateMessage;

    private float ratedValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);// Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvRateMessage = findViewById(R.id.tvRateMessage);
        tvRateCount = findViewById(R.id.tvRateCount);
        //Rating
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_feedback);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override

            public void onRatingChanged(RatingBar ratingBar, float rating,

                                        boolean fromUser) {

                ratedValue = ratingBar.getRating();

               tvRateCount.setText("Your Rating : "

                        + ratedValue + "/5.");
                tvRateCount.setVisibility(View.VISIBLE);
                if(ratedValue<1){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();

                    tvRateMessage.setText("ohh ho...");
                    tvRateMessage.setVisibility(View.VISIBLE);
                }else if(ratedValue<2){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();
                   tvRateMessage.setText("Ok.");
                    tvRateMessage.setVisibility(View.VISIBLE);
                }else if(ratedValue<3){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();
                    tvRateMessage.setText("Not bad.");
                    tvRateMessage.setVisibility(View.VISIBLE);
                }else if(ratedValue<4){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();
                   tvRateMessage.setText("Nice");
                    tvRateMessage.setVisibility(View.VISIBLE);
                }else if(ratedValue<5){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();
                   tvRateMessage.setText("Very Nice");
                    tvRateMessage.setVisibility(View.VISIBLE);
                }else if(ratedValue==5){
                    Toast.makeText(Feedback.this, "Thank you, your response has been saved", Toast.LENGTH_SHORT).show();
                    tvRateMessage.setText("Thank you..!!!");
                    tvRateMessage.setVisibility(View.VISIBLE);
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