package com.egorius.rawstory.bot.handler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import com.egorius.rawstory.bot.pojos.Constructor;
import static com.egorius.rawstory.bot.instance.Messages.Commands.add;

public class MessageHandler {

    Constructor constructor;
    List<String> lastImages = new ArrayList<>();
    public ReplyKeyboard replyKeyboard = new ReplyKeyboardRemove();
    private List<KeyboardRow> keyboard = new ArrayList<>();
    private KeyboardRow keyboardRow = new KeyboardRow();
    private List<String> lastButtons = new ArrayList<>();

    boolean start = false;
    boolean choice = false;
    boolean name = false;
    boolean description = false;
    boolean images = false;
    private boolean end = false;

    public MessageHandler(Long chatID) {
        System.out.println(chatID);
    }

    public Action processing(Message message) {
        if (message.hasText()) {
            return text(message.getText());
        } else if (message.hasPhoto()) {
            images(message.getPhoto());
        }
        return new Action(Action.Act.No, new String[]{});
    }

    private Action text(String msg) {
        if (start && msg.equals(add)) {
            dropFlags();
        }
        if (!start) {
            return Step.start(this, msg);
        }
        if (!choice) {
            return Step.choice(this, msg);
        }
        if (!name) {
            return Step.name(this, msg);
        }
        if (!description) {
            return Step.description(this, msg);
        }
        if (!images) {
            return Step.images(this, msg);
        }
        if (!end) {
            return Step.end(this, msg);
        }
        return new Action(Action.Act.No, new String[]{});
    }

    private void images(List<PhotoSize> photos) {
        if (start && name && description && !images) {
            String id = Objects.requireNonNull(photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null)).getFileId();
            lastImages.add(id);

            for (PhotoSize photo : photos) {
                constructor.addImages(photo.getFileId());
            }
        }
    }


    void changeKeys(List<String> list) {
        replyKeyboard = new ReplyKeyboardMarkup();
        ((ReplyKeyboardMarkup) replyKeyboard).setSelective(true);
        ((ReplyKeyboardMarkup) replyKeyboard).setResizeKeyboard(true);
        ((ReplyKeyboardMarkup) replyKeyboard).setOneTimeKeyboard(false);
        keyboard.clear();
        keyboardRow.clear();
        lastButtons.clear();
        lastButtons.addAll(list);
        keyboardRow.addAll(lastButtons);
        keyboard.add(keyboardRow);
        ((ReplyKeyboardMarkup) replyKeyboard).setKeyboard(keyboard);
    }

    void dropKeys() {
        replyKeyboard = new ReplyKeyboardRemove();
    }

    private void dropFlags() {
        start = false;
        choice = false;
        name = false;
        description = false;
        images = false;
        end = false;
    }

    void drop() {
        lastImages.clear();
        dropKeys();
        dropFlags();
    }
}
