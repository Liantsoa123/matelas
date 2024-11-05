package com.example.matelas.transformationdetails;

import com.example.matelas.forme.FormUsuelle;
import com.example.matelas.transformation.Transformation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransformationDetails {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuelle_id")
    private FormUsuelle usuelle;

    @ManyToOne
    @JoinColumn(name = "transformation_id")
    private Transformation transformation;

    private double prixRevient;

    public TransformationDetails() {
    }

    public TransformationDetails(int id, FormUsuelle usuelle, Transformation transformation) {
        this.id = id;
        this.usuelle = usuelle;
        this.transformation = transformation;
        this.setPrixRevient();
    }

    public FormUsuelle getUsuelle() {
        return usuelle;
    }

    public void setUsuelle(FormUsuelle usuelle) {
        this.usuelle = usuelle;
        this.setPrixRevient();
    }

    public double getPrixRevient() {
        return prixRevient;
    }


    public void setPrixRevient() {
        if (this.usuelle != null && this.transformation != null && this.transformation.getMere() != null) {
            this.prixRevient = (this.usuelle.volume() * this.transformation.getMere().getPrixRevient())
                    / this.transformation.getMere().volume();
        } else {
            this.prixRevient = 0;
        }
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
        this.setPrixRevient();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
