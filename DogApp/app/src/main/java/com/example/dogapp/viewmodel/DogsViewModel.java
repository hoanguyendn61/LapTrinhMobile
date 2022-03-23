package com.example.dogapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dogapp.model.DogBreed;
import com.example.dogapp.repository.DogsRepo;

import java.util.List;

public class DogsViewModel extends ViewModel {
    private MutableLiveData<List<DogBreed>> mDogBreeds;
    private DogsRepo mRepo;

    public void init(){
        if(mDogBreeds != null){
            return;
        }
        mRepo = DogsRepo.getInstance();
        mDogBreeds = mRepo.getDogBreeds();
    }
    public LiveData<List<DogBreed>> getDogBreeds(){
        return mDogBreeds;
    }
}
