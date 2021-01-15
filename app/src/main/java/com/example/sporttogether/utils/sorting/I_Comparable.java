package com.example.sporttogether.utils.sorting;

public interface I_Comparable <T> {
    /**
     * Método para comparar dos objetos por fechas
     * @param o Objeto que posee un atributo fecha
     * @return True/False
     */
    boolean compareDate(T o);

    /**
     * Método para comparar dos objetos integers
     * @param o Objeto que posee atributo int
     * @return True/False
     */
    boolean compareInt(T o);

    /**
     * Método para comparar dos objetos por array
     * @param o Objeto que posee un atributo array
     * @return True/False
     */
    boolean compareArrays(T o);
}
