package com.example.pmhealthcare.database;

import android.net.Uri;

public class DoctorDetails {

    String doctorName;
    Uri doctorDp;
    String healthID;
    String qualifications;
    String institution;
    String specialization;
    long registrationNumber;
    long helpLineNumber;
    long experience;
    String locationCoordinates;
    String status;
    String district;
    String address;

    public DoctorDetails(String doctorName, Uri doctorDp, String healthID, String qualifications, String institution,
                         String specialization, long registrationNumber, long helpLineNumber, long experience, String locationCoordinates,
                         String status, String district, String address) {

        this.doctorName = doctorName;
        this.doctorDp = doctorDp;
        this.healthID = healthID;
        this.qualifications = qualifications;
        this.institution = institution;
        this.specialization = specialization;
        this.registrationNumber = registrationNumber;
        this.helpLineNumber = helpLineNumber;
        this.experience = experience;
        this.locationCoordinates = locationCoordinates;
        this.status = status;
        this.district = district;
        this.address = address;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
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

    public long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public long getHelpLineNumber() {
        return helpLineNumber;
    }

    public void setHelpLineNumber(long helpLineNumber) {
        this.helpLineNumber = helpLineNumber;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getLocationCoordinates() {
        return locationCoordinates;
    }

    public void setLocationCoordinates(String locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
