package br.com.dio.model;

public enum GameStatus {
    IN_PROGRESS("Em andamento"),
    COMPLETED("Concluído"),
    WITH_ERRORS("Com erros");

    private final String label;

    GameStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}