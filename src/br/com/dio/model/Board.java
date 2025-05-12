package br.com.dio.model;

import java.util.List;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatus getStatus() {
        if (gameIsFinished()) {
            return GameStatus.COMPLETED;
        } else if (hasErrors()) {
            return GameStatus.WITH_ERRORS;
        } else {
            return GameStatus.IN_PROGRESS;
        }
    }
    public boolean changeValue(int col, int row, int value) {
        Space space = spaces.get(row).get(col);
        if (space.isFixed()) {
            return false;
        }
        space.setActual(value);
        return true;
    }

    public boolean clearValue(int col, int row) {
        Space space = spaces.get(row).get(col);
        if (space.isFixed()) {
            return false;
        }
        space.setActual(0);
        return true;
    }

    public boolean hasErrors() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Space space = spaces.get(row).get(col);
                int value = space.getActual();
                if (value != 0 && (hasDuplicateInRow(row, col, value) ||
                        hasDuplicateInColumn(row, col, value) ||
                        hasDuplicateInBlock(row, col, value))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameIsFinished() {
        for (List<Space> row : spaces) {
            for (Space space : row) {
                if (space.getActual() == 0) {
                    return false;
                }
            }
        }
        return !hasErrors();
    }

    private boolean hasDuplicateInRow(int row, int col, int value) {
        for (int c = 0; c < 9; c++) {
            if (c != col && spaces.get(row).get(c).getActual() == value) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDuplicateInColumn(int row, int col, int value) {
        for (int r = 0; r < 9; r++) {
            if (r != row && spaces.get(r).get(col).getActual() == value) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDuplicateInBlock(int row, int col, int value) {
        int blockRowStart = (row / 3) * 3;
        int blockColStart = (col / 3) * 3;

        for (int r = blockRowStart; r < blockRowStart + 3; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if ((r != row || c != col) && spaces.get(r).get(c).getActual() == value) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidMove(int row, int col, int value) {
        return  !hasDuplicateInRow(row, col, value) &&
                !hasDuplicateInColumn(row, col, value) &&
                !hasDuplicateInBlock(row, col, value);
    }

    public void reset() {
        for (List<Space> row : spaces) {
            for (Space space : row) {
                if (!space.isFixed()) {
                    space.setActual(0);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<Space> row : spaces) {
            for (Space space : row) {
                builder.append(space.isFixed() ? "[" + space.getActual() + "]" : " " + (space.getActual() == 0 ? " " : space.getActual()));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}