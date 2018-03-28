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


  ArrayList<RiipaiPatternsTreeNode> endNodes = new ArrayList<RiipaiPatternsTreeNode>();
  ArrayList<RiipaiPatternsTreeNode> maxNodes; //end nodes of tree with minimum shantensuu
  public int loopNum=0;
  public Integer calculateShantensuu() //will update maxNodes
  {
    loopNum++;
    riipai();
    ArrayList<Hai> aTehai = new ArrayList<Hai>();
    //System.out.println(tehai[tehai.length-1].getType());
    for(int i=0; i<tehai.length-1; i++)
    {
      aTehai.add(tehai[i]);
    }
    //kokushi form
    int kokushiShantensuu=13;
    ArrayList<Hai> yaochuu = new ArrayList<Hai>();
    boolean isFirstDuplicate=true;
    for(int i=0; i<aTehai.size(); i++)
    {
      if(aTehai.get(i).getNumber()==1 || aTehai.get(i).getNumber()==9 || aTehai.get(i).getType()=="kaze" || aTehai.get(i).getType()=="sangen")
      {
        boolean yaochuuIsDuplicate=false;

        for(int j=0; j<yaochuu.size(); j++)
        {
          if(aTehai.get(i).equals(yaochuu.get(j)))
          {
            if(isFirstDuplicate)
            {
              isFirstDuplicate=false;
            }
            else
            {
              yaochuuIsDuplicate=true;
            }

          }
        }
        if(!yaochuuIsDuplicate)
        {
          yaochuu.add(aTehai.get(i));
          kokushiShantensuu--;
        }
      }
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

    //algorithm produces duplicate endNodes so need to trim
    ArrayList<RiipaiPatternsTreeNode> tempEndNodes = new ArrayList<RiipaiPatternsTreeNode>();
    for(int i=0; i<endNodes.size(); i++)
    {
      boolean isNodeDuplicate=false;
      for(int j=0; j<tempEndNodes.size(); j++)
      {
        if(endNodes.get(i).haiCollection.equals(tempEndNodes.get(j).haiCollection))
        {
          isNodeDuplicate=true;
        }
      }
      if(!isNodeDuplicate)
      {
        tempEndNodes.add(endNodes.get(i));
      }
    }
    endNodes=tempEndNodes;
    //loop through each end node and find one with the max inverseShantensuu (could be multiple)
    RiipaiPatternsTreeNode maxNode = endNodes.get(0);
    for(int i=1; i<endNodes.size(); i++)
    {
      if(endNodes.get(i).inverseShantensuu>maxNode.inverseShantensuu)
      {
        maxNode=endNodes.get(i);
      }
    }
    maxNodes = new ArrayList<RiipaiPatternsTreeNode>();
    for(int i=0; i<endNodes.size(); i++)
    {
      if(endNodes.get(i).inverseShantensuu==maxNode.inverseShantensuu)
      {
        maxNodes.add(endNodes.get(i));
      }
    }

    //return minimum shantensuu and print all end node haiCollection
    //System.out.println(maxNode.haiCollection.toString() + " Shantensuu is "+(8-maxNode.inverseShantensuu));
    int minS;
    int standardShantennsuu = 8-maxNode.inverseShantensuu;
    minS=standardShantennsuu;
    if(chiitoiShantensuu < minS)
    {
      minS=chiitoiShantensuu;
    }
    if(kokushiShantensuu < minS)
    {
      minS=kokushiShantensuu;
    }
    /*
    System.out.println("tehai: "+aTehai);
    System.out.println("Shantensuu: "+minS);
    if(minS==standardShantennsuu)
    {
      System.out.println("standard options for koritsuhai:");
      for(int i=0; i<maxNodes.size(); i++)
      {
        System.out.println(maxNodes.get(i).haiCollection);
      }
    }
    else
    {
      System.out.println("less shatensuu for chiitoitsu");
    }
    System.out.println("--------------------");
    */

    return minS;

  }//end of minShantensuu()

  private void minShantensuuHelper(RiipaiPatternsTreeNode n)
  {
    loopNum++;
    if(n.numberOfNonAtama==4 || (!kootsuExists(n.haiCollection) && !shuntsuExists(n.haiCollection) && !staatsuExists(n.haiCollection) && !s2taatsuExists(n.haiCollection) && !ktaatsuExists(n.haiCollection)))
    {
      endNodes.add(n);
      return;
    }
    else
    {
        for(int i=2; i<n.haiCollection.size(); i++) //kootsu check
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
              n1.numberOfKootsu=n.numberOfKootsu+1;
              n.addChild(n1);
              minShantensuuHelper(n1);
              break;
            }
          }
        }
        for(int i=2; i<n.haiCollection.size(); i++) //shuntsu check
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
              n2.numberOfShuntsu=n.numberOfShuntsu+1;
              n.addChild(n2);
              minShantensuuHelper(n2);
              break;
            }
          }
        }

        for(int i=1; i<n.haiCollection.size(); i++) //penchan or ryanmen check
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
              if(n.haiCollection.get(i).getNumber()==9 || n.haiCollection.get(i-1).getNumber()==1)//then it's penchan
              {
                n3.numberOfPenchan=n.numberOfPenchan+1;
              }
              else //then it's ryanmen
              {
                n3.numberOfRyanmen=n.numberOfRyanmen+1;
              }
              n.addChild(n3);
              minShantensuuHelper(n3);
              break;
            }
          }
        }
        for(int i=0; i<n.haiCollection.size()-1; i++) //kanchan check
        {
          if(n.haiCollection.get(i).getType().equals(n.haiCollection.get(i+1).getType()))
          {
            if(n.haiCollection.get(i).getNumber()==n.haiCollection.get(i+1).getNumber()-2)
            {
              if(!n.haiCollection.get(i).getType().equals("kaze") && !n.haiCollection.get(i).getType().equals("sangen"))
              {
                ArrayList<Hai> nMinusTaatsu = new ArrayList<Hai>();
                for(int j=0; j<n.haiCollection.size(); j++)
                {
                  if(j!=i && j!=i+1)
                  {
                    nMinusTaatsu.add(n.haiCollection.get(j));
                  }
                }
                RiipaiPatternsTreeNode n4 = new RiipaiPatternsTreeNode(nMinusTaatsu);
                n4.inverseShantensuu=n.inverseShantensuu+1;
                n4.numberOfNonAtama=n.numberOfNonAtama+1;
                n4.numberOfKanchan=n.numberOfKanchan+1;
                n.addChild(n4);
                minShantensuuHelper(n4);
                break;
              }
            }
          }
        }
        for(int i=1; i<n.haiCollection.size(); i++) //toitsu check
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
              RiipaiPatternsTreeNode n5 = new RiipaiPatternsTreeNode(nMinusTaatsu);
              n5.inverseShantensuu=n.inverseShantensuu+1;
              n5.numberOfNonAtama=n.numberOfNonAtama+1;
              n5.numberOfToitsu=n.numberOfToitsu+1;
              n.addChild(n5);
              minShantensuuHelper(n5);
              break;
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
  private boolean s2taatsuExists(ArrayList<Hai> h)
  {
    for(int i=0; i<h.size()-1; i++) //kanchan type taatsu
    {
      if(h.get(i).getType().equals(h.get(i+1).getType()))
      {
        if(h.get(i).getNumber()==h.get(i+1).getNumber()-2)
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
