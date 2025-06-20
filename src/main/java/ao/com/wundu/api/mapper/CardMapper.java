package ao.com.wundu.api.mapper;

import ao.com.wundu.api.dto.CardRequest;
import ao.com.wundu.api.dto.CardResponse;
import ao.com.wundu.domain.model.Card;
import ao.com.wundu.domain.model.BankAccount;
import ao.com.wundu.util.ExpirationDateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    public static Card toEntity(CardRequest request, BankAccount bankAccount) {

        Card entity = new Card();

        entity.setExpirationDate(ExpirationDateUtil.validateAndAdjustExpirationDate(request.expirationDate()));
        entity.setBankAccount(bankAccount);

        return entity;
    }

    public static CardResponse toResponse(Card card) {
        return new CardResponse(
                card.getId(),
                card.getBankAccount().getBankName(),
                card.getCardNumber(),
                ExpirationDateUtil.formatToDDMM(card.getExpirationDate()),
                card.getBankAccount().getId(),
                card.getCreatedAt()
        );
    }

    public static List<CardResponse> toResponseList(List<Card> cards) {
        return cards.stream()
                .map(CardMapper::toResponse)
                .collect(Collectors.toList());
    }
}
