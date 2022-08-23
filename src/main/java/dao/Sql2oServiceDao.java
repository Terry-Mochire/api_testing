package dao;

import models.Hospital;
import models.Location;
import models.Service;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oServiceDao implements ServiceDao {
    private final Sql2o sql2o;

    public Sql2oServiceDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Service service) {
        try( Connection con = sql2o.open()){
            String SQL_INSERT_SERVICE = "INSERT INTO services (name) VALUES (:name);";
            int id = (int) con.createQuery(SQL_INSERT_SERVICE, true)
                    .addParameter("name", service.getName())
                    .bind(service)
                    .executeUpdate()
                    .getKey();
            service.setId(id);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to add new service to the database.");
        }
    }

    @Override
    public void addServiceToHospital(Service service, Hospital hospital) {
        try (Connection con = sql2o.open()){
            String SQL_INSERT_SERVICE_TO_HOSPITAL = "INSERT INTO hospitals.services ( hospital_id, services_id) VALUES (:hospital_id, :service_id);";
            con.createQuery(SQL_INSERT_SERVICE_TO_HOSPITAL)
                    .addParameter("hospital_id", hospital.getId())
                    .addParameter("service_id", service.getId())
                    .executeUpdate();
            service.setHospital_id(hospital.getId());
        } catch (Sql2oException e){
            System.out.println(e + "Unable to add service to Hospital");
        }
    }

    @Override
    public void addServiceToLocation(Service service, Location location) {
        try (Connection con = sql2o.open()){
            String SQL_INSERT_SERVICE_TO_LOCATION = "INSERT INTO locations.services (services_id, location_id) VALUES (:service_id, :location_id);";
            con.createQuery(SQL_INSERT_SERVICE_TO_LOCATION)
                    .addParameter("service_id", service.getId())
                    .addParameter("location_id", location.getId())
                    .executeUpdate();
            service.setLocation_id(location.getId());
        } catch (Sql2oException e){
            System.out.println(e + "Unable to add service to Location");
        }
    }

    @Override
    public List<Service> getAll() {
        try (Connection con = sql2o.open()){
            String SQL_GET_ALL_SERVICES = "SELECT * FROM services";
            return con.createQuery(SQL_GET_ALL_SERVICES)
                    .executeAndFetch(Service.class);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to get all");
            return null;
        }
    }

    @Override
    public Service findById(int service_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_SERVICE_BY_ID = "SELECT * FROM services WHERE id = :service_id";
            return con.createQuery(SQL_GET_SERVICE_BY_ID)
                    .addParameter("service_id", service_id)
                    .executeAndFetchFirst(Service.class);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to find By ID");
            return null;
        }
    }

    @Override
    public Service findByName(String serviceName) {
        try (Connection con = sql2o.open()){
            String SQL_GET_SERVICE_BY_NAME = "SELECT * FROM services WHERE name = :serviceName";
            return con.createQuery(SQL_GET_SERVICE_BY_NAME)
                    .addParameter("serviceName", serviceName)
                    .executeAndFetchFirst(Service.class);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to find By Name");
            return null;
        }
    }

    @Override
    public List<Service> findAllServicesInHospital(int hospital_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_ALL_SERVICES_IN_HOSPITAL = "SELECT * FROM services WHERE id = ( SELECT services_id FROM hospitals.services WHERE id = :hospital_id)";
            return con.createQuery(SQL_GET_ALL_SERVICES_IN_HOSPITAL)
                    .addParameter("hospital_id", hospital_id)
                    .executeAndFetch(Service.class);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to find all hospital services");
            return null;
        }
    }

    @Override
    public List<Hospital> findAllHospitalsWithService(int service_id) {
        try (Connection con = sql2o.open()){
            String SQL_GET_ALL_HOSPITALS_WITH_SERVICE = "SELECT * FROM hospitals INNER JOIN hospitals.services ON hospitals.id = hospitals.services.hospital_id WHERE services.services_id = :service_id";
            return con.createQuery(SQL_GET_ALL_HOSPITALS_WITH_SERVICE)
                    .addParameter("service_id", service_id)
                    .executeAndFetch(Hospital.class);
        } catch (Sql2oException e){
            System.out.println( e + "Unable to find all hospitals with the service");
            return null;
        }
    }

    @Override
    public void update(int id, String name) {
        try (Connection con = sql2o.open()){
            String SQL_UPDATE_SERVICE = "UPDATE services SET name = :name WHERE id = :id";
            con.createQuery(SQL_UPDATE_SERVICE)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println( e + "unable to update");
        }
    }

    @Override
    public Object deleteById(int id) {
        try (Connection con = sql2o.open()){
            String SQL_DELETE_SERVICE_BY_ID = "DELETE FROM services WHERE id = :id";
            con.createQuery(SQL_DELETE_SERVICE_BY_ID)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println( e + "Unable to delete by id");
        }
        return "Service Deleted";
    }

    @Override
    public void clearAll() {
        try (Connection con = sql2o.open()){
            String SQL_CLEAR_ALL_SERVICES = "DELETE FROM services";
            con.createQuery(SQL_CLEAR_ALL_SERVICES)
                    .executeUpdate();
        } catch (Sql2oException e){
            System.out.println( e + "unable to clear all");
        }
    }
}
