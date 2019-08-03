package com.egorius.rawstory.bot.handler;

public enum Verification {
    Instance;

    private final static String success1 = "Название успешно добавлено!";
    private final static String success2 = "Теперь введите описание";
    private final static String success3 = "Описание успешно добавлено!";
    private final static String success4 = "Теперь отправьте фотографии любым способо  и нажмите \"Готово\"!\n" +
            "ВАЖНО: Количество фотографий не должно превышать 5!";
    private final static String success5 = "Если не хотите загружать фотографии нажмите на \"Пропустить\"";

    private final static String error1 = "Длина название не должна превосходить 200 символов!";
    private final static String error2 = "Вы не ввели название";
    private final static String error3 = "Вы не ввели описание";

    public Answer checkName(String name) {
        if (name.length() > 200) {
            return new Answer.Failed(new String[]{error1});
        } else if (name.length() == 0) {
            return new Answer.Failed(new String[]{error2});
        }
        return new Answer.Success(new String[]{
                success1,
                success2
        });
    }

    public Answer checkDescription(String description) {
        if (description.length() == 0) {
            return new Answer.Failed(new String[]{error3});
        }
        return new Answer.Success(new String[]{
                success3,
                success4,
                success5
        });
    }
}
