package com.example.mommyhealthapp.Mommy.ui.PregnancyRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mommyhealthapp.R;

public class PregnancyRecordFragment extends Fragment {

    private PregnancyRecordViewModel dashboardViewModel;
    private CardView earlyTest, mgtt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //dashboardViewModel =
        //        ViewModelProviders.of(this).get(PregnancyRecordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pregnancy_record, container, false);
        //dashboardViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
         //   public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //   }
       // });
        return root;
    }
}