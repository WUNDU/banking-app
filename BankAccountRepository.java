package ao.com.wundu.repository;

import ao.com.wundu.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    
}






