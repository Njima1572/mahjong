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


    System.out.println("--------------------");
    minShantensuu(splitTehai);
    System.out.println("--------------------");

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
        //System.out.println("j: "+j+"size: "+(rootNode[i].children.get(j).haiCollection.size()-1));
        while(kootsuCheckLoopCounter<rootNode[i].children.get(j).haiCollection.size()-1)
        {
          isHaiPartOfKootsu=false;
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
              //System.out.println("this kootsu indexes got added to list of PossibleKootsuIndexes: ");
              //System.out.println(kootsuIndexes);
              isHaiPartOfKootsu=true;
            }
          }
          if(isHaiPartOfKootsu)
          {
            //System.out.println("plus 3");
            kootsuCheckLoopCounter=kootsuCheckLoopCounter+3; //add 3 to the counter to move onto the next kootsu
          }
          else
          {
            //System.out.println("just plus 1");
            kootsuCheckLoopCounter++; //only add 1 because this hai wasn't part of a kootsu but the next one might be
          }
        }
        ArrayList<Integer> allKootsuCombinationsIndexes = new ArrayList<Integer>();
        for(int k=0; k<listOfPossibleKootsuIndexes.size(); k++)
        {
          for(int x=k; x<listOfPossibleKootsuIndexes.size(); x++)
          {

            for(int l=k; l<=x; l++)
            {
              for(int m=0; m<listOfPossibleKootsuIndexes.get(l).size(); m++)
              {
                allKootsuCombinationsIndexes.add(listOfPossibleKootsuIndexes.get(l).get(m));
              }
              //System.out.println("this kootsu indexes got added: ");
              //System.out.println(listOfPossibleKootsuIndexes.get(l));

            }
          }
        }
        ArrayList<Hai> allHaisWithoutChosenKootsuCombinations = new ArrayList<Hai>();
        for(int l=0; l<rootNode[i].children.get(j).haiCollection.size(); l++)
        {
          boolean isTileAvailable=true;
          for(int m=0; m<allKootsuCombinationsIndexes.size(); m++)
          {
            if(l==allKootsuCombinationsIndexes.get(m))
            {
              isTileAvailable=false;
            }
          }
          if(isTileAvailable)
          {
            allHaisWithoutChosenKootsuCombinations.add(rootNode[i].children.get(j).haiCollection.get(l));
            //System.out.println("this hai was added to the all hais without kootsu combo list"+rootNode[i].children.get(j).haiCollection.get(l));
          }
        }
        rootNode[i].children.get(j).addChild(new RiipaiPatternsTreeNode(allHaisWithoutChosenKootsuCombinations));
        rootNode[i].children.get(j).addChild(new RiipaiPatternsTreeNode(splitHais.get(i))); //add a branch in kootsu level/second level of the tree for a pattern where there's no kootsu

        //for each possible combinations of kootsu's, add all possible shuntsu patterns into next level of the tree which is rootNode.children.get(n).children.get(n).children
        for(int k=0; k<rootNode[i].children.get(j).children.size(); k++)
        {
          if(rootNode[i].children.get(j).children.get(k)!=null)
          {
            rootNode[i].children.get(j).children.get(k).InverseShantensuu+=2; //add 2 to InverseShantensuu to this TreeNode because it has a kootsu
            rootNode[i].children.get(j).children.get(k).numberOfKootsu=allKootsuCombinationsIndexes.size()/3;
          }

          //jihais shouldn't be shuntsu so
          if(i!=4 && i!=3)
          {
            //listing all possible shuntsus from the available hais
            ArrayList<ArrayList<Integer>> haiNumberAndIndex = new ArrayList<ArrayList<Integer>>(); //[n][0] is how many hais with n+1 number exists and [n][N] is what the hai indexes are
            for(int nine=0; nine<9; nine++)
            {
              ArrayList<Integer> temp = new ArrayList<Integer>();
              temp.add(0);
              haiNumberAndIndex.add(temp);
            }
            ArrayList<ArrayList<Integer>> listOfPossibleShuntsuIndexes = new ArrayList<ArrayList<Integer>>();
            for(int x=0; x<rootNode[i].children.get(j).children.get(k).haiCollection.size(); x++)
            {
              int numberOfHais = haiNumberAndIndex.get(rootNode[i].children.get(j).children.get(k).haiCollection.get(x).getNumber()-1).get(0);
              haiNumberAndIndex.get(rootNode[i].children.get(j).children.get(k).haiCollection.get(x).getNumber()-1).set(0,numberOfHais+1); //up the count for the hai number
              haiNumberAndIndex.get(rootNode[i].children.get(j).children.get(k).haiCollection.get(x).getNumber()-1).get(0).add(x); //store the index of the hai recorded
            }
            boolean isHaiPartOfShuntsu=false;
            int shuntsuCheckLoopCounter=1;
            while(shuntsuCheckLoopCounter<9-1) //loop through 9 numbers minus one (so the last loop+1 can be checked)
            {
              //System.out.println("type: "+i+" curr idx-1: "+rootNode[i].children.get(j).children.get(k).haiCollection.get(shuntsuCheckLoopCounter-1).getNumber()+" curr idx: "+rootNode[i].children.get(j).children.get(k).haiCollection.get(shuntsuCheckLoopCounter).getNumber()+" curr idx+1: "+rootNode[i].children.get(j).children.get(k).haiCollection.get(shuntsuCheckLoopCounter+1).getNumber());
              isHaiPartOfShuntsu=false;

              //if the hai now is +1 of before and -1 of next its a shuntsu so
              if(haiNumberAndIndex.get(shuntsuCheckLoopCounter-1).get(0)>0 && haiNumberAndIndex.get(shuntsuCheckLoopCounter).get(0)>0)
              {
                if(haiNumberAndIndex.get(shuntsuCheckLoopCounter+1).get(0)>0 && haiNumberAndIndex.get(shuntsuCheckLoopCounter).get(0)>0)
                {
                  int minHaiNumber=9;
                  for(int minLoop=-1; minLoop<2; minLoop++)
                  {
                    if(haiNumberAndIndex.get(shuntsuCheckLoopCounter+minLoop).get(0)<minHaiNumber)
                    {
                      minHaiNumber=haiNumberAndIndex.get(shuntsuCheckLoopCounter+minLoop).get(0);
                    }
                  }

                }
              }

              /*
              ArrayList<Integer> shuntsuIndexes = new ArrayList<Integer>();
              shuntsuIndexes.add(shuntsuCheckLoopCounter-1);
              shuntsuIndexes.add(shuntsuCheckLoopCounter);
              shuntsuIndexes.add(shuntsuCheckLoopCounter+1);
              listOfPossibleShuntsuIndexes.add(shuntsuIndexes);
              System.out.println("this shuntsu indexes got added to list of PossibleKootsuIndexes: ");
              System.out.println(shuntsuIndexes);
              isHaiPartOfShuntsu=true;
              */

              if(isHaiPartOfShuntsu)
              {
                System.out.println("plus 3");
                shuntsuCheckLoopCounter=shuntsuCheckLoopCounter+3; //add 3 to the counter to move onto the next kootsu
              }
              else
              {
                System.out.println("just plus 1");
                shuntsuCheckLoopCounter++; //only add 1 because this hai wasn't part of a kootsu but the next one might be
              }
            }

            ArrayList<Integer> allShuntsuCombinationsIndexes = new ArrayList<Integer>();
            for(int n=0; n<listOfPossibleShuntsuIndexes.size(); n++)
            {
              for(int x=n; x<listOfPossibleShuntsuIndexes.size(); x++)
              {

                for(int l=n; l<=x; l++)
                {
                  for(int m=0; m<listOfPossibleShuntsuIndexes.get(l).size(); m++)
                  {
                    allShuntsuCombinationsIndexes.add(listOfPossibleShuntsuIndexes.get(l).get(m));
                  }

                }
              }
            }

            ArrayList<Hai> allHaisWithoutChosenShuntsuCombinations = new ArrayList<Hai>();
            for(int l=0; l<rootNode[i].children.get(j).children.get(k).haiCollection.size(); l++)
            {
              boolean isTileAvailable=true;
              for(int m=0; m<allShuntsuCombinationsIndexes.size(); m++)
              {
                if(l==allShuntsuCombinationsIndexes.get(m))
                {
                  isTileAvailable=false;
                }
              }
              if(isTileAvailable)
              {
                allHaisWithoutChosenShuntsuCombinations.add(rootNode[i].children.get(j).children.get(k).haiCollection.get(l));
                //System.out.println("this hai was added to the all hais without kootsu combo list"+rootNode[i].children.get(j).haiCollection.get(l));
              }
            }
            rootNode[i].children.get(j).children.get(k).addChild(new RiipaiPatternsTreeNode(allHaisWithoutChosenShuntsuCombinations));
            if(j==rootNode[i].children.size()-1 && k==rootNode[i].children.get(j).children.size()-1)
            {
              rootNode[i].children.get(j).children.get(k).addChild(new RiipaiPatternsTreeNode(rootNode[i].children.get(j).haiCollection)); //add a branch in shuntsu level/third level of the tree for a pattern where there's no shuntsu
            }
          }
        }
      }
    }

    //print out the level1 (the atama level) of tree

    for(int x=0; x<rootNode.length; x++) //type loop
    {
      System.out.println("type: "+x);
      System.out.println("");
      System.out.println("");
      for(int i=0; i<rootNode[x].children.size(); i++) //level 1 loop
      {
        System.out.println("");
        System.out.println("level1");
        System.out.println("minus atama: "+rootNode[x].children.get(i).haiCollection);
        for(int j=0; j<rootNode[x].children.get(i).children.size(); j++) //level 2 loop
        {
          System.out.println("");
          System.out.println("level2");
          System.out.println("minus kootsu: "+rootNode[x].children.get(i).children.get(j).haiCollection);
          for(int k=0; k<rootNode[x].children.get(i).children.get(j).children.size(); k++) //level 2 loop
          {
            System.out.println("");
            System.out.println("level3");
            System.out.println("minus shuntsu: "+rootNode[x].children.get(i).children.get(j).children.get(k).haiCollection);
          }
        }
      }
    }


  }//end of minShantensuu()

  public void naki()
  {

  }

}
