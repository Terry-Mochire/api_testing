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

    Doctor findbyDoctorName(String name);
    List<Doctor> getAllDoctorsByHospital(int hospital_id);

    List<Doctor> getAllDoctorsByLocation(int location_id);

    List<Doctor> getAllDoctorsBySpecialty(int specialty_id);

    List<Doctor> getAllDoctorsByPaymentId(int payment_id);

    List<Doctor> findAllDoctorsByPaymentName(String paymentName);

    //Update

    void update(int doctorId, String docName, int location_id, int specialty_id, String qualification, int consultation_fee, String email, String phone_number, Double rating);

    //Delete

    Object deleteById(int doctorId);

    void clearAll();
}
