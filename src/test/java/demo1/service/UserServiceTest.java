package demo1.service;

import demo1.dao.UserDao;
import demo1.model.User;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock //для того чтобы эмулировать дао
    private UserDao dao;

    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this); //для того чтобы искались моки
        this.userService = new UserService(dao);
    }

    /**
     * Тестируем сервис. Получаем юзера. Проверяем выполнился метод.
     * @throws Exception
     */
    @Test
    public void checkUserPresence_Should_Return_True() throws Exception {

        //когда мы достаем юзера olga@gmail.com тогда получим new User("olga@gmail.com")
        given(dao.getUserByUsername("olga@gmail.com")).willReturn(new User("olga@gmail.com"));

        //реальный вызов сервиса
        boolean userExist = userService.checkUserPresence(new User("olga@gmail.com"));

        //проверяем что пользователь есть
        assertThat(userExist).isTrue();

        //проверяем выполнился ли метод dao.getUserByUsername с параметром "olga@gmail.com"
        verify(dao).getUserByUsername("olga@gmail.com");
    }

    /**
     * Тестируем сервис. Не получаем юзера.
     * @throws Exception
     */
    @Test
    public void checkUserPresence_Should_Return_False() throws Exception {

        //когда мы достаем юзера olga@gmail.com тогда будем получать null
        given(dao.getUserByUsername("olga@gmail.com")).willReturn(null);

        //реальный вызов сервиса
        boolean userExist = userService.checkUserPresence(new User("olga@gmail.com"));

        //проверяем что пользователя нет
        assertThat(userExist).isFalse();
    }

    /**
     * Тестируем сервис. Получаем эксепшн.
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void checkUserPresence_Should_Throw_Exception() throws Exception {

        //когда мы достаем anyString() тогда мы должны получить эксепшн
        given(dao.getUserByUsername(anyString())).willThrow(Exception.class);

        //реальный вызов сервиса
        userService.checkUserPresence(new User("olga@gmail.com"));
    }

    /**
     * Отследим какие параметры были у выполняемого метода
     */
    @Test
    public void testCaptor() throws Exception {

        //при любом параметре типа стринг (anyString()) будем получать юзера Ольгу из дао
        given(dao.getUserByUsername(anyString())).willReturn(new User("olga@gmail.com"));

        //вызов сервиса с параметром
        boolean b = userService.checkUserPresence(new User("olga@gmail.com"));

        //создаем captor чтобы отследить параметр
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        //отслеживаем параметр у getUserByUsername()
        verify(dao).getUserByUsername(captor.capture());

        //получаем значение параметра
        String argument = captor.getValue();

        //проверяем что параметр в dao.getUserByUsername такой же что и при вызове userService.checkUserPresence
        assertThat(argument).isEqualTo("olga@gmail.com");
    }
}