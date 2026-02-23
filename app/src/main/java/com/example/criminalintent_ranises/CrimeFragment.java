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
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mChooseSuspectButton;
    private Button mSendReportButton;
    private ImageView mCrimePhotoView;

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

        mChooseSuspectButton = (Button) v.findViewById(R.id.choose_suspect_button);
        mSendReportButton = (Button) v.findViewById(R.id.send_report_button);
        mCrimePhotoView = (ImageView) v.findViewById(R.id.crime_photo);

        // Set up button click listeners
        mChooseSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuspectActivity.class);
                startActivity(intent);
            }
        });

        mSendReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                startActivity(intent);
            }
        });

        // Set up camera button click listener
        ImageButton cameraButton = (ImageButton) v.findViewById(R.id.crime_camera);
        if (cameraButton != null) {
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Implement camera functionality
                    if (getActivity() != null) {
                        android.widget.Toast.makeText(getActivity(), "Camera feature coming soon!", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return v;
    }
}
