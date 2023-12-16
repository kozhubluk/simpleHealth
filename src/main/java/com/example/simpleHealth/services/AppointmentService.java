package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Appointment;
import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.models.User;
import com.example.simpleHealth.repositories.AppointmentRepository;
import com.example.simpleHealth.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public void createAppointment(Doctor doctor, String date, String time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Appointment appointment = new Appointment();
        Date datetime = new Date();
        try {
            datetime = formatter.parse(date + " " + time);
            appointment.setTime(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (appointmentRepository.getByDoctorAndTime(doctor, datetime) == null) {
            appointment.setDoctor(doctor);
            doctor.addAppointment(appointment);
            doctorRepository.save(doctor);
        }
    }

    public void cancelAppointment(Appointment appointment) {
        appointment.setUser(null);
        appointment.setAvailable(true);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> readAvailableAppointments(Doctor doctor) {
        return appointmentRepository.findByDoctorAndAvailableOrderByTime(doctor, true);
    }

    public void deleteOld() {
         appointmentRepository.deleteOld();
    }


    public List<Appointment> readByUser(User user) {
        return appointmentRepository.
                findByUserOrderByTime(user);
    }


    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> readByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctorOrderByTime(doctor);
    }

    public void setUser(Appointment appointment, User user) {
        appointment.setUser(user);
        appointmentRepository.save(appointment);
    }

}
