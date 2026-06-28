package uz.pdp.utils;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String text, String callBack, String emoji) {
        String emojiText= EmojiParser.parseToUnicode(emoji+" "+text);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojiText);
        button.setCallbackData(callBack);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup mainMenu() {
        InlineKeyboardButton button1=InlineButtonUtil.button("Balance", "balance_callback", "\uD83D\uDCB8");
        InlineKeyboardButton button2=InlineButtonUtil.button("Transfer", "transfer_callback", ":dart:");

        List<InlineKeyboardButton> row = InlineButtonUtil.row(button1, button2);

        InlineKeyboardButton button3=InlineButtonUtil.button("Payments", "payments_callback", ":dart:");
        InlineKeyboardButton button4=InlineButtonUtil.button("Monitoring", "manitoring_callback", ":dart:");

        List<InlineKeyboardButton> row2 = InlineButtonUtil.row(button2, button3);

        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row, row2));
    }



    private static InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }

    public static InlineKeyboardMarkup chekButton(String id) {
        InlineKeyboardButton button=InlineButtonUtil.button("Chek", "chek_callback_" +id, ":dart:");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);
        return InlineButtonUtil.keyboard(InlineButtonUtil.rowList(row));
    }
}
