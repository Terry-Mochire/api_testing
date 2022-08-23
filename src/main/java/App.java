import com.google.gson.Gson;
import dao.*;
import exceptions.ApiException;
import models.*;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String connectionString = "";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        Sql2oDoctorDao doctor = new Sql2oDoctorDao(sql2o);
        Sql2oHospitalDao hospital = new Sql2oHospitalDao(sql2o);
        Sql2oLocationDao location = new Sql2oLocationDao(sql2o);
        Sql2oPaymentDao payment = new Sql2oPaymentDao(sql2o);
        Sql2oServiceDao service = new Sql2oServiceDao(sql2o);
        Sql2oSpecialtyDao speciality = new Sql2oSpecialtyDao(sql2o);

        //1.DOCTOR
        //Create

        //a). new
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
        //a). get all
        get("/doctors", "application/json", (req, res)->{
            if (doctor.getAll().size() > 0) {
                return gson.toJson(doctor.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no doctors are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/doctors/:doctor_Id", "application/json", (req, res)->{
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
        get("/hospitals/:hospital_id/doctors", "application/json", (req, res)->{//can we change this to hospital name?
            int hospitalId = Integer.parseInt(req.params(":hospital_id"));
            List<Doctor> allDoctorsByHospital;
            if (hospital == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            allDoctorsByHospital = doctor.getAllDoctorsByHospital(hospitalId);
            return gson.toJson(allDoctorsByHospital);
        });

        //e). get doctors by payment methods
        get("/payments/:payment_id/doctors", "application/json", (req, res)->{
            int paymentId = Integer.parseInt(req.params(":payment_id"));
            List<Doctor> allDoctorsByPayment;
            if (payment == null){
                throw new ApiException(404, String.format("No payment with the id: \"%s\" exists", req.params("id")));
            }
            allDoctorsByPayment = doctor.getAllDoctorsByPaymentId(paymentId);
            return gson.toJson(allDoctorsByPayment);
        });

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
        get("/doctors/:doc_name", "application/json", (req, res)->{
            int doctorId = Integer.parseInt(req.params("doc_name"));
            if (doctor.findbyDoctorId(doctorId) == null){
                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("name")));
            }
            return gson.toJson(doctor.findbyDoctorId(doctorId));
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
            if (doctor.getAll().size() > 0) {
                return gson.toJson(doctor.getAll());
            }
            else {
                return "{\"message\":\"I'm sorry, but no hospitals are currently listed in the database.\"}";
            }
        });

        //b). get by id
        get("/hospitals/:hospital_Id", "application/json", (req, res)->{
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            if (hospital.findById(hospitalId) == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(doctor.findbyDoctorId(hospitalId));
        });

        //c). get hospitals by services

        //d). get hospitals by doctors
        get("/doctors/:doc_name/hospitals", "application/json", (req, res)->{
            String docName = req.params(":doc_name");
            List<Hospital> allHospitalsByDoctors;
            if (doctor == null){
                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("name")));
            }
            //add method
            /*return gson.toJson(allHospitalsByDoctors);*/
            return null;
        });

        //e). get hospitals by specialities
        //f). get hospitals by location
        //g). get hospitals by name

        //Update

        //Delete
        delete("/hospitals/:hospital_Id/delete", "application/json", (req, res) -> {
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            res.status(200);
            return gson.toJson(hospital.findById(hospitalId));
        });


        //3.LOCATION
        //create
        post("locations/new", "application/json", (req, res)->{
            Location newLocation = gson.fromJson(req.body(), Location.class);
            location.add(newLocation);
            res.status(201);
            return gson.toJson(newLocation);
        });

        //Read
        //a). get all
        //b). get by id
        //c). get location by services
        //d). get location by specialties
        //e). get by name


        //Update

        //Delete

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
        //b). get by id
        //c). get payment by doctors
        //d). get payment by hospitals
        //e). get by name


        //Update

        //Delete
        delete("/payments/:payment_Id/delete", "application/json", (req, res) -> {
            int paymentId = Integer.parseInt(req.params("payment_Id"));
            res.status(200);
            return null;
        });


        //5.SERVICE
        //Create
        post("services/new", "application/json", (req, res)->{
            Service newService = gson.fromJson(req.body(), Service.class);
            service.add(newService);
            res.status(201);
            return gson.toJson(newService);
        });

        //read
        //a).get all
        //b). get by id
        //c). get services by location
        //d). get services by hospitals
        //e). get by name

        //update

        //Delete
        delete("/services/:payment_Id/delete", "application/json", (req, res) -> {
            int serviceId = Integer.parseInt(req.params("service_Id"));
            res.status(200);
            return null;
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
        //b). get by id
        //c). get specialties by location
        //d). get specialties by hospitals
        //e). get specialties by doctors
        //f). get by name

        //Update

        //Delete
        delete("/specialities/:payment_Id/delete", "application/json", (req, res) -> {
            int specialityId = Integer.parseInt(req.params("speciality_Id"));
            res.status(200);
            return null;
        });
    }
}
