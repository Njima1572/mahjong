import java.util.ArrayList;


public class Table{

  ArrayList<OTile> tiles;
  ArrayList<OTile> mountain;
  public Table(){
    tiles = new ArrayList<OTile>();

  }
  public void init(ArrayList<OTile> tiles){
    for(int j = 0; j < 4; j++){
      for(int i = 1; i < 10; i++){
        OTile s = new OTile(i, 's');
        OTile m = new OTile(i, 'm');
        OTile p = new OTile(i, 'p');
        tiles.add(s);
        tiles.add(m);
        tiles.add(p);
      }
      OTile N = new OTile(0, 'N');
      OTile S = new OTile(0, 'S');
      OTile W = new OTile(0, 'W');
      OTile E = new OTile(0, 'E');
      OTile r = new OTile(0, 'r');
      OTile w = new OTile(0, 'w');
      OTile g = new OTile(0, 'g');
      tiles.add(N);
      tiles.add(S);
      tiles.add(W);
      tiles.add(E);
      tiles.add(r);
      tiles.add(w);
      tiles.add(g);
    }
  }

  //public ArrayList<OTiles> makeMountain(){

  //}

  // public void shuffleMountain(){
  //
  // }

  public static void main(String[] args){
    ArrayList<OTile> tilesMain = new ArrayList<OTile>();
    Table t = new Table();
    t.init(tilesMain);
    for(int i = 0; i < tilesMain.size(); i++)
      System.out.print(" " + tilesMain.get(i) + " ");
  }

}
