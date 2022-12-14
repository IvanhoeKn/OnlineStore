package proj3ct.onlinestore.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import proj3ct.onlinestore.botapi.handler.BotStatesHandler;
import proj3ct.onlinestore.botapi.handler.callbackquery.CallbackUpdateReciever;
import proj3ct.onlinestore.dao.DiscountDao;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Users;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Slf4j
@Component
public class UpdateReceiver {
    private final BotStatesHandler botStatesHandler;

    private final ReplyMessageService replyMessageService;

    private final CallbackUpdateReciever callbackUpdateReciever;
    public UpdateReceiver(BotStatesHandler botStatesHandler,
                          CallbackUpdateReciever callbackUpdateReciever,
                          ReplyMessageService replyMessageService) {
        this.botStatesHandler = botStatesHandler;
        this.callbackUpdateReciever = callbackUpdateReciever;
        this.replyMessageService = replyMessageService;
    }

    public PartialBotApiMethod<? extends Serializable> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            BotStates botStates = getBotStates(message);
            log.info(
                    "Message from: {}; " +
                            "chat id: {};  " +
                            "text: {}; " +
                            "bot condition: {}",
                    message.getFrom().getUserName(),
                    message.getChatId(),
                    message.getText(),
                    botStates
            );

            return botStatesHandler.processInputMessage(botStates, message);
        }
        else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info(
                    "CallbackQuery from: {}; " +
                            "data: {}; " +
                            "message id: {}",
                    callbackQuery.getFrom().getUserName(),
                    callbackQuery.getData(),
                    callbackQuery.getId()
            );
            return callbackUpdateReciever.processCallbackQuery(callbackQuery);
        }
        else {
            log.error(
                    "Unsupported request from: {}; " +
                            "chatId: {}",
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getChatId()
            );
            return replyMessageService.getTextMessage(update.getMessage().getChatId(), "?? ???????? ?????????????????? ???????????? ?????????????????? ??????????????????!");
        }
    }

    private BotStates getBotStates(Message message) {
        Long userId = message.getFrom().getId();
        String userTextMessage = message.getText();
        BotStates botStates;
        UserDao userDao = new UserDao();
        switch (userTextMessage) {
            case "/start":
                Users user = userDao.getUserByTgId(userId);
                if (user == null) {
                    DiscountDao discountDao = new DiscountDao();
                    user = new Users();
                    user.setName("default");
                    user.setSurname("default");
                    user.setPhoneNumber("80000000000");
                    user.setIdTelegram(userId);
                    user.setDiscountByIdDiscount(discountDao.findById(5));
                    userDao.persist(user);
                    System.out.println(userId);
                    botStates = BotStates.REGISTRATION;
                }
                else {
                    botStates = BotStates.SHOW_MAIN_MENU;
                }
                break;
            case "/help":
                botStates = BotStates.HELP;
                break;
            case "??????????????":
                botStates = BotStates.SHOW_PROFILE;
                break;
            case "??????????????":
                botStates = BotStates.SHOW_BASKET;
                break;
            case "/catalog":
                botStates = BotStates.SHOW_CATALOG;
                break;
            case "/admin":
                botStates = BotStates.ADMIN;
                break;
            default:
                botStates = userDao.getCurrentBotStatesForUserByTgId(userId);
                break;
        }
        userDao.setCurrentBotStatesForUserWithTgId(userId, botStates);
        return botStates;
    }
}
