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
    minShantensuu(splitTehai);
    //System.out.println("--------------------");

  } //riipai()

  public void minShantensuu(ArrayList<ArrayList<Hai>> splitHais)
  {
    RiipaiPatternsTreeNode[] rootNode = new RiipaiPatternsTreeNode[splitHais.size()];
    //we will loop through each types of tiles in splitHais and add up each InverseShantensuu (shantensuu = 8-InverseShantensuu)
    for(int i=0; i<splitHais.size(); i++)
    {
      int tempAtamaHaiNumber=-1;
      rootNode[i] = new RiipaiPatternsTreeNode(new ArrayList<Hai>());//a null root

      for(int j=1; j<splitHais.get(i).size(); j++)
      {
        //adds all possible atama patterns into first level of tree which is rootNode.children
        if(splitHais.get(i).get(j).getNumber()==splitHais.get(i).get(j-1).getNumber()) //check for atama algorithm
        {
          if(splitHais.get(i).get(j).getNumber()!=tempAtamaHaiNumber)
          {
            tempAtamaHaiNumber=splitHais.get(i).get(j).getNumber();
            ArrayList<Hai> allHaisWithoutChosenAtama = new ArrayList<Hai>();
            for(int k=0; k<splitHais.get(i).size(); k++)
            {
              if(k!=j && k!=j-1)
              {
                allHaisWithoutChosenAtama.add(splitHais.get(i).get(k)); //make a list of hais without that chosen atama
              }
            }
            rootNode[i].addChild(new RiipaiPatternsTreeNode(allHaisWithoutChosenAtama));
          }
        }
      }
      rootNode[i].addChild(new RiipaiPatternsTreeNode(splitHais.get(i))); //add a branch in atama level/first level of the tree for a pattern where there's no atama

      //for each possible combinations of atama's, add all possible kootsu patterns into next level of the tree which is rootNode.children.get(n).children
      for(int j=0; j<rootNode[i].children.size(); j++)
      {
        if(rootNode[i].children.get(j)!=null)
        {
          rootNode[i].children.get(j).InverseShantensuu++; //add 1 to InverseShantensuu to this TreeNode because it has an atama
          rootNode[i].children.get(j).numberOfAtama=1;
        }

        boolean isHaiPartOfKootsu=false;
        int kootsuCheckLoopCounter=1;
        ArrayList<ArrayList<Integer>> listOfPossibleKootsuIndexes = new ArrayList<ArrayList<Integer>>();
        while(kootsuCheckLoopCounter<rootNode[i].children.get(j).haiCollection.size()-1)
        {
          //if the hai now is the same as both before and after it's a kootsu so
          if(rootNode[i].children.get(j).haiCollection.get(kootsuCheckLoopCounter).getNumber()==rootNode[i].children.get(j).haiCollection.get(kootsuCheckLoopCounter-1).getNumber())
          {
            if(rootNode[i].children.get(j).haiCollection.get(kootsuCheckLoopCounter).getNumber()==rootNode[i].children.get(j).haiCollection.get(kootsuCheckLoopCounter+1).getNumber())
            {
              ArrayList<Integer> kootsuIndexes = new ArrayList<Integer>();
              kootsuIndexes.add(kootsuCheckLoopCounter-1);
              kootsuIndexes.add(kootsuCheckLoopCounter);
              kootsuIndexes.add(kootsuCheckLoopCounter+1);
              listOfPossibleKootsuIndexes.add(kootsuIndexes);
              isHaiPartOfKootsu=true;
            }
          }
          if(isHaiPartOfKootsu)
          {
            kootsuCheckLoopCounter+=3; //add 3 to the counter to move onto the next kootsu
          }
          else
          {
            kootsuCheckLoopCounter++; //only add 1 because this hai wasn't part of a kootsu but the next one might be
          }
        }
        ArrayList<ArrayList<Integer>> allKootsuCombinationsIndexes = new ArrayList<ArrayList<Integer>>();
        for(int k=0; k<listOfPossibleKootsuIndexes.size(); k++)
        {
          for(int x=k; x<listOfPossibleKootsuIndexes.size(); x++)
          {

            for(int l=k; l<x; l++)
            {
              allKootsuCombinationsIndexes.add(listOfPossibleKootsuIndexes.get(l));
            }
          }
        }
        ArrayList<Hai> allHaisWithoutChosenKootsuCombinations = new ArrayList<Hai>();
        for(int m=0; m<allKootsuCombinationsIndexes.size(); m++)
        {
          for(int l=0; l<rootNode[i].children.get(j).haiCollection.size(); l++)
          {
            for(int n=0; n<allKootsuCombinationsIndexes.get(m).size(); n++)
            {
              if(l!=allKootsuCombinationsIndexes.get(m).get(n))
              {
                allHaisWithoutChosenKootsuCombinations.add(rootNode[i].children.get(j).haiCollection.get(l));
              }
            }
          }
          rootNode[i].children.get(j).addChild(new RiipaiPatternsTreeNode(allHaisWithoutChosenKootsuCombinations));
        }



      }


    }

    //print out the level1 (the atama level) of tree

    for(int x=0; x<rootNode.length; x++)
    {
      //System.out.println("type: "+x);
      for(int i=0; i<rootNode[x].children.size(); i++)
      {
        for(int k=0; k<rootNode[x].children.get(i).haiCollection.size(); k++)
        {
          //System.out.println("no atama: "+rootNode[x].children.get(i).haiCollection.get(k));
        }
        for(int j=0; j<rootNode[x].children.get(i).children.size(); j++)
        {
          for(int k=0; k<rootNode[x].children.get(i).children.get(j).haiCollection.size(); k++)
          {
            //System.out.println("no kootsu nor atama: "+rootNode[x].children.get(i).children.get(j).haiCollection.get(k));
          }

          //System.out.println("level2 next branch------------------");
        }
        //System.out.println("level1 next branch------------------");
      }
    }


  }//end of minShantensuu()

  public void naki()
  {

  }

}
