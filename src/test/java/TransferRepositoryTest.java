import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import api.model.Amount;
import api.model.OperationConfirmation;
import api.model.Transfer;
import repository.TransferRepositoryImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
public class TransferRepositoryTest {

    @InjectMocks
    private TransferRepositoryImpl transferRepository;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(transferRepository, "transfers",
                new ConcurrentHashMap<>(Map.of(
                        1L, new Transfer(
                                "1111 1111 1111 1111", "01/24", "111", "1234567890123456",
                                new Amount("RUR", 50000)))));
        ReflectionTestUtils.setField(transferRepository, "id", new AtomicLong(1));
    }

    @Test
    public void testAddTransferWhenAddTransferThenReturnCorrectId() {
        Transfer newTransfer = new Transfer("2222 2222 2222 2222", "02/26", "222",
                "3333 3333 3333 3333", new Amount("EUR",10000 ));

        Long id = transferRepository.addTransfer(newTransfer);
        Assertions.assertEquals(2, id);
    }

    @Test
    public void testConfirmOperationWhenValidOperationIdThenReturnTrue() {
        OperationConfirmation confirmation = new OperationConfirmation("0000", "1");

        Assertions.assertTrue(transferRepository.confirmOperation(confirmation));
    }

    @Test
    public void testConfirmOperationWhenInvalidOperationIdThenReturnFalse() {
        OperationConfirmation confirmation = new OperationConfirmation("0000", "5");

        Assertions.assertFalse(transferRepository.confirmOperation(confirmation));
    }
}