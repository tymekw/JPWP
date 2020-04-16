package model;

import java.util.List;

public class CheckersManager {
    public Player whitePlayer, blackPlayer;
    MoveValidator moveValidator;
    Board board;
    static public Player currentPlayer;
    static Move lastMoveInfo;
    boolean isPromotion;
    boolean isPlayerDefeated;

    public CheckersManager(){
        whitePlayer = new Player(PawnType.WHITE, PawnType.WHITE_KING,PlayerType.WHITE_PLAYER);
        blackPlayer = new Player(PawnType.BLACK, PawnType.BLACK_KING,PlayerType.BLACK_PLAYER);
        currentPlayer = blackPlayer;
        moveValidator = new MoveValidator(new EnglishRuleSet());
        board = new Board();
        board.init();
        lastMoveInfo = new Move();
        isPlayerDefeated = false;
    }

    public boolean isPossibleMove(Position src, Position dst){
        return moveValidator.validate(board.getField(src), board.getField(dst),board);
    }

    public boolean isCaptured(){
        return lastMoveInfo.isCaptured();
    }

    public void performMove(List<Move> moves){
        Position position = moves.get(moves.size()-1).getDestination().position;
        moves.forEach(e -> board.performMove(e));
        lastMoveInfo = moves.get(moves.size()-1);
        checkPromotion(board.getField(position));
    }

    public void move(Position src, Position dst){
        JumpAnalyzer analyzer = new JumpAnalyzer(board);


        boolean isAnyJumpBeforeMove, isAnyJumpAfterMove;
        Field source = board.getField(src);
        Field destination = board.getField(dst);
        lastMoveInfo.setSource(source);
        lastMoveInfo.setDestination(destination);
        isAnyJumpBeforeMove = analyzer.isAnyJump();
        board.move(src,dst);

        lastMoveInfo.setCaptured(false);

        if(analyzer.isJump(source,destination)){
            List<Field> pawnsToRemove = analyzer.getJumpedOverPawns(source,destination);
            if(pawnsToRemove.size() == 0){
                lastMoveInfo.setCaptured(false);
            }else{
                lastMoveInfo.setCaptured(true);
                lastMoveInfo.setCaptured(pawnsToRemove.get(0));//!!!!!!!
                pawnsToRemove.forEach(e ->{
                    board.removePawn(e);
                });
                checkDefeat();
            }
        }
        isAnyJumpAfterMove = analyzer.isAnyJump();
        checkPromotion(board.getField(dst));
        changePlayerIfPossible(isAnyJumpBeforeMove, isAnyJumpAfterMove);
    }

    public Position getCaptured(){
        return lastMoveInfo.getCaptured().position;
    }

    public boolean hasAnyAction(Position position){
        Field field = board.getField(position);
        return field.isPawn() && currentPlayer.isMyPawn(field.getPawnType());
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
    //gotta remove change player form promote
    void promote(Field field){
        isPromotion = true;
        if(field.getPawnType() == PawnType.WHITE){
            //System.out.println("PROMOTE WHITE PROMOTE WHYITE 1");
            field.setPawn1(PawnType.WHITE_KING);
        }else{
            field.setPawn1(PawnType.BLACK_KING);
            changePlayer();
        }
        //changePlayer();
    }

    void changePlayerIfPossible(boolean before, boolean after){
        //System.out.println("before: "+before+"  after: "+after);
        if(isPromotion) return;
        if(!before){
            changePlayer();
        }else if(before && !after){
            changePlayer();
        }else if(before && after){
            JumpAnalyzer analyzer = new JumpAnalyzer(board);
            if(!analyzer.getPawnJumps(lastMoveInfo.getDestination()).isEmpty()){
                //do not change player
            }else{
                changePlayer();
            }
        }
    }

    public void changePlayer(){
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
        lastMoveInfo = new Move();
        isPlayerDefeated = false;
        currentPlayer = blackPlayer;
        isPromotion = false;
    }

    public Board getBoardCopy(){
        return board.copyBoard();
    }
}
