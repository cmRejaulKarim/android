package me.iamcrk.myfirstapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Subject extends AppCompatActivity {

    RecyclerView recyclerView;
    CourseAdapter adapter;
    List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_subject);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate data
        courseList = new ArrayList<>();
        courseList.add(new Course("Web and Mobile App Development using Spring Boot, Android & Flutter", 788, 8.5));
        courseList.add(new Course("Web Application Development with Laravel, React, Vue.js & WordPress", 788, 8.5));
        courseList.add(new Course("Cross Platform Apps using ASP.NET, Angular & React", 788, 8.5));
        courseList.add(new Course("Oracle Database Application Development", 788, 8.5));
        courseList.add(new Course("Network Solutions and System Administration", 788, 8.5));
        courseList.add(new Course("Graphics, Animation and Video Editing", 788, 8.5));

        adapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(adapter);
    }
}