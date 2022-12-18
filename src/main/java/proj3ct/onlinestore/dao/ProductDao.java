package proj3ct.onlinestore.dao;

import org.hibernate.query.Query;
import proj3ct.onlinestore.model.Product;

import java.util.List;

public class ProductDao extends DAO<Product> {
    public ProductDao() {
        super();
        setModelClass(Product.class);
    }

    public List<Product> getProductsByCategoryId(Integer categoryId) {
        Query query = openCurrentSession()
                .createNativeQuery("SELECT * FROM online_store_repo.product p WHERE p.id_category = :paramId and p.amount > 0", Product.class)
                .setParameter("paramId", categoryId);
        List<Product> list = query.list();
        closeCurrentSession();
        return list;
    }
}
