package br.com.dio.gui;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class SudokuGUI {

    private static final int BOARD_SIZE = 9;
    private final JTextField[][] cells = new JTextField[BOARD_SIZE][BOARD_SIZE];
    private final Board board;

    public SudokuGUI(Board board) {
        this.board = board;
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));

                for (int i = 0; i <= BOARD_SIZE; i++) {
                    if (i % 3 == 0) {
                        g2.drawLine(0, i * getHeight() / BOARD_SIZE, getWidth(), i * getHeight() / BOARD_SIZE);
                        g2.drawLine(i * getWidth() / BOARD_SIZE, 0, i * getWidth() / BOARD_SIZE, getHeight());
                    }
                }
            }
        };

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);

                Space space = board.getSpaces().get(row).get(col);

                if (space.isFixed()) {
                    cell.setText(String.valueOf(space.getActual()));
                    cell.setEditable(false);
                    cell.setBackground(Color.LIGHT_GRAY);
                } else {
                    cell.setText("");
                    ((AbstractDocument) cell.getDocument()).setDocumentFilter(new SudokuInputFilter());
                }

                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }

        JButton checkButton = new JButton("Check Status");
        checkButton.addActionListener(e -> {
            System.out.println("Button clicked!"); // Debugging line
            checkGameStatus();
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(checkButton);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void checkGameStatus() {
        boolean hasErrors = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Space space = board.getSpaces().get(row).get(col);
                JTextField cell = cells[row][col];

                if (!space.isFixed()) {
                    String text = cell.getText().trim();
                    int value = text.isEmpty() ? 0 : Integer.parseInt(text);

                    // Atualiza o valor no modelo
                    space.setActual(value);

                    // Valida o valor
                    if (value >= 10 || ( value != 0 && !board.isValidMove(row, col, value))) {
                        cell.setBackground(Color.RED);
                        hasErrors = true;
                    } else {
                        cell.setBackground(Color.WHITE);
                    }
                }
            }
        }

        if (hasErrors) {
            JOptionPane.showMessageDialog(null, "O tabuleiro contém erros! Os quadrados em vermelho indicam os erros.");
        } else if (board.gameIsFinished()) {
            JOptionPane.showMessageDialog(null, "Parabéns! Você completou o Sudoku!");
        }
    }
    private static class SudokuInputFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isValidInput(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValidInput(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidInput(String text) {
            return text.matches("[1-9]");
        }
    }
}