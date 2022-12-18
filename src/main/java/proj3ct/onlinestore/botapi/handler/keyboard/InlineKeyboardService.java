package proj3ct.onlinestore.botapi.handler.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import proj3ct.onlinestore.model.Product;

@Service
public class InlineKeyboardService {
    public SendMessage getOrderInlineKeyboard(Long chatId, Product product, Integer discount, Integer amount) {
        Double totalPrice = (1 - (double) discount / 100) * product.getPrice() * amount;
        InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkupBuilder.create(chatId).setText("<b>Описание</b>"
                        + "\n\nНаименование: " + product.getName()
                        + "\nКоличество на складе: " + product.getAmount()
                        + "\nЦена: " + product.getPrice())
                .row()
                .button("+", product.getIdProduct() + "_INC")
                .button(amount.toString(), "amount")
                .button("-", product.getIdProduct() + "_DEC")
                .endRow()
                .row()
                .button("Цена с учетом скидки: " + totalPrice, "totalPrice")
                .endRow()
                .row()
                .button("Купить", product.getIdProduct() + "_CONFIRM")
                .button("Отменить", product.getIdProduct() + "_CANCEL")
                .endRow();
        return inlineKeyboardMarkupBuilder.build();
    }
}
