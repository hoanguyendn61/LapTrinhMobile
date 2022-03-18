package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.contactapp.databinding.ActivityNewContactBinding;

public class NewContactActivity extends AppCompatActivity {
    private static final String TAG = "NewContactActivity";
    private ActivityNewContactBinding binding;
    private ActionBar actionBar;
    private ContactViewModel mContactVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try {
            initValues();
        } catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
    private void initValues(){
        mContactVM = new ViewModelProvider(this).get(ContactViewModel.class);
        setSupportActionBar(binding.toolbarNewcontact);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Create new contact");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        String[] phoneItems = new String[] { "Mobile", "Home", "Work" };
        String[] emailItems = new String[] { "Home", "Work", "Other"};
        ArrayAdapter<String> adapterPhone = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phoneItems);
        ArrayAdapter<String> adapterEmail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, emailItems);
        binding.spinnerEmail.setAdapter(adapterEmail);
        binding.spinnerPhone.setAdapter(adapterPhone);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etFirst.getText().toString() + " " + binding.etLast.getText().toString();
                String phone = binding.editTextPhone.getText().toString();
                String email = binding.editTextTextEmailAddress.getText().toString();
                if (!name.equals("") && !phone.equals("")){
                    Contact contact = new Contact(name, phone, email);
                    mContactVM.insertContact(contact);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newcontact,menu);
        return true;
    }
}