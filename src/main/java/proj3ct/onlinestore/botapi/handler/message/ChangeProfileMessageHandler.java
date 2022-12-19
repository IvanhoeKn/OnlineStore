package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardMarkupBuilder;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Users;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class ChangeProfileMessageHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    private UserDao userDao;

    public ChangeProfileMessageHandler(ReplyMessageService replyMessageService,
                                       LocaleMessageService messageService,
                                       InlineKeyboardService keyboardService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
        this.keyboardService = keyboardService;
        this.userDao = new UserDao();
    }

    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        String userAnswer = message.getText();
        BotStates currentState = userDao.getCurrentBotStatesForUserByTgId(userId);
        Users user = userDao.getUserByTgId(userId);
        switch (currentState) {
            case CHANGE_NAME:
                if (checkNameSurname(userAnswer)) {
                    user.setName(userAnswer);
                } else {
                    return replyMessageService.getTextMessage(chatId,
                            localeMessageService.getMessage("reply.change.name"));
                }
                break;
            case CHANGE_SURNAME:
                if (checkNameSurname(userAnswer)) {
                    user.setSurname(userAnswer);
                } else {
                    return replyMessageService.getTextMessage(chatId,
                            localeMessageService.getMessage("reply.change.surname"));
                }
                break;
            case CHANGE_PHONE:
                if (checkPhone(userAnswer)) {
                    user.setPhoneNumber(userAnswer);
                } else {
                    return replyMessageService.getTextMessage(chatId,
                            localeMessageService.getMessage("reply.change.phone"));
                }
                break;
        }
        user.setBotState(BotStates.SHOW_PROFILE.toString());
        userDao.update(user);
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = keyboardService.getProfile(message.getChatId());
        keyboardMarkupBuilder.setText("Изменения в профиль внесены!\n\n" + userDao.getInfoProfile(message.getFrom().getId()));
        SendMessage sendMessage = keyboardMarkupBuilder.build();
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public BotStates getHandlerName() {
        return BotStates.CHANGE;
    }

    private boolean checkNameSurname(String forChecking) {
        return forChecking != null && forChecking.matches("^[a-zA-Zа-яА-Я]*$");
    }

    private boolean checkPhone(String phone) {
        String regex = "\\D";
        phone = phone.replaceAll(regex, "");
        boolean result = false;
        switch (phone.length()) {
            case 10:
                phone = "8" + phone;
                result = true;
                break;
            case 11:
                if (phone.charAt(0) == '7') {
                    phone = "8" + phone.substring(1, phone.length());
                    result = true;
                } else if (phone.charAt(0) == '8') {
                    result = true;
                } else {
                    result = false;
                }
                break;
            default:
                result = false;
                break;
        }
        return result;
    }
}