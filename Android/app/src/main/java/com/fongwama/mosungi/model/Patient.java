package com.fongwama.mosungi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Karl on 20/07/2016.
 */
public class Patient implements Parcelable{
    private String idPatient;
    private String nom;
    private String prenom;
    private String sexe;
    private String dateNaissance;
    private String telephone;
    private String casPatient;

    public Patient() {
    }

    public Patient(String nom, String prenom, String sexe, String dateNaissance, String telephone, String casPatient) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.casPatient = casPatient;
    }

    protected Patient(Parcel in) {
        idPatient = in.readString();
        nom = in.readString();
        prenom = in.readString();
        sexe = in.readString();
        dateNaissance = in.readString();
        telephone = in.readString();
        casPatient = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCasPatient() {
        return casPatient;
    }

    public void setCasPatient(String casPatient) {
        this.casPatient = casPatient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idPatient);
        dest.writeString(nom);
        dest.writeString(prenom);
        dest.writeString(sexe);
        dest.writeString(dateNaissance);
        dest.writeString(telephone);
        dest.writeString(casPatient);
    }
}
