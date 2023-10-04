package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api.TransferResource;
import api.model.OperationConfirmation;
import api.model.Transfer;
import api.model.SuccessConfirmation;
import api.model.SuccessTransfer;
import service.TransferService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"${cors.allowed-origins}"},
        methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class TransferController implements TransferResource {

    private final TransferService transferService;

    @Override
    public ResponseEntity<SuccessTransfer> transferMoney(@RequestBody Transfer transfer) {
        return ResponseEntity.ok(transferService.transfer(transfer));
    }

    @Override
    public ResponseEntity<SuccessConfirmation> confirmOperation(@RequestBody OperationConfirmation confirmation) {
        return ResponseEntity.ok(transferService.confirm(confirmation));
    }

}