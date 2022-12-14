package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Users;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class RegistrationHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    private UserDao userDao;

    public RegistrationHandler(ReplyMessageService replyMessageService,
                               LocaleMessageService messageService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
        this.userDao = new UserDao();
    }

    public SendMessage handle(Message message) {
        if (userDao.getCurrentBotStatesForUserByTgId(message.getFrom().getId()).equals(BotStates.REGISTRATION)) {
            userDao.setCurrentBotStatesForUserWithTgId(message.getFrom().getId(), BotStates.ASC_NAME);
        }
        return processUsersRegistration(message);
    }

    public BotStates getHandlerName() {
        return BotStates.REGISTRATION;
    }

    private SendMessage processUsersRegistration(Message message) {
        Long chatId = message.getChatId();
        Long userId = message.getFrom().getId();
        SendMessage replyMessage = null;
        String userAnswer = message.getText();
        String chooseLocale = "reply.error";
        BotStates currentState = userDao.getCurrentBotStatesForUserByTgId(userId);
        switch (currentState) {
            case ASC_NAME:
                userDao.setCurrentBotStatesForUserWithTgId(userId, BotStates.ASC_SURNAME);
                chooseLocale = "reply.name";
                break;
            case ASC_SURNAME:
                if (checkNameSurname(userAnswer)) {
                    Users user = userDao.getUserByTgId(userId);
                    user.setName(userAnswer);
                    userDao.update(user);
                    userDao.setCurrentBotStatesForUserWithTgId(userId, BotStates.ASC_PHONE);
                    chooseLocale = "reply.surname";
                }
                else {
                    chooseLocale = "reply.incorrect";
                }
                break;
            case ASC_PHONE:
                if (checkNameSurname(userAnswer)) {
                    Users user = userDao.getUserByTgId(userId);
                    user.setSurname(userAnswer);
                    userDao.update(user);
                    userDao.setCurrentBotStatesForUserWithTgId(userId, BotStates.PROFILE_REGISTERED);
                    chooseLocale = "reply.phone";
                }
                else {
                    chooseLocale = "reply.incorrect";
                }
                break;
            case PROFILE_REGISTERED:
                if (checkPhone(userAnswer)) {
                    Users user = userDao.getUserByTgId(userId);
                    user.setPhoneNumber(userAnswer);
                    userDao.update(user);
                    userDao.setCurrentBotStatesForUserWithTgId(userId, BotStates.SHOW_MAIN_MENU);
                    chooseLocale = "reply.registrationSuccess";
                }
                else {
                    chooseLocale = "reply.incorrectPhone";
                }
                break;
        }
        return replyMessageService.getTextMessage(chatId, localeMessageService.getMessage(chooseLocale));
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
                }
                else
                    if (phone.charAt(0) == '8') {
                        result = true;
                    }
                    else {
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

