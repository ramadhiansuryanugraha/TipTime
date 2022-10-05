package com.example.tiptime;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tiptime.databinding.ActivityMainBinding;
import java.util.Objects;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.calculateButton.setOnClickListener(v-> calculateTip());
        binding.costOfServiceEditText.setOnKeyListener((v, keyCode, event) -> {

            if (keyCode == KeyEvent.KEYCODE_ENTER){
                InputMethodManager enter = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                enter.hideSoftInputFromWindow(v.getWindowToken(),0);
                return true;
            }
            return false;
        });
    }

    private void calculateTip() {
        double cost = 0.0;
        String stringInTextField = Objects.requireNonNull(binding.costOfServiceEditText.getText()).toString();
        if (stringInTextField.equals("") || stringInTextField.equals("0")){
            displayTip(0.0);
        }else{
            cost = Double.parseDouble(stringInTextField) ;
        }

        double tipPercentage;
        int id = binding.tipOptions.getCheckedRadioButtonId();
        if (id == R.id.option_twenty_percent){
            tipPercentage = 0.20;
        }else if (id == R.id.option_eighteen_percent){
            tipPercentage = 0.18;
        }else{
            tipPercentage = 0.15;
        }

        double tip = tipPercentage * cost;
        boolean roundUp = binding.roundUpSwitch.isChecked();
        if (roundUp) {
            tip = Math.ceil(tip);
        }
        displayTip(tip);
    }
    private void displayTip(double tip) {
        String formattedTip = NumberFormat.getCurrencyInstance().format(tip);
        binding.tipResult.setText(getString(R.string.tip_amount, formattedTip));
    }
}