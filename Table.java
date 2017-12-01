import java.util.ArrayList;


public class Table{

  ArrayList<OTile> tiles;
  ArrayList<OTile> mountain; //mountain should be stack so we can just use a destructive .pop() to tsumo a tile
  public Table(){
    tiles = new ArrayList<OTile>();

  }

  /**
    *
    *
    */
  public void init(ArrayList<OTile> tiles){
    for(int j = 0; j < 4; j++){
      for(int i = 1; i < 10; i++){
        tiles.add(new OTile(i, 's'));
        tiles.add(new OTile(i, 'm'));
        tiles.add(new OTile(i, 'p'));
      }
      tiles.add(new OTile(0, 'N'));
      tiles.add(new OTile(0, 'S'));
      tiles.add(new OTile(0, 'W'));
      tiles.add(new OTile(0, 'E'));
      tiles.add(new OTile(0, 'r'));
      tiles.add(new OTile(0, 'w'));
      tiles.add(new OTile(0, 'g'));
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
