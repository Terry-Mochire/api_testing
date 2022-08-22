package dao;

import models.Specialty;

import java.util.List;

public interface SpecialtyDao {

    //Create

    //Read
    List<Specialty> findAllSpecialtiesInHospital(int hospital_id);
    //Update

    //Delete
}
