import java.util.*;

public class Table{

  public ArrayList<Hai> tiles;
  public Stack<Hai> mountain; //mountain should be stack so we can just use a destructive .pop() to tsumo a Hai
  public Stack<Hai> wanpai;
  private Player[] players;
  public int turn;
  public Hai doraHyouji;
  public Hai justDiscarded;
  public Hai dora;

  Hai.Type SOUZU = Hai.Type.SOUZU;
  Hai.Type MANZU = Hai.Type.MANZU;
  Hai.Type PINZU = Hai.Type.PINZU;
  Hai.Type SANGEN = Hai.Type.SANGEN;
  Hai.Type KAZE = Hai.Type.KAZE;


  public Table(Player[] p){
    players = p;
    tiles = new ArrayList<Hai>();
    turn = 0;
  }

  /**
    *
    *@param tiles - all 136 tiles stored in here.
    */
  public void init(boolean isDesiredTehai){
    ArrayList<Hai> tiles = new ArrayList<Hai>();
    for(int j = 0; j < 4; j++){
      for(int i = 1; i < 10; i++){
        tiles.add(new Hai(i, SOUZU));//Souzu
        tiles.add(new Hai(i, MANZU));//Manzu
        tiles.add(new Hai(i, PINZU));//Pinzu
      }
      /** 0 - kaze = East
        * 1 - kaze = South
        * 2 - kaze = West
        * 3 - kaze = North

        * 0 - sangen = White
        * 1 - sangen = Green
        * 2 - sangen = Red
        */
      for(int i = 0; i < 4; i++){
        tiles.add(new Hai(i, KAZE));
      }
      for(int i = 0; i < 3; i++){
        tiles.add(new Hai(i, SANGEN));
      }
    }

    mountain = this.makeMountain(tiles);

    wanpai = this.makeWanpai(mountain);

    // int left = countLeft(mountain);
    // System.out.println(left);

    deal(players, mountain);//deal to this list of players
    if(isDesiredTehai){
      for(int i = 0; i < 4; i++){
        for(int j = 0; j < 3; j++){
          Hai desired = new Hai(i, MANZU);
          players[0].tehai[i * 3 + j] = desired;
        }
      }
      Hai desired2 = new Hai(5, MANZU);
      players[0].tehai[13] = desired2;
    }
    doraHyouji = dora(wanpai);
    if(doraHyouji.getNumber() == 9){
      dora = new Hai(1, doraHyouji.getType());
    }else if(doraHyouji.getType() == KAZE){
      dora = new Hai((doraHyouji.getNumber() + 1) % 4, doraHyouji.getType());
    }else if(doraHyouji.getType() == SANGEN){
      dora = new Hai((doraHyouji.getNumber() + 1) % 3, doraHyouji.getType());
    }else{
      dora = new Hai(doraHyouji.getNumber() + 1, doraHyouji.getType());
    }

    System.out.println("The dora hyouji is: " + doraHyouji + ", dora is: " + dora);
    System.out.println("----------------------------------");

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

  private void deal(Player[] dealplayers, Stack<Hai> mountain){
    for(int j = 0; j < 4; j++){
      for(int i = 0; i < 13; i++){
        dealplayers[j].tehai[i] = mountain.pop();
      }
    }
  }

  private int countLeft(Stack<Hai> mountain){
    int counter = 0;
    Stack<Hai> temp = new Stack<Hai>();
    while(!mountain.empty()){
      counter++;
      temp.push(mountain.pop());
    }
    mountain = temp;

    return counter;
  }

  public Hai dora(Stack<Hai> wanpai){
    Hai doraHyouji;
    doraHyouji = wanpai.pop();
    return doraHyouji;
  }


//   public static void main(String[] args){ //To see if table is working.
//     ArrayList<Hai> tilesMain = new ArrayList<Hai>();
//     Stack<Hai> mountain = new Stack<Hai>();
//     Table t = new Table();
//     t.init();
//     while(!t.mountain.empty()){
//       System.out.print(" " + t.mountain.pop() + " ");
//     }
//   }
}
