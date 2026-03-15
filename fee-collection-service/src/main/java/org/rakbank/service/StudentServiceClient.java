package org.rakbank.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.rakbank.customException.ServiceUnavailableException;
import org.rakbank.customException.StudentNotFoundException;
import org.rakbank.model.Student;
import org.slf4j.MDC;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StudentServiceClient {

    private final WebClient webClient;
    public StudentServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://student-service")
                .build();
    }

    @CircuitBreaker(name="studentService", fallbackMethod = "fallbackStudent")
    public Student getStudent(String studentId) {
        return webClient
                .get()
                .uri("/students/{id}", studentId)
                .header("X-Correlation-Id", MDC.get("correlationId"))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new StudentNotFoundException("Student with ID " + studentId + " not found!");
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    throw new ServiceUnavailableException("Student service is unavailable, please try again later!!!");
                })
                .bodyToMono(Student.class)
                .block();
    }

    public Student fallbackStudent(String studentId, Throwable ex) {
        System.out.println("fall back student method called ");
        throw new ServiceUnavailableException("Student service is unavailable, try again later!!!");
    }

}
