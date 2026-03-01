package com.example.criminalintent_ranises;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private CheckBox mRequiresPoliceCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        if (crimeId != null) {
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
            if (mCrime == null) {
                // Create a new crime if it doesn't exist
                mCrime = new Crime();
                CrimeLab.get(getActivity()).addCrime(mCrime);
            }
        } else {
            // Create a new crime if no ID provided
            mCrime = new Crime();
            CrimeLab.get(getActivity()).addCrime(mCrime);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        if (mCrime == null) {
            mCrime = new Crime();
        }

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        if (mTitleField != null) {
            mTitleField.setText(mCrime.getTitle() != null ? mCrime.getTitle() : "");
            mTitleField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(
                        CharSequence s, int start, int count, int after) {
                    // This space intentionally left blank
                }

                @Override
                public void onTextChanged(
                        CharSequence s, int start, int before, int count) {
                    mCrime.setTitle(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // This one too
                }
            });
        }

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        if (mDateButton != null) {
            mDateButton.setText(mCrime.getDate() != null ? mCrime.getDate().toString() : "");
            mDateButton.setEnabled(false);
        }

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        if (mSolvedCheckBox != null) {
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    mCrime.setSolved(isChecked);
                }
            });
        }

        mRequiresPoliceCheckBox = (CheckBox)v.findViewById(R.id.requires_police);
        if (mRequiresPoliceCheckBox != null) {
            mRequiresPoliceCheckBox.setChecked(mCrime.isRequiresPolice());
            mRequiresPoliceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    mCrime.setRequiresPolice(isChecked);
                }
            });
        }


        return v;
    }
}
