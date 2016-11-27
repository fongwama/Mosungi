package com.fongwama.mosungi.model;

/**
 * Created by STEVEN on 27/11/2016.
 */

public class CategoriePatient {

    String id;
    String description;
    String nom;

    public CategoriePatient(String id, String description, String nom) {
        this.id = id;
        this.description = description;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
