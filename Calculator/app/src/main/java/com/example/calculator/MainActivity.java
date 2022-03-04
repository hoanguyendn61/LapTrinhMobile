package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.lang.Character;
import java.text.DecimalFormat;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private DoubleEvaluator evaluator;
    private boolean mathExists = false;
    private boolean hasDot = false;
    private DecimalFormat df = new DecimalFormat();
    private Double ans;
    private StringBuilder strBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.tvCalc.setText("0");
        binding.tvHistory.setText("0");
        df.setMaximumFractionDigits(10);
        strBuilder = new StringBuilder("0");
        evaluator = new DoubleEvaluator();
        setContentView(view);
    }
    public void btnNumberOnClick(View view){
        Button btn = (Button) view;
        if (strBuilder.charAt(0) == '0'){
            try {
                if (strBuilder.charAt(1) != '.'){
                    strBuilder.setLength(0);
                }
            } catch (StringIndexOutOfBoundsException e){
                strBuilder.setLength(0);
            }

        }
        strBuilder.append(btn.getText().toString());
        binding.tvCalc.setText(strBuilder);
    }
    public void btnClearOnClick(View view){
        binding.tvCalc.setText("0");
        binding.tvHistory.setText("0");
        strBuilder.setLength(0);
        strBuilder.append('0');
    }
    public void btnEqualOnClick(View view){
        try {
            handleCalculation();
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }
    public void btnMathOnClick(View view){
        Button btn = (Button) view;
        int idx = strBuilder.indexOf(btn.getText().toString());
        if (!mathExists){
            strBuilder.append(btn.getText().toString());
        } else if(Character.isDigit(strBuilder.charAt(strBuilder.length()-1))) {
            handleCalculation();
            strBuilder.setLength(0);
            strBuilder.append(df.format(ans) + btn.getText().toString());
        } else {
            strBuilder.setCharAt(strBuilder.length()-1,btn.getText().charAt(0));
        }
        mathExists = true;
        binding.tvCalc.setText(strBuilder);
    }
    public void btnDotOnClick(View view){
        int idxDot = strBuilder.indexOf(".");
        if(idxDot == -1){
            strBuilder.append('.');
            binding.tvCalc.setText(strBuilder);
        }
    }
    public void btnBackSpaceOnClick(View view){
        if (strBuilder.length() > 0){
            strBuilder.setLength(strBuilder.length()-1);
            binding.tvCalc.setText(strBuilder);
        }
    }
    public void handleCalculation(){
        int idxMul = strBuilder.indexOf("×");
        int idxDiv = strBuilder.indexOf("÷");
        if (idxMul!=-1){
            strBuilder.setCharAt(idxMul,'*');
        }
        if (idxDiv != -1){
            strBuilder.setCharAt(idxDiv,'/');
        }
        mathExists = false;
        ans = evaluator.evaluate(strBuilder.toString());
        binding.tvCalc.setText(df.format(ans));
        binding.tvHistory.setText(strBuilder.toString() + "=" + ans.toString());
        strBuilder.setLength(0);
        strBuilder.append(df.format(ans));
    }
}