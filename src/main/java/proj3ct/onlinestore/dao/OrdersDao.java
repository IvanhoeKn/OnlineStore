package proj3ct.onlinestore.dao;

import proj3ct.onlinestore.model.Orders;

public class OrdersDao extends DAO<Orders> {
    public OrdersDao() {
        super();
        setModelClass(Orders.class);
    }

    /*public Orders isertOrders(Long userId, Integer productId) {
        Query query = openCurrentSessionWithTransaction()
                .createNativeQuery("INSERT INTO online_categories.orders(id_user, id_prduct, status, amount) VALUES(:userid, :productId, 1, 1)")
                .setParameter("userId", userId)
                .setParameter("productId", productId);
        query.executeUpdate();
        List<Product> list = query.list();
        closeCurrentSession();
        return list;
    }*/
}
