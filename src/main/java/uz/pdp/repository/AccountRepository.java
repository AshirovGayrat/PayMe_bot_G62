package uz.pdp.repository;

import uz.pdp.entity.Account;
import uz.pdp.entity.Card;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    public void saveAccount(Account account){
        List<Account> list = getAccounts();
        list.add(account);
        saveAccounts(list);
    }

    public void saveAccounts(List<Account> profiles) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream( new File("src/main/resources/account.txt") );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAccounts(){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/account.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Account> list = (List<Account>) objectInputStream.readObject();

            return list;

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    {

        if (getAccounts()==null){
            List<Account> profiles = new ArrayList<>();
            saveAccounts(profiles);
        }

    }


}
