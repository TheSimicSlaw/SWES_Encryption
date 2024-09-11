package swesencryption;

public class ChainScrambler {
  CharacterChain scramblechain;

  public ChainScrambler(CharacterChain inputcharacterchain) {
    scramblechain = inputcharacterchain;
  }

  public CharacterChain getScrambleChain() {
    return scramblechain;
  }
}
