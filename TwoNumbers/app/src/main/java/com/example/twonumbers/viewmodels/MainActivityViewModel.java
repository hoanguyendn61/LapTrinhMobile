package com.example.twonumbers.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import com.fathzer.soft.javaluator.DoubleEvaluator;
public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<String>> mNumbers;
    private ArrayList<String> current;
    private DoubleEvaluator evaluator;
    public void init(){
        if (mNumbers != null){
            return;
        }
        evaluator = new DoubleEvaluator();
        mNumbers = new MutableLiveData<>();
        current = new ArrayList<>();
    }
    public void calcTwoNumbers(final String expression){
        Double result = evaluator.evaluate(expression);
        String str = expression + " = " + result;
        current.add(str);
        mNumbers.setValue(current);
    }
    public LiveData<List<String>> getExpression(){
        return mNumbers;
    }
}
