package me.iamcrk.medinfo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView textName, textDescription, textUses, textSideEffects;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        textUses = findViewById(R.id.textUses);
        textSideEffects = findViewById(R.id.textSideEffects);
        btnEdit = findViewById(R.id.btnEdit);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String uses = getIntent().getStringExtra("uses");
        String sideEffects = getIntent().getStringExtra("sideEffects");

        textName.setText(name);
        textDescription.setText(description);
        textUses.setText(uses);
        textSideEffects.setText(sideEffects);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, AddEditActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("description", description);
            intent.putExtra("uses", uses);
            intent.putExtra("sideEffects", sideEffects);
            startActivity(intent);
        });
    }
}
