package me.iamcrk.medinfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMedicine;
    private Button buttonSearch;
    private TextView textViewResult;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMedicine = findViewById(R.id.editTextMedicine);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewResult = findViewById(R.id.textViewResult);

        db = FirebaseFirestore.getInstance();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medName = editTextMedicine.getText().toString().trim();

                if (TextUtils.isEmpty(medName)) {
                    Toast.makeText(MainActivity.this, "Enter a medicine name", Toast.LENGTH_SHORT).show();
                    return;
                }

                searchMedicine(medName);
            }
        });
    }

    private void searchMedicine(String name) {
        db.collection("medicines")
                .whereEqualTo("name", name) // search by field instead of doc ID
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        String medName = documentSnapshot.getString("name");
                        String description = documentSnapshot.getString("description");
                        String uses = documentSnapshot.getString("uses");
                        String sideEffects = documentSnapshot.getString("sideEffects");

                        String result = "Name: " + medName + "\n\n" +
                                "Description: " + description + "\n\n" +
                                "Uses: " + uses + "\n\n" +
                                "Side Effects: " + sideEffects;

                        textViewResult.setText(result);
                    } else {
                        textViewResult.setText("Medicine not found.");
                    }
                })
                .addOnFailureListener(e -> textViewResult.setText("Error: " + e.getMessage()));
    }


//    private void searchMedicine(String name) {
//        db.collection("medicines").document(name)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        String medName = documentSnapshot.getString("name");
//                        String description = documentSnapshot.getString("description");
//                        String uses = documentSnapshot.getString("uses");
//                        String sideEffects = documentSnapshot.getString("sideEffects");
//
//                        String result = "Name: " + medName + "\n\n" +
//                                "Description: " + description + "\n\n" +
//                                "Uses: " + uses + "\n\n" +
//                                "Side Effects: " + sideEffects;
//
//                        textViewResult.setText(result);
//                    } else {
//                        textViewResult.setText("Medicine not found.");
//                    }
//                })
//                .addOnFailureListener(e -> textViewResult.setText("Error: " + e.getMessage()));
//    }
}
