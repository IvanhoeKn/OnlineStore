package proj3ct.onlinestore.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "online_store_repo", catalog = "online_store")
public class Users {
    @Basic
    @Column(name = "surname", nullable = false, length = 30)
    private String surname;
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic
    @Column(name = "id_discount", nullable = true, insertable = false, updatable = false)
    private Integer idDiscount;
    @Basic
    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;
    @Basic
    @Column(name = "bot_state", nullable = true, length = 20)
    private String botState;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private Integer idUser;
    @Basic
    @Column(name = "id_telegram", nullable = false)
    private Long idTelegram;
    @OneToMany(mappedBy = "usersByIdUser")
    private Collection<Orders> ordersByIdUser;
    @ManyToOne
    @JoinColumn(name = "id_discount", referencedColumnName = "id_discount")
    private Discount discountByIdDiscount;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(Integer idDiscount) {
        this.idDiscount = idDiscount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBotState() {
        return botState;
    }

    public void setBotState(String botState) {
        this.botState = botState;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Long getIdTelegram() {
        return idTelegram;
    }

    public void setIdTelegram(Long idTelegram) {
        this.idTelegram = idTelegram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(surname, users.surname) && Objects.equals(name, users.name) && Objects.equals(idDiscount, users.idDiscount) && Objects.equals(phoneNumber, users.phoneNumber) && Objects.equals(botState, users.botState) && Objects.equals(idUser, users.idUser) && Objects.equals(idTelegram, users.idTelegram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, idDiscount, phoneNumber, botState, idUser, idTelegram);
    }

    public Collection<Orders> getOrdersByIdUser() {
        return ordersByIdUser;
    }

    public void setOrdersByIdUser(Collection<Orders> ordersByIdUser) {
        this.ordersByIdUser = ordersByIdUser;
    }

    public Discount getDiscountByIdDiscount() {
        return discountByIdDiscount;
    }

    public void setDiscountByIdDiscount(Discount discountByIdDiscount) {
        this.discountByIdDiscount = discountByIdDiscount;
    }
}
