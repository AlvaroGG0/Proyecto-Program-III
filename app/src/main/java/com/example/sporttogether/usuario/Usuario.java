package com.example.sporttogether.usuario;

public class Usuario {
	
	private String username;
	private String password;
	private String nombre;
	private String apellidos;
	private int edad;
	private int admin;
	private int firstLogin;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Usuario(String username, String password, String nombre, String apellidos, int edad, int admin, int firstLogin) {
		super();
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.admin = admin;
		this.firstLogin = firstLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public int isAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public int getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}

	

	
}
