package com.dsa.pattern.slidingwindow;

import java.util.*;

/**
 * The LongestSubstring class provides several methods to find the longest substring
 * without repeating characters in a given string. It includes brute-force, optimized,
 * and more optimal solutions using different data structures and algorithms.
 */
public class LongestSubstring {
    /**
     * Main method to demonstrate different approaches to finding the longest substring
     * without repeating characters in a given string. Prints results for sample inputs.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        LongestSubstring ls = new LongestSubstring();
        System.out.println(ls.longestSubstring("pwwkew"));
        System.out.println(ls.optimizedLongestSubstring("abcabcbb"));
        System.out.println(ls.optimizedLongestSubstring2("abcabcbb"));
        System.out.println("===============");
        System.out.println(ls.moreOptimalSolution("pwwkew"));
        System.out.println(ls.moreOptimalSolution("abcabcbb"));
    }

    /**
     * Finds the longest substring without repeating characters using a brute-force approach.
     * It checks all possible substrings and adds them to a set if they have no duplicates.
     *
     * The loop uses two pointers (i and j) to define the substring window. If a duplicate is found
     * in the current substring, the left pointer (i) is moved forward. Otherwise, the substring is added
     * to the set and the right pointer (j) is moved forward. Finally, the longest substring is returned.
     *
     * @param str the input string
     * @return the longest substring without repeating characters
     */
    public String longestSubstring(String str){
        int i=0,j=0;
        Set<String> set = new HashSet<>();
        while(j<str.length() && i<=j){
            String s = str.substring(i,j);
            if(checkDublicate(s)){
                i++;
            }else{
                set.add(s);
                j++;
            }
        }
        return set.stream().max(Comparator.comparing(String::length)).get();
    }

    /**
     * Checks if the given string contains duplicate characters.
     *
     * @param str the string to check
     * @return true if duplicates exist, false otherwise
     */
    public boolean checkDublicate(String str){
        return str.length() != str.chars().distinct().count();
    }

    /**
     * Optimized sliding window approach to find the length of the longest substring
     * without repeating characters. Uses a HashSet to track unique characters in the current window.
     *
     * The window is defined by two pointers (i and j). If the character at j is not in the set,
     * it is added and the window is expanded. If a duplicate is found, the left pointer (i) is moved
     * forward and the character at i is removed from the set. The maximum window size is tracked.
     *
     * @param str the input string
     * @return the length of the longest substring without repeating characters
     */
    public int optimizedLongestSubstring(String str){
        int i=0,j=0,max=0;
        Set<Character> set = new HashSet<>();
        while (j<str.length()&& i<=j){
            if(!set.contains(str.charAt(j))){
                set.add(str.charAt(j));
                j++;
                max = Math.max(max,j-i);
            }else {
                set.remove(str.charAt(i));
                i++;
            }
        }
        System.out.println(set);
        return max;
    }

    /**
     * Further optimized sliding window approach using an array to store the last seen index
     * of each character (for ASCII input). This allows the left pointer to jump directly past
     * the last occurrence of a duplicate character, making the algorithm more efficient.
     *
     * @param s the input string
     * @return the length of the longest substring without repeating characters
     */
    public int optimizedLongestSubstring2(String s){    if (s == null || s.length() == 0) return 0;

        // If input is ASCII, array of size 128 (or 256) is fastest.
        // Initialize with -1 (meaning not seen).
        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen,-1);

        int left = 0;        // left index of window
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (c >= 128) {
                // If you expect unicode beyond ASCII, use a HashMap instead.
                // This guard is optional; left here to highlight charset limits.
            }
            // If seen before and within current window, move left just after previous index
            if (lastSeen[c] >= left) {
                left = lastSeen[c] + 1;
            }
            // Update last seen index for this char
            lastSeen[c] = right;

            // Update max length
            int currLen = right - left + 1;
            if (currLen > maxLen) maxLen = currLen;
        }
        return maxLen;
    }

    /**
     * Most optimal solution using a HashMap to store the last seen index of each character.
     * The left pointer jumps past the last occurrence of a duplicate, and the window size is updated.
     *
     * This approach works for all Unicode characters, not just ASCII.
     *
     * @param s the input string
     * @return the longest substring without repeating characters
     */
    public String moreOptimalSolution(String s){
        int left=0;
        int currLen = 0, bestLen=0, bestStart = 0;
        Map<Character, Integer> lastSeenMap = new HashMap<>();
        for(int right = 0; right < s.length(); right++){
            char character = s.charAt(right);

            // If character was seen and is inside the current window, move left pointer
            if(lastSeenMap.containsKey(character) && lastSeenMap.get(character) >= left){
                left = lastSeenMap.get(character) + 1;
            }
            lastSeenMap.put(character, right);
            currLen = right - left + 1;
            if(currLen > bestLen){
                bestLen = currLen;
                bestStart = left;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }
}
