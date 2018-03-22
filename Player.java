import java.util.*;
public class Player{
  public char jikaze;
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

  public void riipai()
  {
    ArrayList<ArrayList<Hai>> splitTehai = new ArrayList<ArrayList<Hai>>(); //in the order of souzu, manzu, pinzu, kaze, sangen
    for(int i=0; i<5; i++)
    {
      splitTehai.add(new ArrayList<Hai>());
    }

    for(int i=0; i<13; i++)
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
      while(!isSorted)
      {
        isSorted=true;
        for(int j=1; j<splitTehai.get(i).size(); j++)
        {
          if(splitTehai.get(i).get(j).getNumber()<splitTehai.get(i).get(j-1).getNumber())
          {
            Hai temp=splitTehai.get(i).get(j);
            splitTehai.get(i).set(j,splitTehai.get(i).get(j-1));
            splitTehai.get(i).set(j-1,temp);
            isSorted=false;
          }
        }
      }
    }

    int tehaiIndexCount=0;
    for(int i=0; i<5; i++)
    {
      for(int j=0; j<splitTehai.get(i).size(); j++)
      {
        tehai[tehaiIndexCount+j]=splitTehai.get(i).get(j);

      }
      tehaiIndexCount=tehaiIndexCount+splitTehai.get(i).size();
    }


    //System.out.println("--------------------");
    //minShantensuu(tehai);
    //System.out.println("--------------------");

  } //riipai()

  ArrayList<RiipaiPatternsTreeNode> endNodes = new ArrayList<RiipaiPatternsTreeNode>();

  public Integer minShantensuu()
  {
    riipai();
    ArrayList<Hai> aTehai = new ArrayList<Hai>();
    //System.out.println(tehai[tehai.length-1].getType());
    for(int i=0; i<tehai.length-1; i++)
    {
      aTehai.add(tehai[i]);
    }
    //chiitoitsu form
    int chiitoiShantensuu=6;
    int c=1;
    while(c < aTehai.size()) //selecting atamas
    {
      if(aTehai.get(c).getType().equals(aTehai.get(c-1).getType()))
      {
        if(aTehai.get(c).getNumber()==aTehai.get(c-1).getNumber()) //if hai now and before are the same
        {

          c+=2;
          chiitoiShantensuu--;
        }
        else
        {
          c++;
        }
      }
      else
      {
        c++;
      }
    }

    //standard tehai form
    RiipaiPatternsTreeNode rootNode;
    //----------------------------------------------------
    //make level 1 cases: atama selection
        rootNode = new RiipaiPatternsTreeNode(aTehai); //make root node with split hais as the hai collection
        ArrayList<Hai> selectedAtamas = new ArrayList<Hai>();
        for(int i=1; i<aTehai.size(); i++) //selecting atamas
        {
          if(aTehai.get(i).getType().equals(aTehai.get(i-1).getType()))
          {
            if(aTehai.get(i).getNumber()==aTehai.get(i-1).getNumber()) //if hai now and before are the same
            {
              boolean isAtamaDuplicate=false;
              for(int j=0; j<selectedAtamas.size(); j++) //check if it's a duplicate
              {
                if(aTehai.get(i)==selectedAtamas.get(j))
                {
                  isAtamaDuplicate=true;
                }
              }
              if(!isAtamaDuplicate) //if the hai has not been selected as an atama before
              {
                selectedAtamas.add(aTehai.get(i));
                ArrayList<Hai> tehaiWithoutAtama = new ArrayList<Hai>();
                for(int k=0; k<aTehai.size(); k++)
                {
                  if(k!=i && k!=i-1)
                  {
                    tehaiWithoutAtama.add(aTehai.get(k)); //add all hais that's not the selected atama
                  }
                }
                RiipaiPatternsTreeNode l1 = new RiipaiPatternsTreeNode(tehaiWithoutAtama);
                l1.inverseShantensuu=1;
                rootNode.addChild(l1); //make new branch
              }
            }
          }
        }
        rootNode.addChild(new RiipaiPatternsTreeNode(aTehai)); //make one branch with no atama selection
        //----------------------------------------------------

        //----------------------------------------------------
        //recursively make branches of kootsu selected left to right then shuntsu selected left to right
        //when recursion ends at each branch, store the end nodes which constains the inverseShantensuu

        for(int i=0; i<rootNode.children.size(); i++)
        {
          minShantensuuHelper(rootNode.children.get(i));
        }

    //loop through each end node and find one with the max inverseShantensuu (could be multiple)
    RiipaiPatternsTreeNode maxNode = endNodes.get(0);
    for(int i=1; i<endNodes.size(); i++)
    {
      if(endNodes.get(i).inverseShantensuu>maxNode.inverseShantensuu)
      {
        maxNode=endNodes.get(i);
      }
    }

    //return minimum shantensuu and print all end node haiCollection
    //System.out.println(maxNode.haiCollection.toString() + " Shantensuu is "+(8-maxNode.inverseShantensuu));
    int minS;
    if(chiitoiShantensuu<8-maxNode.inverseShantensuu)
    {
      minS=chiitoiShantensuu;
    }
    else
    {
      minS = 8-maxNode.inverseShantensuu;
    }
    return minS;

  }//end of minShantensuu()

  private void minShantensuuHelper(RiipaiPatternsTreeNode n)
  {
    if(n.numberOfNonAtama==4 || !kootsuExists(n.haiCollection) && !shuntsuExists(n.haiCollection) && !staatsuExists(n.haiCollection) && !ktaatsuExists(n.haiCollection))
    {
      endNodes.add(n);
      return;
    }
    else
    {
      if(kootsuExists(n.haiCollection))
      {
        for(int i=2; i<n.haiCollection.size(); i++) //select kootsu
        {
          if(n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-1).getType()) && n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-2).getType()))
          {
            if(n.haiCollection.get(i).getNumber()==n.haiCollection.get(i-1).getNumber() && n.haiCollection.get(i).getNumber() ==n.haiCollection.get(i-2).getNumber())
            {
              ArrayList<Hai> nMinusKootsu = new ArrayList<Hai>();
              for(int j=0; j<n.haiCollection.size(); j++)
              {
                if(j!=i && j!=i-1 && j!=i-2)
                {
                  nMinusKootsu.add(n.haiCollection.get(j));
                }
              }
              RiipaiPatternsTreeNode n1 =new RiipaiPatternsTreeNode(nMinusKootsu);
              n1.inverseShantensuu=n.inverseShantensuu+2;
              n1.numberOfNonAtama=n.numberOfNonAtama+1;
              n.addChild(n1);
              minShantensuuHelper(n1);
              break;
            }
          }
        }
      }
      if(shuntsuExists(n.haiCollection))
      {
        for(int i=2; i<n.haiCollection.size(); i++) //select shuntsu
        {
          if(n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-1).getType()) && n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-2).getType()))
          {
            if(n.haiCollection.get(i).getNumber()==n.haiCollection.get(i-1).getNumber()+1 && n.haiCollection.get(i).getNumber()==n.haiCollection.get(i-2).getNumber()+2)
            {
              ArrayList<Hai> nMinusShuntsu = new ArrayList<Hai>();
              for(int j=0; j<n.haiCollection.size(); j++)
              {
                if(j!=i && j!=i-1 && j!=i-2)
                {
                  nMinusShuntsu.add(n.haiCollection.get(j));
                }
              }
              RiipaiPatternsTreeNode n2 = new RiipaiPatternsTreeNode(nMinusShuntsu);
              n2.inverseShantensuu=n.inverseShantensuu+2;
              n2.numberOfNonAtama=n.numberOfNonAtama+1;
              n.addChild(n2);
              minShantensuuHelper(n2);
              break;
            }
          }
        }
      }

      if(staatsuExists(n.haiCollection))
      {
        for(int i=1; i<n.haiCollection.size(); i++) //select shuntsu
        {
          if(n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-1).getType()))
          {
            if(n.haiCollection.get(i).getNumber()==n.haiCollection.get(i-1).getNumber()+1)
            {
              ArrayList<Hai> nMinusTaatsu = new ArrayList<Hai>();
              for(int j=0; j<n.haiCollection.size(); j++)
              {
                if(j!=i && j!=i-1)
                {
                  nMinusTaatsu.add(n.haiCollection.get(j));
                }
              }
              RiipaiPatternsTreeNode n3 = new RiipaiPatternsTreeNode(nMinusTaatsu);
              n3.inverseShantensuu=n.inverseShantensuu+1;
              n3.numberOfNonAtama=n.numberOfNonAtama+1;
              n.addChild(n3);
              minShantensuuHelper(n3);
              break;
            }
          }
        }
      }
      if(ktaatsuExists(n.haiCollection))
      {
        for(int i=1; i<n.haiCollection.size(); i++) //select shuntsu
        {
          if(n.haiCollection.get(i).getType().equals(n.haiCollection.get(i-1).getType()))
          {
            if(n.haiCollection.get(i).getNumber()==n.haiCollection.get(i-1).getNumber())
            {
              ArrayList<Hai> nMinusTaatsu = new ArrayList<Hai>();
              for(int j=0; j<n.haiCollection.size(); j++)
              {
                if(j!=i && j!=i-1)
                {
                  nMinusTaatsu.add(n.haiCollection.get(j));
                }
              }
              RiipaiPatternsTreeNode n4 = new RiipaiPatternsTreeNode(nMinusTaatsu);
              n4.inverseShantensuu=n.inverseShantensuu+1;
              n4.numberOfNonAtama=n.numberOfNonAtama+1;
              n.addChild(n4);
              minShantensuuHelper(n4);
              break;
            }
          }
        }
      }

    }
  }

  private boolean kootsuExists(ArrayList<Hai> h)
  {

    for(int i=2; i<h.size(); i++) //select kootsu
    {
      if(h.get(i).getType().equals(h.get(i-1).getType()) && h.get(i).getType().equals(h.get(i-2).getType()))
      {
        if(h.get(i).getNumber()==h.get(i-1).getNumber() && h.get(i).getNumber()==h.get(i-2).getNumber())
        {
          return true;
        }
      }
    }
    return false;
  }
  private boolean shuntsuExists(ArrayList<Hai> h)
  {
    for(int i=2; i<h.size(); i++) //select shuntsu
    {
      if(h.get(i).getType().equals(h.get(i-1).getType()) && h.get(i).getType().equals(h.get(i-2).getType()))
      {
        if(h.get(i).getNumber()==h.get(i-1).getNumber()+1 && h.get(i).getNumber()==h.get(i-2).getNumber()+2)
        {
          if(!h.get(i).getType().equals("kaze") && !h.get(i).getType().equals("sangen"))
          {
            return true;
          }
        }
      }
    }
    return false;
  }
  private boolean staatsuExists(ArrayList<Hai> h)
  {
    for(int i=1; i<h.size(); i++) //shuntsu type taatsu
    {
      if(h.get(i).getType().equals(h.get(i-1).getType()))
      {
        if(h.get(i).getNumber()==h.get(i-1).getNumber()+1)
        {
          if(!h.get(i).getType().equals("kaze") && !h.get(i).getType().equals("sangen"))
          {
            return true;
          }
        }
      }
    }
    return false;
  }
  private boolean ktaatsuExists(ArrayList<Hai> h)
  {
    for(int i=1; i<h.size(); i++) //kootsu type taatsu
    {
      if(h.get(i).getType().equals(h.get(i-1).getType()))
      {
        if(h.get(i).getNumber()==h.get(i-1).getNumber())
        {
            return true;
        }
      }
    }
    return false;
  }

  public void naki()
  {

  }

}
