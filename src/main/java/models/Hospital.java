package models;

public class Hospital {

    private int id;
    private String name;
    private String operatingHours;
    private String email;
    private String phoneNumber;
    private double rating;
    private int location_id;

    public Hospital(int id, String name, String operatingHours, String email, String phoneNumber, double rating, int location_id) {
        this.id = id;
        this.name = name;
        this.operatingHours = operatingHours;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.location_id = location_id;
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

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
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
}
