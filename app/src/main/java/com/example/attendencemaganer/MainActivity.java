package com.example.attendencemaganer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Struct;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button bt;
    DBHelper dbHelper;
    ListView subjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bt = findViewById(R.id.bt);
        subjects = findViewById(R.id.subjects);
        dbHelper = new DBHelper(getApplicationContext());

        ArrayList<Integer> noOfClassesAttended = dbHelper.getAttendedHelper();
        ArrayList<Integer> noOfClassesConducted = dbHelper.getConductedHelper();
        ArrayList<String> s1 = dbHelper.getSubjectNameHelper();
        ArrayList<Integer> totalHours = dbHelper.getTotalHoursHelper();
        

        int[] youCanBunk = new int[totalHours.size()];

        if(s1.size()>0)
        {
            float percent[] = new float[s1.size()];
            for(int i=0;i<s1.size();i++)
            {
                try {
                    if (noOfClassesConducted.get(i) != 0) {
                        percent[i] = ((float) noOfClassesAttended.get(i) / noOfClassesConducted.get(i)) * 100;
                    } else {
                        percent[i] = 0;
                    }
                } catch (Exception e) {
                    percent[i] = 0;
                }
                youCanBunk[i]= ((totalHours.get(i)*20)/100) - (noOfClassesConducted.get(i) - noOfClassesAttended.get(i));
            }
            Toast.makeText(this, ""+youCanBunk[0], Toast.LENGTH_SHORT).show();


            CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),s1,percent,youCanBunk);
            subjects.setAdapter(customBaseAdapter);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddSubject.class);
                startActivity(intent);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "resumed", Toast.LENGTH_SHORT).show();
        refreshSubjectList();
    }

    private void refreshSubjectList() {
        ArrayList<String> s1 = dbHelper.getSubjectNameHelper();
        ArrayList<Integer> noOfClassesAttended = dbHelper.getAttendedHelper();
        ArrayList<Integer> noOfClassesConducted = dbHelper.getConductedHelper();
        ArrayList<Integer> totalHours = dbHelper.getTotalHoursHelper();

        int[] youCanBunk = new int[totalHours.size()];

        if (s1.isEmpty() || noOfClassesAttended.isEmpty() || noOfClassesConducted.isEmpty()) {
            Toast.makeText(this, "No subjects found. Please add a subject.", Toast.LENGTH_SHORT).show();
        } else {
            float percent[] = new float[s1.size()];
            for (int i = 0; i < s1.size(); i++) {
                try {
                    if (noOfClassesConducted.get(i) != 0) {
                        percent[i] = ((float) noOfClassesAttended.get(i) / noOfClassesConducted.get(i)) * 100;
                    } else {
                        percent[i] = 0;
                    }
                } catch (Exception e) {
                    percent[i] = 0;
                }
                youCanBunk[i]= (totalHours.get(i)*20/100) - (noOfClassesConducted.get(i) - noOfClassesAttended.get(i));

            }

            // Create a new adapter with updated data and set it again
            CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(), s1, percent,youCanBunk);
            subjects.setAdapter(customBaseAdapter);
        }
    }

}