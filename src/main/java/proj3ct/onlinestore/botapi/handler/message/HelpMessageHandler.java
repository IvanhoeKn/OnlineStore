package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class HelpMessageHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    public HelpMessageHandler(ReplyMessageService replyMessageService,
                              LocaleMessageService messageService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        return replyMessageService.getTextMessage(chatId, localeMessageService.getMessage("reply.help"));
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.HELP;
    }
}
