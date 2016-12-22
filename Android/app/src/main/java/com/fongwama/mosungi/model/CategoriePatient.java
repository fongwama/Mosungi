package com.fongwama.mosungi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by STEVEN on 27/11/2016.
 */

public class CategoriePatient implements Parcelable {

    String id;
    String description;
    String nom;
    List<Patient> patients;

    public CategoriePatient() {
    }

    public CategoriePatient(String id, String description, String nom, List<Patient> patients) {
        this.id = id;
        this.description = description;
        this.nom = nom;
        this.patients = patients;
    }

    protected CategoriePatient(Parcel in) {
        id = in.readString();
        description = in.readString();
        nom = in.readString();
        patients = in.createTypedArrayList(Patient.CREATOR);
    }

    public static final Creator<CategoriePatient> CREATOR = new Creator<CategoriePatient>() {
        @Override
        public CategoriePatient createFromParcel(Parcel in) {
            return new CategoriePatient(in);
        }

        @Override
        public CategoriePatient[] newArray(int size) {
            return new CategoriePatient[size];
        }
    };

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

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(nom);
        dest.writeTypedList(patients);
    }
}
