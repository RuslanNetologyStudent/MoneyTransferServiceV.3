package validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import api.model.OperationConfirmation;
import api.error.InvalidConfirmationDataException;
import repository.TransferRepository;

@Component
@RequiredArgsConstructor
public class ConfirmationValidator {

    @Value("${verification.code:0000}")
    private String verificationCode;
    private final TransferRepository transferRepository;

    public boolean validateConfirmation(OperationConfirmation confirmation) {
        if (!confirmation.code().equals(verificationCode)) {
            throw new InvalidConfirmationDataException("Неверный код подтверждения (" + confirmation.code() + ")");
        }

        if (!transferRepository.confirmOperation(confirmation)) {
            throw new InvalidConfirmationDataException("Нет операции с id " + confirmation.operationId());
        }

        return true;
    }
}