package domain;

import lombok.*;
import api.model.Amount;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private String number;
    private String validTill;
    private String cvv;
    private Map<String, Amount> amounts;
}