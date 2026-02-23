package com.example.criminalintent_ranises;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private Context mContext;

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mCrimes = new ArrayList<>();
        
        // No sample crimes generated initially. User will add crimes.
        // Adding one sample crime for testing
        Crime sampleCrime = new Crime();
        sampleCrime.setTitle("Scooter stolen while going to the restroom");
        sampleCrime.setSolved(false);
        mCrimes.add(sampleCrime);
    }

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void addCrime(Crime crime) {
        mCrimes.add(crime);
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
