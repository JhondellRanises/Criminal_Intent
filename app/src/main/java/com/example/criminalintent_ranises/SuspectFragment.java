package com.example.criminalintent_ranises;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuspectFragment extends Fragment {

    private RecyclerView mSuspectRecyclerView;
    private SuspectAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suspect, container, false);
        
        mSuspectRecyclerView = (RecyclerView) view.findViewById(R.id.suspect_recycler_view);
        mSuspectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        updateUI();
        
        return view;
    }

    private void updateUI() {
        SuspectLab suspectLab = SuspectLab.get(getActivity());
        List<String> suspects = suspectLab.getSuspects();
        mAdapter = new SuspectAdapter(suspects);
        mSuspectRecyclerView.setAdapter(mAdapter);
    }

    private class SuspectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private String mSuspect;

        public SuspectHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_suspect, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView.findViewById(R.id.suspect_name);
        }

        public void bind(String suspect) {
            mSuspect = suspect;
            mNameTextView.setText(mSuspect);
        }

        @Override
        public void onClick(View v) {
            // Return selected suspect to previous activity
            getActivity().setResult(android.app.Activity.RESULT_OK, null);
            getActivity().finish();
        }
    }

    private class SuspectAdapter extends RecyclerView.Adapter<SuspectHolder> {
        private List<String> mSuspects;

        public SuspectAdapter(List<String> suspects) {
            mSuspects = suspects;
        }

        @Override
        public SuspectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SuspectHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SuspectHolder holder, int position) {
            String suspect = mSuspects.get(position);
            holder.bind(suspect);
        }

        @Override
        public int getItemCount() {
            return mSuspects.size();
        }
    }
}
