import java.util.*;
public class Player{
  public char jikaze;
  Hai[] tehai = new Hai[14];
  ArrayList<Hai> tehai = new ArrayList<Hai>();
  Table t;
  Hai[] sutehai = new Hai[30];
  ArrayList<Hai> sutehai = new ArrayList<Hai>();
  public Player(Table table)
  {
    t=table;
  }
  public void tsumo()
  {
    tehai.get(14)=t.mountain.pop();
    dahai();
  }
  private void dahai()
  {
    Scanner s = new Scanner(System.in);
    for(int i=0; i<sutehai.size(); i++)
    {
      if(sutehai.get(i)==null)
      {
        sutehai.set(i,tehai.get(s.nextInt()));
      }
    }

    tehai.set(s.nextInt(),null);
    s.close();
    riipai();
  }



  public Tile getDiscardSelection()
  {
    if(tehai[tehai.length-1]==null)
    {
      return null;
    }
    else
    {
      calculateShantensuu();
      //create a list of possibleHaiCollection with tehai-maxNodes.get(i).haiCollection
      //ArrayList<ArrayList<Hai>>
      //create a HaiCollection class
      //split each possibleHaiCollection into mentsu, taatsu, and atama
      //for each parts, calculate ukeire probability function and add them up (for every possibleHaiCollection)
      //choose possibleHaiCollection with max ukeire probability function
      //from the selected maxNode, discard in order of character to 1,9 towards the middle
    }
  }

  public void naki()
  {

  }

}
