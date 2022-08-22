package dao;

import models.Hospital;
import models.Service;

import java.util.List;

public interface ServiceDao {
    //Create
    void add(Service service);
    void addServiceToHospital(Service service, Hospital hospital);


    //Read
    List<Service> getAll();
    Service findById(int service_id);
    Service findByName (String serviceName);
    List<Service> findAllServicesInHospital(int hospital_id);

    //Update
    void update(int id, String name);


    //Delete
    void deleteById(int id);
    void clearAll();
}
