package ru.geekbrains.Lesson;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int H = SIZE / 2;



    public static void main(String[] args) {

        float[] arr1 = new float[SIZE];  /* 1) Создаем одномерный длинный массив */
        Arrays.fill (arr1, 1); /* 2) Заполняем массив единицами */
        timeOfFillArrayWithFormula (arr1); /* 3) Засекаем время заполнения массива по формуле */

        float[] arr2 = new float[SIZE]; /* 1) Создаем одномерный длинный массив */
        Arrays.fill (arr2, 1); /* 2) Заполняем массив единицами */
        timeOfFillArrayWithFormulaRunnable (arr2); /* 3) Засекаем время заполнения массива по формуле с разбивкой на 2 потока */

        System.out.println (Arrays.equals (arr1,arr2)); /* Проверяем массивы на одинаковое заполнение*/

    }

    public static void calculateFormula (float[]arr, int shift) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + (i + shift) / 5) * Math.cos(0.2f + (i + shift) / 5) * Math.cos(0.4f + (i + shift) / 2));
        }
    }

    public static void timeOfFillArrayWithFormula (float[]arr){
        long a = System.currentTimeMillis();
        calculateFormula (arr, 0);
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void timeOfFillArrayWithFormulaRunnable (float[]arr){
        long a = System.currentTimeMillis();
        float[] a1 = new float[H];
        float[] a2 = new float[H];
        System.arraycopy(arr, 0, a1, 0, H);
        System.arraycopy(arr, H, a2, 0, H);

        Thread t1 = new Thread (() -> {calculateFormula (a1, 0);});
        Thread t2 = new Thread (() -> {calculateFormula (a2,H);});
        t1.start ();
        t2.start ();

        try {
            t1.join ();
            t2.join ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        System.arraycopy(a1, 0, arr, 0, H);
        System.arraycopy(a2, 0, arr, H, H);
        System.out.println(System.currentTimeMillis() - a);
    }


}
