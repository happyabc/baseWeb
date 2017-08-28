package com.happyabc.model;

public class City {


    private int id;
    private String name;
    private int pro_id;

    public City() {
        super();
    }

    public City(int id, String name, int proId) {
        super();
        this.id = id;
        this.name = name;
        pro_id = proId;
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

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int proId) {
        pro_id = proId;
    }

}