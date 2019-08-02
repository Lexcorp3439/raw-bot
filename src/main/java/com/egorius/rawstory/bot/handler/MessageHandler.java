package com.egorius.rawstory.bot.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static com.egorius.rawstory.bot.handler.Messages.add;
import static com.egorius.rawstory.bot.handler.Messages.drop;


public class MessageHandler {

    private Long chatID;
    private Constructor constructor;
    private String lastMessage = "";
    public ReplyKeyboard replyKeyboard = new ReplyKeyboardRemove();
    private String[] lastImages = new String[]{};
    private List<KeyboardRow> keyboard = new ArrayList<>();
    private KeyboardRow keyboardRow = new KeyboardRow();
    private List<String> lastButtons = new ArrayList<>();

    public boolean start = false;
    private boolean choice = false;
    private boolean name = false;
    private boolean description = false;
    private boolean images = false;

    public MessageHandler(Long chatID) {
        this.chatID = chatID;
        System.out.println(chatID);
    }

    public String[] processing(Message message) {
        if (message.hasText()) {
            return text(message.getText());
        } else if (message.hasPhoto()) {
            return images(message.getPhoto());
        }
        return new String[]{""};
    }

    private String[] text(String msg) {
        if (start && msg.equals(add)) {
            backFlags();
        }
        if (!start) {
            if (msg.equals(add)) {
                changeKeys(Arrays.asList("Добавить новый пост", "Добавить новый товар"));
                start = true;
                return new String[]{"Выберите тип создаваемого объекта"};
            } else {
                return new String[]{"Посмотрите команду '/help'"};
            }
        }
        if (!choice) {
            if (msg.equals(Messages.create_post) || msg.equals(Messages.create_product)) {
                choice = true;
                if (msg.equals(Messages.create_post)) {
                    constructor = new Constructor(Constructor.Type.Post);
                } else {
                    constructor = new Constructor(Constructor.Type.Product);
                }
                dropKeys();
                return new String[]{"Приступим! Отправьте название объекта"};
            } else {
                return new String[]{"Выберите тип создаваемого объекта"};
            }
        }
        if (!name) {
            Answer answer1 = Verification.Instance.checkName(msg);
            if (answer1 instanceof Answer.Success) {
                constructor.setName(msg);
                name = true;
            }
            return answer1.getMsg();
        }
        if (!description) {
            Answer answer1 = Verification.Instance.checkDescription(msg);
            if (answer1 instanceof Answer.Success) {
                constructor.setDescription(msg);
                description = true;
            }
            return answer1.getMsg();
        }
        if (!images && msg.toLowerCase().equals("пропустить")) {
            changeKeys(Arrays.asList("Завершить", "Сбросить", "Проверить"));
            images = true;
            return new String[]{"Этап пропущен"};
        }
        if (images) {
            switch (msg) {
                case Messages.delete:
                    changeKeys(Arrays.asList("Да", "Нет"));
                    return new String[]{"Вы уверены?"};
                case Messages.yes:
                    constructor = null;
                    dropKeys();
                    backFlags();
                    return new String[]{"Сброшено!"};
                case Messages.no:
                    return new String[]{""};
                case Messages.save:
                    dropKeys();
                    constructor.save();
                    backFlags();
                    return new String[]{"Успешно добавлено!"};
                case Messages.check:
                    changeKeys(Arrays.asList("Завершить", "Сбросить", "Проверить"));
                    return new String[]{constructor.check()};
            }

        }

        return new String[]{};
    }

    private String[] images(List<PhotoSize> photos) {
        if (start && name && description && !images) {
            images = true;
            String id = Objects.requireNonNull(photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null)).getFileId();
            System.out.println(id);
            lastImages = new String[photos.size()];
            int i = 0;
            for (PhotoSize photo : photos) {
                lastImages[i] = photo.getFileId();
                i++;
            }
            constructor.setImages(lastImages.clone());
            changeKeys(Arrays.asList("Завершить", "Сбросить", "Проверить"));
            System.out.println(photos.size());
            return new String[]{
                    "Картинки добавлены!"
            };
        }
        return new String[]{};
    }


    private void changeKeys(List<String> list) {
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

    private void dropKeys() {
        replyKeyboard = new ReplyKeyboardRemove();
    }

    private void backFlags() {
        start = false;
        choice = false;
        name = false;
        description = false;
        images = false;
    }

    public void drop() {
        dropKeys();
        backFlags();
    }
}
