package com.example.matelas.model.marge;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Marge {
    @Id
    private  int id ;
    double marge;

    public Marge(int id, double marge) {
        this.id = id;
        this.marge = marge;
    }

    public Marge() {
    }

    public double getMarge() {
        return marge;
    }

    public void setMarge(double marge) {
        this.marge = marge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
