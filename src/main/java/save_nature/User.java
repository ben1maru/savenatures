package save_nature;

/**
 * Клас для роботи з користувачем
 */
public class User {
    /**
     * Оголошення елементів керування
     */
    private String username;
    private String 	user_surname;
    private String login_user ;
    private String 	password_user;

    /**
     * Конструктор
     * @param username Ім'я корисутвача
     * @param user_surname прізвище користувача
     * @param login_user логін
     * @param password_user пароль
     */
    public User(String username, String user_surname, String login_user, String password_user) {
        this.username = username;
        this.user_surname = user_surname;
        this.login_user = login_user;
        this.password_user = password_user;
    }

    public User() { }

    /**
     *
     * @return ім'я корисутвача
     */
    public String getUsername() {
        return username;
    }

    /**
     * присвоєння імені
     * @param username ім'я корисутвача
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return прізвище користувача
     */
    public String getUser_surname() {
        return user_surname;
    }

    /**
     *  Присвоїння призвища
     * @param user_surname
     */
    public void setUser_surname(String user_surname) {
        this.user_surname = user_surname;
    }

    /**
     *
     * @return логін користувача
     */
    public String getLogin_user() {
        return login_user;
    }

    /**
     * задання  логіну
     * @param login_user логін користувача
     */
    public void setLogin_user(String login_user) {
        this.login_user = login_user;
    }

    /**
     *
     * @return пароль користувача
     */
    public String getPassword_user() {
        return password_user;
    }

    /**
     *
     * @param password_user пероль
     */
    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }
}
