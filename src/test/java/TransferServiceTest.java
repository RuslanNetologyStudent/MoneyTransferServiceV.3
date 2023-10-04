import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import api.model.Amount;
import api.model.Transfer;
import api.error.InvalidCardDataException;
import repository.TransferRepository;
import service.TransferServiceImpl;
import validation.CardValidator;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    private final static Transfer VALID_TRANSFER = new Transfer(
            "1111 1111 1111 1111", "01/24", "111", "2222 2222 2222 2222",
            new Amount("RUR",50000 ));
    private final static Transfer INVALID_CARD_TRANSFER = new Transfer(
            "2222 2222 2222 2222", "02/25", "222", "1111 1111 1111 1111",
            new Amount("RUR", 50000));
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private CardValidator cardValidator;
    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(transferService, "transferCommission", 0.01d);
    }

    @Test
    public void testTransferWhenValidTransferThenReturnOperationId() {
        Mockito.when(cardValidator.validateCardData(VALID_TRANSFER)).thenReturn(true);
        Mockito.when(transferRepository.addTransfer(VALID_TRANSFER)).thenReturn(1L);

        String operationId = transferService.transfer(VALID_TRANSFER).operationId();

        Assertions.assertEquals("1", operationId);
    }

    @Test
    public void testTransferWhenInvalidCardTransferThenThrowEx() {
        Mockito.when(cardValidator.validateCardData(INVALID_CARD_TRANSFER)).thenThrow(InvalidCardDataException.class);

        Assertions.assertThrows(InvalidCardDataException.class, () -> transferService.transfer(INVALID_CARD_TRANSFER));
    }
}