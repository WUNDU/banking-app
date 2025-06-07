package ao.com.wundu.infrastructure.repository;

import ao.com.wundu.api.dto.TransactionEventDTO;
import ao.com.wundu.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByCardId(Long cardId, Pageable pageable);
    List<Transaction> findByCardId(Long cardId);
}
