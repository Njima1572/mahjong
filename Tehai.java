import java.util.ArrayList;
public class Tehai extends HaiCollection
{
	public static class Node{

		HaiCollection remainingHais; //ArrayList of hai making an atama, kootsu, shuntsu, or taatsu
		Node parent;
		ArrayList<Node> children;
		private int inverseShantensuu; //where shantensuu = 8 - InverseShantensuu
		private HaiCollection atama;
		private Kootsu kootsu;
		private Shuntsu shuntsu;
		private Kanchan kanchan;
		private Penchan penchan; 
		private Ryanmen ryanmen; 
		private Toitsu toitsu; 
		private int numberOfNonAtamas;
		public Node(HaiCollection hc) {
			this.remainingHais = hc;
			this.children = new ArrayList<Node>();
			atama = new HaiCollection();
			numberOfNonAtamas=0;
		}
		public void addAtama(HaiCollection atama)
		{
			this.atama=atama;
		}
		public void addKootsu(HaiCollection kootsu)
		{
			this.kootsu=(Kootsu)kootsu;
		}
		public void addShuntsu(HaiCollection shuntsu)
		{
			this.shuntsu=(Shuntsu)shuntsu;
		}
		public void addKanchan(HaiCollection kanchan)
		{
			this.kanchan=(Kanchan)kanchan;
		}
		public void addPenchan(HaiCollection penchan)
		{
			this.penchan=(Penchan)penchan;
		}
		public void addRyanmen(HaiCollection ryanmen)
		{
			this.ryanmen=(Ryanmen)ryanmen;
		}
		public void addToitsu(HaiCollection toitsu)
		{
			this.toitsu=(Toitsu)toitsu;
		}
		public void addChild(Node childNode) {
			childNode.parent = this;
			this.children.add(childNode);
		}
		public Integer countParents()
		{
			numberOfNonAtamas=0;
			inverseShantensuu=0;
			int countParents=0;
			Node currentNode=this;
			numberOfNonAtamas += checkNumberOfNonAtamaForNode(currentNode);
			inverseShantensuu+=checkInverseShantensuu(currentNode);
			while(currentNode.parent!=null)
			{
				currentNode=currentNode.parent;
				numberOfNonAtamas += checkNumberOfNonAtamaForNode(currentNode);
				inverseShantensuu+=checkInverseShantensuu(currentNode);
				countParents++;
			}
			return countParents;
		}
		private int checkNumberOfNonAtamaForNode(Node n) //should return 0 or 1
		{
			if(n.kootsu!=null || n.shuntsu!=null || n.kanchan!=null || n.penchan!=null || n.ryanmen!=null || n.toitsu!=null)
				return 1;
			return 0;
		}
		private int checkInverseShantensuu(Node n)//should be 0,1, or 2
		{
			if(n.kootsu!=null || n.shuntsu!=null)
			{
				return 2;
			}
			else if(n.kanchan!=null || n.penchan!=null || n.ryanmen!=null || n.toitsu!=null)
			{
				return 1;
			}
			return 0;
		}
		public int getNumberOfNonAtama()
		{
			countParents(); 
			return numberOfNonAtamas;
		}
		public int getInverseShantensuu()
		{
			countParents();
			return inverseShantensuu;
		}
	}
	
	  Hai.Type SOUZU = Hai.Type.SOUZU;
	  Hai.Type MANZU = Hai.Type.MANZU;
	  Hai.Type PINZU = Hai.Type.PINZU;
	  Hai.Type SANGEN = Hai.Type.SANGEN;
	  Hai.Type KAZE = Hai.Type.KAZE;
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
		return null;
	}
	public ArrayList<HaiCollection> getMentsus()
	{
		return null;
	}
	public ArrayList<HaiCollection> getTaatsus()
	{
		return null;
	}


	ArrayList<Node> endNodes = new ArrayList<Node>();
	ArrayList<Node> maxNodes; //end nodes of tree with minimum shantensuu
	public int loopNum=0;
	public Integer getShantensuu() //will update maxNodes
	{
		loopNum++;
		sort();
		//kokushi form
		int kokushiShantensuu=13;
		ArrayList<Hai> yaochuu = new ArrayList<Hai>();
		boolean isFirstDuplicate=true;
		for(int i=0; i<size(); i++)
		{
			if(get(i).getNumber()==1 || get(i).getNumber()==9 || get(i).getType()==KAZE || get(i).getType()==SANGEN)
			{
				boolean yaochuuIsDuplicate=false;

				for(int j=0; j<yaochuu.size(); j++)
				{
					if(get(i).equals(yaochuu.get(j)))
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
					yaochuu.add(get(i));
					kokushiShantensuu--;
				}
			}
		}

		//chiitoitsu form
		int chiitoiShantensuu=6;
		int c=1;
		while(c < size()) //selecting atamas
		{
			if(get(c).getType().equals(get(c-1).getType()))
			{
				if(get(c).getNumber()==get(c-1).getNumber()) //if hai now and before are the same
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
		rootNode = new Node(this); //make root node with split hais as the hai collection
		ArrayList<HaiCollection> selectedAtamas = new ArrayList<HaiCollection>();
		for(int i=1; i<size(); i++) //selecting atamas
		{
			if(get(i).getType().equals(get(i-1).getType()))
			{
				if(get(i).getNumber()==get(i-1).getNumber()) //if hai now and before are the same
				{
					boolean isAtamaDuplicate=false;
					for(int j=0; j<selectedAtamas.size(); j++) //check if it's a duplicate
					{
						if(get(i)==selectedAtamas.get(j).get(0))
						{
							isAtamaDuplicate=true;
						}
					}
					if(!isAtamaDuplicate) //if the hai has not been selected as an atama before
					{
						HaiCollection atama = new HaiCollection();
						atama.add(get(i)); //add two of same tiles for atama
						atama.add(get(i));
						selectedAtamas.add(atama);
						Node l1 = new Node(this.minus(atama)); //add the tehai without the atama to the first level of the tree
						l1.addAtama(atama);
						rootNode.addChild(l1); //make new branch
					}
				}
			}
		}
		rootNode.addChild(new Node(this)); //make one branch with no atama selection
		//----------------------------------------------------

		//----------------------------------------------------
		//recursively make branches of kootsu selected left to right then shuntsu selected left to right
		//when recursion ends at each branch, store the end nodes which contains the inverseShantensuu

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
				if(endNodes.get(i).remainingHais.equals(tempEndNodes.get(j).remainingHais))
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
			if(endNodes.get(i).getInverseShantensuu()>maxNode.getInverseShantensuu())
			{
				maxNode=endNodes.get(i);
			}
		}
		maxNodes = new ArrayList<Node>();
		for(int i=0; i<endNodes.size(); i++)
		{
			if(endNodes.get(i).getInverseShantensuu()==maxNode.getInverseShantensuu())
			{
				maxNodes.add(endNodes.get(i));
			}
		}

		//return minimum shantensuu and print all end node haiCollection
		//System.out.println(maxNode.haiCollection.toString() + " Shantensuu is "+(8-maxNode.inverseShantensuu));
		int minS;
		int standardShantennsuu = 8-maxNode.getInverseShantensuu();
		minS=standardShantennsuu;
		if(chiitoiShantensuu < minS)
		{
			minS=chiitoiShantensuu;
		}
		if(kokushiShantensuu < minS)
		{
			minS=kokushiShantensuu;
		}

		System.out.println("tehai: "+hais.toString());
		System.out.println("Shantensuu: "+minS);
		if(minS==standardShantennsuu)
		{
			System.out.println("standard options for koritsuhai:");
			for(int i=0; i<maxNodes.size(); i++)
			{
				System.out.println(maxNodes.get(i).remainingHais);
			}
		}
		else
		{
			System.out.println("less shatensuu for chiitoitsu");
		}
		System.out.println("--------------------");


		return minS;

	}//end of minShantensuu()

	private void minShantensuuHelper(Node currentNode)
	{
		loopNum++;
		if(currentNode.getNumberOfNonAtama()==4 || (!kootsuExists(currentNode.remainingHais) && !shuntsuExists(currentNode.remainingHais) && !staatsuExists(currentNode.remainingHais) && !s2taatsuExists(currentNode.remainingHais) && !ktaatsuExists(currentNode.remainingHais)))
		{
			endNodes.add(currentNode);
			return;
		}
		else
		{
			for(int i=2; i<currentNode.remainingHais.size(); i++) //kootsu check
			{
				if(currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-1).getType()) && currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-2).getType()))
				{
					if(currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i-1).getNumber() && currentNode.remainingHais.get(i).getNumber() ==currentNode.remainingHais.get(i-2).getNumber())
					{
						HaiCollection kootsu = new HaiCollection();
						kootsu.add(currentNode.remainingHais.get(i));
						kootsu.add(currentNode.remainingHais.get(i));
						kootsu.add(currentNode.remainingHais.get(i));
						Node n1 =new Node(currentNode.remainingHais.minus(kootsu)); //add current node's HaiCollection minus the kootsu that was selected to this node
						currentNode.addChild(n1);
						n1.addKootsu(kootsu);
						minShantensuuHelper(n1);
						break;
					}
				}
			}
			for(int i=2; i<currentNode.remainingHais.size(); i++) //shuntsu check
			{
				if(currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-1).getType()) && currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-2).getType()))
				{
					if(currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i-1).getNumber()+1 && currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i-2).getNumber()+2)
					{
						if(currentNode.remainingHais.get(i).getType()!=KAZE && currentNode.remainingHais.get(i).getType()!=SANGEN)
						{
							HaiCollection shuntsu = new HaiCollection();
							shuntsu.add(currentNode.remainingHais.get(i));
							shuntsu.add(currentNode.remainingHais.get(i-1));
							shuntsu.add(currentNode.remainingHais.get(i-2));
							Node n2 = new Node(currentNode.remainingHais.minus(shuntsu));
							n2.addShuntsu(shuntsu);
							currentNode.addChild(n2);
							minShantensuuHelper(n2);
							break;
						}
					}
				}
			}

			for(int i=1; i<currentNode.remainingHais.size(); i++) //penchan or ryanmen check
			{
				if(currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-1).getType()))
				{
					if(currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i-1).getNumber()+1)
					{
						if(currentNode.remainingHais.get(i).getType()!=KAZE && currentNode.remainingHais.get(i).getType()!=SANGEN)
						{
							HaiCollection taatsu = new HaiCollection();
							taatsu.add(currentNode.remainingHais.get(i));
							taatsu.add(currentNode.remainingHais.get(i-1));
							Node n3 = new Node(currentNode.remainingHais.minus(taatsu));
							if(currentNode.remainingHais.get(i).getNumber()==9 || currentNode.remainingHais.get(i-1).getNumber()==1)//then it's penchan
							{
								n3.addPenchan(taatsu);
							}
							else //then it's ryanmen
							{
								n3.addRyanmen(taatsu);
							}
							currentNode.addChild(n3);
							minShantensuuHelper(n3);
							break;
						}
					}
				}
			}
			for(int i=0; i<currentNode.remainingHais.size()-1; i++) //kanchan check
			{
				if(currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i+1).getType()))
				{
					if(currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i+1).getNumber()-2)
					{
						if(currentNode.remainingHais.get(i).getType()!=KAZE && currentNode.remainingHais.get(i).getType()!=SANGEN)
						{
							HaiCollection taatsu = new HaiCollection();
							taatsu.add(currentNode.remainingHais.get(i));
							taatsu.add(currentNode.remainingHais.get(i+1));
							Node n4 = new Node(currentNode.remainingHais.minus(taatsu));
							n4.addKanchan(taatsu);
							currentNode.addChild(n4);
							minShantensuuHelper(n4);
							break;
						}
					}
				}
			}
			for(int i=1; i<currentNode.remainingHais.size(); i++) //toitsu check
			{
				if(currentNode.remainingHais.get(i).getType().equals(currentNode.remainingHais.get(i-1).getType()))
				{
					if(currentNode.remainingHais.get(i).getNumber()==currentNode.remainingHais.get(i-1).getNumber())
					{
						HaiCollection taatsu = new HaiCollection();
						taatsu.add(currentNode.remainingHais.get(i));
						taatsu.add(currentNode.remainingHais.get(i-1));
						Node n5 = new Node(currentNode.remainingHais.minus(taatsu));
						n5.addToitsu(taatsu);
						currentNode.addChild(n5);
						minShantensuuHelper(n5);
						break;
					}
				}
			}

		}
	}

	private boolean kootsuExists(HaiCollection h)
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
	private boolean shuntsuExists(HaiCollection h)
	{
		for(int i=2; i<h.size(); i++) //select shuntsu
		{
			if(h.get(i).getType().equals(h.get(i-1).getType()) && h.get(i).getType().equals(h.get(i-2).getType()))
			{
				if(h.get(i).getNumber()==h.get(i-1).getNumber()+1 && h.get(i).getNumber()==h.get(i-2).getNumber()+2)
				{
					if(h.get(i).getType()!=KAZE && h.get(i).getType()!=SANGEN)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean staatsuExists(HaiCollection h)
	{
		for(int i=1; i<h.size(); i++) //shuntsu type taatsu
		{
			if(h.get(i).getType().equals(h.get(i-1).getType()))
			{
				if(h.get(i).getNumber()==h.get(i-1).getNumber()+1)
				{
					if(h.get(i).getType()!=KAZE && h.get(i).getType()!=SANGEN)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean s2taatsuExists(HaiCollection h)
	{
		for(int i=0; i<h.size()-1; i++) //kanchan type taatsu
		{
			if(h.get(i).getType().equals(h.get(i+1).getType()))
			{
				if(h.get(i).getNumber()==h.get(i+1).getNumber()-2)
				{
					if(h.get(i).getType()!=KAZE && h.get(i).getType()!=SANGEN)
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	private boolean ktaatsuExists(HaiCollection h)
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
