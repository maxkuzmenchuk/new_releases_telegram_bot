package com.maxkuzmenchuk.new_releases_bot.util;

import lombok.experimental.UtilityClass;

/**
 * Some static values for Telegram, Spotify and messages for users
 */

@UtilityClass
public class StaticValues {

    /**
     * ### Values for Spotify API ###
     *
     * @apiNote REFRESH_TOKEN - token for updating access token to Spotify API
     * @apiNote CLIENT_ID - app id from Spotify for Developers
     * @apiNote CLIENT_SECRET - app key
     */
    public static final String REFRESH_TOKEN = "AQBA7xp9xos09gRgLCMPfYJ3_If8EtDABUh0_GuwSHP5aJ3HcGSlOkIRFxW3iy" +
            "mzzKWuMVAX5viJ7R7CqrSNNmyvEC5D8T4HxSUh8UJEhOFEsndSQMdoE5ShQX8znUk1W9s";

    public static final String CLIENT_ID = "91e72835d55d41c8a1f03d469f9eadce";

    public static final String CLIENT_SECRET = "4629e1b05a80428a89cf2e90cdee8e8a";


    /**
     * ### Values for Telegram ###
     *
     * @apiNote BOT_NAME - bot name @name
     * @apiNote BOT_TOKEN - token for Telegram API
     */
    public static final String BOT_NAME = "@TestMSC_bot";

    public static final String BOT_TOKEN = "1750890938:AAGhhTK8QiUi7JbHM2pZ5W2KkPqITWpUFyE";

    public static final String MESSAGE_TO_CHANNEL_URL = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage?";


    /**
     * ### Messages ###
     *
     * @apiNote HELP_MESSAGE - /help command response
     * @apiNote NOT_VALID_CHANNEL_COMMAND - wrong /channel input
     * @apiNote NO_CHANNEL_NAME -  empty string after @
     * @apiNote NO_CHANNEL_ID - id channel search error
     * @apiNote DELETION_ERROR - channel deletion error
     * @apiNote NO_CHANNEL_FOR_USER - channel is not set for user
     * @apiNote WRONG_MESSAGE - input message not start with /
     * @apiNote CHANNEL_TEST_MESSAGE - test message to user channel
     * @apiNote CHANNEL_IS_EXISTS - channel is exists in database
     * @apiNote CHANNEL_FOR_USER_IS_EXISTS - channel is already set for user
     * @apiNote CHANNEL_SAVED_SUCCESSFULLY - successful saving channel to database
     * @apiNote CHANNEL_DELETED_SUCCESSFULLY - successful deletion
     * @apiNote NO_NEW_RELEASES - json array with releases is empty
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


    public static final String NOT_VALID_CHANNEL_COMMAND = "❌ *Неправильно введена команда!* \n Шаблон: </channel @___имяканала___>";

    public static final String NO_CHANNEL_NAME = "❌ *Имя канала не может быть пустым!*";

    public static final String NO_CHANNEL_ID = "❌ *Ошибка получения ID канала!*";

    public static final String DELETION_ERROR = "❌ *Ошибка удаления канала!*";

    public static final String NO_CHANNEL_FOR_USER = "❌ *Для данного пользователя канал не установлен!*";

    public static final String WRONG_MESSAGE = "❌ Что-то пошло не так \uD83E\uDD2D Введи команду /help для справки";

    public static final String CHANNEL_TEST_MESSAGE = "Не забывай проверять канал!\uD83D\uDE09";

    public static final String CHANNEL_IS_EXISTS = "❌ *Канал с таким именем уже существует в базе данных*";

    public static final String CHANNEL_FOR_USER_IS_EXISTS = "❌ *Для данного аккаунта канал уже установлен* \n Удалить существующий канал: /delete \n\n";

    public static final String CHANNEL_SAVED_SUCCESSFULLY = "✔️ *Канал успешно добавлен!*";

    public static final String CHANNEL_DELETED_SUCCESSFULLY = "✔️ *Канал успешно удален!*";

    public static final String NO_NEW_RELEASES = "*Ничего нового не дропнули!* \uD83D\uDE22";
}