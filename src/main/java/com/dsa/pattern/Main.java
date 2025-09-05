package com.dsa.pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int[]  arr = {1,2,3,4,5};
        Main m = new Main();
        m.sum(arr);
    }
    public void sum(int[] arr){
        int len = arr.length;
        int auxArr[] = new int[len];
        int tempSum = 0;
        for(int i=0; i<len; i++){
            tempSum += arr[i];
            auxArr[i] = tempSum;
        }
    }
}