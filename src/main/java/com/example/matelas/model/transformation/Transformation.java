package com.example.matelas.model.transformation;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.forme.FormUsuelle;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class Transformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mere_block_id")
    private Block mere;

    @ManyToOne
    @JoinColumn(name = "reste_block_id")
    private Block reste;

    private Date dateTransformation;

    @Transient
    private List<FormUsuelle> formUsuelles ;

    public Transformation() {
    }

    public Transformation(int id, Block mere, Block reste, Date dateTransformation) {
        this.id = id;
        this.mere = mere;
        this.reste = reste;
        this.dateTransformation = dateTransformation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Block getMere() {
        return mere;
    }

    public void setMere(Block mere) {
        this.mere = mere;
    }

    public Block getReste() {
        return reste;
    }

    public void setReste(Block reste) {
        this.reste = reste;
    }

    public Date getDateTransformation() {
        return dateTransformation;
    }

    public void setDateTransformation(Date dateTransformation) {
        this.dateTransformation = dateTransformation;
    }

    public List<FormUsuelle> getFormUsuelles() {
        return formUsuelles;
    }

    public void setFormUsuelles(List<FormUsuelle> formUsuelles) {
        this.formUsuelles = formUsuelles;
    }
}
