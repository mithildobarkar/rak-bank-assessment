package org.rakbank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.rakbank.model.Student;
import org.rakbank.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Student API", description = "CRUD operations for students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "Create new student")
    @PostMapping("")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
       Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @Operation(summary = "Get student by ID")
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") String studentId) {
        log.info("Fetching Student {}", studentId);
        return ResponseEntity.ok(studentService.getStudent(studentId));
    }

    @Operation(summary = "Get all students")
    @GetMapping("")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @Operation(summary = "Update student")
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable("studentId") String studentId,
                                                 @Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(studentId, student));
    }

    @Operation(summary = "Delete student")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") String studentId) {
        return ResponseEntity.ok(studentService.deleteStudent(studentId));
    }


}
