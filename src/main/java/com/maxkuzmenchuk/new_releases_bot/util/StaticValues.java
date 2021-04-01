package com.maxkuzmenchuk.new_releases_bot.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StaticValues {

    /**
     * ### Значения для работы со Spotify API ###
     * @field REFRESH_TOKEN - токен для обновления токена доступа к Spotify API
     * @field CLIENT_ID - id приложения, созданного на Spotify for Developers
     * @field CLIENT_SECRET - ключ приложения
     */
    public static final String REFRESH_TOKEN = "AQBA7xp9xos09gRgLCMPfYJ3_If8EtDABUh0_GuwSHP5aJ3HcGSlOkIRFxW3iy" +
            "mzzKWuMVAX5viJ7R7CqrSNNmyvEC5D8T4HxSUh8UJEhOFEsndSQMdoE5ShQX8znUk1W9s";

    public static final String CLIENT_ID = "91e72835d55d41c8a1f03d469f9eadce";

    public static final String CLIENT_SECRET = "4629e1b05a80428a89cf2e90cdee8e8a";


    /**
     * ### Данные для работы с Telegram ###
     *
     * @field BOT_NAME - имя бота
     * @field BOT_TOKEN - токен для работы с сервером
     */
    public static final String BOT_NAME = "@TestMSC_bot";

    public static final String BOT_TOKEN = "1750890938:AAGhhTK8QiUi7JbHM2pZ5W2KkPqITWpUFyE";

    public static final String MESSAGE_TO_CHANNEL_URL = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?";


    /**
     *   ### Messages ###
     *  @field HELP_MESSAGE - сообщение, которое выводится при вызове команды /help
     *  @field NOT_VALID_CHANNEL_COMMAND_MESSAGE - ошибка ввода канала
     *  @field NO_CHANNEL_NAME_MESSAGE - сообщение, которое выводится при пустой строке после @
     *  @field NO_CHANNEL_ID_MESSAGE - ошибка поиска ID канала @
     *  @field WRONG_MESSAGE - сообщение, которое выводится при вызове команды начинающейся не с /
     *  @field CHANNEL_IS_EXISTS_MESSAGE - канал уже существует в БД
     *  @field CHANNEL_SAVED_SUCCESSFULLY - канал успешно сохранен в БД
     */
    public static final String HELP_MESSAGE = "ℹ️ *Список доступных команд и их описание*: \n"
            + "\n \uD83D\uDCE2 /channel - позволяет установить свой канал, куда будут приходить обновления \n"
            + "\n ⚙️ Как добавить канал: \n"
            + "\n 1️⃣ *Создай канал* \n ‼️*ВАЖНО*‼️️: канал должен быть в общим доступом, позже ты сможешь его ограничить "
            + "\n 2️⃣ *Добавь бота в свой канал*: \n Управление каналом ➡ добавить пользователя ➡ в поисковую строку введи `юзернейм бота` "
            + "\n 3️⃣ *Пришли боту сообщение вида*:\n </channel @___имяканала___>"
            + "\n 4️⃣ *Проверь канал* \n Если пришло тестовое сообщение, значит все прошло успешно. В случае отсутствия сообщения обратись к администратору \n"
            + "\n \uD83C\uDD97 Теперь можешь смело изменить настройки приватности и закрыть канал \n"
            + "\n ❕ Добавление канала - не обязательное действие! Посты по умолчанию приходят в чат с ботом \n"
            + "\n \uD83D\uDCE2 /update - запустить поиск вручную"
            + "\n \uD83D\uDCE2 /help - описание доступных команд";


    public static final String NOT_VALID_CHANNEL_COMMAND_MESSAGE = "❌ *Неправильно введена команда!* \n Шаблон: </channel @___имяканала___>";

    public static final String NO_CHANNEL_NAME_MESSAGE = "❌ *Имя канала не может быть пустым!*";

    public static final String NO_CHANNEL_ID_MESSAGE = "❌ *Ошибка получения ID канала!*";

    public static final String WRONG_MESSAGE = "❌ Что-то пошло не так \uD83E\uDD2D Введи команду /help для справки";

    public static final String CHANNEL_TEST_MESSAGE = "Не забывай проверять канал!\uD83D\uDE09";

    public static final String CHANNEL_IS_EXISTS_MESSAGE = "❌ *Канал уже существует в базе данных*";

    public static final String CHANNEL_SAVED_SUCCESSFULLY_MESSAGE = "✔️ *Канал успешно добавлен!*";
}