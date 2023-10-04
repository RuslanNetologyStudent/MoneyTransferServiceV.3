package repository;

import domain.Card;

import java.util.Optional;

public interface CardRepository {

    Optional<Card> getCardByNumber(String cardNumber);
}