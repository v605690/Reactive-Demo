package com.crus.reactive_demo.repository;

import com.crus.reactive_demo.model.Teacher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TeacherRepository extends ReactiveCrudRepository<Teacher, Integer> {
}
