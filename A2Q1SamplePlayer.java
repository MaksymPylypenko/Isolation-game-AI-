/* With a pretty simple scoring approach, this player beats/ties random
 *  - in an 8x8 2-player game: 92% of the time
 *  - in a 25x25 4-player game: 76% of the time
 * (running 100 trials each time).
 */
public class A2Q1SamplePlayer implements A2Q1AI {
   private A2Q1GameI.Move move;
   private int[] initialPos;

   public A2Q1GameI.Move move(A2Q1GameI game) {
      do {
         move = A2Q1GameI.Move.values()[(int)(Math.random() * 4 + 1)];
      } while (!game.canMove(move));

      if (initialPos == null) {
         initialPos = find(game.board(), (char)('0' + game.currentPlayer()));
      }

      minimax(game, game.board(), game.currentPlayer(), Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max((int)(20/Math.sqrt(game.players()))-game.board().length/10,3), true);

      return move;
   }
   
   private int minimax(A2Q1GameI game, char[][] board, int player, int alpha, int beta, int depth, boolean first) {
      int score;
      if (depth == 0) {
         return score(board, game.currentPlayer());
      }
      if (player == game.currentPlayer()) {
         alpha = Integer.MIN_VALUE;
         // these should be sorted by quality...
         for (A2Q1GameI.Move m : A2Q1GameI.Move.values()) {
            if (m != A2Q1GameI.Move.NONE && canApply(board, m, player)) {
               score = minimax(game, apply(board, m, player), player < game.players() ? player + 1 : 1, alpha, beta, depth-1, false);
               if (score > alpha) {
                  if (first)
                     move = m;
                  alpha = score;
               }
               if (alpha >= beta && game.players() == 2)
                  break;
            }
         }
         return alpha;
      } else {
         beta = Integer.MAX_VALUE;
         for (A2Q1GameI.Move m : A2Q1GameI.Move.values()) {
            if (m != A2Q1GameI.Move.NONE && canApply(board, m, player)) {
               score = minimax(game, apply(board, m, player), player < game.players() ? player + 1 : 1, alpha, beta, depth-1, false);
               if (score < beta) {
                  beta = score;
               }
               if (alpha >= beta && game.players() == 2)
                  break;
            }
         }
         return beta;
      }
   }
   
   private boolean canApply(char[][] board, A2Q1GameI.Move m, int player) {
      int[] pos = find(board, (char)('0' + player));
      int r = pos[0];
      int c = pos[1];

      if (m == A2Q1GameI.Move.N && r > 0) {
         return board[r-1][c] == ' ';
      }

      if (m == A2Q1GameI.Move.S && r < board.length-1) {
         return board[r+1][c] == ' ';
      }

      if (m == A2Q1GameI.Move.W && c > 0) {
         return board[r][c-1] == ' ';
      }

      if (m == A2Q1GameI.Move.E && c < board[r].length-1) {
         return board[r][c+1] == ' ';
      }
      
      return false;
   }

   private char[][] apply(char[][] board, A2Q1GameI.Move m, int player) {
      int[] pos = find(board, (char)('0' + player));
      int r = pos[0];
      int c = pos[1];

      char[][] b = copy(board);
      
      if (m == A2Q1GameI.Move.N) {
         b[r-1][c] = b[r][c];
         b[r][c] = (char)('a' + player - 1);
      }

      if (m == A2Q1GameI.Move.S) {
         b[r+1][c] = b[r][c];
         b[r][c] = (char)('a' + player - 1);
      }

      if (m == A2Q1GameI.Move.W) {
         b[r][c-1] = b[r][c];
         b[r][c] = (char)('a' + player - 1);
      }

      if (m == A2Q1GameI.Move.E) {
         b[r][c+1] = b[r][c];
         b[r][c] = (char)('a' + player - 1);
      }

      return b;
   }

   private int score(char[][] board, int player) {
      int[] pos = find(board, (char)('0' + player));
      int dist = Math.abs(pos[0] - initialPos[0]) + Math.abs(pos[1] - initialPos[1]) + Math.abs(pos[0] - initialPos[0]) * Math.abs(pos[1] - initialPos[1]);

      int possibleMoves = 0;
      for (A2Q1GameI.Move m : A2Q1GameI.Move.values()) {
         if (canApply(board, m, player)) {
            possibleMoves++;
         }
      };
      return dist * possibleMoves;
   }
   
   private int[] find(char[][] board, char value) {
      for (int r = 0; r < board.length; r++) {
         for (int c = 0; c < board[r].length; c++) {
            if (board[r][c] == value)
               return new int[] {r,c};
         }
      }
      return null;
   }
   
   public char[][] copy(char[][] board) {
      char[][] b = new char[board.length][];
      for (int r = 0; r < board.length; r++) {
         b[r] = new char[board[r].length];
         System.arraycopy(board[r], 0, b[r], 0, b[r].length);
      }
      return b;
   }
   
   public String toString() {
      return "Simple sample player";
   }
}