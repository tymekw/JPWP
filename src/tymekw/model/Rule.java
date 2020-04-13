package model;

public interface Rule {
    boolean isValid(Field src, Field dst, Board board);
}
