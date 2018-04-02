import java.util.*;
public class Player{
  public char jikaze;
  private Tehai tehai;
  Table t;
  //Hai[] sutehai = new Hai[30];
  HaiCollection sutehai;
  public Player(Table table)
  {
    t=table;
    tehai=new Tehai();
  }
  public void tsumo()
  {
    tehai.set(14,t.mountain.pop());
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
    tehai.sort();
  }
  public Tehai getTehai()
  {
	  return tehai;
  }



  public Hai getDiscardSelection()
  {
	  return null;
  }

  public void naki()
  {

  }

}
