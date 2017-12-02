public class Yaku{

  int shanten;
  int sum;

  public Yaku(){

  }

  public int checkShanten(Hai[] hand){
    int shanten = 8;

    shanten -= headCheck(hand);
    // shanten -= tatsuCheck(hand);
    shanten -= shuntsuCheck(hand);
    shanten -= kotsuCheck(hand);

    return shanten;
  }


  public int headCheck(Hai[] hand){
    for(int i = 0; i < hand.length; i++){
      for(int j = 13; j > i; j--){
        if(hand[i] == hand[j]){
          return 1;
        }
      }
    }
    return 0;
  }

  public int tatsuCheck(Hai[] hand){
    int sum = 0;
    for(int i = 0; i < hand.length; i++){
      for(int j = hand.length; j > i; j--){
        if(sum <= 4){
          if(typeCheck(hand[i], hand[j])){
            if(Math.abs(hand[i].getNumber() + 2) > hand[j].getNumber() || Math.abs(hand[i].getNumber() + 2) > hand[j].getNumber()){
              sum++;
            }else if(hand[i].getNumber() == hand[j].getNumber()){
              sum++;
            }
          }
        }
      }
    }
    return sum;
  }

  public int shuntsuCheck(Hai[] hand){
    int sum = 0;
    for(int i = 0; i < hand.length; i++){
      for(int j = 13; j > i; j--){
        if(typeCheck(hand[i], hand[j])){
          if(Math.abs(hand[i].getNumber() + 1) == hand[j].getNumber() || Math.abs(hand[i].getNumber() - 1) == hand[j].getNumber()){
            for(int k = i; k < j; k++){
              if((i < j && hand[i].getNumber()-1 == hand[k].getNumber()) ||( i > j && hand[i].getNumber()+1 == hand[k].getNumber()) ){
                sum += 2;
              }
            }
          }
        }
      }
    }
    return sum;

  }


  public int kotsuCheck(Hai[] hand){
    int sum = 0;
    for(int i = 0; i < hand.length; i++){
      for(int j = 13; j > i; j--){
        if(typeCheck(hand[i], hand[j])){
          if(hand[i].getNumber() == hand[j].getNumber()){
            for(int k = i + 1; k < j; k++){
              if(typeCheck(hand[i], hand[k])){
                if(hand[i].getNumber() == hand[k].getNumber()){
                  sum += 2;
                }
              }
            }
          }
        }
      }
    }
    return sum;
  }


  public boolean typeCheck(Hai h1, Hai h2){
    return h1.getType() == h2.getType();
  }

  public static void main(String[] args){
    Hai[] test = new Hai[14];
    test
  }

}











public int shantenCheck(ArrayList<ArrayList<Hai>> splitTehai){
  int atama = 0;
  int kootsu = 0;
  int taatsu = 0;
  int shuntsu = 0;
  int sum;
  for(int i = 0; i < 5; i++){
    for(int j = 0; j < splitTehai.get(i).size() - 2; j++){

      if(splitTehai.get(i).get(j).getNumber() == splitTehai.get(i).get(j + 1).getNumber()){
        if(splitTehai.get(i).get(j).getNumber() == splitTehai.get(i).get(j + 2).getNumber()){
          splitTehai.get(i).remove(j);
          splitTehai.get(i).remove(j + 1);
          splitTehai.get(i).remove(j + 2);
          kootsu += 1;
          j += 2;
          break;
        }
        splitTehai.get(i).remove(j);
        splitTehai.get(i).remove(j + 1);
        j += 1;
        if(atama < 1){
          atama += 1;
          break;
        }else{
          taatsu += 1;
          break;
        }
      }else if(splitTehai.get(i).get(j).getNumber() + 1 == splitTehai.get(i).get(j + 1).getNumber()){
        if(splitTehai.get(i).get(j).getNumber() + 2 == splitTehai.get(i).get(j + 2).getNumber()){
          splitTehai.get(i).remove(j);
          splitTehai.get(i).remove(j + 1);
          splitTehai.get(i).remove(j + 2);
          shuntsu += 1;
          j += 2;
          break;
        }
        splitTehai.get(i).remove(j);
        splitTehai.get(i).remove(j + 1);
        taatsu += 1;
        break;
      }
    }
  }
  sum = 8 - atama + kootsu * 2 + taatsu + shuntsu * 2;
  return sum;
}
