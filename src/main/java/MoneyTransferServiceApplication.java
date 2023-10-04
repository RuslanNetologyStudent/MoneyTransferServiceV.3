import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import api.model.Amount;
import domain.Card;
import repository.CardRepositoryImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class MoneyTransferServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeCards(CardRepositoryImpl cardRepository) {
        return args -> {
            Map<String, Card> cards = cardRepository.getCards();

            cards.put("1111 1111 1111 1111", new Card("1111 1111 1111 1111", "01/24", "111",
                    new ConcurrentHashMap<>(Map.of("RUR", new Amount("RUR",100000 )))));
            cards.put("2222 2222 2222 2222", new Card("2222 2222 2222 2222", "02/25", "222",
                    new ConcurrentHashMap<>(Map.of("EUR", new Amount("EUR",200000 )))));
            cards.put("3333 3333 3333 3333", new Card("3333 3333 3333 3333", "03/26", "333",
                    new ConcurrentHashMap<>(Map.of("USD", new Amount("USD", 300000)))));
            cards.put("1122334455667788", new Card("1122334455667788", "03/31", "444",
                    new ConcurrentHashMap<>(Map.of(
                            "RUR", new Amount("RUR",10000 ),
                            "EUR", new Amount("EUR",20000 )))));
            cards.put("9876543210987654", new Card("9876543210987654", "08/24", "555",
                    new ConcurrentHashMap<>(Map.of(
                            "RUR", new Amount("RUR",30000 ),
                            "USD", new Amount("USD",40000 ),
                            "EUR", new Amount("EUR", 50000)))));
        };
    }
}