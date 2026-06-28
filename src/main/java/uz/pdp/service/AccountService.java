package uz.pdp.service;

import uz.pdp.entity.Account;
import uz.pdp.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final AccountRepository accountRepository =  new AccountRepository();

    public Optional<Account> getAccountByPhone(String phoneNumber ){
        List<Account> accounts = accountRepository.getAccounts();
        for (Account account : accounts){
            if (account.getPhoneNumber().equals(phoneNumber)){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }


    public Optional<Account> getAccountById(Long id ){
        List<Account> accounts = accountRepository.getAccounts();
        for (Account account : accounts){
            if (account.getId().equals(id)){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }


    public  boolean saveAccounts(Account account){
        Optional<Account> optional = getAccountByPhone(account.getPhoneNumber());
        if (optional.isPresent()){
            return false;
        }

        accountRepository.saveAccount(account);
        return true;
    }


    public boolean login(String phoneNumber, String password) {
        List<Account> accounts = accountRepository.getAccounts();
        for (Account account : accounts){
            if ( account.getPhoneNumber().equals(phoneNumber) && account.getPassword().equals(password) ) {
                return true;
            }
        }
        return false;
    }


}
