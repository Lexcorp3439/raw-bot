package com.egorius.rawstory.bot.handler;

public enum Verification {
    Instance;

    public Answer checkName(String name) {
        if (name.length() > 200) {
            return new Answer.Failed(new String[]{"Длина название не должна превосходить 200 символов!"});
        } else if (name.length() == 0) {
            return new Answer.Failed(new String[]{"Вы не ввели название"});
        }
        return new Answer.Success(new String[]{"Название успешно добавлено!",
                "Теперь введите описание"});
    }

    public Answer checkDescription(String description) {
        if (description.length() == 0) {
            return new Answer.Failed(new String[]{"Вы не ввели описание"});
        }
        return new Answer.Success(new String[]{
                "Описание успешно добавлено!",
                "Теперь отправьте фотографии одним сообщением ",
                "Если не хотите загружать фотографии напишите \"Пропустить\"(без кавычек)"
        });
    }
}
