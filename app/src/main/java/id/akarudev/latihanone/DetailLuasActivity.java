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

public class DetailLuasActivity extends AppCompatActivity {
    Spinner spLuas, spLuasHasil;
    EditText edInputLuas;
    TextView txtHasilLuas;

    int posFrom = 0;
    int posTo = 0;

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear;
    ImageButton btnBackspace;

    private static final double M2_TO_CM2 = 10000.0;
    private static final double M2_TO_MM2 = 1000000.0;
    private static final double M2_TO_HA = 0.0001;
    private static final double M2_TO_KM2 = 0.000001;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_luas);

        decimalFormat = new DecimalFormat("0.##########");

        edInputLuas = findViewById(R.id.edInputLuas);
        txtHasilLuas = findViewById(R.id.txtHasilLuas);
        spLuas = findViewById(R.id.spLuas);
        spLuasHasil = findViewById(R.id.spLuasHasil);

        setupNumberPad();

        String[] dataLuas = new String[] {
                "Meter² (m²)",
                "Centimeter² (cm²)",
                "Milimeter² (mm²)",
                "Hektar (ha)",
                "Kilometer² (km²)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataLuas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLuas.setAdapter(adapter);
        spLuasHasil.setAdapter(adapter);

        spLuas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posFrom = position;
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spLuasHasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        edInputLuas.getText().append(number);
        calculate();
    }

    private void deleteFromInput() {
        Editable currentText = edInputLuas.getText();
        if (currentText.length() > 0) {
            edInputLuas.setText(currentText.subSequence(0, currentText.length() - 1));
            edInputLuas.setSelection(edInputLuas.getText().length());
        }
        calculate();
    }

    private void clearInput() {
        edInputLuas.setText("");
        calculate();
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        String input = edInputLuas.getText().toString();
        if (input.isEmpty()) {
            txtHasilLuas.setText("0");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            double valueInM2 = 0;

            switch (posFrom) {
                case 0:
                    valueInM2 = value;
                    break;
                case 1:
                    valueInM2 = value / M2_TO_CM2;
                    break;
                case 2:
                    valueInM2 = value / M2_TO_MM2;
                    break;
                case 3:
                    valueInM2 = value / M2_TO_HA;
                    break;
                case 4:
                    valueInM2 = value / M2_TO_KM2;
                    break;
            }

            double hasilKonversi = 0;
            switch (posTo) {
                case 0:
                    hasilKonversi = valueInM2;
                    break;
                case 1:
                    hasilKonversi = valueInM2 * M2_TO_CM2;
                    break;
                case 2:
                    hasilKonversi = valueInM2 * M2_TO_MM2;
                    break;
                case 3:
                    hasilKonversi = valueInM2 * M2_TO_HA;
                    break;
                case 4:
                    hasilKonversi = valueInM2 * M2_TO_KM2;
                    break;
            }

            txtHasilLuas.setText(decimalFormat.format(hasilKonversi));

        } catch (NumberFormatException e) {
            txtHasilLuas.setText("Error");
        }
    }
}
