package com.example.pmhealthcare.database;

import android.net.Uri;

public class DoctorDetails {

    Uri doctorDp;
    String healthID;
    String qualification;
    String institution;
    String specialization;
    int registrationNumber;
    int helpLineNumber;
    int experience;
    int[] locationCoordinates;
    String status;

    public DoctorDetails(Uri doctorDp, String healthID, String qualification, String institution, String specialization, int registrationNumber,
                         int helpLineNumber, int experience, int[] locationCoordinates, String status) {

        this.doctorDp = doctorDp;
        this.healthID = healthID;
        this.qualification = qualification;
        this.institution = institution;
        this.specialization = specialization;
        this.registrationNumber = registrationNumber;
        this.helpLineNumber = helpLineNumber;
        this.experience = experience;
        this.locationCoordinates = locationCoordinates;
        this.status = status;
    }

    public Uri getDoctorDp() {
        return doctorDp;
    }

    public void setDoctorDp(Uri doctorDp) {
        this.doctorDp = doctorDp;
    }

    public String getHealthID() {
        return healthID;
    }

    public void setHealthID(String healthID) {
        this.healthID = healthID;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getHelpLineNumber() {
        return helpLineNumber;
    }

    public void setHelpLineNumber(int helpLineNumber) {
        this.helpLineNumber = helpLineNumber;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int[] getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(int[] locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
