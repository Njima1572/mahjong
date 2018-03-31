import java.util.*;
public class HaiCollection
{
  private ArrayList<Hai> hais;
  private ArrayList<HaiCollection> typedHaiCollections;
  public HaiCollection(ArrayList<Hai> h)
  {
    hais=h;
    
  }
  public HaiCollection()
  {
    hais=new ArrayList<Hai>();
  }
  public HaiCollection(HaiCollection hc)
  {
	  hais=hc.getHais();
  }

  /**
  * Sets tile at an index
  * @param i index
  * @param h tile to be set
  */
  public void set(int i, Hai h)
  {
    hais.set(i,h);
  }
  /**
  * Adds tile to collection
  * @param h tile to be added
  */
  public void add(Hai h)
  {
    hais.add(h);
  }
  /**
  * Gets tile at an index
  * @param i index
  */
  public Hai get(int i)
  {
    return hais.get(i);
  }
  /**
  * Gets how many tiles exist in this collection
  */
  public int size()
  {
    return hais.size();
  }
  /**
  * Gets tiles in collection as an ArrayList
  */
  public ArrayList<Hai> getHais()
  {
    this.sort();
    return hais;
  }

  /**
  * Gets HaiCollection with only one specified Hai type.
  * @param t The Hai type
  */
  public HaiCollection getTypedHaiCollection(Hai.Type t)
  {
    HaiCollection typedHaiCollection=new HaiCollection();
    for(int i=0; i<this.size(); i++)
    {
      if(this.get(i).Type==t)
      {
    	  typedHaiCollection.add(this.get(i));
      }
    }
    return typedHaiCollection;
  }

  /**
  * Converts this object into a readable string.
  *
  */
  public String toString()
  {
    String output=null;
    for(int i=0; i<this.size(); i++)
    {
      if(i!=this.size()-1)
        output+=this.get(i).toString()+", ";
      else
        output+=this.get(i).toString();
    }
    return output;
  }
  
  public boolean equals(HaiCollection other)
  {
	  if(other.getHais().equals(this.getHais()))
	  {
		  return true;
	  }
	  return false;
  }

  /**
  * Subtracts tiles in other from this HaiCollection. Will not sort the combined HaiCollection.
  * @param other The other HaiCollection
  */
  public HaiCollection minus(HaiCollection other)
  {
    if(this.size()>=other.size())
    {
      HaiCollection temphc = new HaiCollection();
      for(int i=0; i<this.size(); i++)
      {
        for(int j=0; j<other.size(); j++)
        {
          if(this.get(i).equals(other.get(j)))
          {
            break;
          }
          if(j==other.size()-1)
          {
            temphc.add(this.get(i));
          }
        }
      }
      this=temphc;
      return this;
    }
    else
    {
      throw new IllegalArgumentException("Cannot subtract a larger HaiCollection from a smaller HaiCollection.");
    }
  }
  /**
  * Adds tiles in other from this HaiCollection. Will not sort the combined HaiCollection.
  * @param other The other HaiCollection
  */
  public HaiCollection plus(HaiCollection other)
  {
    for(int i=0; i<other.size(); i++)
    {
      this.add(other.get(i));
    }
    return this;
  }


  /**
  * Sorts the hai collection in the order of manzu, pinzu, souzu, kaze, sangen in ascending order. AKA "riipai"
  */
  public HaiCollection sort()
  {
    ArrayList<HaiCollection> typedhcs = new ArrayList<HaiCollection>();
    typedhcs.add(this.getTypedHaiCollection(Hai.Type.MANZU));
    typedhcs.add(this.getTypedHaiCollection(Hai.Type.PINZU));
    typedhcs.add(this.getTypedHaiCollection(Hai.Type.SOUZU));
    typedhcs.add(this.getTypedHaiCollection(Hai.Type.KAZE));
    typedhcs.add(this.getTypedHaiCollection(Hai.Type.SANGEN));
    if(typedhcs.size()==1)
    {
      Collections.sort(this.hais);
      return this;
    }
    else
    {
      HaiCollection combinedhc = new HaiCollection();
      for(int i=0; i<typedhcs.size(); i++)
      {
        combinedhc.plus(typedhcs.get(i).sort());
      }
      this.hais=combinedhc.getHais();
      return this;
    }
  }

}
