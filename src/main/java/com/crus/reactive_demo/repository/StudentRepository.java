package com.crus.reactive_demo.repository;

import com.crus.reactive_demo.model.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
