package dao;

import models.Doctor;
import models.Hospital;
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
            String SQL_INSERT_SPECIALTY_HOSPITAL = "INSERT INTO hospitals.specialty (hospital_id, specialty_id,) VALUES (:hospital_id, :specialty_id);";
            con.createQuery(SQL_INSERT_SPECIALTY_HOSPITAL)
                    .addParameter("hospital_id", hospital.getId())
                    .addParameter("specialty_id", specialty.getId())
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add specialty to hospital.");
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
}

