package id.akarudev.latihanone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailLuasActivity extends AppCompatActivity {
    Spinner spLuas;
    EditText edInputLuas;
    TextView txtHasilLuas;
    Button tbHitungLuas;
    float hasilKonversi = 0;
    int posKonversi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_luas);

        edInputLuas = findViewById(R.id.edInputLuas);
        txtHasilLuas = findViewById(R.id.txtHasilLuas);
        spLuas = findViewById(R.id.spLuas);
        tbHitungLuas = findViewById(R.id.tbHitungLuas);

        String[] dataLuas = new String[] {
                "m² to cm²", "m² to mm²", "m² to ha", "m² to km²",
                "cm² to m²", "cm² to mm²", "cm² to ha", "cm² to km²",
                "mm² to m²", "mm² to cm²", "mm² to ha", "mm² to km²",
                "ha to m²", "ha to cm²", "ha to mm²", "ha to km²",
                "km² to m²", "km² to cm²", "km² to mm²", "km² to ha"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataLuas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLuas.setAdapter(adapter);

        spLuas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posKonversi = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tbHitungLuas.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String input = edInputLuas.getText().toString();
                if (input.isEmpty()) {
                    txtHasilLuas.setText("0");
                    return;
                }
                float value = Float.parseFloat(input);
                switch (posKonversi) {
                    case 0: hasilKonversi = value * 10000; break; // m² to cm²
                    case 1: hasilKonversi = value * 1_000_000; break; // m² to mm²
                    case 2: hasilKonversi = value / 10_000; break; // m² to ha
                    case 3: hasilKonversi = value / 1_000_000; break; // m² to km²

                    case 4: hasilKonversi = value / 10000; break; // cm² to m²
                    case 5: hasilKonversi = value * 100; break; // cm² to mm²
                    case 6: hasilKonversi = value / 100_000_000; break; // cm² to ha
                    case 7: hasilKonversi = value / 10_000_000_000f; break; // cm² to km²

                    case 8: hasilKonversi = value / 1_000_000; break; // mm² to m²
                    case 9: hasilKonversi = value / 100; break; // mm² to cm²
                    case 10: hasilKonversi = value / 10_000_000_000f; break; // mm² to ha
                    case 11: hasilKonversi = value / 1_000_000_000_000f; break; // mm² to km²

                    case 12: hasilKonversi = value * 10_000; break; // ha to m²
                    case 13: hasilKonversi = value * 1_000_000_000; break; // ha to cm²
                    case 14: hasilKonversi = value * 10_000_000_000f; break; // ha to mm²
                    case 15: hasilKonversi = value / 100; break; // ha to km²

                    case 16: hasilKonversi = value * 1_000_000; break; // km² to m²
                    case 17: hasilKonversi = value * 10_000_000_000f; break; // km² to cm²
                    case 18: hasilKonversi = value * 1_000_000_000_000f; break; // km² to mm²
                    case 19: hasilKonversi = value * 100; break; // km² to ha
                }
                txtHasilLuas.setText("" + hasilKonversi);
            }
        });
    }
}

