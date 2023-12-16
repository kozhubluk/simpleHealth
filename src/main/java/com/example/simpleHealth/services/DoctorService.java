package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Doctor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.simpleHealth.repositories.DoctorRepository;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public Doctor readDoctor(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public List<Doctor> readDoctors(String specialization) {
        if (specialization == null || specialization.isEmpty()) return doctorRepository.findAll();
        return doctorRepository.findBySpecialization(specialization);
    }


    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public void createDoctor(Doctor doctor){
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
