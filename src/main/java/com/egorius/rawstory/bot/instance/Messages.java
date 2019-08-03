package com.egorius.rawstory.bot.instance;

public class Messages {

    //    Команды для бота
    @SuppressWarnings("WeakerAccess")
    public static class Commands {
        public final static String start = "/start";
        public final static String help = "/help";
        public final static String add = "/add";
        public final static String drop = "/drop";
        public final static String bot_version = "/version";
        public final static String auth = "/auth";
    }

    //    Наименования кнопок
    public static class Button {
        public final static String create_post = "Добавить новый пост";
        public final static String create_product = "Добавить новый товар";
        public final static String yes = "Да";
        public final static String no = "Нет";
        public final static String delete = "Сбросить";
        public final static String save = "Завершить";
        public final static String check = "Проверить";
        public final static String good = "Готово";
        public final static String skip = "Пропустить";
    }
}
