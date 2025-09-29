package me.iamcrk.medinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList = new ArrayList<>();
    private FirebaseFirestore db;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(this, medicineList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Floating button → go to Add screen
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        // Search listener
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (keyword.isEmpty()) {
                    loadAllMedicines();  // show all when search box empty
                } else {
                    searchMedicines(keyword);
                }
            }
        });

        // Load all initially
        loadAllMedicines();
    }

    private void loadAllMedicines() {
        db.collection("medicines")
                .orderBy("searchName", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    medicineList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Medicine med = doc.toObject(Medicine.class);
                        if (med != null) medicineList.add(med);
                    }
                    adapter.updateList(medicineList);
                });
    }

    private void searchMedicines(String keyword) {
        db.collection("medicines")
                .orderBy("searchName", Query.Direction.ASCENDING)
                .startAt(keyword.toLowerCase())
                .endAt(keyword.toLowerCase() + "\uf8ff")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    medicineList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Medicine med = doc.toObject(Medicine.class);
                        if (med != null) medicineList.add(med);
                    }
                    adapter.updateList(medicineList);
                });
    }
}
