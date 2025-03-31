package com.example.calculadora1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }
    public void clickButton(View v){
        EditText value1 = (EditText) findViewById(R.id.textEntrada1);
        float value1Int = Float.parseFloat(value1.getText().toString());
        EditText value2 = (EditText) findViewById(R.id.textEntrada2);
        float value2Int = Float.parseFloat(value2.getText().toString());
        TextView resultadoMostrado = (TextView) findViewById(R.id.resultado);
        String tag = v.getTag().toString();

        float result = 0;
        if(tag.equals("buttonSum"))
            result = value1Int + value2Int;
        if(tag.equals("buttonSub"))
            result = value1Int - value2Int;
        if(tag.equals("buttonMul"))
            result = value1Int * value2Int;
        if(tag.equals("buttonDiv"))
            if(value2Int != 0)
                result = value1Int / value2Int;

        resultadoMostrado.setText(String.valueOf(result));
    }
}