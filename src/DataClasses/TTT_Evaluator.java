package DataClasses;

import GameInterfaces.Evaluator;

public class TTT_Evaluator implements Evaluator {
    public Integer evaluate(int[][] board) {
        return boardState(board, 0);
    }

    public Integer evaluate(int[][] board, int depth) {
        return boardState(board, depth);
    }

    private Integer boardState(int[][] board, int depth) {
        Integer evaluation = null;

        if (board[0][0] + board[0][1] + board[0][2] == 3 ||
                board[1][0] + board[1][1] + board[1][2] == 3 ||
                board[2][0] + board[2][1] + board[2][2] == 3 ||
                board[0][0] + board[1][1] + board[2][2] == 3 ||
                board[0][2] + board[1][1] + board[2][0] == 3 ||
                board[0][0] + board[1][0] + board[2][0] == 3 ||
                board[0][1] + board[1][1] + board[2][1] == 3 ||
                board[0][2] + board[1][2] + board[2][2] == 3)
            evaluation = Integer.MAX_VALUE - depth;
        else if (board[0][0] + board[0][1] + board[0][2] == -3 ||
                board[1][0] + board[1][1] + board[1][2] == -3 ||
                board[2][0] + board[2][1] + board[2][2] == -3 ||
                board[0][0] + board[1][1] + board[2][2] == -3 ||
                board[0][2] + board[1][1] + board[2][0] == -3 ||
                board[0][0] + board[1][0] + board[2][0] == -3 ||
                board[0][1] + board[1][1] + board[2][1] == -3 ||
                board[0][2] + board[1][2] + board[2][2] == -3)
            evaluation = Integer.MIN_VALUE + depth;
        else if(board[0][0] != 0 && board[0][1] != 0 && board[0][2] != 0 &&
                board[1][0] != 0 && board[1][1] != 0 && board[1][2] != 0 &&
                board[2][0] != 0 && board[2][1] != 0 && board[2][2] != 0)
            evaluation = 0;

        return evaluation;
    }
}
