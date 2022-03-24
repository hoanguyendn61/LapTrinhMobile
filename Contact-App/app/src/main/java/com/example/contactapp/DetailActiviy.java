package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.contactapp.databinding.ActivityDetailBinding;
import com.google.gson.Gson;

public class DetailActiviy extends AppCompatActivity {
    private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try{
            initValues();
        } catch (Exception e){
            Log.e("DetailAct", e.getMessage());
        }
    }

    private void initValues(){
        Gson gson = new Gson();
        Intent i = getIntent();
        Contact item = gson.fromJson(i.getStringExtra("contactItem"),Contact.class);
        Log.i("DetailAct", item.toString());
        String name = item.getFirstname() + " " + item.getLastname();
        binding.tvName.setText(name);
        binding.tvPhone.setText(item.getPhone());
        binding.tvEmail.setText(item.getEmail());
        // TODO: bind avatar
        if (item.getAvatar().getByteCount() != 57600){
            binding.ivAvatar.setImageBitmap(item.getAvatar());
        }
        // TODO: edit click listener
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String myJson = gson.toJson(item);
                Intent intent = new Intent(getApplicationContext(), NewContactActivity.class);
                intent.putExtra("contactItem", myJson);
                startActivity(intent);
            }
        });

    }
}