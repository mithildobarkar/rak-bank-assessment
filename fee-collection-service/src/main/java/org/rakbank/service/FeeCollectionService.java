package org.rakbank.service;

import org.rakbank.Repository.FeeCollectionRepository;
import org.rakbank.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeCollectionService {

    @Autowired
    private FeeCollectionRepository feeCollectionRepository;

    public Receipt saveReceipt(Receipt receipt) {
        return feeCollectionRepository.save(receipt);
    }

    public List<Receipt> getAllReceipts() {
        return feeCollectionRepository.findAll();
    }

    public List<Receipt> getAllReceiptsForStudentId (String studentId) {
        return feeCollectionRepository.findAllByStudentId(studentId);
    }

}
