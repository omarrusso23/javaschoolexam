package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */


    public boolean find(List x, List y) {
        // Check for invalid input if it is invalid we throw an exception
        if (x == null || y == null) {
            throw new IllegalArgumentException("err");
        }

        //Both pointers to iterate through both lists
        int i = 0;
        int j = 0;

        // Iterate through both lists using two pointers
        while (i < x.size() && j < y.size()) {
            // Check if the element at the current position in X is equal to the element in Y
            if (x.get(i).equals(y.get(j))) {
                i++;
            }
            j++;
        }

        // Return true if all elements in X have been found in Y, false otherwise
        return i == x.size();
    }
}

