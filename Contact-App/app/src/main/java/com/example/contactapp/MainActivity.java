package com.example.contactapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private ActionBar actionBar;
    private List<Contact> mContactList;
    private ContactAdapter mContactAdapter;
    private AppDatabase appDatabase;
    private ContactDao contactDAO;
    private ContactViewModel mContactVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        try {

            initValues();
        } catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
    public void initValues(){
        mContactVM = new ViewModelProvider(this).get(ContactViewModel.class);
        setSupportActionBar(binding.toolbarMain);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Contacts");
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        binding.rvContact.setLayoutManager(linearLayout);
        mContactAdapter = new ContactAdapter(getApplicationContext());
        binding.rvContact.setAdapter(mContactAdapter);
        mContactVM.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                if (contacts != null){
                    setListContact(contacts);
                }
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewContactActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mContactAdapter.filter(s);
                return true;
            }
        });
        return true;
    }
    public void setListContact(List<Contact> itemList){
        if (mContactList == null){
            mContactList = new ArrayList<>();
        }
        mContactList.clear();
        mContactList.addAll(itemList);
        if (mContactAdapter == null){
            Log.i(TAG, "Create new ContactAdapter");
            mContactAdapter = new ContactAdapter(getApplicationContext());
            binding.rvContact.setAdapter(mContactAdapter);
        }
        mContactAdapter.setListContact(itemList);
    }
}