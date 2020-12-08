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
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase Database para la gestión de la Base de Datos de la aplicación.
 */
public class Database extends SQLiteAssetHelper {

	public static String DATABASE_NAME = "SportTogether.db";
	private static final int DATABASE_VERSION = 1;

	public static Connection conn = null;
	public static String url = "jdbc:sqldroid:/data/data/com.example.sporttogether/databases/SportTogether.db";

	/**
	 * Constructor generado para el uso de la interfaz SQLiteAssetHelper que permite la importación de archivos .db dentro de la aplicación
	 * @param context Contexto de la aplicación
	 */
	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Metodo utilzado para inicializar la conexión con la base de datos
	 * @param context Contexto de la aplicación
	 */
	public static void startConnection(Context context) {
		try {
			new Database(context).getReadableDatabase();
			DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
			conn = DriverManager.getConnection(url);
			Log.w("myApp", "Conexion satisfactoria");

		} catch (SQLException | ClassNotFoundException e) {
			Log.w("SportTogether", e.getMessage());
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método utilizado para terminar la conexión con la base de datos.
	 */
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
	 * @param username username del usuario
	 * @param password contraseña insertada
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
	 * @param username username del usuario del que queremos obtener la información
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
				usuario = new Usuario(rs.getString("username"),
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
	 * Método para completar el perfil de un usuario en caso de ser su primer Login en la aplicación
	 * @param username Username del perfil a completar.
	 * @param nombre Nombre del usuario
	 * @param apellidos Apellidos del usuario
	 * @param edad Edad del usuario
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

	/**
	 * Método que devuelve todos los partidos almacenados en la base de datos según la fecha.
	 * @param date String de la fecha en formato "YYYY-MM-DD"
	 * @return Devuelve un ArrayList con los partidos.
	 */
	public static ArrayList<Partido> getDateMatches(String date) {
		String sql = "SELECT * FROM partidos WHERE datetime LIKE '" + date +"%'";
		ArrayList<Partido> partidos = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql)){

			ResultSet rs  = pstmt.executeQuery();
			while (rs.next()) {
				LocalDateTime localDate = LocalDateTime.parse(rs.getString("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
				Partido partido = new Partido(rs.getInt("idpartido"), localDate, rs.getInt("iddeporte"),
						rs.getString("equipo1").split(", "), rs.getString("equipo2").split(", "),
						rs.getString("resultado").split(", "), rs.getInt("equipoganador"));
				partidos.add(partido);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return partidos;
	}

	/**
	 * Este método crea un nuevo partido almacenandolo en la base de datos.
	 * @param usuario Instancia del usuario logeado que es quién crea el partido
	 * @param partido Instancia del partido a crear que contiene todos sus datos
	 */
	public static void createMatch(Usuario usuario, Partido partido) {
		String sql = "INSERT INTO Partidos(datetime, iddeporte, equipo1, equipo2, resultado) VALUES(?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, partido.getDatetime().toString());
			pstmt.setInt(2, partido.getIdDeporte());

			partido.getEquipo1()[0] = usuario.getUsername();
			pstmt.setString(3, Arrays.toString(partido.getEquipo1()).replace("[", "").replace("]", ""));
			pstmt.setString(4, Arrays.toString(partido.getEquipo2()).replace("[", "").replace("]", ""));
			pstmt.setString(5, Arrays.toString(partido.getResultado()).replace("[", "").replace("]", ""));
			pstmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

}