package api.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

//public record Amount(String currency, int value) {

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Amount amount = (Amount) o;
//        return value == amount.value && currency == amount.currency;
//    }
//
//}

@Getter
@Setter
@AllArgsConstructor
public class Amount {

    private String currency;
    private Integer value;

    @Override
    public String toString() {
        return value + " " + currency;
    }
}