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
public class AdminMessageHandler implements MessageHandler {
    private final ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public AdminMessageHandler(ReplyMessageService replyMessageService,
                               LocaleMessageService messageService,
                               InlineKeyboardService keyboardService) {
        this.messageService = replyMessageService;
        this.localeMessageService = messageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public SendMessage handle(Message message) {
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = keyboardService.getProfile(message.getChatId());
        UserDao userDao = new UserDao();
        keyboardMarkupBuilder.setText(localeMessageService.getMessage("reply.admin.select"));
        return keyboardMarkupBuilder.build();
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.ADMIN;
    }
}