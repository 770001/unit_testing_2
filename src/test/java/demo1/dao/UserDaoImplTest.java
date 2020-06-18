package demo1.dao;

import demo1.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Демонстрация как тестить код с помощью моков.
 * Мокаем слой дао чтобы не ходить в бд.
 * https://www.youtube.com/watch?v=Wmrdfzzpr6A&t=759s
 */
public class UserDaoImplTest {
    private UserDao dao;

    /**
     * Перед каждым тестом будем создаем dao
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dao = new UserDaoImpl();
    }

    /**
     * Тестим что метод возвращает true
     * @throws Exception
     */
    @Test
    public void getUserByUsername_Should_Return_True() throws Exception {

        //получаем существующего юзера
        User remy = dao.getUserByUsername("remy@gmail.com");

        //проверяем что пользователь не null
        assertThat(remy).isNotNull();

        //проверяем что юзернейм пользователя соответствует
        assertThat(remy.getUsername()).isEqualTo("remy@gmail.com");
    }

    /**
     * Тестируем когда dao вместо пользователя возвращает null
     */
    @Test
    public void getUserByUsername_Null_User() throws Exception {

        //пробуем достать несуществующего юзера
        User alex = dao.getUserByUsername("alex@gmail.com");

        //проверяем что null
        assertThat(alex).isNull();
    }

    /**
     * Сравниваем объекты.
     */
    @Test
    public void findAllUsers_Contain() {

        List<User> allUsers = dao.findAllUsers();

        //сравниваем объект по полям
        assertThat(allUsers.get(2)).isEqualToComparingFieldByField(new User("remy@gmail.com", "ADMIN"));

        //для работы этого теста нужно переопределить equals() и hashcode() у User
        assertThat(allUsers).contains(new User("remy@gmail.com", "ADMIN"));
    }
}