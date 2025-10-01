package com.crus.reactive_demo.controller;

import com.crus.reactive_demo.model.Teacher;
import com.crus.reactive_demo.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Bean
    public RouterFunction<ServerResponse> getAllTeachersRoute() {
        return route()
                .GET("/teachers-route", req -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(teacherService.getAllTeachers(), Teacher.class))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> updateTeacherRoute() {
        return route()
                .PUT("/update-route/{id}", req -> {
                    Integer teacherId = Integer.parseInt(req.pathVariable("id"));
                    Mono<Teacher> teacherMono = req.bodyToMono(Teacher.class);
                    return teacherService.updateTeacher(teacherId, teacherMono)
                            .flatMap(teacher -> ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(Mono.just(teacher), Teacher.class))
                            .switchIfEmpty(ServerResponse.notFound()
                                    .build());
                }).build();
    }

    public Mono<ServerResponse> getAllTeachers(ServerRequest request) {
        Flux<Teacher> teachers = teacherService.getAllTeachers();
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teachers, Teacher.class);
    }

    public Mono<ServerResponse> getTeacher(ServerRequest request) {
        Integer teacherId = Integer.parseInt(request.pathVariable("id"));
        return teacherService.getTeacher(teacherId)
                .flatMap(teacher -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(teacher), Teacher.class))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    public Mono<ServerResponse> addNewTeacher(ServerRequest request) {
        Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
        Mono<Teacher> newTeacher =
                teacherMono.flatMap(teacherService::saveTeacher);
        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTeacher, Teacher.class);
    }

    public Mono<ServerResponse> updateTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teacherService.updateTeacher(
                                Integer.valueOf(teacherId), teacherMono),
                        Teacher.class);
    }

    public Mono<ServerResponse> deleteTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        return teacherService.getTeacher(Integer.valueOf(teacherId))
                .flatMap(teacher -> teacherService
                        .deleteStudent(teacher)
                        .then(ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
