package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;

public interface CallbackQueryHandler {
    PartialBotApiMethod<? extends Serializable> handleCallbackQuery(CallbackQuery callbackQuery);

    CallbackQueryType getHandlerQueryType();
}
