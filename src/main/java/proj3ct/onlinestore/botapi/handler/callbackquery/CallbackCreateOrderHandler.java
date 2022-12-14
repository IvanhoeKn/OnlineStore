package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardMarkupBuilder;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.dao.OrdersDao;
import proj3ct.onlinestore.dao.ProductDao;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Discount;
import proj3ct.onlinestore.model.Orders;
import proj3ct.onlinestore.model.Product;
import proj3ct.onlinestore.model.Users;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Component
public class CallbackCreateOrderHandler implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.CREATE_ORDER;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public CallbackCreateOrderHandler(ReplyMessageService messageService,
                                      LocaleMessageService localeMessageService,
                                      InlineKeyboardService keyboardService) {
        this.messageService = messageService;
        this.localeMessageService = localeMessageService;
        this.keyboardService = keyboardService;
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return HANDLER_QUERY_TYPE;
    }

    @Override
    public PartialBotApiMethod<? extends Serializable> handleCallbackQuery(CallbackQuery callbackQuery) {
        final Long chatId = callbackQuery.getMessage().getChatId();
        UserDao userDao = new UserDao();
        ProductDao productDao = new ProductDao();
        OrdersDao ordersDao = new OrdersDao();
        Users user = userDao.getUserByTgId(callbackQuery.getFrom().getId());
        Integer prefixId = Integer.parseInt(callbackQuery.getData().split("_")[0]);
        Product product = productDao.findById(prefixId);
        Discount discount = user.getDiscountByIdDiscount();
        Orders order = ordersDao.isertOrders(user.getIdUser(), product.getIdProduct());
        product.setAmount(product.getAmount() - 1);
        productDao.update(product);
        InlineKeyboardMarkupBuilder keyboardMarkupBuilder = keyboardService.getOrderInlineKeyboard(chatId, order, product, discount, 1);
        return messageService.getEditedMarkup(chatId,
                callbackQuery.getMessage().getMessageId(),
                keyboardMarkupBuilder.getKeyboard(),
                keyboardMarkupBuilder.getText());
    }
}
