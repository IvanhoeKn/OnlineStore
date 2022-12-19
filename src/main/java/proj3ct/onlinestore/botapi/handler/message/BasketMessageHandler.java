package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.dao.OrdersDao;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class BasketMessageHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    public BasketMessageHandler(ReplyMessageService replyMessageService,
                                LocaleMessageService messageService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        OrdersDao ordersDao = new OrdersDao();
        String replyText = ordersDao.getBasket(message.getFrom().getId());
        return replyMessageService.getTextMessage(chatId, replyText);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_BASKET;
    }
}
