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

public class DetailMasaActivity extends AppCompatActivity {
    Spinner spMasa;
    EditText edInputMasa;
    TextView txtHasilMasa;
    Button tbHitungMasa;
    float hasilKonversi = 0;
    int posKonversi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_masa);

        edInputMasa = findViewById(R.id.edInputMasa);
        txtHasilMasa = findViewById(R.id.txtHasilMasa);
        spMasa = findViewById(R.id.spMasa);
        tbHitungMasa = findViewById(R.id.tbHitungMasa);

        String[] dataMasa = new String[] {
                "Kg to Gram", "Kg to Mg", "Kg to Lb", "Kg to Oz",
                "Gram to Kg", "Gram to Mg", "Gram to Lb", "Gram to Oz",
                "Mg to Kg", "Mg to Gram", "Mg to Lb", "Mg to Oz",
                "Lb to Kg", "Lb to Gram", "Lb to Mg", "Lb to Oz",
                "Oz to Kg", "Oz to Gram", "Oz to Mg", "Oz to Lb"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataMasa);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMasa.setAdapter(adapter);

        spMasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posKonversi = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tbHitungMasa.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String input = edInputMasa.getText().toString();
                if (input.isEmpty()) {
                    txtHasilMasa.setText("0");
                    return;
                }
                float value = Float.parseFloat(input);
                switch (posKonversi) {
                    case 0: hasilKonversi = value * 1000; break; // Kg to Gram
                    case 1: hasilKonversi = value * 1_000_000; break; // Kg to Mg
                    case 2: hasilKonversi = value * 2.20462f; break; // Kg to Lb
                    case 3: hasilKonversi = value * 35.27396f; break; // Kg to Oz

                    case 4: hasilKonversi = value / 1000; break; // Gram to Kg
                    case 5: hasilKonversi = value * 1000; break; // Gram to Mg
                    case 6: hasilKonversi = value * 0.00220462f; break; // Gram to Lb
                    case 7: hasilKonversi = value * 0.03527396f; break; // Gram to Oz

                    case 8: hasilKonversi = value / 1_000_000; break; // Mg to Kg
                    case 9: hasilKonversi = value / 1000; break; // Mg to Gram
                    case 10: hasilKonversi = value * 0.00000220462f; break; // Mg to Lb
                    case 11: hasilKonversi = value * 0.00003527396f; break; // Mg to Oz

                    case 12: hasilKonversi = value * 0.453592f; break; // Lb to Kg
                    case 13: hasilKonversi = value * 453.592f; break; // Lb to Gram
                    case 14: hasilKonversi = value * 453592f; break; // Lb to Mg
                    case 15: hasilKonversi = value * 16f; break; // Lb to Oz

                    case 16: hasilKonversi = value * 0.0283495f; break; // Oz to Kg
                    case 17: hasilKonversi = value * 28.3495f; break; // Oz to Gram
                    case 18: hasilKonversi = value * 28349.5f; break; // Oz to Mg
                    case 19: hasilKonversi = value * 0.0625f; break; // Oz to Lb
                }
                txtHasilMasa.setText("" + hasilKonversi);
            }
        });
    }
}
