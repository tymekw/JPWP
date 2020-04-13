package model;

import java.util.List;

public class CheckersManager {
    private Player whitePlayer, blackPlayer;
    MoveValidator moveValidator;
    Board board;
    public Player currentPlayer;
    LastMoveInfo lastMoveInfo;
    boolean isPromotion;
    boolean isPlayerDefeated;

    public CheckersManager(){
        whitePlayer = new Player(PawnType.WHITE, PawnType.WHITE_KING);
        blackPlayer = new Player(PawnType.BLACK, PawnType.BLACK_KING);
        currentPlayer = blackPlayer;
        moveValidator = new MoveValidator(new EnglishRuleSet());
        board = new Board(this);
        board.init();
        lastMoveInfo = new LastMoveInfo();
        isPlayerDefeated = false;
    }

    public boolean isPossibleMove(Position src, Position dst){
        return moveValidator.validate(board.getField(src), board.getField(dst),board);
    }

    public boolean isCaptured(){
        return lastMoveInfo.isCaptured();
    }

    public void move(Position src, Position dst){
        JumpAnalyzer analyzer = new JumpAnalyzer(board, this);
        boolean isAnyJumpBeforeMove, isAnyJumpAfterMove;
        Field source = board.getField(src);
        Field destination = board.getField(dst);
        lastMoveInfo.setSource(source);
        lastMoveInfo.setDestination(destination);
        isAnyJumpBeforeMove = analyzer.isAnyJump();
        board.move(src,dst);

        lastMoveInfo.setCaptured(false);

        if(analyzer.isJump(source,destination)){
            System.out.println("im in move, isJump=true");
            List<Field> pawnsToRemove = analyzer.getJumpedOverPawns(source,destination);
            if(pawnsToRemove.size() == 0){
                lastMoveInfo.setCaptured(false);
            }else{
                //System.out.println("usuwamy piona ofiare");
                lastMoveInfo.setCaptured(true);
                lastMoveInfo.setCaptured(pawnsToRemove.get(0));//!!!!!!!
                pawnsToRemove.forEach(e ->{
                    board.removePawn(e);
                });
                checkDefeat();
            }
            //można zrobić ogólnie, lastMoveInfo captured zrobic na tablice usuwać wszystkie przeskoczone
        }
        isAnyJumpAfterMove = analyzer.isAnyJump();
        //board.printBoard();
        checkPromotion(board.getField(dst));
        changePlayerIfPossible(isAnyJumpBeforeMove, isAnyJumpAfterMove);

        //System.out.println("board.isAnyJump: "+board.isAnyJump());
    }

    public Position getCaptured(){
        return lastMoveInfo.getCaptured().position;
    }

    public boolean hasAnyAction(Position position){
        Field field = board.getField(position);
        return field.isPawn() && currentPlayer.isMyPawn(field.getPawnType());
    }

    //To be worked on
    public boolean isAnyJump(){
        JumpAnalyzer analyzer = new JumpAnalyzer(board,this);
        return analyzer.isAnyJump();
    }

    boolean isPawnInTurn(Field field){
        return currentPlayer.isMyPawn(field.getPawnType());
    }

    void checkPromotion(Field field){
        if(!isPawnPromoted(field) && isValidPromotionCondition(field)){
            promote(field);
        }else{
            isPromotion = false;
        }
    }

    boolean isValidPromotionCondition(Field field){
        PawnType type = field.getPawnType();
        int row = field.position.y;
        return row == 0 && type==PawnType.BLACK || row==7 && type==PawnType.WHITE;
    }

    boolean isPawnPromoted(Field field){
        if(field.isPawn()){
            return field.getPawnType() == PawnType.WHITE_KING || field.getPawnType() == PawnType.BLACK_KING;
        }
        return false;
    }

    void promote(Field field){
        isPromotion = true;
        if(field.getPawnType() == PawnType.WHITE){
            field.setPawn(PawnType.WHITE_KING);
        }else{
            field.setPawn(PawnType.BLACK_KING);
        }
        changePlayer();
    }

    void changePlayerIfPossible(boolean before, boolean after){
        System.out.println("before: "+before+"  after: "+after);
        if(isPromotion) return;
        if(!before){
            changePlayer();
        }else if(before && !after){
            changePlayer();
        }else if(before && after){
            JumpAnalyzer analyzer = new JumpAnalyzer(board, this);
            if(analyzer.canPawnJump(lastMoveInfo.getDestination())){
                //do not change player
            }else{
                changePlayer();
            }
        }
    }

    void changePlayer(){
        if(currentPlayer.equals(whitePlayer)){
            currentPlayer = blackPlayer;
        }else{
            currentPlayer = whitePlayer;
        }
    }

    void checkDefeat(){
        if(!board.hasAnyPawn(whitePlayer) || !board.hasAnyPawn(blackPlayer)){
               isPlayerDefeated = true;
        }
    }

    public boolean isPlayerDefeated() {
        return isPlayerDefeated;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void restartGame(){
        board.init();
        lastMoveInfo = new LastMoveInfo();
        isPlayerDefeated = false;
        currentPlayer = blackPlayer;
        isPromotion = false;
    }
}
