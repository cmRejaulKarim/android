package me.iamcrk.medinfo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
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
        adapter = new MedicineAdapter(this, medicineList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadMedicines();

        // 🔹 Search listener
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMedicines(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMedicines() {
        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    medicineList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Medicine medicine = doc.toObject(Medicine.class);
                        medicineList.add(medicine);
                    }
                    // Sort alphabetically
                    Collections.sort(medicineList, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
                    adapter.updateList(medicineList);
                })
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error loading medicines", e)
                );
    }

    // 🔹 Filter results live
    private void filterMedicines(String query) {
        List<Medicine> filtered = new ArrayList<>();
        for (Medicine m : medicineList) {
            if (m.getName() != null && m.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(m);
            }
        }
        adapter.updateList(filtered);
    }
}
