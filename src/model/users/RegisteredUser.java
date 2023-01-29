package model. users;

/**
 * class for Registered users with usernames and passwords
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class RegisteredUser extends User {
	/** Default UID */
	private static final long serialVersionUID = 1L;
	/** username */
	private String username;
	/** password */
	private String password;

	/***
	 * constructor to set the attributes username and password
	 * 
	 * @param username username handle of the user, uniquely identifies each user
	 * @param password authentication that allows the user access to their account
	 *                 and the system as a registered user
	 */
	public RegisteredUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Getter for the username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Getter for the password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Checks the equality of two registered users. Mainly used in the testers
	 * 
	 * @return true if their username and password match
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		return this.getUsername().equals(((RegisteredUser) o).getUsername())
				&& this.getPassword().equals(((RegisteredUser) o).getPassword());
	}

}
