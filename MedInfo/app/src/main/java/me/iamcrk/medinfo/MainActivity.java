package me.iamcrk.medinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(v -> {
            // Navigate to Add/Edit activity
            startActivity(new Intent(MainActivity.this, AddEditActivity.class));
        });

        // Optional: add text change listener to trigger search later
        // editTextSearch.addTextChangedListener(...);
    }
}
