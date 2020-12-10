package com.example.sporttogether.usuario;

/**
 * Clase usuario para la gestión de los usuarios de la bd.
 */
public class Usuario {
	
	private String username;
	private String nombre;
	private String apellidos;
	private int edad;
	private int firstLogin;

	/**
	 * Constructor vacío
	 */
	public Usuario() {
		super();
	}

	public Usuario(String username){
		super();
		this.username=username;
	}

	/**
	 * Constructor completo utilizado en el método GetUserInfo() de la clase Database
	 * @param username username del usuario
	 * @param nombre nombre del usuario
	 * @param apellidos apellidos del usuario
	 * @param edad edad del usuario
	 * @param firstLogin primer login del usuario
	 */
	public Usuario(String username, String nombre, String apellidos, int edad, int firstLogin) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.firstLogin = firstLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) { this.username = username; }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}

}
