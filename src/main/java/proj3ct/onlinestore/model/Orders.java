package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(OrdersPK.class)
@Table(name = "orders", schema = "online_store_repo", catalog = "online_store")
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false, insertable = false, updatable = false)
    private Integer idUser;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_product", nullable = false, insertable = false, updatable = false)
    private Integer idProduct;
    @Basic
    @Column(name = "status", nullable = true, insertable = false, updatable = false)
    private Integer status;
    @Basic
    @Column(name = "amount", nullable = false)
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private Users usersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id_product", nullable = false)
    private Product productByIdProduct;
    @ManyToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private OrderStatus orderStatusByStatus;

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        Orders orders = (Orders) o;
        return Objects.equals(idOrder, orders.idOrder) && Objects.equals(idUser, orders.idUser) && Objects.equals(idProduct, orders.idProduct) && Objects.equals(status, orders.status) && Objects.equals(amount, orders.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, idUser, idProduct, status, amount);
    }

    public Users getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(Users usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    public Product getProductByIdProduct() {
        return productByIdProduct;
    }

    public void setProductByIdProduct(Product productByIdProduct) {
        this.productByIdProduct = productByIdProduct;
    }

    public OrderStatus getOrderStatusByStatus() {
        return orderStatusByStatus;
    }

    public void setOrderStatusByStatus(OrderStatus orderStatusByStatus) {
        this.orderStatusByStatus = orderStatusByStatus;
    }
}
