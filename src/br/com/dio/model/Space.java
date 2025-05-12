package br.com.dio.model;

public class Space {
    private int actual;
    private final boolean fixed;

    public Space(int actual, boolean fixed) {
        this.actual = actual;
        this.fixed = fixed;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public boolean isFixed() {
        return fixed;
    }
}