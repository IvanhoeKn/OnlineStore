package proj3ct.onlinestore.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrdersPK implements Serializable {
    @Column(name = "id_order", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "online_store_repo.hibernate_sequence")
    private Integer idOrder;
    @Column(name = "id_order_user", nullable = false, insertable = false, updatable = false)
    @Id
    private Integer idOrderUser;
    @Column(name = "id_order_product", nullable = false, insertable = false, updatable = false)
    @Id
    private Integer idOrderProduct;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersPK ordersPK = (OrdersPK) o;
        return Objects.equals(idOrder, ordersPK.idOrder) && Objects.equals(idOrderUser, ordersPK.idOrderUser) && Objects.equals(idOrderProduct, ordersPK.idOrderProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOrder, idOrderUser, idOrderProduct);
    }
}
