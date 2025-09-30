package com.crus.reactive_demo.service;

import com.crus.reactive_demo.model.Student;
import com.crus.reactive_demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TransactionalOperator transactionalOperator;

    public Mono<Student> saveStudent(Student student) {
        return transactionalOperator.transactional(
                studentRepository.save(student));
    }

}
