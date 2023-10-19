package com.example.studentmgr;

import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ActivityStudent extends AppCompatActivity {

    private EditText editName, editNumber;
    private RadioButton radioMale, radioFemale;
    private Spinner spinnerFaculty, spinnerMajor;
    private CheckBox checkBoxLiterature, checkBoxSports, checkBoxMusic, checkBoxArt;
    private Button btnSubmit;
    private DatePicker datePicker;

    public void showToast(String message, int duration) {
        // Inflate the custom layout/view
        LayoutInflater inflater = getLayoutInflater();
        View customToastRoot = inflater.inflate(R.layout.custom_toast, null);

        Toast customToast = new Toast(this); // 使用 this 替代 context

        // Set the text of the custom Toast
        TextView textView = (TextView) customToastRoot.findViewById(R.id.toast_text);
        textView.setText(message);

        // Set the image of the custom Toast
        ImageView imageView = (ImageView) customToastRoot.findViewById(R.id.toast_icon);
        imageView.setImageResource(R.drawable.ads01);

        customToast.setView(customToastRoot);
        customToast.setDuration(duration);
        customToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Initialize views
        editName = findViewById(R.id.editName);
        editNumber = findViewById(R.id.editNumber);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        spinnerFaculty = findViewById(R.id.spinnerFaculty);
        spinnerMajor = findViewById(R.id.spinnerMajor);
        checkBoxLiterature = findViewById(R.id.checkBoxLiterature);
        checkBoxSports = findViewById(R.id.checkBoxSports);
        checkBoxMusic = findViewById(R.id.checkBoxMusic);
        checkBoxArt = findViewById(R.id.checkBoxArt);
        btnSubmit = findViewById(R.id.btnSubmit);
        datePicker = findViewById(R.id.datepicker);

        // Set adapters for spinners
        ArrayAdapter<CharSequence> facultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.faculty_array, android.R.layout.simple_spinner_item);
        facultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFaculty.setAdapter(facultyAdapter);

        spinnerFaculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> majorAdapter;
                switch (position) {
                    case 0: // Computer Science
                        majorAdapter = ArrayAdapter.createFromResource(ActivityStudent.this,
                                R.array.major_array_CS, android.R.layout.simple_spinner_item);
                        break;
                    case 1: // Electrical Engineering
                        majorAdapter = ArrayAdapter.createFromResource(ActivityStudent.this,
                                R.array.major_array_EE, android.R.layout.simple_spinner_item);
                        break;
                    // TODO: Add more cases if there are more faculties
                    default:
                        majorAdapter = ArrayAdapter.createFromResource(ActivityStudent.this,
                                R.array.major_array_CS, android.R.layout.simple_spinner_item);
                }
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMajor.setAdapter(majorAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                showToast("提交成功", Toast.LENGTH_SHORT);

                // Collect data from views
                String name = editName.getText().toString();
                String number = editNumber.getText().toString();
                String gender = radioMale.isChecked() ? "Male" : "Female";
                String faculty = spinnerFaculty.getSelectedItem().toString();
                String major = spinnerMajor.getSelectedItem().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                String date = String.format("%d-%d-%d", year, month+1, day);


                StringBuilder hobbies = new StringBuilder();
                if (checkBoxLiterature.isChecked()) hobbies.append("Literature, ");
                if (checkBoxSports.isChecked()) hobbies.append("Sports, ");
                if (checkBoxMusic.isChecked()) hobbies.append("Music, ");
                if (checkBoxArt.isChecked()) hobbies.append("Art, ");
                String hobbiesStr = hobbies.toString();

                String studentInfo = String.format("Name: %s, Number: %s, Gender: %s, Faculty: %s, Major: %s, Hobbies: %s",
                        name, number, gender, faculty, major, hobbiesStr);
                studentInfo += " birthday:" + date;

                // Add data to intent
                intent.putExtra("studentInfo", studentInfo);
                intent.putExtra("position", getIntent().getIntExtra("position", -1));

                // Set result
                setResult(RESULT_OK, intent);

                // Finish ActivityStudent
                finish();
            }
        });

    }
}
