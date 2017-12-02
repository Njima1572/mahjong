public class ShantenCount{

  int kotsuCount;
  int tatsuCount;
  int shuntsuCount;
  boolean headCheck;
  public ShantenCount(){

  }

  /**This function just checks Jihais which means only Tatsu and Kotsu
    *
    *
    */

  public JihaiCheck(){

    for(int i = 0; i < hand.size(); i++){
      for(int j = hand.size(); j > i; j--){
        if(hand[i].equals(hand[j])){
          for(int k = i + 1; k < j - i; k++){
              if(hand[i].equals(hand[k])){
                kotsuCount++;
                break
              }
              headCheck = true;
              break
          }
        }
      }
    }
  }

  public KazuhaiCheck(){
    for(int i = 0; i < hand.size(); i++){
      for(int j = hand.size(); j > i; j--){
        if(Math.abs(hand[i] - hand[j]) < 2){
          for(int k = i + 1; k < j - i; k++){

          }
        }
      }
    }

  }


}
