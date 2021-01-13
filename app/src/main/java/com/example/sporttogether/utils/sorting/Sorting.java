package com.example.sporttogether.utils.sorting;

import java.util.ArrayList;
import java.util.Collections;

public class Sorting {

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
     * Generic Merge function to merge and sort the divided lists (using id)
     * @param a left list
     * @param b right list
     * @param <T> type of the objects in the list
     * @return merged (and sorted) list
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
        //At this point either a or b is empty
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
     * Generic Merge Sort using name
     * @param list list to be sorted
     * @param <T> type of the objects to sort
     * @return sorted list
     */
    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortInt(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortInt(listLeft);
            listRight = mergeSortInt(listRight);

            return mergeInt(listLeft, listRight);
        }


    }

    /**
     * Generic Merge function to merge and sort the divided lists (using name)
     * @param a left list
     * @param b right list
     * @param <T> type of the objects in the list
     * @return merged (and sorted) list
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
        //At this point either a or b is empty
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

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static <T extends I_Comparable<T>> ArrayList<T> mergeSortArrays(ArrayList<T> list) {
        if (list.size() == 1) return list;
        else {
            ArrayList<T> listLeft = new ArrayList<T>(list.subList(0, list.size() / 2 ));
            ArrayList<T> listRight = new ArrayList<T>(list.subList(list.size() / 2, list.size()));

            listLeft = mergeSortArrays(listLeft);
            listRight = mergeSortArrays(listRight);

            return mergeArrays(listLeft, listRight);
        }


    }

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
        //At this point either a or b is empty
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
