package uz.pdp.repository;

import uz.pdp.entity.Card;
import uz.pdp.entity.Transfer;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardRepository {


    public void saveTransfer(Card from, Card to, Transfer transfer) {
        List<Card> list = getCards();
        for (Card card : list) {
            if ( card.getNumber().equals(from.getNumber()) ) {
                card.setBalance( from.getBalance() );
            } else if (card.getNumber().equals(to.getNumber())) {
                card.setBalance( to.getBalance() );
            }
        }
        saveCards(list);

        List<Transfer> transferList = getTransfers();
        transferList.add(transfer);
        saveTransfers(transferList);

    }





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




    public void saveTransfers(List<Transfer> profiles) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( new File("src/main/resources/transfer.txt") );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transfer> getTransfers(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/transfer.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Transfer> list = (List<Transfer>) objectInputStream.readObject();

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

    {
        if ( getTransfers() == null ) {
            List<Transfer> list = new ArrayList<>();
            saveTransfers(list);
        }
    }


}
