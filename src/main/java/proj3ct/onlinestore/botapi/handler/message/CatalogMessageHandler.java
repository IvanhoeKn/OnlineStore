package proj3ct.onlinestore.botapi.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardMarkupBuilder;
import proj3ct.onlinestore.dao.ProductCategoriesDao;
import proj3ct.onlinestore.model.ProductCategories;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.util.List;

@Component
public class CatalogMessageHandler implements MessageHandler {
    private final ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    public CatalogMessageHandler(ReplyMessageService replyMessageService,
                              LocaleMessageService messageService) {
        this.messageService = replyMessageService;
        this.localeMessageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotStates getHandlerName() {
        return BotStates.SHOW_CATALOG;
    }

    private SendMessage processUsersInput(Message message) {
        Long chatId = message.getChatId();
        return getInlineMessageButtons(chatId);
    }

    private SendMessage getInlineMessageButtons(Long chatId) {
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = InlineKeyboardMarkupBuilder.create(chatId)
                .setText(localeMessageService.getMessage("reply.catalog"));
        ProductCategoriesDao productCategoriesDao = new ProductCategoriesDao();
        List<ProductCategories> list = productCategoriesDao.findAll();
        if (!list.isEmpty()) {
            list.forEach(productCategories -> {
                keyboardMarkupBuilder.row()
                        .button(productCategories.getCategoryName(), productCategories.getIdCategories().toString() + "_SHOW_PRODUCTS")
                        .endRow();
            });
        }
        else {
            keyboardMarkupBuilder.setText(localeMessageService.getMessage("reply.catalog.empty"));
        }
        return keyboardMarkupBuilder.build();
    }
}
