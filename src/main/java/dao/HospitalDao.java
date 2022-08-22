package dao;

import models.Hospital;
import models.Service;
import models.Specialty;

import java.util.List;

public interface HospitalDao {
    //Create
    void add(Hospital hospital);

    //Read

    List<Hospital> getAll();
    Hospital findById(int hospital_id);
    Hospital findHospitalsByName(String hospital_name);
    List<Hospital> findHospitalsByLocationId(int location_id);
    List<Hospital> findHospitalsByLocationName(String location_name);
    List<Hospital> findHospitalByWorkingHours(String working_hours);

    List<Hospital> findHospitalByRatings(double ratings);


    //Update

    void update(int hospital_id, String hospital_name, String operating_hours, String email, String phone_number, Double rating, int location_id);


    //Delete
    void deleteById(int hospital_id);
    void clearAll();
}
