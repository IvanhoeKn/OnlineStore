package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardMarkupBuilder;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class ProfileMessageHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public ProfileMessageHandler(ReplyMessageService replyMessageService,
                                 LocaleMessageService messageService,
                                 InlineKeyboardService keyboardService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage handle(Message message) {
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = keyboardService.getProfile(message.getChatId());
        UserDao userDao = new UserDao();
        keyboardMarkupBuilder.setText(userDao.getInfoProfile(message.getFrom().getId()));
        SendMessage sendMessage =  keyboardMarkupBuilder.build();
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_PROFILE;
    }
}

