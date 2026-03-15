package org.rakbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rakbank.model.Student;
import org.rakbank.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .build();
    }

    private Student createStudent() {
        return Student.builder()
                .id(1L)
                .studentId("S100")
                .studentName("Ram")
                .grade("10")
                .mobileNumber("999999999")
                .schoolName("ABC School")
                .build();
    }

    @Test
    void shouldAddStudent() throws Exception {

        Student student = createStudent();

        when(studentService.saveStudent(any()))
                .thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value("S100"));
    }

    @Test
    void shouldGetStudent() throws Exception {

        Student student = createStudent();

        when(studentService.getStudent("S100"))
                .thenReturn(student);

        mockMvc.perform(get("/students/S100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Ram"));
    }

    @Test
    void shouldGetStudents() throws Exception {

        Student student = createStudent();

        when(studentService.getStudents())
                .thenReturn(List.of(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentId").value("S100"));
    }

    @Test
    void shouldUpdateStudent() throws Exception {

        Student student = createStudent();

        when(studentService.updateStudent(eq("S100"), any()))
                .thenReturn(student);

        mockMvc.perform(put("/students/S100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteStudent() throws Exception {

        Student student = createStudent();

        when(studentService.deleteStudent("S100"))
                .thenReturn(student);

        mockMvc.perform(delete("/students/S100"))
                .andExpect(status().isOk());
    }
}