package me.iamcrk.medinfo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
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
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList = new ArrayList<>();

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicineAdapter(medicineList, medicine -> showMedicineDetails(medicine));
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadMedicines();

        // Search filter
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMedicines(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMedicines() {
        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    medicineList.clear();
                    for (var doc : queryDocumentSnapshots) {
                        Medicine med = doc.toObject(Medicine.class);
                        medicineList.add(med);
                    }
                    // Sort alphabetically
                    Collections.sort(medicineList, Comparator.comparing(Medicine::getName, String.CASE_INSENSITIVE_ORDER));
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void filterMedicines(String query) {
        query = query.toLowerCase();
        List<Medicine> filtered = new ArrayList<>();
        for (Medicine med : medicineList) {
            if (med.getSearchName() != null && med.getSearchName().contains(query)) {
                filtered.add(med);
            }
        }
        adapter = new MedicineAdapter(filtered, this::showMedicineDetails);
        recyclerView.setAdapter(adapter);
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
