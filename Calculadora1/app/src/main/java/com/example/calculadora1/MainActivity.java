package com.example.calculadora1;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private boolean resultReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        display = findViewById(R.id.display);
        display.setInputType(InputType.TYPE_NULL);
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonSum, R.id.buttonSub, R.id.buttonMul, R.id.buttonDiv,
                R.id.buttonDot, R.id.buttonEqual, R.id.buttonCleaner, R.id.buttonBack
        };
        for (int id : buttonIds) {
            Button btn = findViewById(id);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickButton(v);
                }
            });
        }
    }
    public void clickButton(View v){
        Button btn = (Button) v;
        String btnText = btn.getText().toString();
        String currentText = display.getText().toString();

        if (btnText.equals("<<")) {
            if (!currentText.isEmpty()) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
            return;
        }

        if (btnText.equals("C")) {
            display.setText("");
            resultReady = false;
            return;
        }

        //faz a expressão
        if (btnText.equals("=")) {
            if (currentText.isEmpty()) return;


            String operator = "";
            if (currentText.contains("+"))
                operator = "\\+";
            else if (currentText.contains("-"))
                operator = "-";
            else if (currentText.contains("*"))
                operator = "\\*";
            else if (currentText.contains("/"))
                operator = "/";

            if (operator.isEmpty()) return;

            String[] parts = currentText.split(operator);

            try {
                double op1 = Double.parseDouble(parts[0]);
                double op2 = Double.parseDouble(parts[1]);
                double result = 0;

                if (operator.equals("\\+"))
                    result = op1 + op2;
                else if (operator.equals("-"))
                    result = op1 - op2;
                else if (operator.equals("\\*"))
                    result = op1 * op2;
                else if (operator.equals("/")) {
                    if (op2 == 0) {
                        display.setText("ERROR");
                        resultReady = true;
                        return;
                    }
                    result = op1 / op2;
                }

                if (result == (long) result)
                    display.setText(String.valueOf((long) result));
                else
                    display.setText(String.valueOf(result));
                resultReady = true;
            } catch (NumberFormatException e) {
                display.setText("ERROR");
                resultReady = true;
            }
            return;
        }

        if (btnText.equals("+") || btnText.equals("-") || btnText.equals("*") || btnText.equals("/")) {
            if (!currentText.isEmpty()) {
                char lastChar = currentText.charAt(currentText.length() - 1);
                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/')
                    currentText = currentText.substring(0, currentText.length() - 1);
                display.setText(currentText + btnText);
            }
            resultReady = false;
            return;
        }

        if (btnText.equals(".")) {
            String currentOperand = currentText;
            int lastOpIndex = Math.max(
                    Math.max(currentText.lastIndexOf("+"), currentText.lastIndexOf("-")),
                    Math.max(currentText.lastIndexOf("*"), currentText.lastIndexOf("/"))
            );
            if (lastOpIndex != -1) {
                currentOperand = currentText.substring(lastOpIndex + 1);
            }
            if (!currentOperand.isEmpty() && !currentOperand.contains("."))
                display.append(btnText);
            return;
        }

        if (resultReady) {
            display.setText(btnText);
            resultReady = false;
        } else {
            display.append(btnText);
        }
    }
}