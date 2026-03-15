package org.rakbank.Repository;

import org.rakbank.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeCollectionRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findAllByStudentId(String studentId);

}
