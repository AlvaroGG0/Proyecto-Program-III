package com.example.sporttogether.utils.sorting;

import java.util.ArrayList;
import java.util.Collections;

public class Sorting {

    /**
     * Función MergeSort generica utilizando fecha
     * @param list Lista a ser ordenada
     * @param <T> Tipo de objeto de la lista
     * @return La lista ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortDate(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortDate(listLeft);
            listRight = mergeSortDate(listRight);

            return mergeDate(listLeft, listRight);
        }
    }

    /**
     * Función Merge genérica para unir y ordenar las listas divididas (utilizando las fechas)
     * @param a Lista izquierda
     * @param b Lista derecha
     * @param <T> Tipo de objeto de la lista
     * @return La lista unida y ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeDate(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compareDate(b.get(0))) {
                c.add(b.get(0));
                b.remove(0);
            } else {
                c.add(a.get(0));
                a.remove(0);
            }
        }

        while (!a.isEmpty()) {
            c.add(a.get(0));
            a.remove(0);
        }
        while ((!b.isEmpty())) {
            c.add(b.get(0));
            b.remove(0);
        }
        return c;
    }

    /**
     * Función MergeSort generica utilizando un Integer
     * @param list Lista a ser ordenada
     * @param <T> Tipo de objeto de la lista
     * @return La lista ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortInt(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<>(list.subList(0, list.size() / 2));
            ArrayList<T> listRight = new ArrayList<>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortInt(listLeft);
            listRight = mergeSortInt(listRight);

            return mergeInt(listLeft, listRight);
        }
    }

    /**
     * Función Merge genérica para unir y ordenar las listas divididas (utilizando un integer)
     * @param a Lista izquierda
     * @param b Lista derecha
     * @param <T> Tipo de objeto de la lista
     * @return La lista unida y ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeInt(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compareInt(b.get(0))) {
                c.add(b.get(0));
                b.remove(0);
            } else {
                c.add(a.get(0));
                a.remove(0);
            }
        }

        while (!a.isEmpty()) {
            c.add(a.get(0));
            a.remove(0);
        }
        while ((!b.isEmpty())) {
            c.add(b.get(0));
            b.remove(0);
        }
        return c;
    }

    /**
     * Función MergeSort generica utilizando un Array
     * @param list Lista a ser ordenada
     * @param <T> Tipo de objeto de la lista
     * @return La lista ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortArrays(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<>(list.subList(0, list.size() / 2));
            ArrayList<T> listRight = new ArrayList<>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortArrays(listLeft);
            listRight = mergeSortArrays(listRight);

            return mergeArrays(listLeft, listRight);
        }
    }

    /**
     * Función Merge genérica para unir y ordenar las listas divididas (utilizando un Array)
     * @param a Lista izquierda
     * @param b Lista derecha
     * @param <T> Tipo de objeto de la lista
     * @return La lista unida y ordenada
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeArrays(ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> c = new ArrayList<>();
        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.get(0).compareArrays(b.get(0))) {
                c.add(b.get(0));
                b.remove(0);
            } else {
                c.add(a.get(0));
                a.remove(0);
            }
        }

        while (!a.isEmpty()) {
            c.add(a.get(0));
            a.remove(0);
        }
        while ((!b.isEmpty())) {
            c.add(b.get(0));
            b.remove(0);
        }
        return c;
    }
}
