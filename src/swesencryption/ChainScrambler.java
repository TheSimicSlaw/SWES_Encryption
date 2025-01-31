package swesencryption;

import java.util.ArrayList;

public class ChainScrambler {
  CharacterChain chaintoscramble;
  CharacterChain scrambledchainwithkey;
  ChainKey chainkeyforscrambling;
  ArrayList<ArrayList<CharacterChainLink>> workablekey;

  private boolean scrambled = false;

  public ChainScrambler(String inputstring) {
    chaintoscramble = new CharacterChain(inputstring);
    chainkeyforscrambling = new ChainKey(chaintoscramble);
    workablekey = chainkeyforscrambling.getWorkableKey();
  }

  public ChainScrambler(CharacterChain inputcharacterchain) {
    chaintoscramble = inputcharacterchain;
    chainkeyforscrambling = new ChainKey(inputcharacterchain);
    workablekey = chainkeyforscrambling.getWorkableKey();
  }

  public ChainScrambler(CharacterChain inputcharacterchain, ChainKey inputchainkey) {
    chaintoscramble = inputcharacterchain;
    chainkeyforscrambling = inputchainkey;
    workablekey = chainkeyforscrambling.getWorkableKey();
  }

  public ChainScrambler(String inputstring, boolean isalphabetical) {
    if (isalphabetical) {
      chaintoscramble = new CharacterChain(inputstring, true);
      chainkeyforscrambling = new ChainKey(chaintoscramble);
    } else {
      chaintoscramble = new CharacterChain(inputstring);
      chainkeyforscrambling = new ChainKey(chaintoscramble);
    }
    workablekey = chainkeyforscrambling.getWorkableKey();
  }

  public String returnScrambledChain() {
    if (!scrambled) {
      scrambleChain();
    }
    return chaintoscramble.getChainText();
  }

  public String returnScrambledChainWithKey() {
    if (!scrambled) {
      scrambleChain();
    }

    // ArrayList<CharacterChainLink> temporaryChain = new
    // ArrayList<CharacterChainLink>();
    // CharacterChainLink temporaryLink;

    // for (ArrayList<CharacterChainLink> characterblock : workablekey) {
    // for (int i = 0; i < characterblock.size(); i++) {
    // temporaryLink = new CharacterChainLink((" " + i).charAt(1));

    // CharacterChainLink ccl = characterblock.get(i);
    // CharacterChainLink cclnext = ccl.nextlink;

    // temporaryLink.prevlink = ccl;
    // temporaryLink.nextlink = cclnext;

    // ccl.nextlink = temporaryLink;
    // cclnext.prevlink = temporaryLink;

    // temporaryChain.add(temporaryLink);
    // }
    // }

    ArrayList<CharacterChainLink> temporaryChain = integrateKey();

    String output = chaintoscramble.getChainText();

    for (CharacterChainLink temporaryLinkTwo : temporaryChain) {
      temporaryLinkTwo.nextlink.prevlink = temporaryLinkTwo.prevlink;
      temporaryLinkTwo.prevlink.nextlink = temporaryLinkTwo.nextlink;
    }

    return output + "\n\n" + chainkeyforscrambling.printKeyCount();
  }

  public ArrayList<CharacterChainLink> integrateKey() {
    ArrayList<CharacterChainLink> temporaryChain = new ArrayList<CharacterChainLink>();
    CharacterChainLink temporaryLink;

    for (ArrayList<CharacterChainLink> characterblock : workablekey) {
      for (int i = 0; i < characterblock.size(); i++) {
        temporaryLink = new CharacterChainLink((" " + i).charAt(1)); // problem is that it gets first digit, so if i=123
                                                                     // it counts it as 1

        CharacterChainLink ccl = characterblock.get(i);
        CharacterChainLink cclnext = ccl.nextlink;

        temporaryLink.prevlink = ccl;
        temporaryLink.nextlink = cclnext;

        ccl.nextlink = temporaryLink;
        cclnext.prevlink = temporaryLink;

        temporaryChain.add(temporaryLink);
      }
    }
    return temporaryChain;
  }

  public void scrambleChain() {
    if (scrambled) {
      throw new RuntimeException("This chain has already been scrambled.");
    }
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
