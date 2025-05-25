package ao.com.wundu.infrastructure.service;

import ao.com.wundu.api.dto.TransactionRequest;
import ao.com.wundu.api.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);
    Page<TransactionResponse> listTransactionsByCard(Long cardId, Pageable pageable);

}
