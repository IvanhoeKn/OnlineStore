package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.dao.OrderStatusDao;
import proj3ct.onlinestore.dao.OrdersDao;
import proj3ct.onlinestore.dao.ProductDao;
import proj3ct.onlinestore.dao.UserDao;
import proj3ct.onlinestore.model.Discount;
import proj3ct.onlinestore.model.Orders;
import proj3ct.onlinestore.model.Product;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Component
public class CallbackProcessOrderHandler implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.PROCESS_ORDER;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public CallbackProcessOrderHandler(ReplyMessageService messageService,
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
        CallbackQueryType queryType = CallbackQueryType.valueOf(callbackQuery.getData().replaceFirst("^[0-9]+_", ""));
        Integer prefixId = Integer.parseInt(callbackQuery.getData().split("_")[0]);
        OrdersDao ordersDao = new OrdersDao();
        UserDao userDao = new UserDao();
        ProductDao productDao = new ProductDao();
        Orders order = ordersDao.find(prefixId);
        Product product = order.getProductByIdOrderProduct();
        Discount discount = userDao.getUserByTgId(callbackQuery.getFrom().getId()).getDiscountByIdDiscount();
        switch (queryType) {
            case INC:
                if (product.getAmount() > 0) {
                    order.setOrderAmount(order.getOrderAmount() + 1);
                    product.setAmount(product.getAmount() - 1);
                    ordersDao.update(order);
                    productDao.update(product);
                    callbackQuery.getMessage().getReplyMarkup().getKeyboard().get(0).get(1).setText(order.getOrderAmount().toString());
                    double totalPrice = (1.0 - (double) discount.getDiscount() / 100) * order.getOrderAmount() * product.getPrice();
                    callbackQuery.getMessage()
                            .getReplyMarkup()
                            .getKeyboard()
                            .get(1)
                            .get(0)
                            .setText("???????? ?? ???????????? ????????????: " + totalPrice);
                }
                else {
                    return messageService.sendAnswerCallbackQuery(localeMessageService.getMessage("reply.order.limit.max"), true, callbackQuery);
                }
                break;
            case DEC:
                if (order.getOrderAmount() > 1) {
                    order.setOrderAmount(order.getOrderAmount() - 1);
                    product.setAmount(product.getAmount() + 1);
                    ordersDao.update(order);
                    productDao.update(product);
                    callbackQuery.getMessage().getReplyMarkup().getKeyboard().get(0).get(1).setText(order.getOrderAmount().toString());
                    double totalPrice = (1.0 - (double) discount.getDiscount() / 100) * order.getOrderAmount() * product.getPrice();
                    callbackQuery.getMessage()
                            .getReplyMarkup()
                            .getKeyboard()
                            .get(1)
                            .get(0)
                            .setText("???????? ?? ???????????? ????????????: " + totalPrice);
                }
                else {
                    return messageService.sendAnswerCallbackQuery(localeMessageService.getMessage("reply.order.limit.min"), true, callbackQuery);
                }
                break;
            case CONFIRM:
                OrderStatusDao orderStatusDao = new OrderStatusDao();
                order.setOrderStatusByIdOrderStatus(orderStatusDao.findById(2));
                ordersDao.update(order);
                String text = "<b>?????? ?????????? ????????????????!</b>"
                        + "\n????????????????????????: " + product.getName()
                        + "\n????????????????????: " + order.getOrderAmount()
                        + "\n???? ???????????????? ???????????? ???????????? ???? ???????????? ?????????????? ?? ?????????????? \"??????????????\"";
                return messageService.getEditedTextMessage(chatId, callbackQuery.getMessage().getMessageId(), text);
            case CANCEL:
                product.setAmount(product.getAmount() + order.getOrderAmount());
                productDao.update(product);
                ordersDao.delete(order);
                String canselText = "?????? ?????????? ??????????????!";
                return messageService.getEditedTextMessage(chatId, callbackQuery.getMessage().getMessageId(), canselText);
        }
        String textMessage = "<b>????????????????</b>"
                + "\n\n????????????????????????: " + product.getName()
                + "\n???????????????????? ???? ????????????: " + product.getAmount()
                + "\n????????: " + product.getPrice();
        return messageService.getEditedMarkup(chatId, callbackQuery.getMessage().getMessageId(), callbackQuery.getMessage().getReplyMarkup(), textMessage);
    }
}
