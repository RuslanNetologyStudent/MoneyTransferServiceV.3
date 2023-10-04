package api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import api.model.OperationConfirmation;
import api.model.SuccessConfirmation;
import api.model.SuccessTransfer;
import api.model.Transfer;

@RequestMapping
public interface TransferResource {

    @PostMapping("/transfer")
    ResponseEntity<SuccessTransfer> transferMoney(Transfer transfer);
    @PostMapping("/confirmOperation")
    ResponseEntity<SuccessConfirmation> confirmOperation(OperationConfirmation confirmation);
}