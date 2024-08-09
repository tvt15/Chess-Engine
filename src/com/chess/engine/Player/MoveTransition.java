package com.chess.engine.Player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.concurrent.Future;

public class MoveTransition {//transfers all the data from prev move to next move;
    protected final Board transitionBoard;
    protected final MoveStatus moveStatus;
    protected final Move move;

    public MoveTransition(final Board transitionBoard,
                   final MoveStatus moveStatus,
                   final Move move){
        this.transitionBoard=transitionBoard;
        this.moveStatus=moveStatus;
        this.move=move;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
