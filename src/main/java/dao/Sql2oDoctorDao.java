package dao;

import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDoctorDao implements DoctorDao{

    private final Sql2o sql2o;

    public Sql2oDoctorDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Doctor doctor) {
        String sql = "INSERT INTO doctors (doc_name, location_id, specialty_id, qualification, consultation_fee, email, phone_number, rating) VALUES (:doc_name, :location_id, :specialty-id, :qualification, :consultation_fee, :email, :phone_number, :rating);";
        try(Connection con = sql2o.open()){
//            int id = (int) con.createQuery(sql, true)
//                    .addParameter("doc_name", doctor.getDoc_name())
//                    .addParameter("location_id", doctor.getLocation_id())
//                    .addParameter("specialty_id", doctor.getSpecialty_id())
//                    .addParameter("qualification", doctor.getQualification())
//                    .addParameter("consultation_fee", doctor.getConsultation_fee())
//                    .addParameter("email", doctor.getEmail())
//                    .addParameter("phone_number", doctor.getPhone_number())
//                    .addParameter("rating", doctor.getRating())
//                    .executeUpdate()
//                    .getKey();
//            doctor.setId(id);
        } catch (Sql2oException ex){
            System.out.println(ex + "Error Occurs");
        }
    }

    @Override
    public void addDoctorToHospital(Doctor doctor, Hospital hospital) {
        String sql = "INSERT INTO hospitals.doctors (doctor_id, hospital_id) VALUES (:doctor_id, :hospital_id);";
        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("doctor_id", doctor.getId())
//                    .addParameter("hospital_id", hospital.getId())
//                    .executeUpdate();
//            doctor.setHospitalId(hospital.getId());
        } catch (Sql2oException ex) {
            System.out.println("Error adding doctor to hospital" + ex);
        }

    }


    @Override
    public void addDoctorToSpecialty(Doctor doctor, Specialty specialty) {
        String sql = "INSERT INTO doctors.specialties (doctor_id, specialty_id) VALUES (:doctor_id, :specialty_id);";
        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("doctor_id", doctor.getId())
//                    .addParameter("specialty_id", specialty.getId())
//                    .executeUpdate();
//            doctor.setSpecialtyId(specialty.getId());
        } catch (Sql2oException ex) {
            System.out.println("Error adding doctor to specialty" + ex);
        }
    }

    @Override
    public void addDoctorToPayments(Doctor doctor, Payment payment) {
        String sql = "INSERT INTO doctors.payments (doctor_id, payment_id) VALUES (:doctor_id, :payment_id);";
        try (Connection con = sql2o.open()) {
//            con.createQuery(sql)
//                    .addParameter("doctor_id", doctor.getId())
//                    .addParameter("payment_id", payment.getId())
//                    .executeUpdate();
//            doctor.setPaymentId(payment.getId());
        } catch (Sql2oException ex) {
            System.out.println("Error adding doctor to payment" + ex);
        }
    }

    @Override
    public List<Doctor> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors")
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public Doctor findbyDoctorId(int doctor_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE id = :id")
                    .addParameter("id", doctor_id)
                    .executeAndFetchFirst(Doctor.class);
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByHospital(int hospital_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE hospital_id = :hospital_id")
                    .addParameter("hospital_id", hospital_id)
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByLocation(int location_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE location_id = :location_id")
                    .addParameter("location_id", location_id)
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public List<Doctor> getAllDoctorsBySpecialty(int specialty_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE specialty_id = :specialty_id")
                    .addParameter("specialty_id", specialty_id)
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public List<Doctor> getAllDoctorsByPayment(int payment_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE payment_id = :payment_id")
                    .addParameter("payment_id", payment_id)
                    .executeAndFetch(Doctor.class);
        }
    }

    @Override
    public void update(int doctorId, String docName, int location_id, int specialty_id, String qualification, int consultation_fee, String email, String phone_number, Double rating) {
        String sql = "UPDATE doctors SET (doc_name, location_id, specialty_id, qualification, consultation_fee, email, phone_number, rating) = (:doc_name, :location_id, :specialty_id, :qualification, :consultation_fee, :email, :phone_number, :rating) WHERE id = :id;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("doc_name", docName)
                    .addParameter("location_id", location_id)
                    .addParameter("specialty_id", specialty_id)
                    .addParameter("qualification", qualification)
                    .addParameter("consultation_fee", consultation_fee)
                    .addParameter("email", email)
                    .addParameter("phone_number", phone_number)
                    .addParameter("rating", rating)
                    .addParameter("id", doctorId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println("Error updating doctor" + ex);
        }
    }


    @Override
    public void deleteById(int doctorId) {
        String sql = "DELETE FROM doctors WHERE id = :id;";
        String deleteJoin = "DELETE FROM hospitals.doctors WHERE doctor_id = :id;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", doctorId)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("id", doctorId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println("Error deleting doctor" + ex);
        }

    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM doctors;";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println("Error clearing doctors" + ex);
        }

    }
}
