package com.example.mommyhealthapp.Nurse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mommyhealthapp.Class.ProblemPE;
import com.example.mommyhealthapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PregnancyExaminationActivity extends AppCompatActivity {

    private EditText editTextDueDate, editTextProblem, editTextTreatment, bodyWeightEditText, bloodPressureEditText, pulseEditText;
    DatePickerDialog datePickerDialog;
    private TextInputLayout bodyWeightInputLayout, bloodPressureInputLayout, pulseInputLayout;
    private FloatingActionButton btnAddProblem;
    private Button btnProblemSave, btnProblemEdit, btnSavePE;
    private ListView listViewProblem;
    private ProblemAdapter problemAdapter;
    private List<ProblemPE> problemList;
    private Dialog problemDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy_examination);

        editTextDueDate = (EditText)findViewById(R.id.editTextDueDate);
        bodyWeightEditText = (EditText)findViewById(R.id.bodyWeightEditText);
        bloodPressureEditText = (EditText)findViewById(R.id.bloodPressureEditText);
        pulseEditText = (EditText)findViewById(R.id.pulseEditText);
        btnAddProblem = (FloatingActionButton)findViewById(R.id.btnAddProblem);
        listViewProblem = (ListView)findViewById(R.id.listViewProblem);
        bodyWeightInputLayout = (TextInputLayout)findViewById(R.id.bodyWeightInputLayout);
        bloodPressureInputLayout = (TextInputLayout)findViewById(R.id.bloodPressureInputLayout);
        pulseInputLayout = (TextInputLayout)findViewById(R.id.pulseInputLayout);
        btnSavePE = (Button)findViewById(R.id.btnSavePE);

        listViewProblem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        listViewProblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProblemPE problemPE = problemAdapter.getItem(position);

                problemDialog = new Dialog(PregnancyExaminationActivity.this);
                problemDialog.setContentView(R.layout.customize_problem_dialog);
                problemDialog.setTitle("Problem Dialog");
                editTextProblem = (EditText)problemDialog.findViewById(R.id.editTextProblem);
                editTextTreatment = (EditText)problemDialog.findViewById(R.id.editTextTreatment);
                btnProblemSave = (Button)problemDialog.findViewById(R.id.btnProblemSave);
                btnProblemEdit = (Button)problemDialog.findViewById(R.id.btnProblemEdit);

                btnProblemSave.setVisibility(View.GONE);
                editTextProblem.setText(problemPE.getProblem());
                editTextTreatment.setText(problemPE.getTreatment());

                btnProblemEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i=0; i<problemList.size(); i++)
                        {
                            if(problemList.get(i).equals(problemPE))
                            {
                                String problem = editTextProblem.getText().toString();
                                String treatment = editTextTreatment.getText().toString();
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                Date date = new Date();
                                String pDate = formatter.format(date);
                                problemList.get(i).setProblem(problem);
                                problemList.get(i).setTreatment(treatment);
                                problemList.get(i).setDate(pDate);
                                problemList.get(i).setPersonnel("Alicia OOng");
                                problemAdapter.notifyDataSetChanged();
                            }
                        }
                        problemDialog.cancel();
                    }
                });

                problemDialog.show();
                Window window =problemDialog.getWindow();
                window.setLayout(800, 800);

            }
        });

        problemList = new ArrayList<>();

        editTextDueDate.setFocusable(false);
        editTextDueDate.setClickable(false);
        editTextDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(PregnancyExaminationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                editTextDueDate.setText(dayOfMonth + "/" +(month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnAddProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problemDialog = new Dialog(PregnancyExaminationActivity.this);
                problemDialog.setContentView(R.layout.customize_problem_dialog);
                problemDialog.setTitle("Problem Dialog");
                editTextProblem = (EditText)problemDialog.findViewById(R.id.editTextProblem);
                editTextTreatment = (EditText)problemDialog.findViewById(R.id.editTextTreatment);
                btnProblemSave = (Button)problemDialog.findViewById(R.id.btnProblemSave);
                btnProblemEdit = (Button)problemDialog.findViewById(R.id.btnProblemEdit);

                btnProblemEdit.setVisibility(View.GONE);

                btnProblemSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String problem = editTextProblem.getText().toString();
                        String treatment = editTextTreatment.getText().toString();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String pDate = formatter.format(date);
                        ProblemPE ppe = new ProblemPE(problem, treatment, pDate, "Alicia Ong");
                        problemList.add(ppe);
                        problemAdapter = new ProblemAdapter(PregnancyExaminationActivity.this, R.layout.activity_pregnancy_examination, problemList);
                        listViewProblem.setAdapter(problemAdapter);
                        problemDialog.cancel();
                    }
                });

                problemDialog.show();
                Window window =problemDialog.getWindow();
                window.setLayout(800, 800);
            }
        });

        btnSavePE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bodyWeight = bodyWeightEditText.getText().toString();
                String bloodPressure = bloodPressureEditText.getText().toString();
                String pulse = pulseEditText.getText().toString();
                if(bodyWeight == "")
                {
                    bodyWeightInputLayout.setError("This field is required!");
                }else if(bloodPressure == "")
                {
                    bloodPressureInputLayout.setError("This field is required!");
                }else if(pulse == "")
                {
                    pulseInputLayout.setError("This field is required!");
                }else if(bodyWeight == "" && bloodPressure == "" && pulse == "")
                {
                    bodyWeightInputLayout.setError("This field is required!");
                    bloodPressureInputLayout.setError("This field is required!");
                    pulseInputLayout.setError("This field is required!");
                }else{
                    bodyWeightInputLayout.setError(null);
                    bloodPressureInputLayout.setError(null);
                    pulseInputLayout.setError(null);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

class ProblemAdapter extends ArrayAdapter<ProblemPE>{
    private final List<ProblemPE> list;
    private Activity context;

    public ProblemAdapter(Activity context, int resource, List<ProblemPE> list)
    {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.problem_listview, parent, false);

        TextView textProblemDate = (TextView)view.findViewById(R.id.textProblemDate);
        TextView textProblemPersonnel = (TextView)view.findViewById(R.id.textProblemPersonnel);

        ProblemPE problemPE = getItem(position);
        textProblemDate.setText(problemPE.getDate());
        textProblemPersonnel.setText(problemPE.getPersonnel());

        return view;
    }
}
