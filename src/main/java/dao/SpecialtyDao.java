package dao;

import models.Doctor;
import models.Hospital;
import models.Payment;
import models.Specialty;

import java.util.List;

public interface SpecialtyDao {

    //Create
    void add(Specialty specialty);
    void addSpecialtyToHospital(Specialty specialty, Hospital hospital);


    //Read
    List<Specialty> getAll();

    Specialty findById(int specialty_id);
    Specialty findByName (String specialtyName);
    List<Specialty> findAllSpecialtiesInHospital(int hospital_id);
    List<Doctor> findAllDoctorsInSpecialty(int specialty_id);


    //Update

    //Delete
}
