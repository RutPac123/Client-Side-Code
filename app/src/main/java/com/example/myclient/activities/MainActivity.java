package com.example.myclient.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclient.fragments.DashFragment;
import com.example.myclient.fragments.HomeFragment;
import com.example.myclient.fragments.ProfileFragment;
import com.example.myclient.R;

public class MainActivity extends AppCompatActivity {

    private long back_pressed_time=0L;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedone = new Fragment();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedone = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    selectedone = new DashFragment();
                    break;
                case R.id.navigation_notifications:
                    selectedone = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer,selectedone).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() < back_pressed_time+1000){  // 50500 < 51000
            moveTaskToBack(true);
        }
        else {
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        }
        back_pressed_time = System.currentTimeMillis();  // 50000
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer,new HomeFragment()).commit();


    }

}
