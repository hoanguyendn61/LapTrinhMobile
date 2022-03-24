package com.example.contactapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> getAllContact();

    @Query("SELECT * FROM Contact WHERE first_name LIKE :firstName AND last_name LIKE :lastName")
    public Contact getContact(String firstName, String lastName);

    @Insert
    public void insertAll(Contact... contact);

    @Update
    public void updateContacts(Contact... contact);
}
