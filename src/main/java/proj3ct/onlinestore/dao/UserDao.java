package proj3ct.onlinestore.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.model.Users;

import java.math.BigInteger;

@Repository
public class UserDao extends DAO<Users> {
    public UserDao() {
        super();
        setModelClass(Users.class);
    }

    public Users getUserByTgId(Long userId) {
        Query query = openCurrentSession()
                .createNativeQuery("SELECT * FROM online_store_repo.get_user_by_tg_id(:paramId)", Users.class)
                .setParameter("paramId", userId);
        Users user = (Users) query.getSingleResult();
        closeCurrentSession();
        return user;
    }
    public BotStates getCurrentBotStatesForUserByTgId(Long userId) {
        Users user = this.getUserByTgId(userId);
        if (user == null) {
            return BotStates.ERROR;
        }
        return BotStates.valueOf(user.getBotState());
    }

    public void setCurrentBotStatesForUserWithTgId(Long userId, BotStates botStates) {
        Users user = this.getUserByTgId(userId);
        if (user != null) {
            user.setBotState(botStates.toString());
            update(user);
        }
    }

    public String getInfoProfile(Long userTgId) {
        UserDao userDao = new UserDao();
        Users user = userDao.getUserByTgId(userTgId);
        openCurrentSession();
        StringBuilder formReply = new StringBuilder("<b>Профиль</b>\n");
        formReply.append("\n<b>Имя:</b> " + user.getName());
        formReply.append("\n<b>Фамилия:</b> " + user.getSurname());
        formReply.append("\n<b>Номер телефона:</b> " + user.getPhoneNumber());
        formReply.append("\n<b>Статус вашей скидки:</b> " + user.getDiscountByIdDiscount().getStatus());
        formReply.append("\n<b>Ваша скидка:</b> " + user.getDiscountByIdDiscount().getDiscount() + "%\n");
        formReply.append("\n<b>Статистика аккаунта</b>\n");
        BigInteger totalMoney = getTotalMoneySpent(userTgId);
        formReply.append("\nЗа все время потрачено: " + (totalMoney == null ? 0 : totalMoney));
        BigInteger totalAmount = getTotalAmountProduct(userTgId);
        formReply.append("\nВсего куплено товаров: " + (totalAmount == null ? 0 : totalAmount));
        closeCurrentSession();
        return formReply.toString();
    }

    public BigInteger getTotalMoneySpent(Long userIdTg) {
        BigInteger sum = (BigInteger) openCurrentSession()
                .createNativeQuery("SELECT * FROM online_store_repo.get_total_money_spent(:userIdTg)")
                .setParameter("userIdTg", userIdTg)
                .getSingleResult();
        closeCurrentSession();
        return sum;
    }

    public BigInteger getTotalAmountProduct(Long userIdTg) {
        BigInteger sum = (BigInteger) openCurrentSession()
                .createNativeQuery("SELECT * FROM online_store_repo.get_total_amount_product(:userIdTg)")
                .setParameter("userIdTg", userIdTg)
                .getSingleResult();
        closeCurrentSession();
        return sum;
    }
}
