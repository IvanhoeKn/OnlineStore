package proj3ct.onlinestore.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import proj3ct.onlinestore.botapi.BotStates;
import proj3ct.onlinestore.model.Users;

@Repository
public class UserDao extends DAO<Users> {
    public UserDao() {
        super();
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
}
