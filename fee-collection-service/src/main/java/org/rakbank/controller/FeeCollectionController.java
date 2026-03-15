package org.rakbank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rakbank.customException.NoReceiptsRecordFoundException;
import org.rakbank.model.Receipt;
import org.rakbank.model.Student;
import org.rakbank.service.StudentServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.rakbank.service.FeeCollectionService;

import java.util.List;

@RestController
@RequestMapping("/fees")
@RequiredArgsConstructor
@Slf4j
public class FeeCollectionController {

    private final FeeCollectionService feeCollectionService;
    private final StudentServiceClient studentServiceClient;

      @PostMapping("/collect")
    public ResponseEntity<Receipt> collectFees(@Valid @RequestBody Receipt receipt) {

          log.info("Processing fees for student id {} ", receipt.getStudentId());
        Student studentResponse = studentServiceClient.getStudent(receipt.getStudentId());

        Receipt savedReceipt = feeCollectionService.saveReceipt(receipt);
        savedReceipt.setStudentName(studentResponse.getStudentName());
        savedReceipt.setSchoolName(studentResponse.getSchoolName());
        savedReceipt.setGrade(studentResponse.getGrade());

        return new ResponseEntity<>(savedReceipt, HttpStatus.CREATED);
    }

    @GetMapping("/receipts")
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = feeCollectionService.getAllReceipts();
        if(receipts.isEmpty()) {
            throw new NoReceiptsRecordFoundException("No receipts found!!!");
        }
        return ResponseEntity.ok(receipts);
    }

    @GetMapping("/{studentId}/receipts")
    public ResponseEntity<List<Receipt>> getAllReceiptsForStudent(@PathVariable("studentId") String studentId) {
        Student savedStudent = studentServiceClient.getStudent(studentId);
        List<Receipt> receipts = feeCollectionService.getAllReceiptsForStudentId(savedStudent.getStudentId());
        if(receipts.isEmpty()) {
            throw new NoReceiptsRecordFoundException(String.format("No receipts found, for Student id %s !!!", studentId));
        }
        receipts.forEach(receipt -> {
            receipt.setStudentName(savedStudent.getStudentName());
            receipt.setSchoolName(savedStudent.getSchoolName());
            receipt.setGrade(savedStudent.getGrade());
        });
        return ResponseEntity.ok(receipts);
    }
}
