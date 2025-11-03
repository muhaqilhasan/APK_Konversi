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

public class DetailMasaActivity extends AppCompatActivity {
    Spinner spMasa, spMasaHasil;
    EditText edInputMasa;
    TextView txtHasilMasa;

    int posFrom = 0;
    int posTo = 0;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear;
    ImageButton btnBackspace;
    private static final double KG_TO_GRAM = 1000;
    private static final double KG_TO_MG = 1000000;
    private static final double KG_TO_LB = 2.20462262;
    private static final double KG_TO_OZ = 35.2739619;

    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_masa);
        decimalFormat = new DecimalFormat("0.#####");
        edInputMasa = findViewById(R.id.edInputMasa);
        txtHasilMasa = findViewById(R.id.txtHasilMasa);
        spMasa = findViewById(R.id.spMasa);
        spMasaHasil = findViewById(R.id.spMasaHasil);
        setupNumberPad();
        String[] dataMasa = new String[] {
                "Kilogram (kg)",
                "Gram (g)",
                "Miligram (mg)",
                "Pound (lb)",
                "Ons (oz)"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataMasa);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMasa.setAdapter(adapter);
        spMasaHasil.setAdapter(adapter);
        spMasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posFrom = position;
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spMasaHasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        edInputMasa.getText().append(number);
        calculate();
    }
    private void deleteFromInput() {
        Editable currentText = edInputMasa.getText();
        if (currentText.length() > 0) {
            edInputMasa.setText(currentText.subSequence(0, currentText.length() - 1));
            edInputMasa.setSelection(edInputMasa.getText().length());
        }
        calculate();
    }
    private void clearInput() {
        edInputMasa.setText("");
        calculate();
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        String input = edInputMasa.getText().toString();
        if (input.isEmpty()) {
            txtHasilMasa.setText("0");
            return;
        }

        try {
            double value = Double.parseDouble(input);
            double valueInKg = 0;
            switch (posFrom) {
                case 0:
                    valueInKg = value;
                    break;
                case 1:
                    valueInKg = value / KG_TO_GRAM;
                    break;
                case 2:
                    valueInKg = value / KG_TO_MG;
                    break;
                case 3:
                    valueInKg = value / KG_TO_LB;
                    break;
                case 4:
                    valueInKg = value / KG_TO_OZ;
                    break;
            }
            double hasilKonversi = 0;
            switch (posTo) {
                case 0:
                    hasilKonversi = valueInKg;
                    break;
                case 1:
                    hasilKonversi = valueInKg * KG_TO_GRAM;
                    break;
                case 2:
                    hasilKonversi = valueInKg * KG_TO_MG;
                    break;
                case 3:
                    hasilKonversi = valueInKg * KG_TO_LB;
                    break;
                case 4:
                    hasilKonversi = valueInKg * KG_TO_OZ;
                    break;
            }
            txtHasilMasa.setText(decimalFormat.format(hasilKonversi));

        } catch (NumberFormatException e) {
            txtHasilMasa.setText("Error");
        }
    }
}

