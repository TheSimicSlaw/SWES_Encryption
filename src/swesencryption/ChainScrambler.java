package swesencryption;

import java.util.ArrayList;

public class ChainScrambler {
  CharacterChain chaintoscramble;
  ChainKey chainkeyforscrambling;

  private boolean scrambled = false;

  public ChainScrambler(String inputstring) {
    chaintoscramble = new CharacterChain(inputstring);
    chainkeyforscrambling = new ChainKey(chaintoscramble);
  }

  public ChainScrambler(CharacterChain inputcharacterchain) {
    chaintoscramble = inputcharacterchain;
    chainkeyforscrambling = new ChainKey(inputcharacterchain);
  }

  public ChainScrambler(CharacterChain inputcharacterchain, ChainKey inputchainkey) {
    chaintoscramble = inputcharacterchain;
    chainkeyforscrambling = inputchainkey;
  }

  public void scrambleChain() {
    if (scrambled) {
      throw new RuntimeException("This chain has already been scrambled.");
    }
    ArrayList<ArrayList<CharacterChainLink>> workablekey = chainkeyforscrambling.getWorkableKey();
    transpositionStep(workablekey);
    scrambled = true;
  }

  private void transpositionStep(ArrayList<ArrayList<CharacterChainLink>> inworkablekey) {
    for (ArrayList<CharacterChainLink> characterblock : inworkablekey) {
      for (CharacterChainLink link : characterblock) {
        transposeLink(link);
      }
    }
  }

  private void transposeLink(CharacterChainLink currlink) {
    CharacterChainLink swaplink = currlink;
    CharacterChainLink forelink = currlink.nextlink, aftlink = currlink.prevlink;

    int currlinkvalue = currlink.getCharValue();
    for (int i = 0; i < currlinkvalue; i++) {
      swaplink = swaplink.nextlink;
    }
    CharacterChainLink newforelink = swaplink.nextlink;

    aftlink.nextlink = forelink;
    forelink.prevlink = aftlink;

    currlink.nextlink = newforelink;
    currlink.prevlink = swaplink;
    swaplink.nextlink = currlink;
    newforelink.prevlink = currlink;
  }

  public CharacterChain getScrambleChain() {
    return chaintoscramble;
  }
}
