package com.example.firstapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.firstapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private MyViewModel model;
    private ArrayAdapter<String> arrayAdapter;
    private static final int REQ_CODE = 6100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        // create model instance for main activity
        model = new ViewModelProvider(this).get(MyViewModel.class);

//         this runs everytime we rotate the phone
        model.getNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCount.setText("" + integer);
            }
        });
        model.getListNumbers().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings != null){
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                    binding.lvCount.setAdapter(arrayAdapter);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        });

        binding.btnUp.setOnClickListener(new OnClickEvent());
        binding.btnDown.setOnClickListener(new OnClickEvent());
//        binding.lvCount.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                return false;
//            }
//        });
        // send data to another activity and wait for result
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            String extra = data.getStringExtra("number");
                            model.setNumber(extra);
                            model.addNumber(extra);
                        }
                    }
                });
        Intent intent = new Intent(this, DetailsActivity.class);

        binding.lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("number",((TextView) view).getText().toString());
                someActivityResultLauncher.launch(intent);
            }
        });
    }
    public class OnClickEvent implements View.OnClickListener{
        @Override
        public void onClick(View v){
//            Random rnd = new Random();
//            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            switch (v.getId()){
                case R.id.btn_up:
//                    int count = Integer.parseInt(binding.tvCount.getText().toString());
//                    binding.tvCount.setText(String.valueOf(++count));
                    model.increaseNumber();
                    model.addNumber(binding.tvCount.getText().toString());
//                    binding.tvCount.setTextColor(color);
                    break;
                case R.id.btn_down:
//                    count = Integer.parseInt(binding.tvCount.getText().toString());
//                    binding.tvCount.setTextColor(color);
//                    binding.tvCount.setText(String.valueOf(--count));
                    model.decreaseNumber();
                    model.addNumber(binding.tvCount.getText().toString());
                    break;
            }
        }
    }
}