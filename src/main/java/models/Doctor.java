package models;

public class Doctor {

    private int id;
    private String name;
    private String qualification;
    private String email;
    private String phoneNumber;
    private int consultationFee;
    private double rating;
    private int location_id;
    private int specialty_id;
    private int hospital_id;
    private int payment_id;

    public Doctor(int id, String name, String qualification, String email, String phoneNumber, int consultationFee, double rating, int location_id, int specialty_id) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.consultationFee = consultationFee;
        this.rating = rating;
        this.location_id = location_id;
        this.specialty_id = specialty_id;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getSpecialty_id() {
        return specialty_id;
    }

    public void setSpecialty_id(int specialty_id) {
        this.specialty_id = specialty_id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospitalId(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
