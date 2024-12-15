package swesencryption; // Scrambleverse Warpworld Encryption System

import java.util.function.Function;

public class CharacterChainLink {
  private char thisChar;
  private int thisCharValue;
  CharacterChainLink prevlink, nextlink;

  public CharacterChainLink(char character) {
    thisChar = character;
    // setValueAlphabet();
    setValueASCII();
  }

  public CharacterChainLink(char character, boolean alpha) {
    thisChar = character;
    if (alpha) {
      setValueAlphabet();
    } else {
      setValueASCII();
    }
  }

  public void setValueAlphabet() {
    int thisCharAscii = (int) thisChar;
    if (!Character.isLetter(thisChar)) {
      thisCharValue = 0;
    } else if (thisCharAscii >= 97) {
      thisCharValue = thisCharAscii - 96;
    } else {
      thisCharValue = thisCharAscii - 64;
    }
  }

  private void setValueASCII() {
    thisCharValue = (int) thisChar;
  }

  public char getCharacter() {
    return thisChar;
  }

  public int getCharValue() {
    return thisCharValue;
  }

  public boolean modifyCharacterValue(Function<Double, Double> thisFunction) { // change to modifyCharacterValue
    double test = thisFunction.apply((double) thisCharValue);
    if (!isValidCharacter(test)) {
      return false;
    }
    thisChar = (char) test;
    return true;
  }

  public boolean isValidCharacter(double character) {
    if (character < 0 || character % 1 != 0) {
      return false;
    }
    return true;
  }
}
