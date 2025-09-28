package me.iamcrk.medinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private MedicineAdapter adapter;
    private List<Medicine> medicineList = new ArrayList<>();
    private List<Medicine> displayedList = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);  // Add a ProgressBar in your layout

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicineAdapter(displayedList, this::showMedicineDetails);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadMedicines();

        buttonSearch.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString().trim();
            filterMedicines(query);
        });
    }

    private void loadMedicines() {
        progressBar.setVisibility(View.VISIBLE);

        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (isFinishing() || isDestroyed()) return; // avoid updating UI if activity is finishing

                    medicineList.clear();

                    for (var doc : queryDocumentSnapshots) {
                        Medicine med = doc.toObject(Medicine.class);
                        if (med != null) {  // null safety
                            medicineList.add(med);
                        }
                    }

                    // Sort alphabetically ignoring case
                    Collections.sort(medicineList, Comparator.comparing(Medicine::getName, String.CASE_INSENSITIVE_ORDER));

                    displayedList.clear();
                    displayedList.addAll(medicineList);
                    adapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    if (isFinishing() || isDestroyed()) return;

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error loading medicines: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void filterMedicines(String query) {
        displayedList.clear();

        if (query.isEmpty()) {
            displayedList.addAll(medicineList);
        } else {
            query = query.toLowerCase();

            for (Medicine med : medicineList) {
                if (med.getSearchName() != null && med.getSearchName().toLowerCase().contains(query)) {
                    displayedList.add(med);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void showMedicineDetails(Medicine medicine) {
        String message = "Name: " + medicine.getName() + "\n\n"
                + "Description: " + medicine.getDescription() + "\n\n"
                + "Uses: " + medicine.getUses() + "\n\n"
                + "Side Effects: " + medicine.getSideEffects();

        new AlertDialog.Builder(this)
                .setTitle("Medicine Details")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
