package ao.com.wundu.infrastructure.repository;

import ao.com.wundu.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByCardId(Long cardId, Pageable pageable);
}
