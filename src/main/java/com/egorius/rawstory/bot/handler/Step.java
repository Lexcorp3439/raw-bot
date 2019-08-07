package com.egorius.rawstory.bot.handler;

import java.math.BigDecimal;
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
    private static String[] answer31 = new String[]{"Принято!", "Введите цену товара!"};
    private static String[] err3 = new String[]{
            "Количество фотографий не должно превышать 6!\n" +
                    "Фотографии будут удалены, повторите попытку снова!"
    };

    private static String[] answer4 = new String[]{"Вы уверены?"};
    private static String[] answer5 = new String[]{"Сброшено!"};
    private static String[] answer6 = new String[]{"Успешно добавлено!"};
    private static String[] errorCost = new String[]{"Неверный формат числа!", "Введите дргуое число!"};
    private static String[] answerCost = new String[]{"Отлично!", "Теперь введите количество Калорий"};
    private static String[] errorCal = new String[]{"Неверный формат числа!", "Введите дргуое число!"};
    private static String[] answerCal = new String[]{"Введите количество Белков"};
    private static String[] errorSqu = new String[]{"Неверный формат числа!", "Введите дргуое число!"};
    private static String[] answerSqu = new String[]{"Введите количество Жиров"};
    private static String[] errorFat = new String[]{"Неверный формат числа!", "Введите дргуое число!"};
    private static String[] answerFat = new String[]{"Введите количество Углеводов"};
    private static String[] errorCar = new String[]{"Неверный формат числа!", "Введите дргуое число!"};


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
                if (handler.constructor.isProduct()) {
                    handler.dropKeys();
                    return new Action(Act.Message, answer31);
                } else {
                    handler.changeKeys(Arrays.asList(Messages.Button.save, Messages.Button.delete, Messages.Button.check));
                    return new Action(Act.Message, answer3);
                }
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
                handler.constructor.clear();
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


    static Action cost(MessageHandler messageHandler, String msg) {
        if (isNumber(msg)) {
            long cost = Long.parseLong(msg);
            if (cost < 0) {
                return new Action(Act.Message, errorCost);
            } else {
                messageHandler.cost = true;
                messageHandler.constructor.setCost(new BigDecimal(cost));
                return new Action(Act.Message, answerCost);
            }
        }
        return new Action(Act.Message, errorCost);
    }

    static Action cal(MessageHandler messageHandler, String msg) {
        if (isNumber(msg)) {
            int cal = Integer.parseInt(msg);
            if (cal < 0) {
                return new Action(Act.Message, errorCal);
            } else {
                messageHandler.cal = true;
                messageHandler.constructor.setCal(cal);
                return new Action(Act.Message, answerCal);
            }
        }
        return new Action(Act.Message, errorCal);
    }

    static Action squirrels(MessageHandler messageHandler, String msg) {
        if (isNumber(msg)) {
            int squirrels = Integer.parseInt(msg);
            if (squirrels < 0) {
                return new Action(Act.Message, errorSqu);
            } else {
                messageHandler.squirrels = true;
                messageHandler.constructor.setSquirrels(squirrels);
                return new Action(Act.Message, answerSqu);
            }
        }
        return new Action(Act.Message, errorSqu);
    }

    static Action fats(MessageHandler messageHandler, String msg) {
        if (isNumber(msg)) {
            int fats = Integer.parseInt(msg);
            if (fats < 0) {
                return new Action(Act.Message, errorFat);
            } else {
                messageHandler.fats = true;
                messageHandler.constructor.setFats(fats);
                return new Action(Act.Message, answerFat);
            }
        }
        return new Action(Act.Message, errorFat);
    }

    static Action carbohydrates(MessageHandler messageHandler, String msg) {
        if (isNumber(msg)) {
            int carbohydrates = Integer.parseInt(msg);
            if (carbohydrates < 0) {
                return new Action(Act.Message, errorCar);
            } else {
                messageHandler.carbohydrates = true;
                messageHandler.constructor.setCarbohydrates(carbohydrates);
                messageHandler.changeKeys(Arrays.asList(Messages.Button.save, Messages.Button.delete, Messages.Button.check));
                return new Action(Act.Message, answer3);
            }
        }
        return new Action(Act.Message, errorCar);
    }

    private static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
