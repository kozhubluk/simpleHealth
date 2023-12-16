package com.example.simpleHealth.controllers;

import com.example.simpleHealth.models.Doctor;
import com.example.simpleHealth.services.DoctorService;
import com.example.simpleHealth.services.AppointmentService;
import com.example.simpleHealth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    private final UserService userService;
    private final AppointmentService appointmentsService;

    @GetMapping("/f")
    public String f() {
        return "error.html";
    }

    @GetMapping("/")
    public String doctors(@RequestParam(name = "specialization", required = false) String specialization,
                          Principal principal, Model model) {
        model.addAttribute("doctors", doctorService.readDoctors(specialization));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("specialization", specialization);
        return "index";
    }


    @GetMapping("doctor/{id}")
    public String doctorInfo(@PathVariable Long id, Principal principal, Model model) {
        Doctor doctor = doctorService.readDoctor(id);
        model.addAttribute("doctor", doctor);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("appointments", appointmentsService.readAvailableAppointments(doctor));
        return "doctor-info";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("doctor/new")
    public String formDoctor() {
        return "doctor-form";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("doctor/create")
    public String createDoctor(Doctor product) {
        doctorService.createDoctor(product);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("doctor/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctor/list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("doctor/list")
    public String listDoctor(@RequestParam(name = "name", required = false) String name,
                             Model model) {
        model.addAttribute("doctors", doctorService.readDoctors(name));
        return "doctor-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("doctor/list/{doctor}")
    public String DoctorEditForm(@PathVariable Doctor doctor, Model model) {
        model.addAttribute("doctor", doctor);
        return "doctor-edit";
    }

    @PostMapping("doctor/edit")
    public String userSave(@RequestParam String name,
                           @RequestParam int price,
                           @RequestParam String specialization,
                           @RequestParam("doctorId") Doctor product) {
        doctorService.updateDoctor(name, price, specialization, product);

        return "redirect:/doctor/list";
    }
}
