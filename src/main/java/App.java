import com.google.gson.Gson;
import dao.Sql2oDoctorDao;
import dao.Sql2oHospitalDao;
import exceptions.ApiException;
import models.*;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.*;
import static spark.route.HttpMethod.post;

public class App {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String connectionString = "";
        Sql2o sql2o = new Sql2o(connectionString, null, null);
        Sql2oDoctorDao doctor = new Sql2oDoctorDao(sql2o);
        Sql2oHospitalDao hospital = new Sql2oHospitalDao(sql2o);

        //1.DOCTOR
        //create
        post("locations/:location_Id/doctors/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Doctor newDoctor = gson.fromJson(req.body(), Doctor.class);
            newDoctor.setLocation_id(locationId);
            doctor.add(newDoctor);
            res.status(201);
            return gson.toJson(newDoctor);
        });
        //read
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
                throw new ApiException(404, String.format("No doctor with the name: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(doctor.findbyDoctorId(doctorId));
        });

        //c). get doctors by speciality

        //d). get doctors by hospitals
        get("/hospitals/:hospital_id/doctors", "application/json", (req, res)->{//not working
            int hospitalId = Integer.parseInt(req.params(":hospital_id"));
            List<Doctor> allDoctorsByHospital;
            if (hospital == null){
                throw new ApiException(404, String.format("No hospital with the id: \"%s\" exists", req.params("id")));
            }
            allDoctorsByHospital = doctor.getAllDoctorsByHospital(hospitalId);
            return gson.toJson(allDoctorsByHospital);
        });

        //e). get doctors by payment methods
        //f). get doctors by location

        //update

        //delete
        delete("/doctors/:doctor_Id/delete", "application/json", (req, res) -> {
            int doctorId = Integer.parseInt(req.params("doctor_Id"));
            res.status(200);
            return gson.toJson(doctor.findbyDoctorId(doctorId));
        });


        //2.HOSPITAL
        //create
        post("locations/:location_Id/hospitals/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Hospital newHospital = gson.fromJson(req.body(), Hospital.class);
            newHospital.setLocation_id(locationId);
            hospital.add(newHospital);
            res.status(201);
            return gson.toJson(newHospital);
        });

        //read
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
        get("/doctors/:doctor_id/hospitals", "application/json", (req, res)->{//not working
            int doctorId = Integer.parseInt(req.params(":doctor_id"));
            List<Hospital> allHospitalsByDoctors;
            if (doctor == null){
                throw new ApiException(404, String.format("No doctor with the id: \"%s\" exists", req.params("id")));
            }
            //add method
            /*return gson.toJson(allHospitalsByDoctors);*/
            return null;
        });

        //e). get hospitals by specialities
        //f). get hospitals by location

        //update

        //delete
        delete("/hospitals/:hospital_Id/delete", "application/json", (req, res) -> {
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            res.status(200);
            return gson.toJson(hospital.findById(hospitalId));
        });


        //3.LOCATION
        //create
        post("locations/new", "application/json", (req, res)->{
            Location newLocation = gson.fromJson(req.body(), Location.class);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newLocation);
        });

        //read
        //a).get all
        //b). get by name
        //get location by services
        //get location by specialties

        //update

        //delete

        //4.PAYMENT
        //create
        post("payments/new", "application/json", (req, res)->{
            Payment newPayment = gson.fromJson(req.body(), Payment.class);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newPayment);
        });
        //read
        //a).get all
        //b). get by name
        //get payment by doctors
        //get payment by hospitals

        //update

        //delete
        delete("/payments/:payment_Id/delete", "application/json", (req, res) -> {
            int paymentId = Integer.parseInt(req.params("payment_Id"));
            res.status(200);
            return null;
        });


        //5.SERVICE
        //create
        post("services/new", "application/json", (req, res)->{
            Service newService = gson.fromJson(req.body(), Service.class);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newService);
        });

        //read
        //a).get all
        //b). get by name
        //get services by location
        //get services by hospitals

        //update

        //delete
        delete("/services/:payment_Id/delete", "application/json", (req, res) -> {
            int serviceId = Integer.parseInt(req.params("service_Id"));
            res.status(200);
            return null;
        });


        //6.SPECIALITY
        //create
        post("specialities/new", "application/json", (req, res)->{
            Specialty newSpecialty = gson.fromJson(req.body(), Specialty.class);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newSpecialty);
        });

        //read
        //a). get all
        //b). get by name
        //get specialties by location
        //get specialties by hospitals
        //get specialties by doctors

        //update

        //delete
        delete("/specialities/:payment_Id/delete", "application/json", (req, res) -> {
            int specialityId = Integer.parseInt(req.params("speciality_Id"));
            res.status(200);
            return null;
        });
    }
}
