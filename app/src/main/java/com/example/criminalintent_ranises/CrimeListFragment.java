package com.example.criminalintent_ranises;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import java.util.UUID;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private TextView mEmptyText;
    private FloatingActionButton mAddCrimeFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mEmptyText = (TextView) view.findViewById(R.id.empty_text);
        mAddCrimeFab = (FloatingActionButton) view.findViewById(R.id.new_crime_fab);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        // Set up FAB click listener
        mAddCrimeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Crime crime = new Crime();
                    CrimeLab.get(getActivity()).addCrime(crime);
                    Intent intent = CrimeActivity.newIntent(getActivity(), crime.getId());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Try opening without crime ID as fallback
                    try {
                        Intent intent = new Intent(getActivity(), CrimeActivity.class);
                        startActivity(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        // Show error message to user
                        if (getActivity() != null) {
                            android.widget.Toast.makeText(getActivity(), "Error opening crime page", android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        
        updateUI();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        
        if (crimes.isEmpty()) {
            mEmptyText.setVisibility(View.VISIBLE);
            mCrimeRecyclerView.setVisibility(View.GONE);
        } else {
            mEmptyText.setVisibility(View.GONE);
            mCrimeRecyclerView.setVisibility(View.VISIBLE);
            
            if (mAdapter == null) {
                mAdapter = new CrimeAdapter(crimes);
                mCrimeRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved_image);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            
            // Show handcuffs icon only if crime is solved
            if (mCrime.isSolved()) {
                mSolvedImageView.setVisibility(View.VISIBLE);
                mSolvedImageView.setImageResource(R.drawable.ic_solved_handcuff);
            } else {
                mSolvedImageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class PoliceCrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Button mContactPoliceButton;
        private Crime mCrime;

        public PoliceCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime_police, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved_image);
            mContactPoliceButton = (Button) itemView.findViewById(R.id.contact_police_button);
            
            mContactPoliceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactPolice();
                }
            });
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            
            // Show handcuffs icon only if crime is solved
            if (mCrime.isSolved()) {
                mSolvedImageView.setVisibility(View.VISIBLE);
                mSolvedImageView.setImageResource(R.drawable.ic_solved_handcuff);
            } else {
                mSolvedImageView.setVisibility(View.GONE);
            }
        }

        private void contactPolice() {
            if (getActivity() != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:911"));
                startActivity(intent);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int NORMAL_CRIME = 0;
        private static final int POLICE_CRIME = 1;
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);
            return crime.isRequiresPolice() ? POLICE_CRIME : NORMAL_CRIME;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == POLICE_CRIME) {
                return new PoliceCrimeHolder(layoutInflater, parent);
            } else {
                return new CrimeHolder(layoutInflater, parent);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            if (holder instanceof PoliceCrimeHolder) {
                ((PoliceCrimeHolder) holder).bind(crime);
            } else {
                ((CrimeHolder) holder).bind(crime);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
