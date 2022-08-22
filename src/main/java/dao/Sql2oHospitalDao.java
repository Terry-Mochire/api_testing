package dao;

import models.Hospital;
import models.Location;
import models.Service;
import models.Specialty;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oHospitalDao implements HospitalDao {

    private final Sql2o sql2o;

    public Sql2oHospitalDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Hospital hospital) {
        try(Connection con = sql2o.open()) {
            String SQL_INSERT_HOSPITAL = "INSERT INTO hospitals (name, operating_hours, email, phone_number, rating, location_id) VALUES (:name, :operating_hours, :email, :phone_number, :rating, :location_id)";
            int id = (int) con.createQuery(SQL_INSERT_HOSPITAL, true)
                    .bind(hospital)
                    .executeUpdate()
                    .getKey();
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to add new hospital to the database.");
        }

    }

    @Override
    public List<Hospital> getAll() {
        try(Connection con = sql2o.open()) {
            String SQL_GET_ALL_HOSPITALS = "SELECT * FROM hospitals";
            return  con.createQuery(SQL_GET_ALL_HOSPITALS, true)
                    .executeAndFetch(Hospital.class);
        } catch (Sql2oException e) {
            System.out.println(e + "Unable to get all hospitals");
            return null;
        }
    }

    @Override
    public Hospital findById(int hospital_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_ID = "SELECT * FROM hospitals WHERE id = :hospital_id";
            return con.createQuery(SQL_GET_HOSPITAL_BY_ID)
                    .addParameter("hospital_id", hospital_id)
                    .executeAndFetchFirst(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by hospital id from the database.");
            return null;
        }
    }

    @Override
    public Hospital findHospitalsByHospitalName(String hospital_name) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_NAME = "SELECT * FROM hospitals WHERE name = :hospital_name";
            return con.createQuery(SQL_GET_HOSPITAL_BY_NAME)
                    .addParameter("hospital_name", hospital_name)
                    .executeAndFetchFirst(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by name from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findHospitalsByLocationId(int location_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_LOCATION_ID = "SELECT * FROM hospitals WHERE location_id = :location_id";
            return con.createQuery(SQL_GET_HOSPITAL_BY_LOCATION_ID)
                    .addParameter("location_id", location_id)
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by location id from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findHospitalsByLocationName(String location_name) {
        try (Connection con = sql2o.open()){
            Location location = new Sql2oLocationDao(sql2o).findByName(location_name);
            int location_id = location.getId(); // get the location_id from the location object

            return con.createQuery("SELECT * FROM hospitals WHERE location_id = :location_id;")
                    .addParameter("location_id", location_id )
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get hospital by location name from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findHospitalByWorkingHours(String working_hours) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_WORKING_HOURS = "SELECT * FROM hospitals WHERE working_hours = :working_hours";
            return con.createQuery(SQL_GET_HOSPITAL_BY_WORKING_HOURS)
                    .addParameter("working_hours", working_hours)
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println( e + "Unable to get hospital by working hours from the database.");
            return null;
        }
    }

    @Override
    public List<Hospital> findHospitalByRatings(double ratings) {
        try (Connection con = sql2o.open()){
            String SQL_GET_HOSPITAL_BY_RATING = "SELECT * FROM hospitals WHERE rating = :ratings";
            return con.createQuery(SQL_GET_HOSPITAL_BY_RATING)
                    .addParameter("ratings", ratings)
                    .executeAndFetch(Hospital.class);
        } catch (Exception e) {
            System.out.println( e + "Unable to get hospital by rating from the database.");
            return null;
        }
    }


    @Override
    public void update(int hospital_id, String hospital_name, String operating_hours, String email, String phone_number, Double rating, int location_id) {
        try (Connection con = sql2o.open()) {
            String SQL_UPDATE_HOSPITAL = "UPDATE hospitals SET name = :name, operating_hours = :operating_hours, email = :email, phone_number = :phone_number, rating = :rating, location_id = :location_id WHERE id = :id";
            con.createQuery(SQL_UPDATE_HOSPITAL)
                    .addParameter("id", hospital_id)
                    .addParameter("name", hospital_name)
                    .addParameter("operating_hours", operating_hours)
                    .addParameter("email", email)
                    .addParameter("phone_number", phone_number)
                    .addParameter("rating", rating)
                    .addParameter("location_id", location_id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println( e + "Unable to update hospital in the database.");
        }
    }

    @Override
    public void deleteById(int hospital_id) {
        try (Connection con = sql2o.open()) {
            String SQL_DELETE_HOSPITAL = "DELETE FROM hospitals WHERE id = :id";
            con.createQuery(SQL_DELETE_HOSPITAL)
                    .addParameter("id", hospital_id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e + "Unable to delete hospital by id from the database.");
        }

    }

    @Override
    public void clearAll() {
        try (Connection con = sql2o.open()) {
            String SQL_CLEAR_ALL_HOSPITALS = "DELETE FROM hospitals";
            con.createQuery(SQL_CLEAR_ALL_HOSPITALS)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println(e + "Unable to clear all hospitals from the database.");
        }
    }
}
