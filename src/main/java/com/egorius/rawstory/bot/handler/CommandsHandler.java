package com.egorius.rawstory.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandsHandler {
    public static Action add(MessageHandler handler, Message msg) {
        if (handler.start) {
            String[] arr3 = new String[]{"Конструктор уже запущен. " +
                    "Для выхода завершите создание объекта или введите команду /drop"};
            return new Action(Action.Act.Message, arr3);
        } else {
            return handler.processing(msg);
        }
    }

    public static Action drop(MessageHandler handler) {
        handler.drop();
        String[] arr4 = new String[]{"Вы сбросили конструктор"};
        return new Action(Action.Act.Message, arr4);
    }

    public static Action bot_version(String version) {
        String[] arr5 = new String[]{"Версия бота: " + version};
        return new Action(Action.Act.Message, arr5);
    }

    public static Action help() {
        String[] arr1 = new String[]{
                "" +
                        "/auth + <кодовое слово> (без пробела) - авторизация пользователя\n" +
                        "Методы, доступные только после авторизации: \n" +
                        "/add - создать новый объект\n" +
                        "/drop - сбросить конструктор\n" +
                        "/version - узнать версию бота"
        };
        return new Action(Action.Act.Message, arr1);
    }

    public static Action start(String username) {
        String[] arr = new String[]{"Привет! Я - " + username + "! \n" +
                "Чтобы узнать мои возможности, введи команду /help"};
        return new Action(Action.Act.Message, arr);
    }

    public static Action auth(boolean a) {
        String[] arr2;
        if (!a) {
            arr2 = new String[]{"Авторизация прошла успешно!"};
        } else {
            arr2 = new String[]{"Вы уже авторизованы"};
        }
        return new Action(Action.Act.Message, arr2);
    }

    public static Action noAuth() {
        String[] arr6 = new String[]{"Для использования всех функций необходимо авторизоваться. " +
                "Введите /help для подробной информации"};
        return new Action(Action.Act.Message, arr6);
    }
}
