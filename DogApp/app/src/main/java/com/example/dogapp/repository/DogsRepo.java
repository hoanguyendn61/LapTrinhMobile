package com.example.dogapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dogapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DogsRepo {
    private static DogsRepo instance;
    private DogsApiService dogsApiService;
    MutableLiveData<List<DogBreed>> data = new MutableLiveData<>();
    public static DogsRepo getInstance(){
        if(instance == null){
            instance = new DogsRepo();
        }
        return instance;
    }

    //get data from an online source
    public MutableLiveData<List<DogBreed>> getDogBreeds(){
//        Get dog breeds list from internet
        dogsApiService = new DogsApiService();
        dogsApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
            @Override
            public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                Log.d("DEBUG1","get data successful");
                data.setValue(dogBreeds);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("DEBUG1", e.getMessage());
            }
        });
        return data;
    }
}
