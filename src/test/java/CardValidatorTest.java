import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import api.model.Amount;
import domain.Card;
import api.model.Transfer;
import api.error.InvalidCardDataException;
import validation.CardValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class CardValidatorTest {

    @InjectMocks
    private CardValidator cardValidator;
    private final static Card VALID_CARD = new Card ("1111 1111 1111 1111", "01/24", "111",
            new ConcurrentHashMap<>(Map.of("RUR", new Amount("RUR",100000 ))));

    @Test
    public void testValidateCardDataWhenInvalidTillDateThenThrowEx() {
        Transfer invalidTillDateTransfer = new Transfer(
                "1111 1111 1111 1111", "01/24", "101", "2222 2222 2222 2222",
                new Amount("RUR", 50000));

        Exception ex = Assertions.assertThrows(InvalidCardDataException.class,
                () -> cardValidator.validateDateAndCvv(invalidTillDateTransfer, VALID_CARD));

        assertThat(ex).hasMessage("Введены неверные данные карты (срок действия / CVV номер)");
    }

    @Test
    public void testValidateCardDataWhenInvalidCVVThenThrowEx() {
        Transfer invalidCVVTransfer = new Transfer(
                "1111222233334444", "01/24", "111", "2222 2222 2222 2222",
                new Amount("RUR",40000 ));

        Exception ex = Assertions.assertThrows(InvalidCardDataException.class,
                () -> cardValidator.validateDateAndCvv(invalidCVVTransfer, VALID_CARD));

        assertThat(ex).hasMessage("Введены неверные данные карты (срок действия / CVV номер)");
    }

    @Test
    public void testValidateCardDataWhenInvalidCurrencyThenThrowEx() {
        Transfer invalidCurrencyTransfer = new Transfer(
                "1111 1111 1111 1111", "01/24", "111", "2222 2222 2222 2222",
                new Amount("USD",2000 ));
        String transferCurrency = invalidCurrencyTransfer.amount().getCurrency();

        Exception ex = Assertions.assertThrows(InvalidCardDataException.class,
                () -> cardValidator.validateCurrency(invalidCurrencyTransfer, VALID_CARD));

        assertThat(ex).hasMessage("На выбранной карте отсутствует счет в валюте " + transferCurrency);
    }

    @Test
    public void testValidateCardDataWhenInvalidAmountThenThrowEx() {
        ReflectionTestUtils.setField(cardValidator, "transferCommission", 0.01d);
        Transfer invalidAmountTransfer = new Transfer(
                "1111 1111 1111 1111", "01/24", "111", "2222 2222 2222 2222",
                new Amount("RUR", 15000000));

        Exception ex = Assertions.assertThrows(InvalidCardDataException.class,
                () -> cardValidator.validateAmount(invalidAmountTransfer, VALID_CARD));

        assertThat(ex).hasMessage("На выбранной карте недостаточно средств");
    }
}