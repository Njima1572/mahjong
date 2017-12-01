import java.util.*;
public class Player{
  int jikaze;
  Hai[] tehai = new Hai[14];
  Table t;
  Hai[] sutehai = new Hai[30];
  public Player(Table table)
  {
    t=table;
  }
  public void tsumo()
  {
    tehai[14]=t.mountain.pop();
    dahai();
  }
  private void dahai()
  {
    Scanner s = new Scanner(System.in);
    for(int i=0; i<sutehai.length; i++)
    {
      if(sutehai[i]==null)
      {
        sutehai[i]=tehai[s.nextInt()];
      }
    }

    tehai[s.nextInt()]=null;
    s.close();
    riipai();
  }
  private void riipai()
  {
    ArrayList<ArrayList<Hai>> splitTehai = new ArrayList<ArrayList<Hai>>(); //in the order of souzu, manzu, pinzu, kaze, sangen
    for(int i=0; i<5; i++)
    {
      splitTehai.add(new ArrayList<Hai>());
    }

    for(int i=0; i<14; i++)
    {
      switch(tehai[i].getType())
      {
        case "souzu":
          splitTehai.get(0).add(tehai[i]);
          break;
        case "manzu":
          splitTehai.get(1).add(tehai[i]);
          break;
        case "pinzu":
          splitTehai.get(2).add(tehai[i]);
          break;
        case "kaze":
          splitTehai.get(3).add(tehai[i]);
          break;
        case "sangen":
          splitTehai.get(4).add(tehai[i]);
          break;
      }
    }
    for(int i=0; i<5; i++)
    {
      boolean isSorted=false;
      int swapCount=0;
      while(isSorted)
      {
        for(int j=1; j<splitTehai.get(i).size(); j++)
        {
          if(splitTehai.get(i).get(j).getNumber()>splitTehai.get(i).get(j-1).getNumber())
          {
            Hai temp=splitTehai.get(i).get(j);
            splitTehai.get(i).set(j,splitTehai.get(i).get(j-1));
            splitTehai.get(i).set(j-1,splitTehai.get(i).get(j));
            swapCount++;
          }
        }
        if(swapCount==0)
        {
          isSorted=true;
        }
      }
    }
    int tehaiIndexCount=0;
    for(int i=0; i<5; i++)
    {
      for(int j=1; j<splitTehai.get(i).size(); j++)
      {
        tehai[tehaiIndexCount+j]=splitTehai.get(i).get(j);
        tehaiIndexCount++;
      }
    }
  } //riipai()

  public void naki()
  {

  }

}
