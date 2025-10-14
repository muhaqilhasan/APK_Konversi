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

public class DetailJarakActivity extends AppCompatActivity {
    Spinner spJarak;
    EditText edInputJarak;
    TextView txtHasilJarak;
    Button tbHitungJarak;
    float hasilKonversi = 0;
    int posKonversi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jarak);

        edInputJarak = findViewById(R.id.edInputJarak);
        txtHasilJarak = findViewById(R.id.txtHasilJarak);
        spJarak = findViewById(R.id.spJarak);
        tbHitungJarak = findViewById(R.id.tbHitungJarak);

        String[] dataJarak = new String[] {
                "Km to M", "Km to Cm", "Km to Mm", "Km to Mile",
                "M to Km", "M to Cm", "M to Mm", "M to Mile",
                "Cm to Km", "Cm to M", "Cm to Mm", "Cm to Mile",
                "Mm to Km", "Mm to M", "Mm to Cm", "Mm to Mile",
                "Mile to Km", "Mile to M", "Mile to Cm", "Mile to Mm"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataJarak);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJarak.setAdapter(adapter);

        spJarak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posKonversi = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tbHitungJarak.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String input = edInputJarak.getText().toString();
                if (input.isEmpty()) {
                    txtHasilJarak.setText("0");
                    return;
                }
                float value = Float.parseFloat(input);
                switch (posKonversi) {
                    case 0: hasilKonversi = value * 1000; break; // Km to M
                    case 1: hasilKonversi = value * 100000; break; // Km to Cm
                    case 2: hasilKonversi = value * 1000000; break; // Km to Mm
                    case 3: hasilKonversi = value * 0.621371f; break; // Km to Mile

                    case 4: hasilKonversi = value / 1000; break; // M to Km
                    case 5: hasilKonversi = value * 100; break; // M to Cm
                    case 6: hasilKonversi = value * 1000; break; // M to Mm
                    case 7: hasilKonversi = value * 0.000621371f; break; // M to Mile

                    case 8: hasilKonversi = value / 100000; break; // Cm to Km
                    case 9: hasilKonversi = value / 100; break; // Cm to M
                    case 10: hasilKonversi = value * 10; break; // Cm to Mm
                    case 11: hasilKonversi = value * 0.0000062137f; break; // Cm to Mile

                    case 12: hasilKonversi = value / 1000000; break; // Mm to Km
                    case 13: hasilKonversi = value / 1000; break; // Mm to M
                    case 14: hasilKonversi = value / 10; break; // Mm to Cm
                    case 15: hasilKonversi = value * 0.00000062137f; break; // Mm to Mile

                    case 16: hasilKonversi = value * 1.60934f; break; // Mile to Km
                    case 17: hasilKonversi = value * 1609.34f; break; // Mile to M
                    case 18: hasilKonversi = value * 160934f; break; // Mile to Cm
                    case 19: hasilKonversi = value * 1609340f; break; // Mile to Mm
                }
                txtHasilJarak.setText("" + hasilKonversi);
            }
        });
    }
}

