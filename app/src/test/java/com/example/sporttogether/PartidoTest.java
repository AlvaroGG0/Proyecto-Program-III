package com.example.sporttogether;

import com.example.sporttogether.partido.Partido;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PartidoTest {

    Partido testPartido = new Partido(LocalDateTime.now(), 1, new String[]{"test", "null"}, new String[]{"null", "null"}, new String[]{"null"});
    Partido testPartido1 = new Partido(LocalDateTime.now().minusMinutes(1), 0, new String[]{"null", "null"}, new String[]{"null", "null"}, new String[]{"null"});

    @Test
    public void testGetPlazasOcupadas() {
        int[] ocupadas = testPartido.getPlazasOcupadas();
        assertEquals(1, ocupadas[0]);
        assertEquals(0, ocupadas[1]);
    }

    @Test
    public void testGetPlazasDisponibles() {
        assertEquals(3, testPartido.getPlazasDisponibles());
    }

    @Test
    public void testCompareDate() {
        assertTrue(testPartido.compareDate(testPartido1));
    }

    @Test
    public void testCompareInt() {
        assertTrue(testPartido.compareInt(testPartido1));
    }

    @Test
    public void testCompareArrays() {
        assertTrue(testPartido.compareArrays(testPartido1));
    }
}