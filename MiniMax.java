import java.util.*;


/*
 *  MiniMax search with Alpha Beta pruning. 
 *  Does not support more that 2 players yet... 
 * 
 *  #Variables
 *     1. me - if player is 1, then player id is 1+96, then me is an index related to player id 
 *     2. enemy - closest enemy.
 *     3. initialState - current state of the board   
 * 
 *  #Functions
 *     1.  maxValue(...)   - generates moves and calls minimizer...
 *     2.  minValue(...)   - generates moves and calls maximizer...
 *     3.  decision(...)   - initates minimax and returns the best move  
 */


public class MiniMax
{
  
  int me;
  int enemy;  
  int maxDepth; 
  State initialState;
 
  // constructor 
  public MiniMax(State s, int player, int d)   
  {
    initialState = s;
    maxDepth = d;
    me = s.playerIndex( player +96);   
    enemy = s.enemyIndex(me); // make sure @me is a player index, not player id   
       
  }
  
  
  /*
   *  Recursive maximizer. Generates moves and calls minimizer...
   *  @return the biggest score   
   */
  private double maxValue(State state, double alpha, double beta, int depth) {
    if(depth > maxDepth)
      return state.evaluate(me); 
    
    ArrayList<State> moves = state.genMoves(me);    
    
    if(moves.size() == 0) // terminal state     
      return  -100000 + state.pathways.get(me).visited.size();    
    
    for(int i = 0; i < moves.size(); i++) {
      double tmp = minValue(moves.get(i), alpha, beta, depth + 1);       
            
      if(tmp > alpha) 
        alpha = tmp; 
      
      if(alpha >= beta )    
        return alpha; // prune        
    }     
    return alpha;
    
  } 
  
     
  /*
   *  Recursive minimzer. Generates moves and calls maximizer...
   *  @return the smallest cost
   */
  private double minValue(State state, double alpha, double beta, int depth) {
    if(depth > maxDepth)
      return state.evaluate(me); 
        
    ArrayList<State> moves = state.genMoves(enemy);    
    
    if(moves.size() == 0) // terminal state      
      return 100000 - state.pathways.get(me).visited.size();   
    
    for(int i = 0; i < moves.size(); i++) {   
      double tmp = maxValue( moves.get(i), alpha, beta, depth + 1);
      
      if(tmp < beta)      
        beta = tmp;  
   
      if(beta <= alpha)    
        return beta; // prune     
    }
    return beta;
  }      
  
  
  /*
   *  Initiates minimax with deafault alpha and beta.
   *  @return a move with the highest score.
   */
  public A2Q1GameI.Move decision(boolean salt) 
  {   
    Random r;    
    r = new Random(); 
    
    ArrayList<State> moves = initialState.genMoves(me);    
    
    if(moves.size() == 0) // loss
      return A2Q1GameI.Move.NONE;
    
    double best = Double.NEGATIVE_INFINITY;
    int j = -1;
    
    for(int i = 0; i < moves.size(); i++) 
    {
      double temp = minValue(moves.get(i), -100000, 100000, 0); 
      System.out.print("\nDecision cost is: "+temp+"\n");
      
      if(best<temp || j==-1)
      {        
        best = temp;
        j = i;
      }     
      
      // same cost moves, add random       
      else if(salt && best == temp && r.nextBoolean()) 
      {        
        j = i;
      }
      
    }  
    return initialState.reverseMove(moves.get(j),me);   
  } 
  
  
}










