package com.example.simpleHealth.repositories;

import com.example.simpleHealth.models.Appointment;
import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorOrderByTime(Doctor doctor);
    List<Appointment> findByDoctorAndAvailableOrderByTime(Doctor doctor, Boolean available);

    List<Appointment> findByUserOrderByTime(User user);
    Appointment getByDoctorAndTime(Doctor doctor, Date time);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM appointments WHERE time < NOW();", nativeQuery = true)
    void deleteOld();

}
