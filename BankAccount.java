package ao.com.wundu.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BankAccount {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String accountNumber;  // Deve ser criptografado

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BankAccount() {
    }

    public BankAccount(UUID id, String accountNumber, String bankName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

