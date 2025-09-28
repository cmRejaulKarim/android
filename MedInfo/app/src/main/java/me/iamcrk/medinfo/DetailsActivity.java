package me.iamcrk.medinfo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    private TextView textViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textViewDetails = findViewById(R.id.textViewDetails);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String uses = getIntent().getStringExtra("uses");
        String sideEffects = getIntent().getStringExtra("sideEffects");

        String details = "Name: " + name + "\n\n" +
                "Description: " + description + "\n\n" +
                "Uses: " + uses + "\n\n" +
                "Side Effects: " + sideEffects;

        textViewDetails.setText(details);
    }
}
