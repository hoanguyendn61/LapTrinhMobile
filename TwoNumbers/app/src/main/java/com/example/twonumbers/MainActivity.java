package com.example.twonumbers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.twonumbers.databinding.ActivityMainBinding;
import com.example.twonumbers.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private ArrayAdapter arrayAdapter;
    private MainActivityViewModel mMainActivityModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mMainActivityModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mMainActivityModel.init();
        mMainActivityModel.getExpression().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> expression) {
                if (expression != null){
                    arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,expression);
                    binding.lvTwonumbers.setAdapter(arrayAdapter);
                }
                arrayAdapter.notifyDataSetChanged();
                // scroll to bottom when list updating
                binding.lvTwonumbers.smoothScrollToPosition(arrayAdapter.getCount()-1);
            }
        });
    }

    public void btnOnClick(View view){
        String expression = binding.etA.getText().toString() + ((Button)view).getText().toString() + binding.etB.getText().toString();
        try {
            mMainActivityModel.calcTwoNumbers(expression);
            binding.etA.setText(null);
            binding.etB.setText(null);
            binding.etA.setFocusable(true);
        } catch(Exception e){
            Log.e(TAG, e.toString());
        }

    }
}