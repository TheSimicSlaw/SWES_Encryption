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

  public boolean modifyCharacter(Function<Double, Double> thisFunction) {
    double test = thisFunction.apply((double) thisChar);
    if (!isValidCharacter(test)) {
      return false;
    }
    thisChar = (char) test;
    return true;
  }

  public boolean isValidCharacter(double character) {
    if (character < 0 || character > 127 || character % 1 != 0) {
      return false;
    }
    return true;
  }
}
