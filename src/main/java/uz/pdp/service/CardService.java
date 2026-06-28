package uz.pdp.service;

import uz.pdp.entity.Card;
import uz.pdp.repository.CardRepository;

import java.util.List;

public class CardService {

    private final CardRepository cardRepository =  new CardRepository();


    public boolean checkCardsPhoneNumber(String phoneNumber, String cardNumber) {
        List<Card> list = cardRepository.getCards();
        for (Card card : list) {
            if ( card.getNumber().equals(cardNumber) && card.getPhoneNumber().equals(phoneNumber) ) {
                return true;
            }
        }
        return false;
    }


    public Card getCardByPhoneNumber(String phone) {
        List<Card> list = cardRepository.getCards();
        for (Card card : list) {
            if ( card.getPhoneNumber().equals(phone) ) {
                return card;
            }
        }
        return null;
    }

}
