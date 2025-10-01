package com.crus.reactive_demo.config;

import com.crus.reactive_demo.controller.StudentController;
import com.crus.reactive_demo.controller.TeacherController;
import com.crus.reactive_demo.model.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction<ServerResponse> routes(
            StudentController studentController, TeacherController teacherController) {

        return route()
                .GET("/students",
                        accept(MediaType.APPLICATION_JSON),
                        studentController::getAllStudents)
                .GET("/students/{id}",
                        accept(MediaType.APPLICATION_JSON),
                        studentController::getStudent)
                .POST("/students",
                        contentType(MediaType.APPLICATION_JSON),
                        studentController::addNewStudent)
                .PUT("/students/{id}",
                        contentType(MediaType.APPLICATION_JSON),
                        studentController::updateStudent)
                .DELETE("/students/{id}",
                        studentController::deleteStudent)

                .GET("/teachers",
                        accept(MediaType.APPLICATION_JSON),
                        teacherController::getAllTeachers)
                .GET("/teachers/{id}",
                        accept(MediaType.APPLICATION_JSON),
                        teacherController::getTeacher)
                .POST("/teachers",
                        contentType(MediaType.APPLICATION_JSON),
                        teacherController::addNewTeacher)
                .PUT("/teachers/{id}",
                        contentType(MediaType.APPLICATION_JSON),
                        teacherController::updateTeacher)
                .DELETE("/teachers/{id}",
                        teacherController::deleteTeacher)
                .build();
    }
}



