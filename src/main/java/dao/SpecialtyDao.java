package dao;

import models.*;

import java.util.List;

public interface SpecialtyDao {

    //Create
    void add(Specialty specialty);
    void addSpecialtyToHospital(Specialty specialty, Hospital hospital);

    void addSpecialtyToLocation(Specialty specialty, Location location);


    //Read
    List<Specialty> getAll();

    Specialty findById(int specialty_id);
    Specialty findByName (String specialtyName);
    List<Specialty> findAllSpecialtiesInHospital(int hospital_id);
    List<Doctor> findAllDoctorsInSpecialty(int specialty_id);


    //Update

    //Delete
}
