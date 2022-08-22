package dao;

import models.Doctor;
import models.Hospital;
import models.Payment;

import java.util.List;

public interface PaymentDao {

    //Create
    void add(Payment payment);

    //Read
    List<Payment> getAll();
    Payment findById(int id);
    Payment findByName(String name);

    List<Doctor> findAllDoctorsByPaymentName(String paymentName);
    List<Doctor> findAllDoctorsByPaymentId(int payment_id);
    List<Hospital> findAllHospitalsWithPaymentName(String paymentName);
    List<Hospital> findAllHospitalsWithPaymentId(int payment_id);

    //Update
    void update(int id, String name);

    //Delete
    void deleteById(int id);
    void clearAll();
}
