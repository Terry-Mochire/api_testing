package models;

public class Service {


    private int id;
    private String name;

    private int hospital_id;

    public Service(int id, String name, int hospital_id) {
        this.id = id;
        this.name = name;
        this.hospital_id = hospital_id;
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

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }
}
