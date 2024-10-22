package com.example.attendencemaganer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddSubject extends AppCompatActivity {

    EditText subjectName, totalHours;
    Button add;

    DBHelper dbhelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_subject);

        subjectName = findViewById(R.id.subjectName);
        totalHours = findViewById(R.id.totalHours);
        add = findViewById(R.id.add);
        dbhelper= new DBHelper(getApplicationContext());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addSubject(View view) {

        String subName = subjectName.getText().toString();

        int hrs = Integer.parseInt(totalHours.getText().toString());
        dbhelper.addSubject(subName,hrs);
        Toast.makeText(this, "Subject Added"+subName, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }
}