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

public class DetailSuhuActivity extends AppCompatActivity {
    Spinner spSuhu;
    EditText edInputSuhu;
    TextView txtHasilSuhu;
    Button tbHitung;
    float hasilConversi = 0;
    int posConversi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_suhu);

        edInputSuhu = findViewById(R.id.edInputSuhu);
        txtHasilSuhu = findViewById(R.id.txtHasilSuhu);
        spSuhu = findViewById(R.id.spSuhu);
        tbHitung = findViewById(R.id.tbHitung);

        String[] dataSuhu = new String[] {"Cel to Re", "Cel to Far", "Re to Cel", "Re to Far"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, dataSuhu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSuhu.setAdapter(adapter);

        spSuhu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posConversi = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        tbHitung.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String input = edInputSuhu.getText().toString();
                if (input.isEmpty()) {
                    txtHasilSuhu.setText("0");
                    return;
                }
                float value = Float.parseFloat(input);
                switch (posConversi) {
                    case 0:
                        hasilConversi = (value * 4) / 5;
                        break;
                    case 1:
                        hasilConversi = (value * 9 / 5) + 32;
                        break;
                    case 2:
                        hasilConversi = (value * 5) / 4;
                        break;
                    case 3:
                        hasilConversi = ((value * 9 / 4) + 32);
                        break;
                }
                txtHasilSuhu.setText("" + hasilConversi);
            }
        });
    }
}
