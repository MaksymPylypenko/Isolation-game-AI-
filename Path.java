import java.util.*;



/*
 *  Helper class to represent a path  
 */


public class Path
{
  public int id; // 
  public Point player; 
  public ArrayList<Point> visited;
    
  public Path(int i, Point p, ArrayList<Point> v)
  {
    id = i;
    player = p.copy();
    visited = deepCopy(v);
  }
  
  public Path(int i, Point p, boolean isHead)
  {
    id = i;
    visited = new ArrayList<Point>();
    visited.add( p.copy() );    
    
    if(isHead)
    {      
      player = p.copy();  
    }
  }
  
  public void addVisit(Point i)
  {
    visited.add( i.copy() );
  }

  public void setPlayer(Point p)
  {
    player = p.copy();
  }
  
  
  // helper method to make a deep copy of array list
  public ArrayList<Point> deepCopy(ArrayList<Point> a)
  {
    ArrayList<Point> result = new ArrayList<Point>();
    for(int i=0; i<a.size(); i++)
    {
      result.add( a.get(i).copy() );
    }
    return result;
  }
}



/*
 *  Helper class to represent a position
 */



class Point
{
  public int col; // j
  public int row; // i
  
  public Point()
  {
    col = -1;
    row = -1;
  }
  
  public Point(int i, int j)
  {
    col = j;
    row = i;
  }
  
  public boolean equal(Point p)
  {
    if(col == p.col && row == p.row)
      return true;
    else
      return false;
  }
  
  public Point copy()
  {
    return new Point(this.row,this.col);
  }
}