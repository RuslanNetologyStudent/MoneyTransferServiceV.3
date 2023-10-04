package repository;

import org.springframework.stereotype.Repository;
import api.model.OperationConfirmation;
import api.model.Transfer;

@Repository
public interface TransferRepository {

    Long addTransfer(Transfer transfer);
    boolean confirmOperation(OperationConfirmation confirmation);
    Transfer getTransferById(String id);
}