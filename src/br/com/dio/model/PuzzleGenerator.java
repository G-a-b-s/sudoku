package br.com.dio.model;

import java.util.ArrayList;
import java.util.List;

public class PuzzleGenerator {

    public static List<List<Space>> generateSudokuPuzzle() {
        List<List<Space>> spaces = new ArrayList<>();
        int[][] puzzle = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                int value = puzzle[i][j];
                boolean fixed = value != 0;
                row.add(new Space(value, fixed));
            }
            spaces.add(row);
        }
        return spaces;
    }
}