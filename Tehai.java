import java.util.ArrayList;
public class Tehai extends HaiCollection
{
  public static class Node{

      ArrayList<Hai> haiCollection; //ArrayList of hai making an atama, kootsu, shuntsu, or taatsu
      Node parent;
      ArrayList<Node> children;
      int inverseShantensuu; //where shantensuu = 8 - InverseShantensuu
      int numberOfAtama; //should be 0 or 1
      int numberOfNonAtama; //can be taatsu or mentsu
      int numberOfKootsu;
      int numberOfShuntsu;
      int numberOfKanchan;
      int numberOfPenchan;
      int numberOfRyanmen;
      int numberOfToitsu;

      public Node(ArrayList<Hai> haiCollection) {
          this.haiCollection = haiCollection;
          this.children = new ArrayList<Node>();
          inverseShantensuu=0;
          numberOfAtama=0;
          numberOfNonAtama=0;
          numberOfKootsu=0;
          numberOfShuntsu=0;
          numberOfKanchan=0;
          numberOfPenchan=0;
          numberOfRyanmen=0;
          numberOfToitsu=0;
      }

      public void addChild(Node childNode) {
          childNode.parent = this;
          this.children.add(childNode);
      }
      public Integer countParents()
      {
        int countParents=0;
        Node currentNode=this;
        while(currentNode.parent!=null)
        {
          currentNode=currentNode.parent;
          countParents++;
        }
        return countParents;
      }
      private void countParentsHelper(Node n)
      {
        if(n.parent!=null)
        {
          return;
        }
        else
        {
          countParentsHelper(n.parent);
          countParents++;
        }
      }

  }
  private HaiCollection hais;
  public Tehai(HaiCollection hc)
  {
    hais=hc;
  }
  public Tehai()
  {
    hais=new HaiCollection();
  }

  public HaiCollection getAtama()
  {

  }
  public ArrayList<HaiCollection> getMentsus()
  {

  }
  public ArrayList<HaiCollection> getTaatsus()
  {

  }


  ArrayList<Node> endNodes = new ArrayList<Node>();
  ArrayList<Node> maxNodes; //end nodes of tree with minimum shantensuu
  public int loopNum=0;
  public Integer getShantensuu() //will update maxNodes
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
    Node rootNode;
    //----------------------------------------------------
    //make level 1 cases: atama selection
    rootNode = new Node(aTehai); //make root node with split hais as the hai collection
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
            Node l1 = new Node(tehaiWithoutAtama);
            l1.inverseShantensuu=1;
            rootNode.addChild(l1); //make new branch
          }
        }
      }
    }
    rootNode.addChild(new Node(aTehai)); //make one branch with no atama selection
    //----------------------------------------------------

    //----------------------------------------------------
    //recursively make branches of kootsu selected left to right then shuntsu selected left to right
    //when recursion ends at each branch, store the end nodes which constains the inverseShantensuu

    for(int i=0; i<rootNode.children.size(); i++)
    {
      minShantensuuHelper(rootNode.children.get(i));
    }

    //algorithm produces duplicate endNodes so need to trim
    ArrayList<Node> tempEndNodes = new ArrayList<Node>();
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
    Node maxNode = endNodes.get(0);
    for(int i=1; i<endNodes.size(); i++)
    {
      if(endNodes.get(i).inverseShantensuu>maxNode.inverseShantensuu)
      {
        maxNode=endNodes.get(i);
      }
    }
    maxNodes = new ArrayList<Node>();
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

private void minShantensuuHelper(Node n)
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
          Node n1 =new Node(nMinusKootsu);
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
          Node n2 = new Node(nMinusShuntsu);
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
          Node n3 = new Node(nMinusTaatsu);
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
            Node n4 = new Node(nMinusTaatsu);
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
          Node n5 = new Node(nMinusTaatsu);
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

}
