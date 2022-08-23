package dao;

import models.Hospital;
import models.Location;
import models.Service;
import models.Specialty;

import java.util.List;

public interface LocationDao {

    //Create
    void add(Location location);

    //Read

    List<Location> getAll();
    Location findById(int id);
    Location findByName(String name);
    List<Location> findAllLocationsWithSpecialtyName(String specialtyName);
    List<Location> findAllLocationsWithServiceName(String serviceName);

    List<Location> findAllLocationsWithSpecialtyId(int specialty_id);

    List<Location> findAllLocationsWithServiceId(int service_id);

    List<Hospital> findHospitalsByLocationName(String location_name);
    List<Service> findServicesByLocationName(String location_name);
    List<Specialty> findSpecialtiesByLocationName(String location_name);
    //Update
    void update(int id, String name);

    //Delete

    void deleteById(int location_id);
    void clearAll();
}
