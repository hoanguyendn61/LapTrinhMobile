package com.example.firstapplication;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel{
    private ArrayList<String> al;
    private MutableLiveData<Integer> number;
    private MutableLiveData<List<String>> listItems;
    public LiveData<Integer> getNumber(){
        if (number == null) {
            number = new MutableLiveData<Integer>();
            number.setValue(0);
        }
        return number;
    }
    public LiveData<List<String>> getListNumbers() {
        if (listItems == null) {
            listItems = new MutableLiveData<List<String>>();
            al = new ArrayList<String>();
        }
        return listItems;
    }
    public void increaseNumber(){
        number.setValue(number.getValue() + 1);
    }
    public void decreaseNumber(){
        number.setValue(number.getValue() - 1);
    }
    public void setNumber(String i){
        number.setValue(Integer.parseInt(i));
    }
    public void changeNumber(int i, String num){
        al.set(i,num);
        listItems.setValue(al);
    }
    public void addNumber(String num){
        al.add(num);
        listItems.setValue(al);
    }
}
