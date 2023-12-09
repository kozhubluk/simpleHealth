package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.simpleHealth.repositories.DoctorRepository;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public List<Doctor> listDoctors(String specialization) {
        if (specialization == null || specialization.isEmpty()) return doctorRepository.findAll();
        return doctorRepository.findAllBySpecialization(specialization);
    }


    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public void saveDoctor(Doctor doctor){
        doctorRepository.save(doctor);
        log.info("Doctor is saved. ID: {}", doctor.getId());
    }

    public void updateDoctor(String name, int price,
                             String specialization,
                             Doctor doctor) {
        doctor.setName(name);  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        doctor.setPrice(price);
        doctor.setSpecialization(specialization);
        doctorRepository.save(doctor);
    }




}
