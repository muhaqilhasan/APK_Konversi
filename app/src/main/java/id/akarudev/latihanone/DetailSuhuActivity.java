package id.akarudev.latihanone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // Diperlukan untuk ImageButton
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailSuhuActivity extends AppCompatActivity {
    // Referensi untuk 'From' (spSuhu) and 'To' (spSuhuHasil)
    Spinner spSuhu, spSuhuHasil;
    EditText edInputSuhu;
    TextView txtHasilSuhu;

    float hasilConversi = 0;
    // Ganti nama variabel agar lebih jelas
    int posFrom = 0;
    int posTo = 0;

    // Tambahkan referensi untuk tombol-tombol Numpad
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnClear; // TAMBAHKAN TOMBOL CLEAR
    ImageButton btnBackspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan nama layout XML Anda adalah 'activity_detail_suhu'
        setContentView(R.layout.activity_detail_suhu);

        edInputSuhu = findViewById(R.id.edInputSuhu);
        txtHasilSuhu = findViewById(R.id.txtHasilSuhu);
        spSuhu = findViewById(R.id.spSuhu);
        // TAMBAHKAN: Temukan ID untuk spinner hasil
        spSuhuHasil = findViewById(R.id.spSuhuHasil);

        // Panggil fungsi untuk mendaftarkan semua tombol numpad
        setupNumberPad();

        // UBAH: Buat data unit yang terpisah
        String[] dataSuhu = new String[] {"Celcius", "Reamur", "Fahrenheit"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataSuhu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Terapkan adapter yang SAMA ke KEDUA spinner
        spSuhu.setAdapter(adapter);
        spSuhuHasil.setAdapter(adapter);

        // Listener untuk spinner 'From' (spSuhu)
        spSuhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posFrom = position;
                // Hitung ulang setiap kali pilihan spinner diubah
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // TAMBAHKAN: Listener untuk spinner 'To' (spSuhuHasil)
        spSuhuHasil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posTo = position;
                // Hitung ulang setiap kali pilihan spinner diubah
                calculate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    /**
     * Fungsi untuk mencari ID semua tombol numpad dan memberi mereka OnClickListener
     */
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
        btnClear = findViewById(R.id.btnClear); // TAMBAHKAN ID TOMBOL CLEAR

        // Menggunakan lambda untuk OnClickListener yang lebih ringkas
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

    /**
     * Fungsi untuk menambahkan angka ke EditText
     */
    private void appendToInput(String number) {
        edInputSuhu.getText().append(number);
        // Hitung ulang setiap kali angka ditambahkan
        calculate();
    }

    /**
     * Fungsi untuk menghapus satu karakter dari EditText
     */
    private void deleteFromInput() {
        Editable currentText = edInputSuhu.getText();
        if (currentText.length() > 0) {
            // Hapus karakter terakhir
            edInputSuhu.setText(currentText.subSequence(0, currentText.length() - 1));
            // Pindahkan kursor ke akhir
            edInputSuhu.setSelection(edInputSuhu.getText().length());
        }
        // Hitung ulang setiap kali angka dihapus
        calculate();
    }

    /**
     * TAMBAHKAN FUNGSI BARU
     * Fungsi untuk menghapus semua input
     */
    private void clearInput() {
        edInputSuhu.setText("");
        // Hitung ulang (yang akan mengatur hasil ke "0")
        calculate();
    }


    /**
     * UBAH TOTAL: Fungsi ini sekarang mengkonversi DARI unit 'From' KE unit 'To'.
     * Kita menggunakan Celcius sebagai unit perantara.
     */
    @SuppressLint("SetTextI18n")
    private void calculate() {
        String input = edInputSuhu.getText().toString();
        if (input.isEmpty()) {
            txtHasilSuhu.setText("0"); // Set ke 0 jika input kosong
            return;
        }

        try {
            float value = Float.parseFloat(input);
            float valueInCelcius = 0;

            // LANGKAH 1: Konversi nilai input (dari unit 'From') ke Celcius
            switch (posFrom) {
                case 0: // From Celcius
                    valueInCelcius = value;
                    break;
                case 1: // From Reamur
                    valueInCelcius = (value * 5) / 4;
                    break;
                case 2: // From Fahrenheit
                    valueInCelcius = (value - 32) * 5 / 9;
                    break;
            }

            // LANGKAH 2: Konversi nilai Celcius ke unit 'To'
            switch (posTo) {
                case 0: // To Celcius
                    hasilConversi = valueInCelcius;
                    break;
                case 1: // To Reamur
                    hasilConversi = (valueInCelcius * 4) / 5;
                    break;
                case 2: // To Fahrenheit
                    hasilConversi = (valueInCelcius * 9 / 5) + 32;
                    break;
            }

            // Tampilkan hasil
            txtHasilSuhu.setText(String.valueOf(hasilConversi));

        } catch (NumberFormatException e) {
            // Tangani jika input tidak valid (meskipun dengan numpad, ini jarang terjadi)
            txtHasilSuhu.setText("Error");
        }
    }
}
