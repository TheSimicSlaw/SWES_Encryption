package swesencryption;

import java.util.HashMap;

// Now marks characters as char-DEL-num-DEL to make the count number easier to distinguish
// Added CharacterKey, which is programmed with the numerical equivalents of the characters and will handle value increases during the substitution step
// Added CharacterSubstitutor, which handles the actual substitution step
// Still need to integrate CharacterChainLink and CharacterKey

public class CharacterKey {
  private HashMap<Character, Integer> charMap;

  public CharacterKey(int[] vals) { // NOPMD
    // assumes using only ASCII characters [SPACE] until [DEL]
    if (vals.length != 95) {
      throw new RuntimeException("You tried to create a CharacterKey with the incorrect number of characters. You had "
          + vals.length + " characters.");
    }
    char i_char;
    for (int i = 0; i < 32; i++) {
      i_char = (char) i;
      charMap.put(i_char, 0);
    }
    for (int i = 32; i < 127; i++) {
      i_char = (char) i;
      charMap.put(i_char, vals[i]);
    }
    i_char = (char) 127;
    charMap.put(i_char, 0);
  }

  public int getCharacterKeyValue(char inChar) {
    return charMap.get(inChar);
  }

  public void substitutionStepValIncrease(int substitutorval) { // substitution step includes looping each
    char c;
    for (int i = 32; i < 127; i++) {
      c = (char) i;
      charMap.put(c, charMap.get(c) + substitutorval);
    }
  }
}
