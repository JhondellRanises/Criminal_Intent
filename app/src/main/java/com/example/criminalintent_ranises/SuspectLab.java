package com.example.criminalintent_ranises;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class SuspectLab {
    private static SuspectLab sSuspectLab;
    private List<String> mSuspects;
    private Context mContext;

    private SuspectLab(Context context) {
        mContext = context.getApplicationContext();
        mSuspects = new ArrayList<>();
        
        // Add some sample suspects
        mSuspects.add("John Doe");
        mSuspects.add("Jane Smith");
        mSuspects.add("Bob Johnson");
        mSuspects.add("Alice Brown");
        mSuspects.add("Charlie Wilson");
        mSuspects.add("Diana Prince");
        mSuspects.add("Edward Norton");
        mSuspects.add("Fiona Apple");
        mSuspects.add("George Lucas");
        mSuspects.add("Helen Troy");
    }

    public static SuspectLab get(Context context) {
        if (sSuspectLab == null) {
            sSuspectLab = new SuspectLab(context);
        }
        return sSuspectLab;
    }

    public List<String> getSuspects() {
        return mSuspects;
    }

    public void addSuspect(String suspect) {
        mSuspects.add(suspect);
    }
}
