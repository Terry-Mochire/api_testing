package models;

public class Doctor {

    private int id;
    private String doc_name;
    private String qualification;
    private String email;
    private String phone_number;
    private int consultation_fee;
    private double rating;
    private int location_id;
    private int specialty_id;
    private int hospital_id;
    private int payment_id;

    public Doctor(int id, String doc_name, String qualification, String email, String phone_number, int consultation_fee, double rating, int location_id, int specialty_id) {
        this.id = id;
        this.doc_name = doc_name;
        this.qualification = qualification;
        this.email = email;
        this.phone_number = phone_number;
        this.consultation_fee = consultation_fee;
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
        return doc_name;
    }

    public void setName(String doc_name) {
        this.doc_name = doc_name;
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
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getConsultationFee() {
        return consultation_fee;
    }

    public void setConsultationFee(int consultation_fee) {
        this.consultation_fee = consultation_fee;
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
