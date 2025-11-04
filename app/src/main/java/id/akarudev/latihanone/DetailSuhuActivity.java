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

public class DetailSuhuActivity extends AppCompatActivity {
    Spinner spSuhu, spSuhuHasil;
    EditText edInputSuhu;
    TextView txtHasilSuhu;

    float hasilConversi = 0;
    int posFrom = 0;
    int posTo = 0;

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear;
    ImageButton btnBackspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_suhu);

        edInputSuhu = findViewById(R.id.edInputSuhu);
        txtHasilSuhu = findViewById(R.id.txtHasilSuhu);
        spSuhu = findViewById(R.id.spSuhu);
        spSuhuHasil = findViewById(R.id.spSuhuHasil);

        setupNumberPad();

        String[] dataSuhu = new String[] {"Celcius", "Reamur", "Fahrenheit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataSuhu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSuhu.setAdapter(adapter);
        spSuhuHasil.setAdapter(adapter);

        spSuhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posFrom = position;
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spSuhuHasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        btnClear.setOnClickListener(v -> clearInput()); // TAMBAHKAN LISTENER TOMBOL CLEAR
    }

    private void appendToInput(String number) {
        edInputSuhu.getText().append(number);
        calculate();
    }

    private void deleteFromInput() {
        Editable currentText = edInputSuhu.getText();
        if (currentText.length() > 0) {
            edInputSuhu.setText(currentText.subSequence(0, currentText.length() - 1));
            edInputSuhu.setSelection(edInputSuhu.getText().length());
        }
        calculate();
    }

    private void clearInput() {
        edInputSuhu.setText("");
        calculate();
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        String input = edInputSuhu.getText().toString();
        if (input.isEmpty()) {
            txtHasilSuhu.setText("0");
            return;
        }

        try {
            float value = Float.parseFloat(input);
            float valueInCelcius = 0;

            switch (posFrom) {
                case 0:
                    valueInCelcius = value;
                    break;
                case 1:
                    valueInCelcius = (value * 5) / 4;
                    break;
                case 2:
                    valueInCelcius = (value - 32) * 5 / 9;
                    break;
            }

            switch (posTo) {
                case 0:
                    hasilConversi = valueInCelcius;
                    break;
                case 1:
                    hasilConversi = (valueInCelcius * 4) / 5;
                    break;
                case 2:
                    hasilConversi = (valueInCelcius * 9 / 5) + 32;
                    break;
            }

            txtHasilSuhu.setText(String.valueOf(hasilConversi));

        } catch (NumberFormatException e) {
            txtHasilSuhu.setText("Error");
        }
    }
}
