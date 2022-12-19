package proj3ct.onlinestore.dao;

import proj3ct.onlinestore.model.Discount;

public class DiscountDao extends DAO<Discount> {
    public DiscountDao() {
        super();
        setModelClass(Discount.class);
    }
}