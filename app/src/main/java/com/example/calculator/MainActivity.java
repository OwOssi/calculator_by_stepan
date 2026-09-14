package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String current = "";
    private double firstNum = 0;
    private String operator = "";
    private boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);

        int[] digits = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int i = 0; i < digits.length; i++) {
            final String d = String.valueOf(i);
            findViewById(digits[i]).setOnClickListener(v -> onDigit(d));
        }

        findViewById(R.id.btnDot).setOnClickListener(v -> onDot());
        findViewById(R.id.btnC).setOnClickListener(v -> onClear());
        findViewById(R.id.btnDel).setOnClickListener(v -> onDelete());
        findViewById(R.id.btnAdd).setOnClickListener(v -> onOp("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> onOp("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> onOp("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> onOp("/"));
        findViewById(R.id.btnPct).setOnClickListener(v -> onPercent());
        findViewById(R.id.btnEq).setOnClickListener(v -> onEquals());
    }

    private void onDigit(String d) {
        if (newInput) { current = ""; newInput = false; }
        if (current.equals("0")) current = d;
        else current += d;
        display.setText(current);
    }

    private void onDot() {
        if (newInput) { current = "0"; newInput = false; }
        if (!current.contains(".")) current += ".";
        display.setText(current);
    }

    private void onClear() {
        current = "";
        firstNum = 0;
        operator = "";
        newInput = true;
        display.setText("0");
    }

    private void onDelete() {
        if (current.length() > 0) current = current.substring(0, current.length() - 1);
        display.setText(current.isEmpty() ? "0" : current);
    }

    private void onOp(String op) {
        if (!current.isEmpty()) {
            if (!operator.isEmpty()) calc();
            else firstNum = Double.parseDouble(current);
            operator = op;
            newInput = true;
        }
    }

    private void onPercent() {
        if (!current.isEmpty()) {
            double v = Double.parseDouble(current) / 100.0;
            current = format(v);
            display.setText(current);
        }
    }

    private void onEquals() {
        if (!operator.isEmpty() && !current.isEmpty()) {
            calc();
            operator = "";
            newInput = true;
        }
    }

    private void calc() {
        double second = Double.parseDouble(current);
        double result;
        switch (operator) {
            case "+": result = firstNum + second; break;
            case "-": result = firstNum - second; break;
            case "*": result = firstNum * second; break;
            case "/":
                if (second == 0) { display.setText("Ошибка"); current = ""; operator=""; newInput=true; return; }
                result = firstNum / second; break;
            default: return;
        }
        firstNum = result;
        current = format(result);
        display.setText(current);
    }

    private String format(double v) {
        if (v == (long) v) return String.valueOf((long) v);
        return String.valueOf(v);
    }
}
