package com.example.menus;
    import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class ConversionActivity extends AppCompatActivity {

        private EditText editText1, editText2;
        private Button convertButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_conversion);

            editText1 = findViewById(R.id.editText1);
            editText2 = findViewById(R.id.editText2);
            convertButton = findViewById(R.id.convertButton);

            convertButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = editText1.getText().toString().trim();
                    if (!input.isEmpty()) {
                        double value = Double.parseDouble(input);
                        showPopupMenu(v, value);
                    }
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.popup, menu);
            return true;
        }


        private void showPopupMenu(View view, final double value) {
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.inflate(R.menu.popup);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.ctof:
                            convertTemperature(true, value);
                            return true;
                        case R.id.ftoc:
                            convertTemperature(false, value);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }

        private void convertTemperature(boolean celsiusToFahrenheit, double value) {
            double result;
            if (celsiusToFahrenheit) {
                result = (value * 9 / 5) + 32;
                String st=String.valueOf(value) + "°C = " + String.valueOf(result) + "°F";
                editText2.setText(st);
            } else {
                result = (value - 32) * 5 / 9;
                String st=String.valueOf(value) + "°F = " + String.valueOf(result) + "°C";
                editText2.setText(st);

            }
        }
    }


