package uz.pdp.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardButtonUtil {

    public static ReplyKeyboardMarkup shareContact() {
        KeyboardButton keyboardButton = new KeyboardButton("Kontaktni yuborish");
        keyboardButton.setRequestContact(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(keyboardButton);
        keyboard.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup shareLocation() {
        KeyboardButton keyboardButton = new KeyboardButton("Joylashuvni yuborish");
        keyboardButton.setRequestLocation(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(keyboardButton);
        keyboard.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }


    public static ReplyKeyboardMarkup authMenu() {

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Registration"));
        row1.add(new KeyboardButton("Login"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);

        return createMarkup(keyboard);
    }


    public static ReplyKeyboardMarkup cancel() {
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("cancel"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        return createMarkup(keyboard);
    }

    private static ReplyKeyboardMarkup createMarkup(List<KeyboardRow> keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup mainMenu() {

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("balance"));
        row1.add(new KeyboardButton("transfer"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("payments"));
        row2.add(new KeyboardButton("monitoring"));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        return createMarkup(keyboard);
    }


}
