package proj3ct.onlinestore.dao;

import proj3ct.onlinestore.model.Discount;
import proj3ct.onlinestore.model.Orders;

import java.util.List;

public class OrdersDao extends DAO<Orders> {
    public OrdersDao() {
        super();
        setModelClass(Orders.class);
    }

    public Orders isertOrders(Integer userId, Integer productId) {
        Orders order = (Orders) openCurrentSessionWithTransaction()
                .createNativeQuery("INSERT INTO online_store_repo.orders(id_order_user, id_order_product, id_order_status, order_amount) VALUES(:userId, :productId, 1, 1) RETURNING online_store_repo.orders.*", Orders.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getSingleResult();
        closeCurrentSessionWithTransaction();
        return order;
    }

    public Orders find(Integer orderId) {
        Orders order = (Orders) openCurrentSession().createNativeQuery("SELECT * FROM online_store_repo.orders o WHERE o.id_order = :orderId", Orders.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
        closeCurrentSession();
        return order;
    }

    public String getBasket(Long telegramUserId) {
        List<Orders> orders = openCurrentSession()
                .createNativeQuery("SELECT * FROM online_store_repo.get_product_basket(:telegramUserId)", Orders.class)
                .setParameter("telegramUserId", telegramUserId)
                .list();
        StringBuilder formReply = new StringBuilder("Список ваших заказов:\n");
        if (orders.isEmpty()) {
            formReply.append("\nВы еще ничего не заказали."
                    + "\nДля совершения покупок перейдите в раздел /category");
        }
        else {
            Discount discount = orders.get(0).getUsersByIdOrderUser().getDiscountByIdDiscount();
            orders.forEach(order -> {
                formReply.append("\n<b>Наименование товара:</b> " + order.getProductByIdOrderProduct().getName());
                formReply.append("\n<b>Количество:</b> " + order.getOrderAmount());
                Double totalPriceOrder = (1.0 - (double) discount.getDiscount() / 100) * order.getOrderAmount() * order.getProductByIdOrderProduct().getPrice();
                formReply.append("\n<b>Итоговая стоимость с учетом скидок:</b> " + totalPriceOrder);
                formReply.append("\n<b>Статус заказа:</b> " + order.getOrderStatusByIdOrderStatus().getStatus() + "\n");
            });
        }
        return formReply.toString();
    }
}
