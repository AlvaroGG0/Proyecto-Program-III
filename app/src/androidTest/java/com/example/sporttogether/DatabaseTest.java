package com.example.sporttogether;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.sporttogether.database.Database;
import com.example.sporttogether.partido.Partido;
import com.example.sporttogether.usuario.Usuario;
import com.example.sporttogether.utils.BCrypt.BCrypt;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private static final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @BeforeClass
    public static void startConnection() {
        Database.DATABASE_NAME="SportTogetherTest.db";
        Database.url="jdbc:sqldroid:/data/data/com.example.sporttogether/databases/SportTogetherTest.db";
        Database.startConnection(appContext);
    }

    @AfterClass
    public static void endConnection() {
        Database.endConnection();
    }

    @Test
    public void testRegisterNewUser() throws SQLException {
        Database.registerNewUser("Test", "Test");
        String sql = "SELECT * "
                + "FROM Usuarios WHERE username = ?";

        PreparedStatement pstmt = Database.conn.prepareStatement(sql);

        pstmt.setString(1, "Test");
        ResultSet rs  = pstmt.executeQuery();
        while(rs.next()){
            assertEquals("El usuario no coincide", "Test", rs.getString("username"));
            System.out.println("El usuario coincide");
            assertTrue("La contrase�a no coincide", BCrypt.checkpw("Test", rs.getString("password")));
            System.out.println("La contraseña coincide");
        }
    }

    @Test
    public void testVerifyLogin() {
        assertTrue("La verificaci�n ha sido fallida", Database.verifyLogin("Test", "Test"));
        System.out.println("Verificaci�n de usuario correcta");
    }

    @Test
    public void testGetUserInfo() {
        Usuario usuario = Database.getUserInfo("Test");
        assertEquals("El nombre no coincide", "", usuario.getNombre());
        assertEquals("Los apellidos no coincide", "", usuario.getApellidos());
        assertEquals("La edad no coincide", 0, usuario.getEdad());
        assertEquals("Admin no coincide", 0, usuario.getAdmin());
        assertEquals("FirstLogin no coincide", 1, usuario.getFirstLogin());
        System.out.println("Recopilación de usuario correcta");
    }

    @Test
    public void testCompleteProfile() {
        Database.completeProfile("Test", "Test", "Test", 0);
        Usuario usuario = Database.getUserInfo("Test");
        assertEquals("El nombre no coincide", "Test", usuario.getNombre());
        assertEquals("Los apellidos no coincide", "Test", usuario.getApellidos());
        assertEquals("La edad no coincide", 0, usuario.getEdad());
        assertEquals("Admin no coincide", 0, usuario.getAdmin());
        assertEquals("FirstLogin no coincide", 0, usuario.getFirstLogin());
        System.out.println("Usuario completado correctamente");
    }

    @Test
    public void testGetMatches() {
        ArrayList<Partido> partidos = Database.getMatches();
        Partido partidoTest = partidos.get(0);
        assertEquals("El idPartido no coincide", 1, partidoTest.getIdPartido());
        assertEquals("La fecha no coincide", LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0), partidoTest.getDatetime());
        assertEquals("El idDeporte no coincide", 0, partidoTest.getIdDeporte());
        assertEquals("El equipo1 no coincide", "Test", partidoTest.getEquipo1()[0]);
        assertEquals("El equipo2 no coincide", "null", partidoTest.getEquipo2()[0]);
        assertEquals("El resultado no coincide", "null", partidoTest.getResultado()[0]);
        assertEquals("El equipo ganador no coincide", 0, partidoTest.getEquipoGanador());
        System.out.println("Partidos recopilados correctamente");
    }

    @Test
    public void testCreateMatch() {
        Usuario usuario = new Usuario("Test");
        Partido partido = new Partido(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0),
                0, new String[1], new String[1], new String[1]);
        Database.createMatch(usuario, partido);
        ArrayList<Partido> partidos = Database.getMatches();
        Partido partidoTest = partidos.get(1);
        assertEquals("El idPartido no coincide", 2, partidoTest.getIdPartido());
        assertEquals("La fecha no coincide", LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0), partidoTest.getDatetime());
        assertEquals("El idDeporte no coincide", 0, partidoTest.getIdDeporte());
        assertEquals("El equipo1 no coincide", "Test", partidoTest.getEquipo1()[0]);
        assertEquals("El equipo2 no coincide", "null", partidoTest.getEquipo2()[0]);
        assertEquals("El resultado no coincide", "null", partidoTest.getResultado()[0]);
        assertEquals("El equipo ganador no coincide", 0, partidoTest.getEquipoGanador());
        System.out.println("Partidos recopilados correctamente");
    }
}