package com.dsa.pattern.slidingwindow;
//https://leetcode.com/problems/minimum-window-substring/description/

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Finds the minimum-length substring (window) in a source string that contains
 * all characters of a target string with their required frequencies.
 *
 * <p>Purpose: This class implements the classic "minimum window substring"
 * problem using the sliding-window technique. It demonstrates how to
 * maintain character frequency counts for the current window and for the
 * required target, expand the window to include required characters, and
 * then contract it to find the smallest valid window.</p>
 *
 * <p>Complexity:
 * - Time: O(n + m) ≈ O(n) where n = length of source string and m = length
 *   of target string. Each character in the source is added and removed
 *   from the window at most once.
 * - Space: O(k) where k is the number of distinct characters in the
 *   target (for the target frequency map) plus the distinct characters
 *   seen in the current window (window frequency map).</p>
 */
public class MinimumWindowSubstring {

    /**
     * Simple demonstration entry point. Prints the smallest window in the
     * example source that contains all characters from the example target.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        MinimumWindowSubstring mws = new MinimumWindowSubstring();
        // Demonstration: expected output "BANC" for source "ADOBECODEBANC" and target "ABC".
        System.out.println(mws.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(mws.minWindow("a", "aa"));
    }

    /**
     * Finds the minimum window substring in 'str' that contains all characters
     * from 'reqString' with at least the same frequency as in 'reqString'.
     */
     private String minWindow(String str, String reqString) {
        // Build frequency map for required characters using groupingBy to count duplicates.
        Map<Character, Integer> targetFreqMap = new HashMap<>();
        for(char c : reqString.toCharArray()){
            targetFreqMap.put(c, targetFreqMap.getOrDefault(c,0)+1);
        }

        // Window frequency map tracks how many times each character appears inside current window.
        Map<Character, Integer> windowFreqMap = new HashMap<>();

        // Initialize window boundaries and helper variables:
        int left = 0;                      // left index of the sliding window (inclusive)
        int right = 0;                     // right index of the sliding window (inclusive)
        int currLen;                       // length of current window (set when needed)
        int bestLen = Integer.MAX_VALUE;   // length of smallest valid window found so far
        int formed = 0;                    // how many distinct required characters currently meet their target count
        int bestLeft = 0;                  // start index of smallest valid window found

        // Expand the window by moving 'right' until we've processed the entire source string.
        while (right < str.length()) {
            // Add current character at position 'right' into the window frequency map.
            char currentChar = str.charAt(right);
            windowFreqMap.put(currentChar, windowFreqMap.getOrDefault(currentChar, 0) + 1);

            // If 'currentChar' is a required character and its count in the window
            // now equals the required count in targetFreqMap, we increment 'formed'.
            // 'formed' counts how many distinct target characters currently satisfy
            // their required frequency inside the current window.
            if (targetFreqMap.containsKey(currentChar)
                    && windowFreqMap.get(currentChar).intValue() == targetFreqMap.get(currentChar).intValue()) {
                formed++;
            }

            /*
             * When 'formed' equals the number of distinct characters required
             * (targetFreqMap.size()), the current window contains all required
             * characters with the needed frequencies. Now we try to contract
             * the window from the left to find the smallest valid window that
             * still satisfies the requirement.
             */
            while (left <= right && formed == targetFreqMap.size()) {
                currLen = right - left + 1; // current window size

                // Update the best (smallest) window if current is smaller.
                if (currLen < bestLen) {
                    bestLen = currLen;
                    bestLeft = left; // record starting index of this smaller window
                }

                // Prepare to remove the leftmost character from the window
                // (because we will move 'left' to the right to shrink the window).
                char removedChar = str.charAt(left);

                // Decrease count of removedChar in the window frequency map.
                int cnt = windowFreqMap.get(removedChar);
                if (cnt > 1) {
                    // More than one occurrence in window: just decrement.
                    windowFreqMap.put(removedChar, cnt - 1);
                } else {
                    // Exactly one occurrence: removing it erases the entry.
                    windowFreqMap.remove(removedChar);
                }

                // If the removed character is required and its frequency in the
                // window has fallen below the required frequency, we decrement
                // 'formed' because the window is no longer valid for that char.
                if (targetFreqMap.containsKey(removedChar)
                        && windowFreqMap.getOrDefault(removedChar, 0) < targetFreqMap.get(removedChar)) {
                    formed--;
                }

                // Move left pointer to actually shrink the window.
                left++;
            }

            // Expand the window by moving right pointer to the next position.
            right++;
        }

        // If bestLen was not updated, no valid window was found; return empty string.
        if (bestLen == Integer.MAX_VALUE) {
            return "";
        }

        // Return the smallest valid window using the recorded starting index and length.
        return str.substring(bestLeft, bestLeft + bestLen);
    }
}

