
import java.awt.Point;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Castle
 */
public class Araba {

    private PriorityQueue<Cell> open;
    private boolean closed[][];
    private int startI, startJ;
    private int endI, endJ;
    private int boyutx,boyuty;
    Cell [][] grid = new Cell[boyutx][boyuty];
    private  int DIAGONAL_COST ,V_H_COST;
    
    private int [][] Kara_Kutu_Dizi;
    public Araba(int boyutx,int boyuty,Point basla,Point bitis,int DIAGONAL_COST,int V_H_COST,int [][] Kara_Kutu_Dizi) 
    {
        this.boyutx = boyutx;
        this.boyuty = boyuty;
        this.startI = basla.x;
        this.startJ = basla.y;
        this.endI = bitis.x;
        this.endJ = bitis.y;
        this.DIAGONAL_COST = DIAGONAL_COST;
        this.V_H_COST = V_H_COST;
        this.Kara_Kutu_Dizi = Kara_Kutu_Dizi;
    }
    
    private  void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    private  void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }
    
    private  void setEndCell(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    private void checkAndUpdateCost(Cell current, Cell t, int cost)
     {
        if(t == null || closed[t.i][t.j])
        {
            return;
        }
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
     
    private  void AStar()
    { 
        
        open.add(grid[startI][startJ]);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Cell t;  
            if(current.i-1>=0)
            {
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){                      
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }

                if(current.j+1<grid[0].length){
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                if(current.j-1>=0){
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST); 
                }  
            }
        } 
    }
    
    public  Stack<Point> test() 
    {
        int x = boyutx, y = boyuty, si=startI ,  sj =startJ, ei =endI,  ej=endJ;
        int[][] blocked = Kara_Kutu_Dizi;
        Stack<Point> Gecis_Noktalari = new Stack<>();

           Point gecici;
           
           grid = new Cell[x][y];
           closed = new boolean[x][y];
           open = new PriorityQueue<>((Object o1, Object o2) -> {
                Cell c1 = (Cell)o1;
                Cell c2 = (Cell)o2;

                return c1.finalCost<c2.finalCost?-1:
                        c1.finalCost>c2.finalCost?1:0;
            });
           
           setStartCell(si, sj);  

           setEndCell(ei, ej); 
           
           for(int i=0;i<x;++i){
              for(int j=0;j<y;++j){
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
//                  System.out.print(grid[i][j].heuristicCost+" ");
                     
              }
//              System.out.println();
                
           }
           grid[si][sj].finalCost = 0;
           
           for(int i=0;i<blocked.length;++i){
               setBlocked(blocked[i][0], blocked[i][1]);
           }
           
          
           
           AStar(); 
           
            
           if(closed[endI][endJ]){
               //Trace back the path 
                //System.out.println("Path: ");
                Cell current = grid[endI][endJ];
                //System.out.print(current.i + "," + current.j);
                gecici=new Point();
                gecici.x = current.i;
                gecici.y = current.j;
                Gecis_Noktalari.push(gecici);
                
                while(current.parent!=null){
                    gecici=new Point();
                    gecici.x = current.parent.i;
                    gecici.y = current.parent.j;
                    Gecis_Noktalari.push(gecici);
                    //System.out.println(" -> "+current.parent);
                    current = current.parent;
                } 
                System.out.println();
           }else System.out.println("No possible path");
           
           return Gecis_Noktalari;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    class Cell
    {
        
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        
        Cell parent; 
        
        Cell(int i, int j){
            this.i = i;
            this.j = j; 
        }
        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }
    
}
