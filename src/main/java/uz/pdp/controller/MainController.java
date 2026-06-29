package uz.pdp.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.entity.Account;
import uz.pdp.entity.Card;
import uz.pdp.entity.LoginAccount;
import uz.pdp.entity.Transfer;
import uz.pdp.enums.AccountStep;
import uz.pdp.enums.LoginStep;
import uz.pdp.enums.TransferStep;
import uz.pdp.service.AccountService;
import uz.pdp.service.CardService;
import uz.pdp.utils.InlineButtonUtil;
import uz.pdp.utils.KeyboardButtonUtil;
import uz.pdp.utils.PDFUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MainController {

    private final CardService cardService =  new CardService();
    private final AccountService  accountService =  new AccountService();

    private Map<Long, Account> map = new HashMap<>();

    private Map<Long, LoginAccount>  loginMap = new HashMap<>();

    private Map<Long, Transfer>   transferMap = new HashMap<>();


    public SendMessage textHandler (Message message) {

        Long chatId = message.getChatId();
        String text = message.getText(); // "100000"

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if ( text.equals("/start") ) {
            sendMessage.setText("Akkountingizga kiring yoki ro'yxatdan o'ting");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.authMenu());
            return sendMessage;
        } else if (text.equals("Registration")) {
            return registration(message);
        } else if (text.equals("Login")) {
            return login(message);
        } else if (loginMap.get(chatId) != null) {
            return login(message);
        } else if ( map.get(chatId) != null) {
            return registration(message);
        } else if (transferMap.get(chatId) != null) {
            return transfer(message);
        }

        return  sendMessage;
    }




    public SendMessage callbackHandler( String callback, Long chatId ) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if ( callback.startsWith("balance") ) {
            String balance = getBalanceByAccountId(chatId);
            sendMessage.setText("Balance : " + balance);
        } else if (callback.startsWith("transfer")) {
            sendMessage.setText("Enter card number");
            Transfer transfer = new Transfer();
            transfer.setId(UUID.randomUUID().toString());
            transfer.setStep( TransferStep.CARD );
            transferMap.put(chatId, transfer);
        }
        return   sendMessage;
    }


    private String getBalanceByAccountId(Long chatId) {
        Optional<Account> account = accountService.getAccountById(chatId);
        Card card = cardService.getCardByPhoneNumber(account.get().getPhoneNumber());
        return String.valueOf(card.getBalance());
    }


    public SendMessage transfer(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Transfer transfer = transferMap.get(chatId);

        if ( transfer.getStep().equals(TransferStep.CARD) ) {
            transfer.setTo( text );
            transfer.setStep(TransferStep.AMOUNT);
            transferMap.put(chatId, transfer);
            sendMessage.setText("Enter amount");
        } else if (transfer.getStep().equals(TransferStep.AMOUNT)) {

            BigDecimal amount = new BigDecimal(text);
            Account fromAccount  = accountService.getAccountById(chatId).get();
            Card fromCard = cardService.getCardByPhoneNumber(fromAccount.getPhoneNumber());
            Card toCard = cardService.getCardByCardNumber(transfer.getTo());

            transfer.setFrom(fromCard.getNumber());
            transfer.setAmount(amount);


            if ( fromCard.getBalance().compareTo(amount) >= 0 ) {
                fromCard.setBalance( fromCard.getBalance().subtract(amount) );
                toCard.setBalance( toCard.getBalance().add(amount) );

                transfer.setTime(LocalDateTime.now());
                transferMap.put( chatId, transfer );
                cardService.saveTransfer(fromCard, toCard, transfer);

                sendMessage.setText("Transfer success");
                sendMessage.setReplyMarkup( InlineButtonUtil.chekButton() );
            }
            else {
                sendMessage.setText("Balance not enough");
            }
        }

        return sendMessage;
    }

    public SendDocument createPdf( Long chatId ) {
        Transfer transfer = transferMap.get(chatId);
        transferMap.remove(chatId);
        return PDFUtil.createAndBuyPdf(transfer, chatId);
    }





    private SendMessage registration(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        Account account = map.get( chatId );

        if ( account == null ) {
            Account newAccount = new Account();
            newAccount.setId(chatId);
            newAccount.setStep(AccountStep.NAME);
            sendMessage.setText("Ismingizni kiriting");
            map.put(chatId, newAccount);
        } else if (account.getStep().equals(AccountStep.NAME)) {
            account.setFullName(text);
            account.setStep(AccountStep.PHONE);
            map.put( chatId, account);
            sendMessage.setText("Telefon raqam kiriting");
        } else if (account.getStep().equals(AccountStep.PHONE)) {
            account.setPhoneNumber(text);
            account.setStep(AccountStep.PASSWORD);
            map.put( chatId, account);
            sendMessage.setText("Parol Yarating");
        } else if (account.getStep().equals(AccountStep.PASSWORD)) {
            account.setPassword(text);
            account.setStep(AccountStep.CARD);
            map.put( chatId, account);
            sendMessage.setText("Karta raqam kiriting");
        } else if (account.getStep().equals(AccountStep.CARD)) {
            boolean res = cardService.checkCardsPhoneNumber( account.getPhoneNumber(), text );

            map.remove(chatId);

            if ( !res ) {
                sendMessage.setText("sms xabarnoma ulanmagan, qayta urinib ko'ring");
                return sendMessage;
            }

            boolean resultReg = accountService.saveAccounts(account);
            if ( !resultReg ) {
                sendMessage.setText("Telefon raqam oldin ro'yxatdan o'tgan");
                return sendMessage;
            }

            sendMessage.setText("Ro'yxatdan o'tildi");
            sendMessage.setReplyMarkup( InlineButtonUtil.mainMenu() );
        }

        return sendMessage;
    }


    public SendMessage login(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        LoginAccount loginAccount = loginMap.get(chatId);

        if (  loginAccount == null ) {
            LoginAccount newLoginAccount = new LoginAccount();
            newLoginAccount.setStep(LoginStep.PHONE);
            loginMap.put(chatId, newLoginAccount);
            sendMessage.setText("Telefon raqam kiriting");
        } else if (loginAccount.getStep().equals(LoginStep.PHONE)) {
            loginAccount.setPhoneNumber(text);
            loginAccount.setStep(LoginStep.PASSWORD);
            sendMessage.setText("Parol kiriting");
            loginMap.put(chatId, loginAccount);
        } else if (loginAccount.getStep().equals(LoginStep.PASSWORD)) {
            loginMap.remove(chatId);
            loginAccount.setPassword(text);
            boolean res = accountService.login( loginAccount.getPhoneNumber(), loginAccount.getPassword() );
            if ( res ) {
                sendMessage.setText("Welcome");
                sendMessage.setReplyMarkup( InlineButtonUtil.mainMenu() );
            }
            else {
                sendMessage.setText("Telefon yoki parol xato");
            }
        }

        return sendMessage;
    }



}
