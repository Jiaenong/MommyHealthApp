package com.example.mommyhealthapp.Mommy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mommyhealthapp.Class.Mommy;
import com.example.mommyhealthapp.Class.MommyDetail;
import com.example.mommyhealthapp.Nurse.AntenatalActivity;
import com.example.mommyhealthapp.Nurse.SectionNActivity;
import com.example.mommyhealthapp.R;
import com.example.mommyhealthapp.SaveSharedPreference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class PregnancyWeightGainActivity extends AppCompatActivity {
    private Spinner spinnerStage;
    private RadioGroup radioGroupTwins;
    private RadioButton radioBtnTwinsYes;
    private RadioButton radioBtnTwinsNo;
    private TextInputLayout txtInputLayoutHeightWG, txtInputLayoutWeightBeforePreg, txtInoutLayoutCurrentWeight;
    private EditText editTextHeightWG, editTextWeightBeforePreg, editTextCurrentWeight;
    private Button btnCalculateOkay, btnClearResult;
    private GraphView pregnancyWeightGainGraph;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference mCollectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_weight_gain);

        spinnerStage = (Spinner)findViewById(R.id.spinnerStage);
        radioGroupTwins = (RadioGroup) findViewById(R.id.radioGroupTwins);
        radioBtnTwinsYes = (RadioButton)findViewById(R.id.radioBtnTwinsYes);
        radioBtnTwinsNo = (RadioButton)findViewById(R.id.radioBtnTwinsNo);
        txtInputLayoutHeightWG = (TextInputLayout)findViewById(R.id.txtInputLayoutHeightWG);
        txtInputLayoutWeightBeforePreg = (TextInputLayout)findViewById(R.id.txtInputLayoutWeightBeforePreg);
        txtInoutLayoutCurrentWeight = (TextInputLayout)findViewById(R.id.txtInoutLayoutCurrentWeight);
        editTextHeightWG = (EditText)findViewById(R.id.editTextHeightWG);
        editTextWeightBeforePreg = (EditText)findViewById(R.id.editTextWeightBeforePreg);
        editTextCurrentWeight = (EditText)findViewById(R.id.editTextCurrentWeight);
        btnCalculateOkay = (Button)findViewById(R.id.btnCalculateOkay);
        btnClearResult = (Button)findViewById(R.id.btnClearResult);
        pregnancyWeightGainGraph = (GraphView)findViewById(R.id.pregnancyWeightGainGraph);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PregnancyWeightGainActivity.this, R.array.pregnant_week, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStage.setAdapter(adapter);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mCollectionReference = mFirebaseFirestore.collection("Mommy").document(SaveSharedPreference.getID(PregnancyWeightGainActivity.this)).collection("MommyDetail");
        mCollectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    MommyDetail mommyDetail = documentSnapshot.toObject(MommyDetail.class);
                    editTextHeightWG.setText(Double.toString(mommyDetail.getHeight()));
                    editTextWeightBeforePreg.setText(String.valueOf(mommyDetail.getWeight()));
                    //editTextWeightBeforePreg.setText(mommyDetail.getWeight()+"");
                    editTextHeightWG.setEnabled(false);
                    editTextWeightBeforePreg.setEnabled(false);
                }
            }
        });

        CheckRequiredFieldChange();

        btnCalculateOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckRequiredField() == true)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PregnancyWeightGainActivity.this);
                    builder.setTitle("Error");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    builder.setMessage("All field must be fill in");
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    boolean checkWeightGain = false;
                    int week = Integer.parseInt(spinnerStage.getSelectedItem().toString().substring(5));
                    int radioBtnId = radioGroupTwins.getCheckedRadioButtonId();
                    RadioButton radioButtonTwins = (RadioButton)findViewById(radioBtnId);
                    String pregnantTwins = radioButtonTwins.getText().toString();
                    double height = Double.parseDouble(editTextHeightWG.getText().toString());
                    double weightBeforePregnancy = Double.parseDouble(editTextWeightBeforePreg.getText().toString());
                    double weightBase = Double.parseDouble(editTextWeightBeforePreg.getText().toString());
                    double currentWeight = Double.parseDouble(editTextCurrentWeight.getText().toString());
                    double BMI = 0;
                    String category;

                    BMI = weightBeforePregnancy/(height*height/10000);
                    if (BMI<18.5)
                    {
                        category = "underweight";
                    }else if (BMI>=18.5 && BMI<24.9)
                    {
                        category = "normalweight";
                    }else if (BMI>=25 && BMI<29.9)
                    {
                        category = "overweight";
                    }else
                    {
                        category = "obese";
                    }

                    pregnancyWeightGainGraph.removeAllSeries();
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                    if (category.equals("underweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.58;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.93;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }
                        }
                    }else if (category.equals("normalweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.5;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.79;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }
                        }
                    }else if (category.equals("overweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.33;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.66;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }
                        }
                    }else
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.25;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBeforePregnancy += (2.0/12);
                                }else{
                                    weightBeforePregnancy += 0.61;
                                }
                                if(week == i && currentWeight > weightBeforePregnancy)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBeforePregnancy);
                                series.appendData(point, true, 40);
                            }
                        }
                    }
                    pregnancyWeightGainGraph.addSeries(series);
                    series.setTitle("Max");
                    series.setColor(Color.BLUE);
                    LineGraphSeries<DataPoint> seriesBase = new LineGraphSeries<>();
                    if (category.equals("underweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.44;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.79;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }
                        }
                    }else if (category.equals("normalweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.39;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.58;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }
                        }
                    }else if (category.equals("overweight"))
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.23;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.48;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }
                        }
                    }else
                    {
                        for(int i=1; i<=40; i++)
                        {
                            if(pregnantTwins.equals("No"))
                            {
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.16;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }else{
                                if(i<=12)
                                {
                                    weightBase += (0.5/12);
                                }else{
                                    weightBase += 0.39;
                                }
                                if(week == i && currentWeight < weightBase)
                                {
                                    checkWeightGain = true;
                                }
                                DataPoint point = new DataPoint(i, weightBase);
                                seriesBase.appendData(point, true, 40);
                            }
                        }
                    }
                    pregnancyWeightGainGraph.addSeries(seriesBase);
                    seriesBase.setTitle("Min");
                    seriesBase.setColor(Color.DKGRAY);

                    PointsGraphSeries<DataPoint> seriesPoint = new PointsGraphSeries<>(new DataPoint[]{
                            new DataPoint(week, currentWeight)
                    });
                    pregnancyWeightGainGraph.addSeries(seriesPoint);
                    seriesPoint.setShape(PointsGraphSeries.Shape.POINT);
                    if(checkWeightGain == true)
                    {
                        seriesPoint.setTitle("Bad");
                        seriesPoint.setColor(Color.RED);
                    }else{
                        seriesPoint.setTitle("Good");
                        seriesPoint.setColor(Color.GREEN);
                    }

                    pregnancyWeightGainGraph.getLegendRenderer().setVisible(true);
                    pregnancyWeightGainGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                    pregnancyWeightGainGraph.getViewport().setScalable(true);
                    pregnancyWeightGainGraph.getViewport().setScalableY(true);
                }
            }
        });

        btnClearResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerStage.setSelection(0);
                editTextCurrentWeight.setText("");
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void CheckRequiredFieldChange()
    {
        editTextHeightWG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextHeightWG.getText().toString().equals(""))
                {
                    txtInputLayoutHeightWG.setErrorEnabled(true);
                    txtInputLayoutHeightWG.setError("This field is required");
                }else{
                    txtInputLayoutHeightWG.setErrorEnabled(false);
                    txtInputLayoutHeightWG.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextWeightBeforePreg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextWeightBeforePreg.getText().toString().equals(""))
                {
                    txtInputLayoutWeightBeforePreg.setErrorEnabled(true);
                    txtInputLayoutWeightBeforePreg.setError("This field is required");
                }else{
                    txtInputLayoutWeightBeforePreg.setErrorEnabled(false);
                    txtInputLayoutWeightBeforePreg.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextCurrentWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editTextCurrentWeight.getText().toString().equals(""))
                {
                    txtInoutLayoutCurrentWeight.setErrorEnabled(true);
                    txtInoutLayoutCurrentWeight.setError("This field is required");
                }else{
                    txtInoutLayoutCurrentWeight.setErrorEnabled(false);
                    txtInoutLayoutCurrentWeight.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroupTwins.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioGroupTwins.getCheckedRadioButtonId() == -1)
                {
                    radioBtnTwinsYes.setError("Required!");
                    radioBtnTwinsNo.setError("Required!");
                }else{
                    radioBtnTwinsYes.setError(null);
                    radioBtnTwinsNo.setError(null);
                }
            }
        });
    }

    private boolean CheckRequiredField()
    {
        String height = editTextHeightWG.getText().toString();
        String previousWeight = editTextWeightBeforePreg.getText().toString();
        String currentWeight = editTextCurrentWeight.getText().toString();
        String stage = spinnerStage.getSelectedItem().toString();

        if(height.equals("") || previousWeight.equals("") || currentWeight.equals("") || stage.equals("") || radioGroupTwins.getCheckedRadioButtonId() == -1)
        {
            if(height.equals(""))
            {
                txtInputLayoutHeightWG.setErrorEnabled(true);
                txtInputLayoutHeightWG.setError("This field is required");
            }else{
                txtInputLayoutHeightWG.setErrorEnabled(false);
                txtInputLayoutHeightWG.setError(null);
            }

            if(previousWeight.equals(""))
            {
                txtInputLayoutWeightBeforePreg.setErrorEnabled(true);
                txtInputLayoutWeightBeforePreg.setError("This field is required");
            }else{
                txtInputLayoutWeightBeforePreg.setErrorEnabled(false);
                txtInputLayoutWeightBeforePreg.setError(null);
            }

            if(currentWeight.equals(""))
            {
                txtInoutLayoutCurrentWeight.setErrorEnabled(true);
                txtInoutLayoutCurrentWeight.setError("This field is required");
            }else{
                txtInoutLayoutCurrentWeight.setErrorEnabled(false);
                txtInoutLayoutCurrentWeight.setError(null);
            }

            if(radioGroupTwins.getCheckedRadioButtonId() == -1)
            {
                radioBtnTwinsYes.setError("Required!");
                radioBtnTwinsNo.setError("Required!");
            }else{
                radioBtnTwinsYes.setError(null);
                radioBtnTwinsNo.setError(null);
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nurse_home, menu);
        return true;
    }
}
