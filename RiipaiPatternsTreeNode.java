import java.util.ArrayList;
public class RiipaiPatternsTreeNode{

    ArrayList<Hai> haiCollection; //ArrayList of hai making an atama, kootsu, shuntsu, or taatsu
    RiipaiPatternsTreeNode parent;
    ArrayList<RiipaiPatternsTreeNode> children;
    int InverseShantensuu; //where shantensuu = 8 - InverseShantensuu
    int numberOfAtama; //should be 0 or 1
    int numberOfKootsu;
    int numberOfShuntsu;
    int numberOfTaatsu;

    public RiipaiPatternsTreeNode(ArrayList<Hai> haiCollection) {
        this.haiCollection = haiCollection;
        this.children = new ArrayList<RiipaiPatternsTreeNode>();
        InverseShantensuu=0;
        numberOfAtama=0;
        numberOfKootsu=0;
        numberOfShuntsu=0;
        numberOfTaatsu=0;
    }

    public void addChild(RiipaiPatternsTreeNode childNode) {
        childNode.parent = this;
        this.children.add(childNode);
    }

    // other features ...

}
