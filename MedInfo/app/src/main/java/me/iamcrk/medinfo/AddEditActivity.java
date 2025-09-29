package me.iamcrk.medinfo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEditActivity extends AppCompatActivity {

    private EditText editName, editDescription, editUses, editSideEffects;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        editName = findViewById(R.id.editName);
        editDescription = findViewById(R.id.editDescription);
        editUses = findViewById(R.id.editUses);
        editSideEffects = findViewById(R.id.editSideEffects);
        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        // If editing
        if (getIntent().hasExtra("name")) {
            editName.setText(getIntent().getStringExtra("name"));
            editDescription.setText(getIntent().getStringExtra("description"));
            editUses.setText(getIntent().getStringExtra("uses"));
            editSideEffects.setText(getIntent().getStringExtra("sideEffects"));
        }

        btnSave.setOnClickListener(v -> saveMedicine());
    }

    private void saveMedicine() {
        String name = editName.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String uses = editUses.getText().toString().trim();
        String sideEffects = editSideEffects.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> medicine = new HashMap<>();
        medicine.put("name", name);
        medicine.put("searchName", name.toLowerCase());
        medicine.put("description", description);
        medicine.put("uses", uses);
        medicine.put("sideEffects", sideEffects);

        db.collection("medicines").document(name.toLowerCase())
                .set(medicine)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
