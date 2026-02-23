package com.example.criminalintent_ranises;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        
        setTitle("CriminalIntent_Ranises");
        
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        
        if (currentFragment == null) {
            Fragment fragment = new ReportFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
