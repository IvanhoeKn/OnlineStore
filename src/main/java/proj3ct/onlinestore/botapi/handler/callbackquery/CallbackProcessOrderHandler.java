package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.dao.ProductDao;
import proj3ct.onlinestore.model.Product;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Component
public class CallbackProcessOrderHandler implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.PROCESS_ORDER;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    public CallbackProcessOrderHandler(ReplyMessageService messageService,
                                      LocaleMessageService localeMessageService) {
        this.messageService = messageService;
        this.localeMessageService = localeMessageService;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return HANDLER_QUERY_TYPE;
    }

    @Override
    public PartialBotApiMethod<? extends Serializable> handleCallbackQuery(CallbackQuery callbackQuery) {
        final Long chatId = callbackQuery.getMessage().getChatId();
        CallbackQueryType queryType = CallbackQueryType.valueOf(callbackQuery.getData().replaceFirst("^[0-9]+_", ""));
        Integer prefixId = Integer.parseInt(callbackQuery.getData().split("_")[0]);
        ProductDao productDao = new ProductDao();
        Product product = productDao.findById(prefixId);
        switch (queryType) {
            case INC:
                //Integer amount = callbackQuery.getData();
                //System.out.println(callbackQuery.getData());
                break;
            case DEC:
                break;
            case CONFIRM:
                break;
            case CANCEL:
                break;
        }
        return new SendMessage();
    }
}
