package proj3ct.onlinestore.dao;

import proj3ct.onlinestore.model.ProductCategories;

public class ProductCategoriesDao extends DAO<ProductCategories> {
    public ProductCategoriesDao() {
        super();
        setModelClass(ProductCategories.class);
    }
}
