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
import api.model.OperationConfirmation;
import api.error.InvalidConfirmationDataException;
import repository.TransferRepository;
import validation.ConfirmationValidator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ConfirmationValidatorTest {

    @Mock
    private TransferRepository transferRepository;
    @InjectMocks
    private ConfirmationValidator confirmationValidator;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(confirmationValidator, "verificationCode",
                "0000");
    }

    @Test
    public void testConfirmWhenValidOperationThenReturnTrue() {
        OperationConfirmation validConfirmation = new OperationConfirmation("0000", "1");

        Mockito.when(transferRepository.confirmOperation(validConfirmation)).thenReturn(true);

        Assertions.assertTrue(confirmationValidator.validateConfirmation(validConfirmation));
    }

    @Test
    public void testConfirmWhenInvalidCodeConfirmationThenThrowEx() {
        OperationConfirmation invalidCodeConfirmation = new OperationConfirmation("2222", "1");

        Exception ex = Assertions.assertThrows(InvalidConfirmationDataException.class,
                () -> confirmationValidator.validateConfirmation(invalidCodeConfirmation));

        assertThat(ex).hasMessage("Неверный код подтверждения");
    }

    @Test
    public void testConfirmWhenInvalidOperationConfirmationThenThrowEx() {
        OperationConfirmation invalidOperationConfirmation = new OperationConfirmation("0000", "98");

        Mockito.when(transferRepository.confirmOperation(invalidOperationConfirmation)).thenReturn(false);

        Exception ex = Assertions.assertThrows(InvalidConfirmationDataException.class,
                () -> confirmationValidator.validateConfirmation(invalidOperationConfirmation));

        assertThat(ex).hasMessage("Нет операции с id");
    }
}
