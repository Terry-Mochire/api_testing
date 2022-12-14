package dao;

import models.*;

import java.util.List;

public interface SpecialtyDao {

    //Create
    void add(Specialty specialty);
    void addSpecialtyToHospital(Specialty specialty, Hospital hospital);

    void addSpecialtyToDoctor(Specialty specialty, Doctor doctor);
    void addSpecialtyToLocation(Specialty specialty, Location location);


    //Read
    List<Specialty> getAll();

    Specialty findById(int specialty_id);
    Specialty findByName (String specialtyName);
    List<Specialty> findAllSpecialtiesInHospital(int hospital_id);
    List<Doctor> findAllDoctorsInSpecialty(int specialty_id);


    //Update
    void update(int specialty_id, String specialty_name );

    //Delete

    void deleteByid(int specialty_id);
    void clearAll();
}
