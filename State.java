import java.util.*;



/*
 *  Helper class to represent a State. 
 *
 *  #Pathways, each pathway has:
 *     1. id - player signature:
 *             "1" and "a" has the same id = 97
 *             "2" and "b" has the same id = 98 ...            
 *     2. player - player's coordinate.
 *     3. visited - coordinates of visited cells.
 * 
 *  #Functions
 *     1.  add(...)          - adds a new or complements an existing path. 
 *     2.  genMoves(...)     - @return new states with possible moves.   
 *     3.  isBlocked(...)    - @return true if the cell is blocked. 
 *     4.  move(...)         - @return a state with a move applied.
 *     5.  reverseMove(...)  - @return extracted move.
 *     6.  playerIndex(...)  - @return player index 
 *     7.  enemyIndex(...)   - @return enemies indexes. 
 *     8.  evaluate(...)     - @return heuristic evaluation of a state
  {
 */



public class State
{
  public ArrayList<Path> pathways;   
  
  public State()
  {
    pathways = new ArrayList<Path>(); 
  }
  
  public State(ArrayList<Path> update)
  {
    pathways = update;
  }
  
  
  // add a new or complement an existing path 
  public void add(char c, Point p)
  {
    int id;
    boolean result = false;
    
    // true - current position, false - visited position
    boolean isHead = false; 
    
    if( (int) c < 58 ) // c is number       
    {      
      id = (int) c + 48;     
      isHead = true; 
    }
    
    else // c is charater 
    {
      id = (int) c;      
    }    
    
    for(int i=0; i<pathways.size() && !result; i++) // existing player signature
    {
      if(pathways.get(i).id == id)  
      {
        if( isHead ) // if this is a player, record its position
        {
          pathways.get(i).setPlayer(p);
        }
        
        pathways.get(i).addVisit(p);
        result = true;
      }      
    }
    
    if(!result) // new player signature 
    {
      pathways.add(new Path(id,p,isHead));
    }
  }
  
  
  // @return new states with possible moves   
  ArrayList<State> genMoves(int id)
  {      
    ArrayList<State> moves = new ArrayList<State>();  
        
    int rows = A2Q1MyPlayer.game.board().length;
    int cols = A2Q1MyPlayer.game.board()[0].length;  
    int row = pathways.get(id).player.row;
    int col = pathways.get(id).player.col;  
    
    if( col > 0 && !isBlocked( row,col-1) )  
    {
      moves.add( move(id,row,col-1) ); // moveLeft 
    }
    
    if( col < cols-1 && !isBlocked( row,col+1) )  
    {
      moves.add( move(id,row,col+1) ); // moveRight
      // double-check cols -1
    }
    
    if( row > 0 && !isBlocked( row-1,col) )  
    {
      moves.add( move(id,row-1,col) ); // moveUp      
    }
    
    if( row < rows-1 && !isBlocked( row+1,col) )  
    {
      moves.add( move(id,row+1,col) ); // moveDown
    }  
    
    //moves.add( this ); // stay 
        
    return moves;
  }
  
  
  // @return true, if no legal moves left
  boolean isDead(int id)
  {
    int rows = A2Q1MyPlayer.game.board().length;
    int cols = A2Q1MyPlayer.game.board()[0].length;  
    int row = pathways.get(id).player.row;
    int col = pathways.get(id).player.col;  
    
    if( col > 0 && !isBlocked( row,col-1) )  
      return false;    
    
    if( col < cols-1 && !isBlocked( row,col+1) )  
      return false;    
    
    if( row > 0 && !isBlocked( row-1,col) )  
       return false;     
    
    if( row < rows-1 && !isBlocked( row+1,col) )  
      return false; 
   
    return true;
  }
  
  
  
  // @return true if the cell is blocked 
  boolean isBlocked( int row, int col)
  {
    boolean result = false;
    
    for(int i=0; i<pathways.size(); i++)
    {
      for(int j=0; j<pathways.get(i).visited.size() && !result; j++)
      {
        if(pathways.get(i).visited.get(j).row == row && pathways.get(i).visited.get(j).col == col)
        {
          result = true;
        }
      }
    }
    
    return result; 
  }
  
  
  // @return a state with a move applied 
  State move(int index, int row, int col)
  {
    ArrayList<Path> pathwaysCopy = new ArrayList<Path>();    
    Path temp;
    
    // deep copy of a state 
    for(int i=0; i<pathways.size(); i++)
    {
      temp = new Path(pathways.get(i).id, pathways.get(i).player, pathways.get(i).visited);
      
      if(i==index)
      {
        temp.setPlayer(new Point(row,col));
        temp.addVisit(new Point(row,col));
      }
      
      pathwaysCopy.add(temp);
    }
    
    return new State(pathwaysCopy); 
  }
  
  
  // @return extracted move from a new state   // Move { NONE, N, S, E, W };
  A2Q1GameI.Move reverseMove( State appliedMove, int id)
  {    
    Point a,b;   
    
    a = pathways.get(id).player;
    b = appliedMove.pathways.get(id).player;
    
    if(a.row < b.row && a.col == b.col)
      return A2Q1GameI.Move.S;
    
    else if(a.row > b.row && a.col == b.col)
      return A2Q1GameI.Move.N;
    
    else if(a.col < b.col && a.row == b.row)
      return A2Q1GameI.Move.E;
    
    else if(a.col > b.col && a.row == b.row)
      return A2Q1GameI.Move.W;
    
    else     
      return A2Q1GameI.Move.NONE;   
  }  
  
  
  // @return enemy index
  int enemyIndex(int myIndex)
  {    
    int okEnemy = -1;
    int bestEnemy = -1;
    
    for(int i=0; i<pathways.size(); i++)
    {
      if(i != myIndex)
      {  
        okEnemy = i;
        
        if(!this.isDead(i))
        {
          bestEnemy = i;
        }        
      }
    }   
    
    if(bestEnemy ==-1)
      bestEnemy = okEnemy;
      
    return bestEnemy;
  }
  
   
  // @return player index 
  int playerIndex(int me)
  {
    for(int i=0; i<pathways.size(); i++)
    {      
      if(pathways.get(i).id == me)
      {
        //System.out.print("\nPlayer: "+(i+1)+" detected\n");
        return i;
      }
    }
    return -1;
  }
  
      
  // @return current evaluation of a state for a player 
  double evaluate(int me)
  {
    /*  Since players do not cooperate, a good strategy might be 
     *  evaluating a total cuptured area...
     */   
    
    Point temp2;
    int i,j;
    
    Path temp = pathways.get(me);  
  
    int maxRow = Integer.MIN_VALUE; // y2
    int maxCol = Integer.MIN_VALUE; // x2
    int minRow = Integer.MAX_VALUE; // y1
    int minCol = Integer.MAX_VALUE; // x1
    
    // get shape cuptured 
    for(i=0; i<temp.visited.size(); i++)
    {
      temp2 = temp.visited.get(i);
      
      if(maxRow < temp2.row)
        maxRow = temp2.row;
      
      if(maxCol < temp2.col)
        maxCol = temp2.col;
      
      if(minRow > temp2.row)
        minRow = temp2.row;
      
      if(minCol > temp2.col)
        minCol = temp2.col;
    }
    
    double score = ( maxRow - minRow ) * ( maxCol-minCol );   
    
               
    // penalise enemy invasions     
    for(i=0; i<pathways.size(); i++)
    {
      if(i!=me)
      {
        for(j=0; j<pathways.get(i).visited.size(); j++)
        {
          temp2 = pathways.get(i).visited.get(j);
          if(temp2.row <maxRow && temp2.row > minRow && temp2.col <maxCol && temp2.col > minCol)
            score-=5;          
        }
      }      
    }    
       
    return score;
  }
  
}