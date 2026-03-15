package org.rakbank.service;

import org.rakbank.customException.NoStudentsRecordFoundException;
import org.rakbank.customException.StudentAlreadyExistsException;
import org.rakbank.model.Student;
import org.rakbank.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent( Student student) {
        if(studentRepository.existsByStudentId(student.getStudentId())){
            throw new StudentAlreadyExistsException(
                    String.format("Student with student id %s is already exists!!!",
                            student.getStudentId()));
        }
        return studentRepository.save(student);
    }

    public Student getStudent(String studentId) {

        return studentRepository.findByStudentId(studentId)
                .orElseThrow(()-> new NoStudentsRecordFoundException(
                        String.format("No record found for student id %s", studentId)));
       /* Student retrievedStudent = studentRepository.findByStudentId(studentId);
        if(retrievedStudent == null) {
            throw new NoStudentsRecordFoundException(String.format("No record found for student id %s",
                    studentId));
        }
        return retrievedStudent;*/
    }

    public List<Student> getStudents() {
        List<Student> students =  studentRepository.findAll();
        if(students.isEmpty()) {
            throw new NoStudentsRecordFoundException("No Student records found");
        }
        return students;
    }

    public Student updateStudent(String studentId, Student student) {
        Student existingStudent = studentRepository.findByStudentId(studentId)
                .orElseThrow(()-> new NoStudentsRecordFoundException(
                        String.format("No record found for student id %s", studentId)));
        existingStudent.setStudentName(student.getStudentName());
        existingStudent.setSchoolName(student.getSchoolName());
        existingStudent.setGrade(student.getGrade());
        existingStudent.setMobileNumber(student.getMobileNumber());

        return studentRepository.save(existingStudent);

    }

    @Transactional
    public Student deleteStudent(String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoStudentsRecordFoundException(
                        String.format("No record found for student id %s", studentId)));

        studentRepository.deleteByStudentId(studentId);
        return student;
    }

}
