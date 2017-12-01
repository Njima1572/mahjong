import java.util.*;

public class Table{

  ArrayList<Hai> tiles;
  Stack<Hai> mountain; //mountain should be stack so we can just use a destructive .pop() to tsumo a Hai
  Stack<Hai> wanpai;

  public Table(){
    tiles = new ArrayList<Hai>();

  }

  /**
    *
    *@param tiles - all 136 tiles stored in here.
    */
  public void init(ArrayList<Hai> tiles){
    for(int j = 0; j < 4; j++){
      for(int i = 1; i < 10; i++){
        tiles.add(new Hai(i, "souzu"));//索子
        tiles.add(new Hai(i, "manzu"));//萬子
        tiles.add(new Hai(i, "pinzu"));//筒子
      }
      for(int i = 0; i < 7; i++){
        tiles.add(new Hai(0, "kaze"));//東
        tiles.add(new Hai(1, "kaze"));//南
        tiles.add(new Hai(2, "kaze"));//西
        tiles.add(new Hai(3, "kaze"));//北
        }
      for(int i = 0; i < 3; i++){
        tiles.add(new Hai(0, "sangen"));//白
        tiles.add(new Hai(1, "sangen"));//發
        tiles.add(new Hai(2, "sangen"));//中
      }
    }

    mountain = this.makeMountain(tiles);

    wanpai = this.makeWanpai(mountain);

  }

  public Stack<Hai> makeMountain(ArrayList<Hai> tiles){
    Stack<Hai> mountain = new Stack<Hai>();
    Collections.shuffle(tiles);
    for(int i = 0; i < tiles.size(); i++){
      mountain.push(tiles.get(i));
    }
    return mountain;
  }

  public Stack<Hai> makeWanpai(Stack<Hai> mountain){
    Stack<Hai> wanpai = new Stack<Hai>();
    for(int i = 0; i < 14; i++){
      wanpai.push(mountain.pop());
    }
    return wanpai;
  }


  // public static void main(String[] args){
  //   ArrayList<Hai> tilesMain = new ArrayList<Hai>();
  //   Stack<Hai> mountain = new Stack<Hai>();
  //   Table t = new Table();
  //   t.init(tilesMain);
  //   for(int i = 0; i < 122; i++)
  //     System.out.print(" " + t.mountain.pop() + " ");
  // }

}
