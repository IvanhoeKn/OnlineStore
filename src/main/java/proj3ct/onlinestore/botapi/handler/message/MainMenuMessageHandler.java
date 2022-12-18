package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.keyboard.ReplyKeyboardMarkupBuilder;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

@Component
public class MainMenuMessageHandler implements MessageHandler {
    private final ReplyMessageService replyMessageService;
    private LocaleMessageService localeMessageService;

    public MainMenuMessageHandler(ReplyMessageService replyMessageService,
                              LocaleMessageService messageService) {
        this.replyMessageService = replyMessageService;
        this.localeMessageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        return getMainMenu(chatId);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_MAIN_MENU;
    }

    private SendMessage getMainMenu(Long chatId) {
        return ReplyKeyboardMarkupBuilder.create(chatId)
                .setText("Добро пожаловать!"
                        + "\n\nЧтобы воспользовться моим функционалом, нажмите нужную кнопку на появившейся клаиватуре.")
                .row()
                .button("Профиль")
                .button("Корзина")
                .endRow()
                .build();
    }
}
