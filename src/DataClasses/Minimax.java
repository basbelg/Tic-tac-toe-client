package DataClasses;

import GameInterfaces.AI;
import GameInterfaces.Evaluator;

public class Minimax implements AI
{
    private static Evaluator Eval = new TTT_Evaluator();

    public Evaluator getEvaluator()
    {
        return Eval;
    }


    public int generateTurn(int[][] board) {
        int turn = 0;
        int pos;

        for(int i = 0; i < 9; i++)
            if(board[i/3][i%3] != 0)
                turn++;

        pos = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, turn%2 == 0);

        board[pos/3][pos%3] = (turn%2 == 0)? 1: -1;

        return pos;
    }

    private static int minimax(int[][] board, int depth, int alpha, int beta, boolean maximizer) {
        int optimalPos = 0;

        Integer value = Eval.evaluate(board, depth);                                    // return evaluation of final board state
        if(value != null)
            return value;

        int minmax = (maximizer)? Integer.MIN_VALUE: Integer.MAX_VALUE;
        for(int i = 0; i < 9; i++) {
            if(board[i/3][i%3] != 0)                                                    // continue if impossible move
                continue;

            board[i/3][i%3] = (maximizer)? 1: -1;                                       // recurse on possible move
            value = Minimax.minimax(board, depth + 1, alpha, beta, !maximizer);
            board[i/3][i%3] = 0;

            if((maximizer && minmax < value) || (!maximizer && minmax > value)) {       // update max/min
                minmax = value;
                if(depth == 0)
                    optimalPos = i;
            }

            if(maximizer && value > alpha)                                              // prune
                alpha = value;
            if(!maximizer && value < beta)
                beta = value;
            if(beta <= alpha)
                break;
        }

        if(depth == 0)
            minmax = optimalPos;

        return minmax;
    }
}
