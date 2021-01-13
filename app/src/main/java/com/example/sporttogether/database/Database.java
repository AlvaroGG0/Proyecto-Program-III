package com.example.sporttogether.database;

import android.content.Context;
import android.util.Log;

import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.ui.activities.LogInActivity;
import com.example.sporttogether.usuario.Usuario;
import com.example.sporttogether.utils.BCrypt.BCrypt;
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
	 * Comprueba si el usuario insertado para el registro ya existe o no
	 * @param username username del usuario
	 * @return True si el usuario existe, en caso contrario False
	 */

	public static boolean verifyRegisterUser(String username) {
		String sql = "SELECT count(*) "
				+ "FROM usuarios WHERE username = ?";
		boolean exists = false;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)){

			pstmt.setString(1, username);
			ResultSet rs  = pstmt.executeQuery();
			while(rs.next()) {
				exists = rs.getBoolean(1);
			}
			return exists;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
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
						rs.getInt("firstlogin"));
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

	/**
	 * Este método elimina un partido a través de su id.
	 * @param idPartido idPartido del partido a eliminar.
	 */
	public static void deleteMatch(int idPartido){
		String sql = "DELETE FROM Partidos WHERE idpartido = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idPartido);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Este método permite incluir a un usuario a un equipo de un partido.
	 * @param username Nombre de usuario del usuario
	 * @param partido Instancia del partido al que unirse
	 * @param numEquipo Equipo al que unirse
	 */
	public static void joinMatch(String username, Partido partido, int numEquipo){
		String[] partidos;

		if (numEquipo==1){
			partidos = partido.getEquipo1();
		}else{
			partidos = partido.getEquipo2();
		}

		for (int i = 0; i < partidos.length; i++) {
			if (partidos[i].equals("null")){
				partidos[i]=username;
				break;
			}
		}

		String sql = "UPDATE partidos SET equipo" + numEquipo + " = ? WHERE idpartido = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (numEquipo==1){
				pstmt.setString(1, Arrays.toString(partido.getEquipo1()).replace("[", "").replace("]", ""));
			}else if (numEquipo==2){
				pstmt.setString(1, Arrays.toString(partido.getEquipo2()).replace("[", "").replace("]", ""));
			}
			pstmt.setInt(2, partido.getIdPartido());

			pstmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	/**
	 * Este método permite eliminar a un usuario de un partido al que esté unido
	 * @param username Nombre de usaurio del usuario a eliminar
	 * @param partido Instancia del partido
	 */
	public static void leaveMatch(String username, Partido partido){
		int numEquipo = 0;
		if (Arrays.asList(partido.getEquipo1()).contains(LogInActivity.usuario.getUsername())){
			for (int i=0; i<partido.getEquipo1().length; i++){
				if (partido.getEquipo1()[i].equals(username)){
					partido.getEquipo1()[i] = "null";
					numEquipo=1;
					break;
				}
			}
		}else{
			for (int i=0; i<partido.getEquipo2().length; i++){
				if (partido.getEquipo2()[i].equals(username)){
					partido.getEquipo2()[i] = "null";
					numEquipo=2;
					break;
				}
			}
		}

		String sql = "UPDATE partidos SET equipo" + numEquipo + " = ? WHERE idpartido = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (numEquipo==1){
				pstmt.setString(1, Arrays.toString(partido.getEquipo1()).replace("[", "").replace("]", ""));
			}else if (numEquipo==2){
				pstmt.setString(1, Arrays.toString(partido.getEquipo2()).replace("[", "").replace("]", ""));
			}
			pstmt.setInt(2, partido.getIdPartido());

			pstmt.executeUpdate();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}


	}

}