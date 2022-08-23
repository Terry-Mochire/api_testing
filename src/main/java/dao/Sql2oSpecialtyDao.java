package dao;

import models.Doctor;
import models.Hospital;
import models.Location;
import models.Specialty;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSpecialtyDao implements SpecialtyDao {
    private final Sql2o sql2o;
    public Sql2oSpecialtyDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void add(Specialty specialty) {
        try (Connection con = sql2o.open()) {
            String SQL_INSERT_SPECIALTY = "INSERT INTO specialties (name) VALUES (:name);";
            int id = (int) con.createQuery(SQL_INSERT_SPECIALTY, true)
                    .addParameter("name", specialty.getName())
                    .bind(specialty)
                    .executeUpdate()
                    .getKey();
            specialty.setId(id);
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add new specialty to the database.");
        }
    }

    @Override
    public void addSpecialtyToHospital(Specialty specialty, Hospital hospital) {
        try (Connection con = sql2o.open()) {
            String SQL_INSERT_SPECIALTY_TO_HOSPITAL = "INSERT INTO hospitals.specialties (specialty_id, hospital_id) VALUES (:specialty_id, :hospital_id);";
            con.createQuery(SQL_INSERT_SPECIALTY_TO_HOSPITAL)
                    .addParameter("specialty_id", specialty.getId())
                    .addParameter("hospital_id", hospital.getId())
                    .executeUpdate();
            specialty.setHospital_id(hospital.getId());
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add specialty to Hospital");
        }
    }

    @Override
    public void addSpecialtyToDoctor(Specialty specialty, Doctor doctor) {
        try (Connection con = sql2o.open()) {
            String SQL_INSERT_SPECIALTY_TO_DOCTOR = "INSERT INTO doctors.specialties (doctor_id, specialty_id) (:specialty_id, :doctor_id);";
            con.createQuery(SQL_INSERT_SPECIALTY_TO_DOCTOR)
                    .addParameter("doctor_id", doctor.getId())
                    .addParameter("specialty_id", specialty.getId())
                    .executeUpdate();
            specialty.setDoctor_id(doctor.getId());
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add specialty to Doctor");
        }
    }

    @Override
    public void addSpecialtyToLocation(Specialty specialty, Location location) {
        try (Connection con = sql2o.open()) {
            String SQL_INSERT_SPECIALTY_TO_LOCATION = "INSERT INTO locations.specialty (specialty_id, location_id) VALUES (:specialty_id, :location_id);";
            con.createQuery(SQL_INSERT_SPECIALTY_TO_LOCATION)
                    .addParameter("specialty_id", specialty.getId())
                    .addParameter("location_id", location.getId())
                    .executeUpdate();
            specialty.setLocation_id(location.getId());
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add specialty to Location");
        }
    }

    @Override
    public List<Specialty> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM specialties")
                    .executeAndFetch(Specialty.class);
        }
    }

    @Override
    public Specialty findById(int specialty_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM specialties WHERE id = :specialty_id")
                    .addParameter("specialty_id", specialty_id)
                    .executeAndFetchFirst(Specialty.class);
        }
    }

    @Override
    public Specialty findByName(String specialtyName) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM specialties WHERE name = :specialtyName")
                    .addParameter("specialtyName", specialtyName)
                    .executeAndFetchFirst(Specialty.class);
        }
    }

    @Override
    public List<Specialty> findAllSpecialtiesInHospital(int hospital_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_ALL_SPECIALTIES_IN_HOSPITAL = "SELECT * FROM specialties WHERE id = (SELECT specialty_id FROM hospitals.specialties = :hospital_id);";
            return con.createQuery(SQL_GET_ALL_SPECIALTIES_IN_HOSPITAL)
                    .addParameter("hospital_id", hospital_id)
                    .executeAndFetch(Specialty.class);
        }
    }

    @Override
    public List<Doctor> findAllDoctorsInSpecialty(int specialty_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_ALL_DOCTORS_IN_SPECIALTY = "SELECT * FROM doctors WHERE id = (SELECT doctor_id FROM doctors.specialties WHERE specialty_id = :specialty_id);";
            return con.createQuery(SQL_GET_ALL_DOCTORS_IN_SPECIALTY)
                    .addParameter("specialty_id", specialty_id)
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public void update(int specialty_id, String specialty_name) {
        try (Connection con = sql2o.open()){
            String SQL_UPDATE_SERVICE = "UPDATE specialties SET name = :specialty_name WHERE id = :specialty_id";
            con.createQuery(SQL_UPDATE_SERVICE)
                    .addParameter("specialty_name", specialty_name)
                    .addParameter("specialty_id", specialty_id)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println( e + "unable to update specialties table");
        }

    }

    @Override
    public void deleteByid(int specialty_id) {
        try(Connection con = sql2o.open()){
            con.createQuery("DELETE FROM specialties WHERE id = :specialty_id")
                    .addParameter("specialty_id", specialty_id)
                    .executeUpdate();
        } catch (Exception e){
            System.out.println(e + "Unable to delete specialty by id");
        }

    }

    @Override
    public void clearAll() {
        try(Connection con = sql2o.open()) {
            con.createQuery("DELETE FROM specialties;")
                    .executeUpdate();

        } catch (Exception e) {
            System.out.println(e + "Unable to delete all specialties");
        }
    }
}

