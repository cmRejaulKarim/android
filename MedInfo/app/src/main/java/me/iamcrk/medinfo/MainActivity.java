package me.iamcrk.medinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextSearch;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList = new ArrayList<>();
    private FirebaseFirestore db;
    private FloatingActionButton fabAdd;
    private ChipGroup chipGroupFilters;
    private String selectedFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        chipGroupFilters = findViewById(R.id.chipGroupFilters);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(this, medicineList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Load filters dynamically from medicine uses
        setupFilters();

        // Search listener
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchMedicines(s.toString().trim(), selectedFilter);
            }
        });

        // Floating Add button
        fabAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEditActivity.class)));

        // Initially load all medicines
        searchMedicines("", null);
    }

    private void setupFilters() {
        db.collection("medicines").get().addOnSuccessListener(queryDocumentSnapshots -> {
            Set<String> keywords = new HashSet<>();
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Medicine med = doc.toObject(Medicine.class);
                if (med != null && med.getUses() != null) {
                    String[] words = med.getUses().split(",\\s*"); // Split by comma
                    keywords.addAll(Arrays.asList(words));
                }
            }

            // Add chips dynamically
            for (String keyword : keywords) {
                Chip chip = new Chip(this);
                chip.setText(keyword);
                chip.setCheckable(true);
                chip.setOnClickListener(v -> {
                    if (chip.isChecked()) {
                        selectedFilter = keyword;
                    } else {
                        selectedFilter = null;
                    }
                    searchMedicines(editTextSearch.getText().toString().trim(), selectedFilter);
                });
                chipGroupFilters.addView(chip);
            }
        });
    }

    private void searchMedicines(String keyword, String filter) {
        db.collection("medicines")
                .orderBy("name")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    medicineList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Medicine med = doc.toObject(Medicine.class);
                        if (med != null &&
                                (keyword.isEmpty()
                                        || med.getName().toLowerCase().contains(keyword.toLowerCase())
                                        || (med.getUses() != null && med.getUses().toLowerCase().contains(keyword.toLowerCase()))
                                        || (med.getDescription() != null && med.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                                && (filter == null || (med.getUses() != null && med.getUses().toLowerCase().contains(filter.toLowerCase())))) {
                            medicineList.add(med);
                        }
                    }
                    adapter.updateList(medicineList);
                });
    }
}
