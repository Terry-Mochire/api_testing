import com.google.gson.Gson;
import dao.*;
import exceptions.ApiException;
import models.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String connectionString = "jdbc:postgresql://localhost:5432/mpate_daktari";
        Sql2o sql2o = new Sql2o(connectionString, "terry", "Postgres4041*");
        Sql2oDoctorDao doctor = new Sql2oDoctorDao(sql2o);
        Sql2oHospitalDao hospital = new Sql2oHospitalDao(sql2o);
        Sql2oLocationDao location = new Sql2oLocationDao(sql2o);
        Sql2oPaymentDao payment = new Sql2oPaymentDao(sql2o);
        Sql2oServiceDao service = new Sql2oServiceDao(sql2o);
        Sql2oSpecialtyDao speciality = new Sql2oSpecialtyDao(sql2o);
        Connection conn = sql2o.open();

        //1.DOCTOR
        //Create
        //a). new
        //Do we need such a location to be set before adding a doctor?
        post("locations/:location_Id/doctors/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Doctor newDoctor = gson.fromJson(req.body(), Doctor.class);
            newDoctor.setLocation_id(locationId);
            doctor.add(newDoctor);
            res.status(201);
            return gson.toJson(newDoctor);
        });

        //b). add to hospital

        //Read
        //a). get all doctors
        get("/doctors", "application/json", (req, res)->{
            if (doctor.getAll().size() > 0) {
                List<Doctor> doctors = doctor.getAll();
                return gson.toJson(doctors);
            }
            else {
                return "{\"message\":\"I'm sorry, but no doctors are currently listed in the database.\"}";
            }
        });

        //b). get doctor by id
        get("/doctors/id/:doctor_Id", "application/json", (req, res)->{
            int doctorId = Integer.parseInt(req.params("doctor_Id"));
            if (doctor.findbyDoctorId(doctorId) == null){
                throw new ApiException(404, String.format("No doctor with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(doctor.findbyDoctorId(doctorId));
        });

        //c). get doctors by speciality
        get("/specialities/:speciality_id/doctors", "application/json", (req, res)->{
            int specialityId = Integer.parseInt(req.params(":speciality_id"));
            List<Doctor> allDoctorsBySpeciality;
            if (speciality == null){
                throw new ApiException(404, String.format("No speciality with the id: \"%s\" exists", req.params("id")));
            }
            allDoctorsBySpeciality = doctor.getAllDoctorsBySpecialty(specialityId);
            return gson.toJson(allDoctorsBySpeciality);
        });

        //d). get doctors by hospitals
//        get("/hospitals/:hospital_id/doctors", "application/json", (req, res)->{//can we change this to hospital name?
//            int hospitalId = Integer.parseInt(req.params(":hospital_id"));
//            List<Doctor> allDoctorsByHospital;
//            if (hospital == null){
//                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
//            }
//            allDoctorsByHospital = doctor.getAllDoctorsByHospital(hospitalId);
//            return gson.toJson(allDoctorsByHospital);
//        });

        //e). get doctors by payment methods
//        get("/payments/:payment_id/doctors", "application/json", (req, res)->{
//            int paymentId = Integer.parseInt(req.params(":payment_id"));
//            List<Doctor> allDoctorsByPayment;
//            if (payment == null){
//                throw new ApiException(404, String.format("No payment with the id: \"%s\" exists", req.params("id")));
//            }
//            allDoctorsByPayment = doctor.getAllDoctorsByPaymentId(paymentId);
//            return gson.toJson(allDoctorsByPayment);
//        });

        //f). get doctors by location
        get("/locations/:location_id/doctors", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params(":location_id"));
            List<Doctor> allDoctorsByLocation;
            if (location == null){
                throw new ApiException(404, String.format("No location with the id: \"%s\" exists", req.params("id")));
            }
            allDoctorsByLocation = doctor.getAllDoctorsByLocation(locationId);
            return gson.toJson(allDoctorsByLocation);
        });

        //g). get by name
        get("/doctors/names/:doc_name", "application/json", (req, res)->{
            String docName = req.params("doc_name");
            Doctor doctor1 = doctor.findbyDoctorName(docName);
            if (doctor1 == null){
                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(doctor1);
        });

        //Update

        //Delete
        delete("/doctors/:doctor_Id/delete", "application/json", (req, res) -> {
            int doctorId = Integer.parseInt(req.params("doctor_Id"));
            res.status(200);
            return gson.toJson(doctor.deleteById(doctorId));
        });


        //2.HOSPITAL
        //Create
        post("locations/:location_Id/hospitals/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Hospital newHospital = gson.fromJson(req.body(), Hospital.class);
            newHospital.setLocation_id(locationId);
            hospital.add(newHospital);
            res.status(201);
            return gson.toJson(newHospital);
        });

        //Read
        //a). get all
        get("/hospitals", "application/json", (req, res)->{
            if (hospital.getAll().size() > 0) {
                return gson.toJson(hospital.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no hospitals are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/hospitals/id/:hospital_Id", "application/json", (req, res)->{
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            if (hospital.findById(hospitalId) == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(hospital.findById(hospitalId));
        });

        //c). get hospitals by services
        get("/services/:service_name/hospitals", "application/json", (req, res)->{
            String serviceName = req.params(":service_name");
            List<Hospital> allHospitalsByServices;
            if (service == null){
                throw new ApiException(404, String.format("No service with the id: \"%s\" exists", req.params("name")));
            }
            allHospitalsByServices = hospital.findAllHospitalsWithServiceName(serviceName);
            return gson.toJson(allHospitalsByServices);
        });

        //d). get hospitals by doctors
//        get("/doctors/:doc_name/hospitals", "application/json", (req, res)->{
//            String docName = req.params(":doc_name");
//            List<Hospital> allHospitalsByDoctors = doctor.;
//            if (doctor == null){
//                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("name")));
//            }
//            return ;
//        });

        //e). get hospitals by specialities
        get("/specialities/:specialty_name/hospitals", "application/json", (req, res)->{
            String specialtyName = req.params(":specialty_name");
            List<Hospital> allHospitalsBySpecialities;
            if (service == null){
                throw new ApiException(404, String.format("No service with the id: \"%s\" exists", req.params("name")));
            }
            allHospitalsBySpecialities = hospital.findAllHospitalsWithSpecialtyName(specialtyName);
            return gson.toJson(allHospitalsBySpecialities);
        });

        //f). get hospitals by location
        get("/locations/:location_name/hospitals", "application/json", (req, res)->{
            String location_name = req.params(":location_name");
            List<Hospital> allHospitalsByLocation;
            if (location == null){
                throw new ApiException(404, String.format("No location with the id: \"%s\" exists", req.params("name")));
            }
            allHospitalsByLocation = hospital.findHospitalsByLocationName(location_name);
            return gson.toJson(allHospitalsByLocation);
        });

        //g). get hospitals by name
        get("/hospitals/:hospital_name", "application/json", (req, res)->{
            String hospitalName = req.params("hospital_name");
            if (hospital.findHospitalsByHospitalName(hospitalName) == null){
                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(hospital.findHospitalsByHospitalName(hospitalName));
        });

        //Update

        //Delete
        delete("/hospitals/:hospital_Id/delete", "application/json", (req, res) -> {
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            res.status(200);
            return gson.toJson(hospital.deleteById(hospitalId));
        });


        //3.LOCATION
        //Create
        post("locations/new", "application/json", (req, res)->{
            Location newLocation = gson.fromJson(req.body(), Location.class);
            location.add(newLocation);
            res.status(201);
            return gson.toJson(newLocation);
        });

        //Read
        //a). get all
        get("/locations", "application/json", (req, res)->{
            if (location.getAll().size() > 0) {
                return gson.toJson(location.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no locations are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/locations/:location_Id", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            if (location.findById(locationId) == null){
                throw new ApiException(404, String.format("No location with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(location.findById(locationId));
        });


        //c). get location by services
        get("/services/:service_name/locations", "application/json", (req, res)->{
            String serviceName = req.params(":service_name");
            List<Location> allLocationsByServices;
            if (service == null){
                throw new ApiException(404, String.format("No service with the id: \"%s\" exists", req.params("name")));
            }
            allLocationsByServices = location.findAllLocationsWithServiceName(serviceName);
            return gson.toJson(allLocationsByServices);
        });

        //d). get location by specialties
        get("/specialities/:specialty_name/locations", "application/json", (req, res)->{
            String speciality_name = req.params("specialty_name");
            List<Location> allLocationsBySpecialties;
            allLocationsBySpecialties = location.findAllLocationsWithSpecialtyName(speciality_name);
            return gson.toJson(allLocationsBySpecialties);
        });

        //e). get by name
        get("/locations/names/:location_name", "application/json", (req, res)->{
            String locationName = req.params("location_name");
            if (location.findByName(locationName) == null){
                throw new ApiException(404, String.format("No location with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(location.findByName(locationName));
        });

        //Update

        //Delete
        delete("/locations/:location_Id/delete", "application/json", (req, res) -> {
            int locationId = Integer.parseInt(req.params("location_Id"));
            res.status(200);
            return gson.toJson(location.deleteById(locationId));
        });


        //4.PAYMENT
        //Create
        post("payments/new", "application/json", (req, res)->{
            Payment newPayment = gson.fromJson(req.body(), Payment.class);
            payment.add(newPayment);
            res.status(201);
            return gson.toJson(newPayment);
        });

        //Read
        //a).get all
        get("/payments", "application/json", (req, res)->{
            if (payment.getAll().size() > 0) {
                return gson.toJson(payment.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no payments are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/payments/:payment_Id", "application/json", (req, res)->{
            int paymentId = Integer.parseInt(req.params("payment_Id"));
            if (location.findById(paymentId) == null){
                throw new ApiException(404, String.format("No payment with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(location.findById(paymentId));
        });

        //c). get payment by doctors
        get("/doctors/:doctor_id/payments", "application/json", (req, res)->{
            int doctorId = Integer.parseInt(req.params(":doctor_id"));
            List<Payment> allPaymentsByDoctors;
            if (payment == null){
                throw new ApiException(404, String.format("No doctor with the id: \"%s\" exists", req.params("id")));
            }
            /*allPaymentsByDoctors = //add method;
            return gson.toJson(allPaymentsByDoctors);*/

            return null;
        });

        //d). get payment by hospitals
        get("/hospitals/:hospital_name/payments", "application/json", (req, res)->{//can we change this to hospital name?
            String hospitalname = req.params(":hospital_name");
            List<Payment> allPaymentsByHospital;
            if (hospital == null){
                throw new ApiException(404, String.format("No hospital with the name: \"%s\" exists", req.params("name")));
            }
            //add method
            /*return gson.toJson(allPaymentsByHospital);*/
            return null;
        });

        //e). get by name
        get("/payments/:payment_name", "application/json", (req, res)->{
            String paymentName = req.params("payment_name");
            if (payment.findByName(paymentName) == null){
                throw new ApiException(404, String.format("No payment with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(location.findByName(paymentName));
        });

        //Update

        //Delete
        delete("/payments/:payment_Id/delete", "application/json", (req, res) -> {
            int paymentId = Integer.parseInt(req.params("payment_Id"));
            res.status(200);
            return gson.toJson(payment.deleteById(paymentId));
        });


        //5.SERVICE
        //Create
        post("services/new", "application/json", (req, res)->{
            Service newService = gson.fromJson(req.body(), Service.class);
            service.add(newService);
            res.status(201);
            return gson.toJson(newService);
        });

        //Read
        //a). get all
        get("/services", "application/json", (req, res)->{
            if (service.getAll().size() > 0) {
                return gson.toJson(service.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no services are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/services/:service_Id", "application/json", (req, res)->{
            int serviceId = Integer.parseInt(req.params("service_Id"));
            if (service.findById(serviceId) == null){
                throw new ApiException(404, String.format("No service with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(service.findById(serviceId));
        });

        //c). get services by location
        get("/locations/:location_name/services", "application/json", (req, res)->{
            String location_name = req.params(":location_name");
            List<Service> allServicesByLocation;
            if (location == null){
                throw new ApiException(404, String.format("No location with the id: \"%s\" exists", req.params("name")));
            }
            //add method
            /*allServicesByLocation = service.(location_name);
            return gson.toJson(allServicesByLocation);*/
            return null;
        });

        //d). get services by hospitals
        get("/hospitals/:hospital_id/services", "application/json", (req, res)->{//can we change this to hospital name?
            int hospitalId = Integer.parseInt(req.params(":hospital_id"));
            List<Service> allServicesByHospital;
            if (hospital == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            allServicesByHospital = service.findAllServicesInHospital(hospitalId);
            return gson.toJson(allServicesByHospital);
        });

        //e). get by name
        get("/services/:service_name", "application/json", (req, res)->{
            String serviceName = req.params("service_name");
            if (service.findByName(serviceName) == null){
                throw new ApiException(404, String.format("No service with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(service.findByName(serviceName));
        });

        //Update

        //Delete
        delete("/services/:service_Id/delete", "application/json", (req, res) -> {
            int serviceId = Integer.parseInt(req.params("service_Id"));
            res.status(200);
            return gson.toJson(service.deleteById(serviceId));
        });


        //6.SPECIALITY
        //Create
        post("specialities/new", "application/json", (req, res)->{
            Specialty newSpecialty = gson.fromJson(req.body(), Specialty.class);
            speciality.add(newSpecialty);
            res.status(201);
            return gson.toJson(newSpecialty);
        });

        //Read
        //a). get all
        get("/specialities", "application/json", (req, res)->{
            if (speciality.getAll().size() > 0) {
                return gson.toJson(speciality.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no specialities are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/specialities/:speciality_Id", "application/json", (req, res)->{
            int specialityId = Integer.parseInt(req.params("speciality_Id"));
            if (speciality.findById(specialityId) == null){
                throw new ApiException(404, String.format("No speciality with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(speciality.findById(specialityId));
        });

        //c). get specialties by location
        get("/locations/:location_name/specialties", "application/json", (req, res)->{
            String location_name = req.params(":location_name");
            List<Specialty> allSpecialitiesByLocation;
            if (location == null){
                throw new ApiException(404, String.format("No location with the name: \"%s\" exists", req.params("name")));
            }
            //add method
            /*allSpecialitiesByLocation = speciality.(location_name);
            return gson.toJson(allSpecialitiesByLocation);*/
            return null;
        });

        //d). get specialties by hospitals
        get("/hospitals/:hospital_id/specialties", "application/json", (req, res)->{//can we change this to hospital name?
            int hospitalId = Integer.parseInt(req.params(":hospital_id"));
            List<Specialty> allSpecialitiesByHospital;
            if (hospital == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            allSpecialitiesByHospital = speciality.findAllSpecialtiesInHospital(hospitalId);
            return gson.toJson(allSpecialitiesByHospital);
        });

        //e). get by name
        get("/specialties/:speciality_name", "application/json", (req, res)->{
            String specialityName = req.params("speciality_name");
            if (speciality.findByName(specialityName) == null){
                throw new ApiException(404, String.format("No speciality with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(speciality.findByName(specialityName));
        });

        //Update

        //Delete
        delete("/specialities/:speciality_Id/delete", "application/json", (req, res) -> {
            int specialityId = Integer.parseInt(req.params("speciality_Id"));
            res.status(200);
            return null;//add sql delete method
        });
    }
}
