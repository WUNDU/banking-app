package ao.com.wundu.infrastructure.service;

import ao.com.wundu.api.dto.TransactionEventDTO;
import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);
    Page<TransactionResponse> listTransactionsByCard(Long cardId, Pageable pageable);
    List<TransactionEventDTO> listTransactionsForFinances(Long cardId);

}
