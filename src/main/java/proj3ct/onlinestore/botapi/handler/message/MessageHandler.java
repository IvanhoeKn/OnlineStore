package proj3ct.onlinestore.botapi.handler.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;

public interface MessageHandler {

    BotStates getHandlerName();

    BotApiMethod<Message> handle(Message message);
}
