package com.example.simpleHealth.Services;


import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.repositories.DoctorRepository;
import com.example.simpleHealth.repositories.UserRepository;
import com.example.simpleHealth.services.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    public void testGetDoctorById() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        doctor.setId(id);
        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        assertEquals(doctor, doctorService.readDoctor(id));
    }

    @Test
    public void testListDoctorsWithSpecialization() {
        String specialization = "Cardiologist";
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findBySpecialization(specialization)).thenReturn(doctors);
        assertEquals(doctors, doctorService.readDoctors(specialization));
    }

    @Test
    public void testListDoctorsWithoutSpecialization() {
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        assertEquals(doctors, doctorService.readDoctors(null));
    }

    @Test
    public void testDeleteDoctor() {
        Long id = 1L;
        doctorService.deleteDoctor(id);
        Mockito.verify(doctorRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testSaveDoctor() {
        Doctor doctor = new Doctor();
        doctorService.createDoctor(doctor);
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }

    @Test
    public void testUpdateDoctor() {
        Doctor doctor = new Doctor();
        String name = "John Doe";
        int price = 100;
        String specialization = "Dermatologist";
        doctorService.updateDoctor(name, price, specialization, doctor);
        assertEquals(name, doctor.getName());
        assertEquals(price, doctor.getPrice());
        assertEquals(specialization, doctor.getSpecialization());
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }
}