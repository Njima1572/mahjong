import java.util.ArrayList;
public class RiipaiPatternsTreeNode{

    ArrayList<Hai> haiCollection; //ArrayList of hai making an atama, kootsu, shuntsu, or taatsu
    RiipaiPatternsTreeNode parent;
    ArrayList<RiipaiPatternsTreeNode> children;
    int inverseShantensuu; //where shantensuu = 8 - InverseShantensuu
    int numberOfAtama; //should be 0 or 1
    int numberOfNonAtama; //can be taatsu or mentsu
    int numberOfKootsu;
    int numberOfShuntsu;
    int numberOfKanchan;
    int numberOfPenchan;
    int numberOfRyanmen;
    int numberOfToitsu;

    public RiipaiPatternsTreeNode(ArrayList<Hai> haiCollection) {
        this.haiCollection = haiCollection;
        this.children = new ArrayList<RiipaiPatternsTreeNode>();
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

    public void addChild(RiipaiPatternsTreeNode childNode) {
        childNode.parent = this;
        this.children.add(childNode);
    }
    int countParents=0;
    public Integer countParents()
    {
      countParentsHelper(this);
      return countParents;
    }
    private void countParentsHelper(RiipaiPatternsTreeNode n)
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
