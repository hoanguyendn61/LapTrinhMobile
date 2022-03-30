package com.example.dogapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DogBreed implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("bred_for")
    private String bredFor;
    @SerializedName("breed_group")
    private String breedGroup;
    @SerializedName("life_span")
    private String lifeSpan;
    @SerializedName("temperament")
    private String temperament;
    @SerializedName("origin")
    private String origin;
    @SerializedName("height")
    private Height height;
    @SerializedName("weight")
    private Weight weight;
    @SerializedName("url")
    private String url;
    public DogBreed(){

    }
    public DogBreed(String id, String name, String bredFor, String breedGroup, String lifeSpan, String temperament, String origin, Height height, Weight weight, String url) {
        this.id = id;
        this.name = name;
        this.bredFor = bredFor;
        this.breedGroup = breedGroup;
        this.lifeSpan = lifeSpan;
        this.temperament = temperament;
        this.origin = origin;
        this.height = height;
        this.weight = weight;
        this.url = url;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
