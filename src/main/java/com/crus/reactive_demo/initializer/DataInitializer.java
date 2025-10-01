package com.crus.reactive_demo.initializer;

import com.crus.reactive_demo.repository.StudentRepository;
import com.crus.reactive_demo.repository.TeacherRepository;
import com.crus.reactive_demo.service.StudentService;
import com.crus.reactive_demo.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.studentRepository
                .deleteAll()
                .thenMany(studentService.saveAll(
                        "Evan", "Ji", "Harry", "Ryan", "Rick", "Chuck", "paul"))
                .thenMany(studentService.getAllStudents())
                .subscribe(s -> System.out.println(s.getName()));

        this.teacherRepository
                .deleteAll()
                .thenMany(teacherService.saveAll(
                        "David", "Larry", "Kim", "Toby"))
                .thenMany(teacherService.getAllTeachers())
                .subscribe(s -> System.out.println(s.getName()));
    }
}

