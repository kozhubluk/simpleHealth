package com.example.simpleHealth.Services;


import com.example.simpleHealth.models.Appointment;
import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.AppointmentRepository;
import com.example.simpleHealth.repositories.DoctorRepository;
import com.example.simpleHealth.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCancelAppointment() {
        Appointment appointment = new Appointment();
        appointment.setUser(new User());
        appointment.setAvailable(false);

        appointmentService.cancelAppointment(appointment);

        assertEquals(true, appointment.getAvailable());
        assertEquals(null, appointment.getUser());
        Mockito.verify(appointmentRepository, times(1)).save(appointment);
    }


    @Test
    public void testDoctorAppointments() {
        Doctor doctor = new Doctor();
        List<Appointment> expectedAppointments = new ArrayList<>();
        Mockito.when(appointmentRepository.findByDoctorAndAvailableOrderByTime(doctor, true)).thenReturn(expectedAppointments);
        List<Appointment> result = appointmentService.readAvailableAppointments(doctor);
        assertEquals(expectedAppointments, result);
    }

    @Test
    public void testDeleteOld() {
        appointmentService.deleteOld();
        Mockito.verify(appointmentRepository, times(1)).deleteOld();
    }

    @Test
    public void testUserAppointments() {
        User user = new User();
        List<Appointment> expectedAppointments = new ArrayList<>();
        Mockito.when(appointmentRepository.findByUserOrderByTime(user)).thenReturn(expectedAppointments);
        List<Appointment> result = appointmentService.readByUser(user);
        assertEquals(expectedAppointments, result);
    }

    @Test
    public void testDeleteAppointment() {
        Long id = 1L;
        appointmentService.deleteAppointment(id);
        Mockito.verify(appointmentRepository, times(1)).deleteById(id);
    }

    @Test
    public void testListAppointments() {
        Doctor doctor = new Doctor();
        List<Appointment> expectedAppointments = new ArrayList<>();
        Mockito.when(appointmentRepository.findByDoctorOrderByTime(doctor)).thenReturn(expectedAppointments);
        List<Appointment> result = appointmentService.readByDoctor(doctor);
        assertEquals(expectedAppointments, result);
    }

    @Test
    public void testAddUser() {
        Appointment appointment = new Appointment();
        User user = new User();
        appointmentService.setUser(appointment, user);
        Mockito.verify(appointmentRepository, times(1)).save(appointment);
    }
}
