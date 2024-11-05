package com.example.matelas.transformation;

import com.example.matelas.block.Block;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

@Entity
public class Transformation {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "mere_block_id")
    private Block mere;

    @ManyToOne
    @JoinColumn(name = "reste_block_id")
    private Block reste;

    private Date dateTransformation;

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
}
