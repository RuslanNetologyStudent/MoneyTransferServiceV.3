package api.model;

public record Transfer (String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
}
