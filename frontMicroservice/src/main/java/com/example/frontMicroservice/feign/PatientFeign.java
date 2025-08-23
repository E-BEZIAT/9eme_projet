package com.example.frontMicroservice.feign;

import com.example.frontMicroservice.parameter.PatientParameter;
import com.example.frontMicroservice.parameter.UserParameter;
import com.example.frontMicroservice.response.PatientDTO;
import com.example.frontMicroservice.response.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patientMicroservice", url = "http://localhost:8081")
public interface PatientFeign {

    @GetMapping("/api/patients")
    List<PatientDTO> getPatient();

    @GetMapping("/patient/{id}")
    PatientDTO getPatientById(@PathVariable("id") int id);

    @PostMapping("/patient/update/{id}")
    void updatePatient(@PathVariable("id") int id, @RequestBody PatientParameter patientParameter);

    @PostMapping("/patient/delete/{id}")
    void deletePatient(@PathVariable("id") int id);

    @PostMapping("user/register")
    void createUser(@RequestBody UserParameter user);

    /**@GetMapping("/user/{username}")
    UserParameter readUser(@PathVariable("username") String username);**/

    @PostMapping("/patient/create")
    void createPatient(@RequestBody PatientParameter patient);
}
