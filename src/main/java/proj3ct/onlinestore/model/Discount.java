package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "discount", schema = "online_store_repo", catalog = "online_store")
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_discount", nullable = false)
    private Integer idDiscount;
    @Basic
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Basic
    @Column(name = "discount", nullable = false)
    private Integer discount;
    @Basic
    @Column(name = "sum_start_discount", nullable = false)
    private Integer sumStartDiscount;
    @OneToMany(mappedBy = "discountByIdDiscount")
    private Collection<Users> usersByIdDiscount;

    public Integer getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(Integer idDiscount) {
        this.idDiscount = idDiscount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getSumStartDiscount() {
        return sumStartDiscount;
    }

    public void setSumStartDiscount(Integer sumStartDiscount) {
        this.sumStartDiscount = sumStartDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount1 = (Discount) o;
        return Objects.equals(idDiscount, discount1.idDiscount) && Objects.equals(status, discount1.status) && Objects.equals(discount, discount1.discount) && Objects.equals(sumStartDiscount, discount1.sumStartDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDiscount, status, discount, sumStartDiscount);
    }

    public Collection<Users> getUsersByIdDiscount() {
        return usersByIdDiscount;
    }

    public void setUsersByIdDiscount(Collection<Users> usersByIdDiscount) {
        this.usersByIdDiscount = usersByIdDiscount;
    }
}
