package com.example.contactapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.invoke.MutableCallSite;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository mContactRepository;
    private LiveData<List<Contact>> mListLiveContact;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        mContactRepository = new ContactRepository(application);
        mListLiveContact = mContactRepository.getAllContact();
    }
    public LiveData<List<Contact>> getAllContact(){
        return mListLiveContact;
    }

    public void insertContact(Contact dataItem) {
        mContactRepository.insert(dataItem);
    }
//    public void deleteItem(DataItem dataItem) {
//        mDataRepository.deleteItem(dataItem);
//    }
//
//    public void deleteItemById(Long idItem) {
//        mDataRepository.deleteItemById(idItem);
//    }
}
