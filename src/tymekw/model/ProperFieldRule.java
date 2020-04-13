package model;

public class ProperFieldRule implements Rule {
    @Override
    public boolean isValid(Field src, Field dst, Board board) {
        return dst.type == FieldType.DARK && !dst.isPawn();
    }
}
