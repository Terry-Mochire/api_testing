package dao;

import models.*;

import java.util.List;

public interface DoctorDao {

    //Create
    void add(Doctor doctor);
    void addDoctorToHospital(Doctor doctor, Hospital hospital);
    void addDoctorToSpecialty(Doctor doctor, Specialty specialty);

    void addDoctorToPayments(Doctor doctor, Payment payment);

    //Read

    List<Doctor> getAll();

    Doctor findbyDoctorId(int doctor_id);

    List<Doctor> getAllDoctorsByHospital(int hospital_id);

    List<Doctor> getAllDoctorsByLocation(int location_id);

    List<Doctor> getAllDoctorsBySpecialty(int specialty_id);

    List<Doctor> getAllDoctorsByPayment(int payment_id);

    //Update

    void update(int doctorId, String docName, int location_id, int specialty_id, String qualification, int consultation_fee, String email, String phone_number, Double rating);

    //Delete

    void deleteById(int doctorId);

    void clearAll();
}
