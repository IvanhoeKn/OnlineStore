package proj3ct.onlinestore.botapi.handler.callbackquery;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import proj3ct.onlinestore.botapi.handler.keyboard.InlineKeyboardService;
import proj3ct.onlinestore.service.LocaleMessageService;
import proj3ct.onlinestore.service.ReplyMessageService;

import java.io.Serializable;

@Component
public class CallbackAdminHandler implements CallbackQueryHandler {
    private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.ADMIN;
    private ReplyMessageService messageService;
    private LocaleMessageService localeMessageService;

    private InlineKeyboardService keyboardService;

    public CallbackAdminHandler(ReplyMessageService messageService,
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
        CallbackQueryType queryType = CallbackQueryType.valueOf(callbackQuery.getData());
        switch (queryType) {
            case ORDER_MANAGER:
                break;
            case PRODUCT_MANAGER:
                break;
        }
        return new SendMessage();
        /*switch (queryType) {
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
                            .setText("Цена с учетом скидки: " + totalPrice);
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
                            .setText("Цена с учетом скидки: " + totalPrice);
                }
                else {
                    return messageService.sendAnswerCallbackQuery(localeMessageService.getMessage("reply.order.limit.min"), true, callbackQuery);
                }
                break;
            case CONFIRM:
                OrderStatusDao orderStatusDao = new OrderStatusDao();
                order.setOrderStatusByIdOrderStatus(orderStatusDao.findById(2));
                ordersDao.update(order);
                String text = "<b>Ваш заказ оформлен!</b>"
                        + "\nНаименование: " + product.getName()
                        + "\nКоличество: " + order.getOrderAmount()
                        + "\nЗа статусом вашего заказа вы можете следить в разделе \"Корзина\"";
                return messageService.getEditedTextMessage(chatId, callbackQuery.getMessage().getMessageId(), text);
            case CANCEL:
                product.setAmount(product.getAmount() + order.getOrderAmount());
                productDao.update(product);
                ordersDao.delete(order);
                String canselText = "Ваш заказ отменен!";
                return messageService.getEditedTextMessage(chatId, callbackQuery.getMessage().getMessageId(), canselText);
        }
        String textMessage = "<b>Описание</b>"
                + "\n\nНаименование: " + product.getName()
                + "\nКоличество на складе: " + product.getAmount()
                + "\nЦена: " + product.getPrice();
        return messageService.getEditedMarkup(chatId, callbackQuery.getMessage().getMessageId(), callbackQuery.getMessage().getReplyMarkup(), textMessage);
    */}
}