package proj3ct.onlinestore.botapi.handler.keyboard;

import org.springframework.stereotype.Service;
import proj3ct.onlinestore.model.Discount;
import proj3ct.onlinestore.model.Orders;
import proj3ct.onlinestore.model.Product;

@Service
public class InlineKeyboardService {
    public InlineKeyboardMarkupBuilder getOrderInlineKeyboard(Long chatId, Orders order, Product product, Discount discount, Integer amount) {
        Double totalPrice = (1 - (double) discount.getDiscount() / 100) * product.getPrice() * amount;
        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkupBuilder.create(chatId).setText("<b>Описание</b>"
                        + "\n\nНаименование: " + product.getName()
                        + "\nКоличество доступного товара: " + product.getAmount()
                        + "\nЦена: " + product.getPrice())
                .row()
                .button("+", order.getIdOrder() + "_INC")
                .button(amount.toString(), "amount")
                .button("-", order.getIdOrder() + "_DEC")
                .endRow()
                .row()
                .button("Цена с учетом скидки: " + totalPrice, "totalPrice")
                .endRow()
                .row()
                .button("Купить", order.getIdOrder() + "_CONFIRM")
                .button("Отменить", order.getIdOrder() + "_CANCEL")
                .endRow();
        return inlineKeyboardMarkupBuilder;
    }

    public InlineKeyboardMarkupBuilder getProfile(Long chatId) {
        return InlineKeyboardMarkupBuilder.create(chatId)
                .row()
                .button("Сменить имя", "CHANGE_NAME")
                .button("Сменить фамилию", "CHANGE_SURNAME")
                .endRow()
                .row()
                .button("Сменить номер телефона", "CHANGE_PHONE")
                .endRow()
                .row()
                .button("Удалить профиль", "DELETE_PROFILE")
                .endRow();
    }

    public InlineKeyboardMarkupBuilder deleteProfile(Long chatId) {
        return InlineKeyboardMarkupBuilder.create(chatId)
                .row()
                .button("Удалить", "CONFIRM_DELETE")
                .button("Отмена", "CANCEL_DELETE")
                .endRow();
    }

    public InlineKeyboardMarkupBuilder selectAdminRole(Long chatId) {
        return InlineKeyboardMarkupBuilder.create(chatId)
                .row()
                .button("Менеджер заказов", "ORDER_MANAGER")
                .button("Менеджер товаров", "PRODUCT_MANAGER")
                .endRow();
    }
}
