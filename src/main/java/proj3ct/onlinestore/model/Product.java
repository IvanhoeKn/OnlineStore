package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "online_store_repo", catalog = "online_store")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_product", nullable = false)
    private Integer idProduct;
    @Basic
    @Column(name = "id_category", nullable = false, insertable = false, updatable = false)
    private Integer idCategory;
    @Basic
    @Column(name = "price", nullable = false)
    private Integer price;
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    private String name;
    @Basic
    @Column(name = "amount", nullable = false)
    private Integer amount;
    @OneToMany(mappedBy = "productByIdOrderProduct")
    private Collection<Orders> ordersByIdProduct;
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_categories", nullable = false)
    private ProductCategories productCategoriesByIdCategory;

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idProduct, product.idProduct) && Objects.equals(idCategory, product.idCategory) && Objects.equals(price, product.price) && Objects.equals(name, product.name) && Objects.equals(amount, product.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, idCategory, price, name, amount);
    }

    public Collection<Orders> getOrdersByIdProduct() {
        return ordersByIdProduct;
    }

    public void setOrdersByIdProduct(Collection<Orders> ordersByIdProduct) {
        this.ordersByIdProduct = ordersByIdProduct;
    }

    public ProductCategories getProductCategoriesByIdCategory() {
        return productCategoriesByIdCategory;
    }

    public void setProductCategoriesByIdCategory(ProductCategories productCategoriesByIdCategory) {
        this.productCategoriesByIdCategory = productCategoriesByIdCategory;
    }
}
