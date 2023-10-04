package service;

import api.model.OperationConfirmation;
import api.model.Transfer;
import api.model.SuccessConfirmation;
import api.model.SuccessTransfer;

public interface TransferService {

    SuccessTransfer transfer(Transfer transfer);
    SuccessConfirmation confirm(OperationConfirmation confirmation);
    boolean executeTransfer(String operationId);
}