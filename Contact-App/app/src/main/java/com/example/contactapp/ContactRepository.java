package com.example.contactapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContact;
    private List<Contact> itemList = new ArrayList<>();
    public ContactRepository(Application app){
        AppDatabase appDatabase = AppDatabase.getInstance(app);
        this.mContactDao = appDatabase.contactDAO();
        this.mAllContact = mContactDao.getAllContact();
    }
    LiveData<List<Contact>> getAllContact(){
        return mAllContact;
    }

    // You must call this on a non-UI thread or your app will crash
    public void insert(Contact dataItem) {
        new insertAsyncTask(mContactDao).execute(dataItem);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao mAsyncTaskDao;
        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

//    public void deleteItem(DataItem dataItem) {
//        new deleteAsyncTask(mDataDao).execute(dataItem);
//    }
//
//    private static class deleteAsyncTask extends AsyncTask<DataItem, Void, Void> {
//        private DataDAO mAsyncTaskDao;
//        deleteAsyncTask(DataDAO dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final DataItem... params) {
//            mAsyncTaskDao.deleteItem(params[0]);
//            return null;
//        }
//    }
//
//    public void deleteItemById(Long idItem) {
//        new deleteByIdAsyncTask(mDataDao).execute(idItem);
//    }
//
//    private static class deleteByIdAsyncTask extends AsyncTask<Long, Void, Void> {
//        private DataDAO mAsyncTaskDao;
//        deleteByIdAsyncTask(DataDAO dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final Long... params) {
//            mAsyncTaskDao.deleteByItemId(params[0]);
//            return null;
//        }
//    }
}
