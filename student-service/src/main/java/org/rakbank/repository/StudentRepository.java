package org.rakbank.repository;

import org.rakbank.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentId(String studentId);
    Optional<Student> findByStudentId(String studentId);
    void deleteByStudentId(String studentId);
}
