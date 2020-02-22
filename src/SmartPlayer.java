


/*
 *  Isolation AI, Minimax search and alpha-beta pruning
 *  COMP3190 A3Q1
 *  @author Pylypenko Maksym  
 */



public class SmartPlayer implements A2Q1AI {
  
  public static A2Q1GameI game; 
  
  // @return the best move 
  public A2Q1GameI.Move move(A2Q1GameI game) 
  {       
    this.game = game;
    MiniMax isolation = new MiniMax( feed(), game.currentPlayer(), 16 );  // 17 ok    
    return isolation.decision(true);
  }
  
  
  // @return AI name 
  public String toString() {
    return "Potato";
  }   
  
  
  // Reads the pathways and @return a root node 
  public State feed()
  {        
    State pathways = new State();        
    int i,j;
    int rows = game.board().length;
    int cols = game.board()[0].length;    
    
    for(i = 0; i<rows; i++){            
      for(j = 0; j<cols; j++){   
        if(game.board()[i][j] != ' ')
        {
          pathways.add(game.board()[i][j],new Point(i,j)); 
        }
      }    
    } 
    return pathways;               
  }      
  
}