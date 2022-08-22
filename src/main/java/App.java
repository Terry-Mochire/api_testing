import com.google.gson.Gson;
import models.*;

import static spark.Spark.*;
import static spark.route.HttpMethod.post;

public class App {
    public static void main(String[] args) {
        Gson gson = new Gson();


        //1.DOCTOR
        //create
        post("locations/:location_Id/doctors/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Doctor newDoctor = gson.fromJson(req.body(), Doctor.class);
            newDoctor.setLocation_id(locationId);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newDoctor);
        });
        //read
        //get doctors by location
        //get doctors by speciality
        //get doctors by hospitals
        // get doctors by payment methods

        //update

        //delete
        delete("/doctors/:doctor_Id/delete", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("doctor_Id"));
            res.status(200);
            return null;
        });


        //2.HOSPITAL
        //create
        post("locations/:location_Id/hospitals/new", "application/json", (req, res)->{
            int locationId = Integer.parseInt(req.params("location_Id"));
            Hospital newHospital = gson.fromJson(req.body(), Hospital.class);
            newHospital.setLocation_id(locationId);
            //add missing sql statements
            res.status(201);
            return gson.toJson(newHospital);
        });
        //read
        //get hospitals by location
        //get hospitals by services
        //get hospitals by doctors
        //get hospitals by specialities

        //update

        //delete
        delete("/hospitals/:hospital_Id/delete", "application/json", (req, res) -> {
            int hospitalId = Integer.parseInt(req.params("hospital_Id"));
            res.status(200);
            return null;
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
