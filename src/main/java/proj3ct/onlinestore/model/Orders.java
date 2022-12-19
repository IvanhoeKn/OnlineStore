package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(OrdersPK.class)
@Table(name = "orders", schema = "online_store_repo", catalog = "online_store")
public class Orders {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "online_store_repo.hibernate_sequence")
    @Id
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;
    @Id
    @Column(name = "id_order_user", nullable = false)
    private Integer idOrderUser;
    @Id
    @Column(name = "id_order_product", nullable = false)
    private Integer idOrderProduct;
    @Basic
    @Column(name = "id_order_status", nullable = false, insertable = false, updatable = false)
    private Integer idOrderStatus;
    @Basic
    @Column(name = "order_amount", nullable = false)
    private Integer orderAmount;
    @ManyToOne
    @JoinColumn(name = "id_order_user", referencedColumnName = "id_user", nullable = false)
    private Users usersByIdOrderUser;
    @ManyToOne
    @JoinColumn(name = "id_order_product", referencedColumnName = "id_product", nullable = false)
    private Product productByIdOrderProduct;
    @ManyToOne
    @JoinColumn(name = "id_order_status", referencedColumnName = "id", nullable = false)
    private OrderStatus orderStatusByIdOrderStatus;

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdOrderUser() {
        return idOrderUser;
    }

    public void setIdOrderUser(Integer idOrderUser) {
        this.idOrderUser = idOrderUser;
    }

    public Integer getIdOrderProduct() {
        return idOrderProduct;
    }

    public void setIdOrderProduct(Integer idOrderProduct) {
        this.idOrderProduct = idOrderProduct;
    }

    public Integer getIdOrderStatus() {
        return idOrderStatus;
    }

    public void setIdOrderStatus(Integer idOrderStatus) {
        this.idOrderStatus = idOrderStatus;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(idOrder, orders.idOrder) && Objects.equals(idOrderUser, orders.idOrderUser) && Objects.equals(idOrderProduct, orders.idOrderProduct) && Objects.equals(idOrderStatus, orders.idOrderStatus) && Objects.equals(orderAmount, orders.orderAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, idOrderUser, idOrderProduct, idOrderStatus, orderAmount);
    }

    public Users getUsersByIdOrderUser() {
        return usersByIdOrderUser;
    }

    public void setUsersByIdOrderUser(Users usersByIdOrderUser) {
        this.usersByIdOrderUser = usersByIdOrderUser;
    }

    public Product getProductByIdOrderProduct() {
        return productByIdOrderProduct;
    }

    public void setProductByIdOrderProduct(Product productByIdOrderProduct) {
        this.productByIdOrderProduct = productByIdOrderProduct;
    }

    public OrderStatus getOrderStatusByIdOrderStatus() {
        return orderStatusByIdOrderStatus;
    }

    public void setOrderStatusByIdOrderStatus(OrderStatus orderStatusByIdOrderStatus) {
        this.orderStatusByIdOrderStatus = orderStatusByIdOrderStatus;
    }
}
