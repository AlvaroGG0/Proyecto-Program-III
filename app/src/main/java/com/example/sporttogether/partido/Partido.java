package com.example.sporttogether.partido;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.example.sporttogether.usuario.Usuario;

/**
 * Clase para el manejo y gestión de los partidos
 */

public class Partido {

	private int idPartido;
	private LocalDateTime datetime;
	private int idDeporte;
	private String[] equipo1;
	private String[] equipo2;
	private String[] resultado;
	private int equipoGanador;

	/**
	 * Constructor vacío
	 */
	public Partido() {
		super();
	}

	/**
	 * Constructor principalmente usado en la creación de partidos
	 * @param datetime Fecha y hora del partido
	 * @param idDeporte Tipo de deporte
	 * @param equipo1 Array de usernames que conforman el equipo1. En él se almacena el usuario que crea el partido
	 * @param equipo2 Array de usernames que conforman el equipo2.
	 * @param resultado Array donde se alamcena el resultado.
	 */
	public Partido(LocalDateTime datetime, int idDeporte, String[] equipo1, String[] equipo2, String[] resultado){
		super();
		this.datetime = datetime;
		this.idDeporte = idDeporte;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.resultado = resultado;
	}

	/**
	 * Constructor completo
	 * @param idPartido
	 * @param datetime
	 * @param idDeporte
	 * @param equipo1
	 * @param equipo2
	 * @param resultado
	 * @param equipoGanador
	 */
	public Partido(int idPartido, LocalDateTime datetime, int idDeporte, String[] equipo1, String[] equipo2,
			String[] resultado, int equipoGanador) {
		super();
		this.idPartido = idPartido;
		this.datetime = datetime;
		this.idDeporte = idDeporte;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.resultado = resultado;
		this.equipoGanador = equipoGanador;
	}

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public int getIdDeporte() {
		return idDeporte;
	}

	public void setIdDeporte(int idDeporte) {
		this.idDeporte = idDeporte;
	}

	public String[] getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(String[] equipo1) {
		this.equipo1 = equipo1;
	}

	public String[] getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(String[] equipo2) {
		this.equipo2 = equipo2;
	}

	public String[] getResultado() {
		return resultado;
	}

	public void setResultado(String[] resultado) {
		this.resultado = resultado;
	}

	public int getEquipoGanador() {
		return equipoGanador;
	}

	public void setEquipoGanador(int equipoGanador) {
		this.equipoGanador = equipoGanador;
	}

}
