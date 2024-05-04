package swesencryption;

import java.util.function.Function;

public class CharacterChainLink {
  private char thisChar;

  public CharacterChainLink(char character) {
    thisChar = character;
  }

  public char getCharacter() {
    return thisChar;
  }

  public boolean modifyCharacter(Function<Integer, Integer> thisFunction) {
    int test = thisChar;
    if ((double) thisFunction.apply(test) % 1 != 0) {
      return false;
    }
    thisChar = (char) (thisFunction.apply((int) thisChar).intValue());
    return true;
  }
}
