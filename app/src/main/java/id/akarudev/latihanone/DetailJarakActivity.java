package id.akarudev.latihanone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class DetailJarakActivity extends AppCompatActivity {
    Spinner spJarak, spJarakHasil; // Sekarang ada 2 spinner
    EditText edInputJarak;
    TextView txtHasilJarak;

    int posFrom = 0;
    int posTo = 0;

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear;
    ImageButton btnBackspace;

    private static final double M_TO_KM = 0.001;
    private static final double M_TO_CM = 100.0;
    private static final double M_TO_MM = 1000.0;
    private static final double M_TO_MILE = 0.000621371;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jarak);


        decimalFormat = new DecimalFormat("0.##########");
        edInputJarak = findViewById(R.id.edInputJarak);
        txtHasilJarak = findViewById(R.id.txtHasilJarak);
        spJarak = findViewById(R.id.spJarak);
        spJarakHasil = findViewById(R.id.spJarakHasil);

        setupNumberPad();


        String[] dataJarak = new String[] {
                "Kilometer (Km)",
                "Meter (m)",
                "Centimeter (cm)",
                "Milimeter (mm)",
                "Mil (Mile)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataJarak);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spJarak.setAdapter(adapter);
        spJarakHasil.setAdapter(adapter);


        spJarak.setSelection(1);
        spJarakHasil.setSelection(0);

        spJarak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posFrom = position;
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spJarakHasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posTo = position;
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupNumberPad() {
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnClear = findViewById(R.id.btnClear);

        btn0.setOnClickListener(v -> appendToInput("0"));
        btn1.setOnClickListener(v -> appendToInput("1"));
        btn2.setOnClickListener(v -> appendToInput("2"));
        btn3.setOnClickListener(v -> appendToInput("3"));
        btn4.setOnClickListener(v -> appendToInput("4"));
        btn5.setOnClickListener(v -> appendToInput("5"));
        btn6.setOnClickListener(v -> appendToInput("6"));
        btn7.setOnClickListener(v -> appendToInput("7"));
        btn8.setOnClickListener(v -> appendToInput("8"));
        btn9.setOnClickListener(v -> appendToInput("9"));

        btnBackspace.setOnClickListener(v -> deleteFromInput());
        btnClear.setOnClickListener(v -> clearInput());
    }


    private void appendToInput(String number) {
        edInputJarak.getText().append(number);
        calculate();
    }

    private void deleteFromInput() {
        Editable currentText = edInputJarak.getText();
        if (currentText.length() > 0) {
            edInputJarak.setText(currentText.subSequence(0, currentText.length() - 1));
            edInputJarak.setSelection(edInputJarak.getText().length());
        }
        calculate();
    }

    private void clearInput() {
        edInputJarak.setText("");
        calculate();
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        String input = edInputJarak.getText().toString();
        if (input.isEmpty()) {
            txtHasilJarak.setText("0");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            double valueInMeter = 0;

            switch (posFrom) {
                case 0:
                    valueInMeter = value / M_TO_KM;
                    break;
                case 1:
                    valueInMeter = value;
                    break;
                case 2:
                    valueInMeter = value / M_TO_CM;
                    break;
                case 3:
                    valueInMeter = value / M_TO_MM;
                    break;
                case 4:
                    valueInMeter = value / M_TO_MILE;
                    break;
            }

            double hasilKonversi = 0;
            switch (posTo) {
                case 0: // To Km
                    hasilKonversi = valueInMeter * M_TO_KM;
                    break;
                case 1: // To Meter
                    hasilKonversi = valueInMeter;
                    break;
                case 2: // To Cm
                    hasilKonversi = valueInMeter * M_TO_CM;
                    break;
                case 3: // To Mm
                    hasilKonversi = valueInMeter * M_TO_MM;
                    break;
                case 4: // To Mile
                    hasilKonversi = valueInMeter * M_TO_MILE;
                    break;
            }

            txtHasilJarak.setText(decimalFormat.format(hasilKonversi));

        } catch (NumberFormatException e) {
            txtHasilJarak.setText("Error");
        }
    }
}
