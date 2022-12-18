package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardMarkupBuilder;
import proj3ct.onlinestore.dao.ProductDao;
import proj3ct.onlinestore.model.Product;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;
import java.util.List;

@Component
public class CallbackProductHandler implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.SHOW_PRODUCTS;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    public CallbackProductHandler(ReplyMessageService messageService,
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
        final long chatId = callbackQuery.getMessage().getChatId();
        Integer prefixId = Integer.parseInt(callbackQuery.getData().split("_")[0]);
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = InlineKeyboardMarkupBuilder.create(chatId)
                .setText(localeMessageService.getMessage("reply.product"));
        ProductDao productDao = new ProductDao();
        List<Product> list = productDao.getProductsByCategoryId(prefixId);
        if (!list.isEmpty()) {
            list.forEach(product -> {
                keyboardMarkupBuilder.row()
                        .button(product.getName(), product.getIdProduct().toString() + "_CREATE_ORDER")
                        .endRow();
            });
            return keyboardMarkupBuilder.build();
        }
        else {
            return messageService.sendAnswerCallbackQuery(localeMessageService.getMessage("reply.product.empty"), true, callbackQuery);
        }
    }
}
