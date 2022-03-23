package com.example.dogapp.model;

import com.google.gson.annotations.SerializedName;

public class DogBreed {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("bred_for")
    private String bredFor;
    @SerializedName("origin")
    private String origin;
    @SerializedName("url")
    private String url;

    public DogBreed(int id, String name, String bredFor, String origin, String url) {
        this.id = id;
        this.name = name;
        this.bredFor = bredFor;
        this.origin = origin;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getBredFor() {
        return bredFor;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DogBreed{" +
                "name='" + name + '\'' +
                ", bredFor='" + bredFor + '\'' +
                '}';
    }
}
