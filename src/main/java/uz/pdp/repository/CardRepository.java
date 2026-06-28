package uz.pdp.repository;

import uz.pdp.entity.Card;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardRepository {


    public void saveCards(List<Card> profiles) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( new File("src/main/resources/card.txt") );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Card> getCards(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/card.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Card> list = (List<Card>) objectInputStream.readObject();

            return list;

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }


    {
        List<Card> list = getCards();
        if ( list == null ) {

               Card card = new Card(UUID.randomUUID().toString(), "Ali Aliyev", "8600111111111111", new BigDecimal("100000"), "934445566", LocalDate.now().plusYears(5));
               Card card1 = new Card(UUID.randomUUID().toString(), "Vali Valiyev", "8600222222222222", new BigDecimal("100000"), "901112233", LocalDate.now().plusYears(5));

               List<Card> list1 = new ArrayList<Card>();
               list1.add(card);
               list1.add(card1);

               saveCards(list1);
        }
    }


}
