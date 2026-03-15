package org.rakbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rakbank.Repository.FeeCollectionRepository;
import org.rakbank.model.Receipt;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeeCollectionServiceTest {

    @Mock
    private FeeCollectionRepository repository;

    @InjectMocks
    private FeeCollectionService service;

    private Receipt receipt;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        receipt = Receipt.builder()
                .receiptNumber(1L)
                .studentId("S100")
                .amountPaid(1000.0)
                .currency("AED")
                .paymentMode("CARD")
                .feeType("TUITION")
                .academicYear("2025")
                .build();
    }

    @Test
    void shouldSaveReceipt() {

        when(repository.save(receipt)).thenReturn(receipt);

        Receipt saved = service.saveReceipt(receipt);

        assertNotNull(saved);
        assertEquals("S100", saved.getStudentId());

        verify(repository).save(receipt);
    }

    @Test
    void shouldReturnAllReceipts() {

        when(repository.findAll()).thenReturn(List.of(receipt));

        List<Receipt> receipts = service.getAllReceipts();

        assertEquals(1, receipts.size());
    }

    @Test
    void shouldReturnReceiptsForStudent() {

        when(repository.findAllByStudentId("S100"))
                .thenReturn(List.of(receipt));

        List<Receipt> receipts =
                service.getAllReceiptsForStudentId("S100");

        assertEquals("S100", receipts.getFirst().getStudentId());
    }
}