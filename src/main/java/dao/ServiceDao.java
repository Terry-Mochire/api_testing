package dao;

import models.Hospital;
import models.Location;
import models.Service;

import java.util.List;

public interface ServiceDao {
    //Create
    void add(Service service);
    void addServiceToHospital(Service service, Hospital hospital);
    void addServiceToLocation(Service service, Location location);


    //Read
    List<Service> getAll();
    Service findById(int service_id);
    Service findByName (String serviceName);
    List<Service> findAllServicesInHospital(int hospital_id);
    List<Hospital> findAllHospitalsWithService(int service_id);

    //Update
    void update(int id, String name);


    //Delete
    void deleteById(int id);
    void clearAll();
}
