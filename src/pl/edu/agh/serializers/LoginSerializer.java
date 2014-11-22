package pl.edu.agh.serializers;

/**
 * Created by Krzysztof Kicinger on 2014-11-10.
 */
public class LoginSerializer {

	private String login;
	private String password;
	private String token;

	public LoginSerializer() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginSerializer{" +
				"login='" + login + '\'' +
				", password='" + password + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
