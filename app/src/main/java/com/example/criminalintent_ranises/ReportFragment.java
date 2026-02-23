package com.example.criminalintent_ranises;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    private Button mSendEmailButton;
    private Button mSendTextButton;
    private Button mSendSocialButton;
    private TextView mReportText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        mReportText = (TextView) view.findViewById(R.id.report_text);
        mSendEmailButton = (Button) view.findViewById(R.id.send_email_button);
        mSendTextButton = (Button) view.findViewById(R.id.send_text_button);
        mSendSocialButton = (Button) view.findViewById(R.id.send_social_button);

        // Set up click listeners
        mSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport("Email");
            }
        });

        mSendTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport("Text Message");
            }
        });

        mSendSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport("Social Media");
            }
        });

        return view;
    }

    private void sendReport(String method) {
        Crime crime = CrimeLab.get(getActivity()).getCrimes().get(0); // Get first crime for demo
        
        String report = "Crime Report\n" +
                "Title: " + crime.getTitle() + "\n" +
                "Date: " + crime.getDate() + "\n" +
                "Solved: " + (crime.isSolved() ? "Yes" : "No");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, report);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Crime Report: " + crime.getTitle());

        if (method.equals("Email")) {
            intent.setType("message/rfc822");
        }

        startActivity(Intent.createChooser(intent, "Send crime report via " + method));
    }
}
