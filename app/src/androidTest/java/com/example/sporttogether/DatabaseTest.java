package com.example.sporttogether;

import android.content.Context;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.sporttogether.database.Database;
import com.example.sporttogether.utils.BCrypt;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private static Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

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
        fail("Not yet implemented");
    }

    @Test
    public void testCompleteProfile() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetLastIdPartido() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateMatch() {
        fail("Not yet implemented");
    }

    @Test
    public void testShowMatches() {
        fail("Not yet implemented");
    }

}