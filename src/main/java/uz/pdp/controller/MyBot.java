package uz.pdp.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {

    private final MainController mainController = new MainController();


    @Override
    public void onUpdateReceived(Update update) {

        if ( update.hasMessage() ) {

            Message message = update.getMessage();
            if ( message.hasText() ){
                SendMessage sendMessage = mainController.textHandler(message);
                send(sendMessage);
            }

            /*else if (message.hasContact()) {
                Contact contact = message.getContact();
                String phoneNumber = contact.getPhoneNumber();
            } else if (message.hasLocation()) {
                Location location = message.getLocation();

            }*/


        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callback =  callbackQuery.getData();
            System.out.println(callbackQuery.getData());

            if ( callback.equals("chek_callback") ) {
                SendDocument sendDocument = mainController.createPdf( callbackQuery.getFrom().getId() );
                send( sendDocument );
            }
            else {
                SendMessage sendMessage = mainController.callbackHandler(callback, callbackQuery.getFrom().getId());
                send(sendMessage);
            }
        }

    }











    public void send(Object message) {
        try {

            if ( message instanceof SendMessage sendMessage ) {
                execute(sendMessage);
            } else if (message instanceof SendDocument sendDocument) {
                execute( sendDocument );
            }


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }








    @Override
    public String getBotUsername() {
        return "https://t.me/G62_first_bot";
    }

    @Override
    public String getBotToken() {
        return "8688915320:AAEOjizO3_hIBLLEGSR1-FHb8G1aRSCbqNY";
    }

}
