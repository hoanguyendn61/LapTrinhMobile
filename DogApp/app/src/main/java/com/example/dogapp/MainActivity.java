package com.example.dogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dogapp.adapter.DogAdapter;
import com.example.dogapp.databinding.ActivityMainBinding;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.repository.DogsApiService;
import com.example.dogapp.viewmodel.DogsViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
//    private DogsApiService dogsApiService;
    private ActivityMainBinding binding;
    private DogAdapter mAdapter;
    private DogsViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try {
            initValues();
        } catch(Exception e){
            Log.e("MainAct",e.getMessage());
        }

//        dogsApiService = new DogsApiService();
//        dogsApiService.getDogs().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
//            @Override
//            public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
//                for (DogBreed dog: dogBreeds){
//                    Log.d("DEBUG1", dog.getName());
//                }
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                    Log.e("DEBUG1", e.getMessage());
//            }
//        });
    }
    private void initValues(){
        mViewModel = new ViewModelProvider(this).get(DogsViewModel.class);
        RecyclerView.LayoutManager linearLayout = new GridLayoutManager(getApplicationContext(), 2);
        binding.rvDogApp.setLayoutManager(linearLayout);
        mViewModel.init();
        mAdapter = new DogAdapter(getApplicationContext());

        binding.rvDogApp.setAdapter(mAdapter);
        mViewModel.getDogBreeds().observe(this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                if(dogBreeds!=null){
                    Log.i("MainAct",dogBreeds.toString());
                    mAdapter.setDogBreedList(dogBreeds);
                }
            }
        });

    }

}