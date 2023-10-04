package repository;

import org.springframework.stereotype.Repository;
import api.model.OperationConfirmation;
import api.model.Transfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransferRepositoryImpl implements TransferRepository {

    private final Map<Long, Transfer> transfers = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    @Override
    public Long addTransfer(Transfer transfer) {
        Long id = this.id.incrementAndGet();
        transfers.put(id, transfer);
        return id;
    }

    @Override
    public boolean confirmOperation(OperationConfirmation confirmation) {
        return transfers.containsKey(Long.parseLong(confirmation.operationId()));
    }

    @Override
    public Transfer getTransferById(String id) {
        return transfers.get(Long.parseLong(id));
    }
}