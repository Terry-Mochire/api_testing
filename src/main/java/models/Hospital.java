package models;

public class Hospital {

    private int id;
    private String name;
    private String operating_hours;
    private String email;
    private String phone_number;
    private double rating;
    private int location_id;

    private int payment_id;

    public Hospital(int id, String name, String operating_hours, String email, String phone_number, double rating, int location_id) {
        this.id = id;
        this.name = name;
        this.operating_hours = operating_hours;
        this.email = email;
        this.phone_number = phone_number;
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
        return operating_hours;
    }

    public void setOperatingHours(String operating_hours) {
        this.operating_hours = operating_hours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
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

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
