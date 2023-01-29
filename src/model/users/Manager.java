package model. users;

/**
 * class for manager users
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class Manager extends RegisteredUser{
    /** Default UID */
    private static final long serialVersionUID = 1L;

	/**
	 * Manager class constructor, saves a Registered user as a manager
	 * 
	 * @param name same as the username of the registered user
	 * @param password same as the password of the registered user
	 */
	public Manager(String name, String password){ 
		super(name, password);
	}
}