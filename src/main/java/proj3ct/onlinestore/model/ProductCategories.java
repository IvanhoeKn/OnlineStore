package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product_categories", schema = "online_store_repo", catalog = "online_store")
public class ProductCategories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_categories", nullable = false)
    private Integer idCategories;
    @Basic
    @Column(name = "category_name", nullable = false, length = 30)
    private String categoryName;
    @OneToMany(mappedBy = "productCategoriesByIdCategory")
    private Collection<Product> productsByIdCategories;

    public Integer getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(Integer idCategories) {
        this.idCategories = idCategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategories that = (ProductCategories) o;
        return Objects.equals(idCategories, that.idCategories) && Objects.equals(categoryName, that.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategories, categoryName);
    }

    public Collection<Product> getProductsByIdCategories() {
        return productsByIdCategories;
    }

    public void setProductsByIdCategories(Collection<Product> productsByIdCategories) {
        this.productsByIdCategories = productsByIdCategories;
    }
}
