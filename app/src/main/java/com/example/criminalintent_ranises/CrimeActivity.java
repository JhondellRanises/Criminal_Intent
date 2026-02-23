package com.example.criminalintent_ranises;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {
    private static final String EXTRA_CRIME_ID = "com.example.criminalintent_ranises.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        
        setTitle("CriminalIntent_Ranises");
        
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        
        if (fragment == null) {
            try {
                fragment = CrimeFragment.newInstance(crimeId);
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
                // Create a new crime fragment without ID if there's an error
                fragment = CrimeFragment.newInstance(null);
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }
}
