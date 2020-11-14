package com.example.sporttogether.database;

import android.content.Context;
import android.util.Log;

import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.usuario.Usuario;
import com.example.sporttogether.utils.BCrypt;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Database extends SQLiteAssetHelper {

	public static String DATABASE_NAME = "SportTogether.db";
	private static final int DATABASE_VERSION = 1;

	public static Connection conn = null;
	public static String url = "jdbc:sqldroid:/data/data/com.example.sporttogether/databases/SportTogether.db";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static void startConnection(Context context) {
		try {
			new Database(context).getReadableDatabase();
			DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
			conn = DriverManager.getConnection(url);
			Log.w("myApp", "Conexion satisfactoria");

		} catch (SQLException | ClassNotFoundException e) {
			Log.w("SportTogether", e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	public static void endConnection() {
		try {
			conn.close();
			System.out.println("Connection to SQLite has been ended.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Método para registrar un nuevo usuario en la base de datos.
	 * @param username (usuario)
	 * @param password (contraseña)
	 */
	public static void registerNewUser(String username, String password, String nombre, String apellidos, int edad) {
		String sql = "INSERT INTO Usuarios(Username, Password, Nombre, Apellidos, Edad) VALUES(?,?,?,?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
			pstmt.setString(3, nombre);
			pstmt.setString(4, apellidos);
			pstmt.setInt(5, edad);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void registerNewUser(String username, String password) {
		String sql = "INSERT INTO Usuarios(Username, Password) VALUES(?,?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Comprueba si las credenciales insertadas son correctas
	 * @param username
	 * @param password
	 * @return True si las credenciales son correctas, en caso contrario False
	 *
	 */

	public static boolean verifyLogin(String username, String password) {
		String sql = "SELECT password "
				+ "FROM usuarios WHERE username = ?";
		boolean checkpw = false;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)){

			pstmt.setString(1, username);
			ResultSet rs  = pstmt.executeQuery();
			while(rs.next()) {
				checkpw = BCrypt.checkpw(password, rs.getString("password"));
			}
			return checkpw;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Recopila toda la información de un usuario guardada en la base de datos
	 * @param username
	 * @return Objeto de tipo Usuario con toda la información
	 */

	public static Usuario getUserInfo(String username) {
		String sql = "SELECT * "
				+ "FROM usuarios WHERE username = ?";
		Usuario usuario = new Usuario();

		try (PreparedStatement pstmt = conn.prepareStatement(sql)){

			pstmt.setString(1, username);
			ResultSet rs  = pstmt.executeQuery();
			while(rs.next()){
				usuario = new Usuario(rs.getString("username"), rs.getString("password"),
						rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"),
						rs.getInt("admin"), rs.getInt("firstlogin"));
			}
			return usuario;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new Usuario();
		}
	}

	/**
	 * Utilizado solamente en el primer login de un usuario para completar los datos de su perfil
	 * @param username
	 * @param nombre
	 * @param apellidos
	 * @param edad
	 */

	public static void completeProfile(String username, String nombre, String apellidos, int edad) {
		String sql = "UPDATE usuarios SET nombre = ? , "
				+ "apellidos = ?, "
				+ "edad = ?, "
				+ "firstlogin = ? "
				+ "WHERE username = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidos);
			pstmt.setInt(3, edad);
			pstmt.setInt(4, 0);
			pstmt.setString(5, username);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}