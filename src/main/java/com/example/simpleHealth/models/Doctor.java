package com.example.simpleHealth.models;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Column(name = "specialization")
    private String specialization;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    List<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<String> getDates() {
        List<Date> dateList = this.appointments.stream().map(Appointment::getTime).collect(Collectors.toList());
        List<String> stringList = new ArrayList<>();
        dateList = this.appointments.stream()
                .filter(appointment -> appointment.getAvailable())
                .map(Appointment::getTime)
                .collect(Collectors.toList());

        for (Date date : dateList) {
            String dateString = new SimpleDateFormat("dd.MM").format(date); // Преобразование Date в строку без времени
            if (!stringList.contains(dateString)) {
                stringList.add(dateString); // Добавление уникальных значений в список строк
            }
        }

        return stringList;
    }
}
