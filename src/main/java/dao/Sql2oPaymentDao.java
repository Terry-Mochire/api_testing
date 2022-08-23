package dao;

import models.Doctor;
import models.Hospital;
import models.Payment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oPaymentDao implements PaymentDao {
    private final Sql2o sql2o;

    public Sql2oPaymentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add(Payment payment) {
        String sql = "INSERT INTO payments (name) VALUES (:name)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .addParameter("name", payment.getName())
                    .bind(payment)
                    .executeUpdate()
                    .getKey();
            payment.setId(id);
        } catch (Exception e) {
            System.out.println( e + "Unable to add new payment to the database.");
        }
    }

    @Override
    public List<Payment> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM payments")
                    .executeAndFetch(Payment.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get all payments from the database.");
            return null;
        }
    }

    @Override
    public Payment findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM payments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Payment.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get payment from the database.");
            return null;
        }
    }

    @Override
    public Payment findByName(String name) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM payments WHERE name = :name")
                    .addParameter("name", name)
                    .executeAndFetchFirst(Payment.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get payment from the database.");
            return null;
        }
    }

    @Override
    public List<Doctor> findAllDoctorsByPaymentName(String paymentName) {
        Payment payment = new Sql2oPaymentDao(sql2o).findByName(paymentName);
        int paymentId = payment.getId();
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE id = (SELECT doctor_id FROM doctors.payments WHERE payment_id = :payment_id);")
                    .addParameter("payment_id", payment.getId())
                    .executeAndFetch(Doctor.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get doctors from the database.");
            return null;
        }

    }

    @Override
    public List<Doctor> findAllDoctorsByPaymentId(int payment_id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM doctors WHERE id = (SELECT doctor_id FROM doctors.payments WHERE payment_id = :payment_id);")
                    .addParameter("payment_id", payment_id)
                    .executeAndFetch(Doctor.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get doctors from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findAllHospitalsWithPaymentName(String paymentName) {
        Payment payment = new Sql2oPaymentDao(sql2o).findByName(paymentName);
        int payment_id = payment.getId(); // get the payment_id from the payment object
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_PAYMENT_ID = "SELECT * FROM hospitals WHERE id = (SELECT hospital_id FROM hospitals.payment WHERE payment_id = :payment_id);";
            return con.createQuery(SQL_GET_HOSPITAL_BY_PAYMENT_ID)
                    .addParameter("payment_id", payment_id)
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by payment id from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findAllHospitalsWithPaymentId(int payment_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_PAYMENT_ID = "SELECT * FROM hospitals WHERE id = (SELECT hospital_id FROM hospitals.payment WHERE payment_id = :payment_id);";
            return con.createQuery(SQL_GET_HOSPITAL_BY_PAYMENT_ID)
                    .addParameter("payment_id", payment_id)
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by payment id from the database.");
            return null;
        }
    }

    @Override
    public void update(int id, String name) {
        String sql = "UPDATE payments SET name = :name WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e + "Unable to update payment in the database.");
        }
    }

    @Override
    public Object deleteById(int id) {
        String sql = "DELETE FROM payments WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e + "Unable to delete payment from the database.");
        }
        return "Payment Deleted";
    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM payments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e + "Unable to clear all payments from the database.");
        }
    }
}
