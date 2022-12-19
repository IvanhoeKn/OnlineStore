package proj3ct.onlinestore.dao;

import proj3ct.onlinestore.model.OrderStatus;

public class OrderStatusDao extends DAO<OrderStatus> {
    public OrderStatusDao() {
        super();
        setModelClass(OrderStatus.class);
    }
}
