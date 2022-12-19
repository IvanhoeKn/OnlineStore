package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Users;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Component
public class CallbackProcessProfile implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.PROCESS_PROFILE;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public CallbackProcessProfile(ReplyMessageService messageService,
                                       LocaleMessageService localeMessageService,
                                       InlineKeyboardService keyboardService) {
        this.messageService = messageService;
        this.localeMessageService = localeMessageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return HANDLER_QUERY_TYPE;
    }

    @Override
    public PartialBotApiMethod<? extends Serializable> handleCallbackQuery(CallbackQuery callbackQuery) {
        final Long chatId = callbackQuery.getMessage().getChatId();
        CallbackQueryType queryType = CallbackQueryType.valueOf(callbackQuery.getData());
        UserDao userDao = new UserDao();
        Users user = userDao.getUserByTgId(callbackQuery.getFrom().getId());
        switch (queryType) {
            case CHANGE_NAME:
                user.setBotState(BotStates.CHANGE_NAME.toString());
                userDao.update(user);
                return messageService.getEditedTextMessage(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        localeMessageService.getMessage("reply.change.name"));
            case CHANGE_PHONE:
                user.setBotState(BotStates.CHANGE_PHONE.toString());
                userDao.update(user);
                return messageService.getEditedTextMessage(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        localeMessageService.getMessage("reply.change.phone"));
            case CHANGE_SURNAME:
                user.setBotState(BotStates.CHANGE_SURNAME.toString());
                userDao.update(user);
                return messageService.getEditedTextMessage(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        localeMessageService.getMessage("reply.change.surname"));
            case DELETE_PROFILE:
                return messageService.getEditedMarkup(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        keyboardService.deleteProfile(chatId).getKeyboard(),
                        "Вы уверены, что хотите удалить профиль?");
            case CONFIRM_DELETE:
                userDao.delete(user);
                return messageService.getEditedTextMessage(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        "\nВаш профиль удален!" + "\nЧтобы снова воспользоваться ботом отправьте /start");
            case CANCEL_DELETE:
                return messageService.getEditedMarkup(chatId,
                        callbackQuery.getMessage().getMessageId(),
                        keyboardService.getProfile(chatId).getKeyboard(),
                        userDao.getInfoProfile(callbackQuery.getFrom().getId()));
        }
        return new SendMessage();
    }
}