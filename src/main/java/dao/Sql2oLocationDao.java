package dao;

import models.Hospital;
import models.Location;
import models.Service;
import models.Specialty;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oLocationDao implements LocationDao {
    private final Sql2o sql2o;

    public Sql2oLocationDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Location location) {
        try(Connection connection = sql2o.open() ){
            int id = (int) connection.createQuery("INSERT INTO locations (name) VALUES (:name)")
                    .bind(location)
                    .executeUpdate()
                    .getKey();

        } catch (Exception e) {
            System.out.println("Unable to add new location to the database.");
        }
    }

    @Override
    public List<Location> getAll() {
        try(Connection connection = sql2o.open() ){
            return connection.createQuery("SELECT * FROM locations")
                    .executeAndFetch(Location.class);
        } catch (Exception e) {
            System.out.println("Unable to get all locations from the database.");
            return null;
        }
    }

    @Override
    public Location findById(int location_id) {
        try(Connection connection = sql2o.open() ){
            return connection.createQuery("SELECT * FROM locations WHERE id = :location_id")
                    .addParameter("location_id", location_id)
                    .executeAndFetchFirst(Location.class);
        } catch (Exception e) {
            System.out.println("Unable to get location from the database.");
            return null;
        }
    }

    @Override
    public Location findByName(String name) {
        try(Connection connection = sql2o.open() ){
            return connection.createQuery("SELECT * FROM locations WHERE name = :name")
                    .addParameter("name", name)
                    .executeAndFetchFirst(Location.class);
        } catch (Exception e) {
            System.out.println("Unable to get location from the database.");
            return null;
        }
    }

    @Override
    public List<Location> findAllLocationsWithSpecialtyName(String specialtyName) {
        try {
            Specialty specialty = new Sql2oSpecialtyDao(sql2o).findByName(specialtyName);
            int specialty_id = specialty.getId(); // get the specialty_id from the specialty object

            try (Connection con = sql2o.open()) {
                return con.createQuery("SELECT * FROM locations WHERE id = (SELECT location_id FROM locations.specialty WHERE specialty_id = :specialty_id)")
                        .addParameter("specialty_id", specialty_id)
                        .executeAndFetch(Location.class);
            }

        } catch (Sql2oException e) {
            System.out.println( e + " Unable to get location from the database.");
            return null;
        }

    }

    @Override
    public List<Location> findAllLocationsWithServiceName(String serviceName) {
        try (Connection con = sql2o.open()) {
            Service service = new Sql2oServiceDao(sql2o).findByName(serviceName);
            int service_id = service.getId(); // get the service_id from the service object
            return con.createQuery("SELECT * FROM locations WHERE id = (SELECT location_id FROM locations.services WHERE services_id = :service_id); ")
                    .addParameter("service_id", service_id)
                    .executeAndFetch(Location.class);
        } catch (Exception e) {
            System.out.println(e + "Unable to get all locations with service from the database.");
            return null;
        }
    }

    @Override
    public List<Location> findAllLocationsWithSpecialtyId(int specialty_id) {
        try(Connection connection = sql2o.open() ){
            return connection.createQuery("SELECT * FROM locations WHERE id = (SELECT location_id FROM locations.specialty WHERE specialty_id = :specialty_id);")
                    .addParameter("specialty_id", specialty_id)
                    .executeAndFetch(Location.class);
        } catch (Exception e) {
            System.out.println("Unable to get locations with specialty from the database.");
            return null;
        }
    }

    @Override
    public List<Location> findAllLocationsWithServiceId(int service_id) {
        try(Connection connection = sql2o.open() ){
            return connection.createQuery("SELECT * FROM locations WHERE id = (SELECT location_id FROM locations.services WHERE services_id = :service_id);")
                    .addParameter("service_id", service_id)
                    .executeAndFetch(Location.class);
        }
    }

    @Override
    public List<Hospital> findHospitalsByLocationName(String location_name) {
        try(Connection connection = sql2o.open() ){
            Location location = new Sql2oLocationDao(sql2o).findByName(location_name);
            int location_id = location.getId(); // get the location_id from the location object

            return connection.createQuery("SELECT * FROM hospitals WHERE location_id = :location_id;")
                    .addParameter("location_id", location_id )
                    .executeAndFetch(Hospital.class);
        }
    }

    @Override
    public List<Service> findServicesByLocationName(String location_name) {
        try(Connection connection = sql2o.open() ){
            Location location = new Sql2oLocationDao(sql2o).findByName(location_name);
            int location_id = location.getId(); // get the location_id from the location object

            return connection.createQuery("SELECT * FROM services WHERE id = (SELECT service_id FROM services.locations WHERE location_id = :location_id);")
                    .addParameter("location_id", location_id)
                    .executeAndFetch(Service.class);
        }
    }

    @Override
    public List<Specialty> findSpecialtiesByLocationName(String location_name) {
        return null;
    }

    @Override
    public void update(int id, String name) {
        try (Connection con = sql2o.open()){
            con.createQuery("UPDATE locations SET name = :name WHERE id = :id")
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to update location in the database.");
        }

    }

    @Override
    public Object deleteById(int location_id) {
        try (Connection con = sql2o.open()){
            con.createQuery("DELETE FROM locations WHERE id = :location_id")
                    .addParameter("location_id", location_id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to delete location from the database.");
        }
        return "Location Deleted";
    }

    @Override
    public void clearAll() {
        try (Connection con = sql2o.open()) {
            String SQL_CLEAR_ALL_HOSPITALS = "DELETE FROM loactions;";
            con.createQuery(SQL_CLEAR_ALL_HOSPITALS)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Unable to clear all locations from the database.");
        }
    }
}
