package com.dsa.pattern.prefix;

/**
 * Utility class to find the minimal difference between the sum of the left
 * and right parts of an integer array when split at any position. The
 * algorithm computes prefix sums and checks every possible split to return
 * the smallest absolute difference between the two parts.
 *
 * <p>Example: for input {1,2,3,4,5} the method returns 3 (split between 3 and 4
 * gives |1+2+3 - (4+5)| = |6 - 9| = 3).</p>
 *
 * <p>Time complexity: O(n). Space complexity: O(n) due to the prefix sum array.</p>
 */
public class TabeEquilibrium {

    /**
     * Simple runner for manual testing. Creates a sample array and prints the
     * minimal difference computed by {@link #solution(int[])}.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        TabeEquilibrium tabeEquilibrium = new TabeEquilibrium();
        System.out.println(tabeEquilibrium.solution(arr));
    }

    /**
     * Computes the minimal absolute difference between the sum of the left
     * part and the sum of the right part of {@code arr} for any split position.
     * The split is considered between indices i and i+1 for i in [0, arr.length-2].
     *
     * @param arr the input array containing integers; must not be {@code null}
     *            and should have at least two elements to form a valid split
     * @return the minimal absolute difference between left and right sums; returns 0
     *         when {@code arr} has length less than 2
     * @throws IllegalArgumentException if {@code arr} is {@code null}
     */
    private int solution(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array must not be null");
        }
        int len = arr.length;
        if (len < 2) {
            return 0; // no valid split
        }

        //calculate prefix sum
        int[] prefixSum = new int[len];
        int tempSum = 0;
        for(int i=0; i<len; i++){
            tempSum += arr[i];
            prefixSum[i] = tempSum;
        }
        int minSum=0;
        int i=0;
        while(i<len-1){
            int sum = Math.abs(prefixSum[i] - (prefixSum[len-1] - prefixSum[i]));
            minSum = i==0?sum:  Math.min(minSum, sum);
            i++;
        }
        return minSum;
    }
}
