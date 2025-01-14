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

  public ChainScrambler(String inputstring, boolean isalphabetical) {
    if (isalphabetical) {
      chaintoscramble = new CharacterChain(inputstring, true);
      chainkeyforscrambling = new ChainKey(chaintoscramble);
    } else {
      chaintoscramble = new CharacterChain(inputstring);
      chainkeyforscrambling = new ChainKey(chaintoscramble);
    }
  }

  public String returnScrambledChain() {
    if (!scrambled) {
      scrambleChain();
    }
    return chaintoscramble.getChainText();
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

  private void transposeLink(CharacterChainLink currlink) { // NOPMD
    CharacterChainLink swaplink = currlink;
    CharacterChainLink forelink, aftlink;

    CharacterChainLink newforelink;

    int currlinkvalue = currlink.getCharValue();
    // System.out.println("Char: " + currlink.getCharacter() + "\nCharVal: " +
    // currlinkvalue);
    for (int i = 0; i < currlinkvalue; i++) {
      // System.out.println(chaintoscramble.getChainText());

      forelink = currlink.nextlink;
      swaplink = currlink.nextlink;
      aftlink = currlink.prevlink;

      newforelink = swaplink.nextlink;

      aftlink.nextlink = swaplink;
      swaplink.prevlink = aftlink;

      // aftlink.nextlink = forelink;
      // forelink.prevlink = aftlink;

      currlink.nextlink = newforelink;
      currlink.prevlink = swaplink;

      swaplink.nextlink = currlink;
      newforelink.prevlink = currlink;

      if (chaintoscramble.isEndingLink(swaplink)) {
        chaintoscramble.moveEndingLinkPointerForwards();
      } else if (chaintoscramble.getBeginningLink().nextlink == chaintoscramble.getEndingLink()) {
        chaintoscramble.swapBeginningAndEndingLinkPointers();
      } else if (chaintoscramble.isBeginningLink(currlink)) {
        chaintoscramble.moveBeginningLinkPointerBackwards();
      }
    }
    // System.out.println(chaintoscramble.getChainText() + "\n");
  }

  public CharacterChain getScrambleChain() {
    return chaintoscramble;
  }
}
