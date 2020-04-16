package model;

public class JumpIfPossibleRule implements Rule {
    JumpAnalyzer analyzer;
    @Override
    public boolean isValid(Field src, Field dst, Board board) {
        analyzer  = new JumpAnalyzer(board);

        boolean isAnyJump = analyzer.isAnyJump();

        if(analyzer.isJump(src,dst) && isAnyJump) return true;
        if(!analyzer.isJump(src,dst) && isAnyJump) return false;
        //if(analyzer.isJump(src,dst) && !isAnyJump) return false;
        if(!analyzer.isJump(src,dst) && !isAnyJump) return true;

        return true;
    }

}
