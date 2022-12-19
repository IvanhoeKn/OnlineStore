package proj3ct.onlinestore.botapi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.message.MessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BotStatesHandler {
    private Map<BotStates, MessageHandler> messageHandlers;

    public BotStatesHandler(List<MessageHandler> messageHandlers) {
        this.messageHandlers = new HashMap<>();
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public BotApiMethod<Message> processInputMessage(BotStates botState, Message message) {
        MessageHandler currentMessageHandler = findMessageHandler(botState);
        return currentMessageHandler.handle(message);
    }

    private MessageHandler findMessageHandler(BotStates botStates) {
        if (isHelp(botStates)) {
            return messageHandlers.get(BotStates.HELP);
        }
        if (isRegistration(botStates)) {
            return messageHandlers.get(BotStates.REGISTRATION);
        }
        if (isChangeProfile(botStates)) {
            return messageHandlers.get(BotStates.CHANGE);
        }
        if (botStates.equals(BotStates.SHOW_CATALOG)) {
            return messageHandlers.get(BotStates.SHOW_CATALOG);
        }
        if (botStates.equals(BotStates.SHOW_BASKET)) {
            return messageHandlers.get(BotStates.SHOW_BASKET);
        }
        if (botStates.equals(BotStates.SHOW_MAIN_MENU)) {
            return messageHandlers.get(BotStates.SHOW_MAIN_MENU);
        }
        if (botStates.equals(BotStates.SHOW_PROFILE)) {
            return messageHandlers.get(BotStates.SHOW_PROFILE);
        }
        return messageHandlers.get(BotStates.ERROR);
    }

    private boolean isHelp(BotStates botStates) {
        switch (botStates) {
            case HELP:
                return true;
            default:
                return false;
        }
    }

    private boolean isRegistration(BotStates botStates) {
        switch (botStates) {
            case REGISTRATION:
            case ASC_NAME:
            case ASC_SURNAME:
            case ASC_PHONE:
            case PROFILE_REGISTERED:
                return true;
            default:
                return false;
        }
    }

    private boolean isChangeProfile(BotStates botStates) {
        switch (botStates) {
            case CHANGE:
            case CHANGE_NAME:
            case CHANGE_SURNAME:
            case CHANGE_PHONE:
                return true;
            default:
                return false;
        }
    }
}
