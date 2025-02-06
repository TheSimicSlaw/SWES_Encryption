package swesencryption;

public class CharacterSubstitutor {
  // private CharacterKey charkey;

  public CharacterSubstitutor(CharacterKey ck) {
    // charkey = ck;
  }

  public void substitutionStepTextForwards(int substitutorval, String text) {
    int len = text.length();
    char charAt;
    int charIntAt;
    for (int i = 0; i < len; i++) {
      charAt = text.charAt(i);
      charIntAt = (int) charAt;
      if (charIntAt > 31 && charIntAt < 127) {
        charIntAt -= 31;
        charIntAt += substitutorval;
        if (charIntAt > 126) {
          charIntAt = charIntAt % 126;
        }
        charIntAt += 31;
      } else {
        if (charIntAt == 127) {
          charIntAt = 0;
        }
        charIntAt += substitutorval;
        if (charIntAt > 31) {
          charIntAt = charIntAt % 32;
        }
      }
    }
  }
}
