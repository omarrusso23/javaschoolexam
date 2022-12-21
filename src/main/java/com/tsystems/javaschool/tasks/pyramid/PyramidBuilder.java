package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.*;
import java.util.stream.Collectors;


public class PyramidBuilder {
    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws CannotBuildPyramidException if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {

        //First I see if the size is a triangular number if not I throw the exception
        int size=inputNumbers.size();
        if((size*(size+1)%2)!=0){
            throw new CannotBuildPyramidException();
        }


        //I check that the list doesn't have nulls
        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }

        //If the both ifs are false then we proceed to create the pyramid
        Collections.sort(inputNumbers);

        int n = inputNumbers.size();
        int idx = 0;
        int numRows = 0;


        while (idx < n) {
            numRows++;
            for (int numInRow = 0; numInRow < numRows; numInRow++) {
                idx++;
            }
        }

        if (inputNumbers.size() < numRows * (numRows + 1) / 2) {
            throw new CannotBuildPyramidException();
        }


        int numCols = (numRows * 2) - 1;

        // Initialize the 2D array that will hold the pyramid
        int[][] pyramid = new int[numRows][numCols];

        // Initialize the variables that will help us navigate through the pyramid
        int row = 0;
        int col = (numCols - 1) / 2;

        int addNum = 0;
        int numWritten = 0;
        int want = 1;
        int after = 5;

        // Iterate through the list of numbers and build the pyramid
        for (int num = 0; num < inputNumbers.size(); num++) {
            want = row + 1;
            pyramid[row][col] = inputNumbers.get(num);

            if (pyramid[row][col] == inputNumbers.get(num)) {
                numWritten++;
            }

            if (numWritten < want && row > 0) {
                col = col + 2;

            } else {
                row++;
                addNum++;
                int minus = (addNum * (addNum + 1) / 2);
                if (minus < 6) {
                    col = col - minus;
                } else {
                    col = col - after;
                    after = after + 2;
                }
                numWritten = 0;
            }

            if (row == numRows || row < 0) {
                break;
            }

        }
        return pyramid;
    }
}





