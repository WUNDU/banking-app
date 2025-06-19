package ao.com.wundu.api.dto;

public record CardValidateResponse(
        Long cardId,
        String bankName,
        boolean approved
) {
}
