package com.egorius.rawstory.bot.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.egorius.rawstory.bot.instance.Messages;
import com.egorius.rawstory.bot.pojos.Constructor;
import com.egorius.rawstory.bot.handler.Action.Act;

class Step {
    private static String[] answer1 = new String[]{"Выберите тип создаваемого объекта"};
    private static String[] err1 = new String[]{"Посмотрите команду '/help'"};

    private static String[] answer2 = new String[]{"Приступим! Отправьте название объекта"};
    private static String[] err2 = new String[]{"Выберите тип создаваемого объекта"};

    private static String[] answer3 = new String[]{"Принято!"};
    private static String[] err3 = new String[]{
            "Количество фотографий не должно превышать 6!\n" +
                    "Фотографии будут удалены, повторите попытку снова!"
    };

    private static String[] answer4 = new String[]{"Вы уверены?"};
    private static String[] answer5 = new String[]{"Сброшено!"};
    private static String[] answer6 = new String[]{"Успешно добавлено!"};


    static Action start(MessageHandler handler, String msg) {
        if (msg.equals(Messages.Commands.add)) {
            handler.changeKeys(Arrays.asList(Messages.Button.create_post, Messages.Button.create_product));
            handler.start = true;
            return new Action(Act.Message, answer1);
        } else {
            return new Action(Act.Message, err1);
        }
    }

    static Action choice(MessageHandler handler, String msg) {
        if (msg.equals(Messages.Button.create_post) || msg.equals(Messages.Button.create_product)) {
            handler.choice = true;
            if (msg.equals(Messages.Button.create_post)) {
                handler.constructor = new Constructor(Constructor.Type.Post);
            } else {
                handler.constructor = new Constructor(Constructor.Type.Product);
            }
            handler.dropKeys();
            return new Action(Act.Message, answer2);
        } else {
            return new Action(Act.Message, err2);
        }
    }

    static Action name(MessageHandler handler, String msg) {
        Answer answer = Verification.Instance.checkName(msg);
        if (answer instanceof Answer.Success) {
            handler.constructor.setName(msg);
            handler.name = true;
        }
        return new Action(Act.Message, answer.getMsg());
    }

    static Action description(MessageHandler handler, String msg) {
        Answer answer = Verification.Instance.checkDescription(msg);
        if (answer instanceof Answer.Success) {
            handler.constructor.setDescription(msg);
            handler.description = true;
        }
        handler.changeKeys(Arrays.asList(Messages.Button.skip, Messages.Button.good));
        return new Action(Act.Message, answer.getMsg());
    }

    static Action images(MessageHandler handler, String msg) {
        if (msg.equals(Messages.Button.good) || msg.equals(Messages.Button.skip)) {
            if (handler.lastImages.size() <= 5) {
                handler.images = true;
                handler.changeKeys(Arrays.asList(Messages.Button.save, Messages.Button.delete, Messages.Button.check));
                return new Action(Act.Message, answer3);
            } else {
                handler.lastImages.clear();
                handler.constructor.dropImages();
                handler.changeKeys(Arrays.asList(Messages.Button.skip, Messages.Button.good));
                return new Action(Act.Message, err3);
            }
        }
        return new Action(Act.No, new String[]{});
    }

    static Action end(MessageHandler handler, String msg) {
        switch (msg) {
            case Messages.Button.delete:
                handler.changeKeys(Arrays.asList(Messages.Button.yes, Messages.Button.no));
                return new Action(Act.Message, answer4);
            case Messages.Button.yes:
                handler.constructor = null;
                handler.drop();
                return new Action(Act.Message, answer5);
            case Messages.Button.no:
                new Action(Act.No, new String[]{});
            case Messages.Button.save:
                handler.constructor.save();
                handler.drop();
                return new Action(Act.Message, answer6);
            case Messages.Button.check:
                handler.changeKeys(Arrays.asList(Messages.Button.save, Messages.Button.delete, Messages.Button.check));
                List<String> a = new ArrayList<>(handler.lastImages);
                a.add(handler.constructor.check());
                return new Action(Act.Photo, a.toArray(new String[0]));
        }
        return new Action(Act.No, new String[]{});
    }


}
