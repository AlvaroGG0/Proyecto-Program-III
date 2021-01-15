package com.example.sporttogether.partido;

import com.example.sporttogether.utils.sorting.I_Comparable;

import java.time.LocalDateTime;

/**
 * Clase para el control y gestión de los partidos
 */

public class Partido implements I_Comparable<Partido> {

	private int idPartido;
	private LocalDateTime datetime;
	private int idDeporte;
	private String[] equipo1;
	private String[] equipo2;
	private String[] resultado;
	private int equipoGanador;

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

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public int getIdDeporte() {
		return idDeporte;
	}

	public String[] getEquipo1() {
		return equipo1;
	}

	public String[] getEquipo2() {
		return equipo2;
	}

	public String[] getResultado() {
		return resultado;
	}

	public int getEquipoGanador() {
		return equipoGanador;
	}

	/**
	 * Método para obtener las plazas ocupadas de cada equipo
	 * @return Array de int cuya primera posición son las plazas del primer equipo y la segunda las del segundo
	 */
	public int[] getPlazasOcupadas(){
		int plazasOcupadasEquipo1 = 0;
		int plazasOcupadasEquipo2 = 0;
		for (String usuario: equipo1) {
			if (!usuario.equals("null")){
				plazasOcupadasEquipo1++;
			}
		}
		for (String usuario: equipo2) {
			if (!usuario.equals("null")){
				plazasOcupadasEquipo2++;
			}
		}
		return new int[] {plazasOcupadasEquipo1, plazasOcupadasEquipo2};
	}

	/**
	 * Método para obtener las plazas disponibles totales entre los dos equipos de un partido
	 * @return int con el resultado
	 */
	public int getPlazasDisponibles(){
		int[] plazasOcupadas = getPlazasOcupadas();
		int plazasDisponibles = 0;
		plazasDisponibles += equipo1.length - plazasOcupadas[0];
		plazasDisponibles += equipo2.length - plazasOcupadas[1];
		return plazasDisponibles;
	}

	@Override
	public boolean compareDate(Partido o) {
		return this.datetime.isAfter(o.datetime);
	}

	@Override
	public boolean compareInt(Partido o) {
		return this.idDeporte>o.idDeporte;
	}

	@Override
	public boolean compareArrays(Partido o) {
		return (this.getPlazasDisponibles() < o.getPlazasDisponibles());
	}

}
