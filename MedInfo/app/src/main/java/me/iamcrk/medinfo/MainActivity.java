package me.iamcrk.medinfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
        String searchText = name.toLowerCase(); // lowercase for consistent search


        // Get all medicines and filter client-side
        db.collection("medicines")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        StringBuilder resultBuilder = new StringBuilder();
                        boolean found = false;

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String medSearchName = document.getString("searchName");
                            Log.d("DEBUG_FIRESTORE", "Document searchName: " + medSearchName + ", searching for: " + searchText);
                        }


                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String medSearchName = document.getString("searchName");
                            if (medSearchName != null && medSearchName.contains(searchText)) {
                                found = true;
                                String medNameValue = document.getString("name");
                                String description = document.getString("description");
                                String uses = document.getString("uses");
                                String sideEffects = document.getString("sideEffects");

                                resultBuilder.append("Name: ").append(medNameValue).append("\n\n")
                                        .append("Description: ").append(description).append("\n\n")
                                        .append("Uses: ").append(uses).append("\n\n")
                                        .append("Side Effects: ").append(sideEffects)
                                        .append("\n\n------------------\n\n");
                            }
                        }

                        if (found) {
                            textViewResult.setText(resultBuilder.toString());
                        } else {
                            textViewResult.setText("Medicine not found.");
                        }
                    } else {
                        textViewResult.setText("Medicine not found.");
                    }
                })
                .addOnFailureListener(e -> textViewResult.setText("Error: " + e.getMessage()));
    }
}
