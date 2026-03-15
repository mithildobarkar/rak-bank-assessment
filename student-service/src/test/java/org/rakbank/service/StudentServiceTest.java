package org.rakbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rakbank.customException.NoStudentsRecordFoundException;
import org.rakbank.customException.StudentAlreadyExistsException;
import org.rakbank.model.Student;
import org.rakbank.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        student = Student.builder()
                .id(1L)
                .studentId("S100")
                .studentName("Ram")
                .grade("10")
                .mobileNumber("999999999")
                .schoolName("ABC School")
                .build();
    }

    @Test
    void shouldSaveStudentSuccessfully() {

        when(studentRepository.existsByStudentId("S100")).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.saveStudent(student);

        assertNotNull(result);
        assertEquals("S100", result.getStudentId());

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void shouldThrowExceptionWhenStudentAlreadyExists() {

        when(studentRepository.existsByStudentId("S100")).thenReturn(true);

        assertThrows(StudentAlreadyExistsException.class,
                () -> studentService.saveStudent(student));

        verify(studentRepository, never()).save(any());
    }

    @Test
    void shouldReturnStudent() {

        when(studentRepository.findByStudentId("S100"))
                .thenReturn(Optional.of(student));

        Student result = studentService.getStudent("S100");

        assertNotNull(result);
        assertEquals("Ram", result.getStudentName());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {

        when(studentRepository.findByStudentId("S100"))
                .thenReturn(Optional.empty());

        assertThrows(NoStudentsRecordFoundException.class,
                () -> studentService.getStudent("S100"));
    }

    @Test
    void shouldReturnStudentsList() {

        when(studentRepository.findAll())
                .thenReturn(List.of(student));

        List<Student> students = studentService.getStudents();

        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void shouldThrowExceptionWhenNoStudentsFound() {

        when(studentRepository.findAll())
                .thenReturn(List.of());

        assertThrows(NoStudentsRecordFoundException.class,
                () -> studentService.getStudents());
    }

    @Test
    void shouldUpdateStudent() {

        Student updated = Student.builder()
                .studentName("Krishna")
                .schoolName("XYZ School")
                .grade("9")
                .mobileNumber("888888888")
                .build();

        when(studentRepository.findByStudentId("S100"))
                .thenReturn(Optional.of(student));

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        Student result = studentService.updateStudent("S100", updated);

        assertEquals("Krishna", result.getStudentName());
        verify(studentRepository).save(student);
    }

    @Test
    void shouldDeleteStudent() {

        when(studentRepository.findByStudentId("S100"))
                .thenReturn(Optional.of(student));

        Student deletedStudent = studentService.deleteStudent("S100");

        assertEquals("S100", deletedStudent.getStudentId());

        verify(studentRepository).deleteByStudentId("S100");
    }
}