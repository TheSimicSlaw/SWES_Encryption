package swesencryption;

public class CharacterChain {
  private CharacterChainLink begin, end;

  public CharacterChain() {
  }

  public CharacterChain(String chainText) {
    createChain(chainText, false);
  }

  public CharacterChain(String chainText, boolean isalphabetical) {
    createChain(chainText, isalphabetical);
  }

  public void createChain(String inText, boolean isalphabetical) throws RuntimeException {
    checkExists();
    checkLength(inText);
    begin = new CharacterChainLink(inText.charAt(0), isalphabetical);
    end = new CharacterChainLink(inText.charAt(inText.length() - 1), isalphabetical);
    end.nextlink = begin;
    begin.prevlink = end;
    fillChain(inText, isalphabetical);
  }

  private void checkExists() {
    if (begin != null) {
      throw new RuntimeException("CharacterChain already exists.");
    }
  }

  private void checkLength(String inText) { // NOPMD
    if (inText.length() == 0) {
      throw new RuntimeException("You did not pass any text to createChain.");
    }
    if (inText.length() == 1) {
      throw new RuntimeException("Cannot create Character Chain with only one character.");
    }
  }

  private void checkValidCharacter(char c) {
    if (c > 127) {
      throw new RuntimeException("Character with val " + (int) c + " is not a valid character.");
    }
  }

  private void fillChain(String inText, boolean isalphabetical) {
    int secondToLast = inText.length() - 2;
    char charat;
    CharacterChainLink ccl = begin;
    CharacterChainLink newccl;
    for (int i = 1; i < secondToLast; i++) {
      charat = inText.charAt(i);
      checkValidCharacter(charat);
      newccl = new CharacterChainLink(charat, isalphabetical);
      newccl.prevlink = ccl;
      ccl.nextlink = newccl;
      ccl = newccl;
    }
    newccl = new CharacterChainLink(inText.charAt(secondToLast), isalphabetical);
    newccl.prevlink = ccl;
    ccl.nextlink = newccl;
    newccl.nextlink = end;
    end.prevlink = newccl;
  }

  public String getChainText() {
    if (begin == null) {
      return "";
    }
    String out = "";
    CharacterChainLink ccl = begin;
    do {
      out += ccl.getOutput();
      ccl = ccl.nextlink;
    } while (ccl != begin);
    return out;
  }

  public CharacterChainLink getBeginningLink() {
    return begin;
  }

  public CharacterChainLink getEndingLink() {
    return end;
  }

  public boolean isEndingLink(CharacterChainLink inlink) {
    return inlink == end;
  }

  public boolean isBeginningLink(CharacterChainLink inlink) {
    return inlink == begin;
  }

  public void moveEndingLinkPointerForwards() {
    // System.out.println("Moving end pointer forwards...");
    end = end.nextlink;
    // System.out.println("...Done moving end pointer forwards.");
  }

  public void moveBeginningLinkPointerBackwards() {
    // System.out.println("Moving beginning link backwards...");
    begin = begin.prevlink;
    // System.out.println("...Done moving beginning link backwards.");
  }

  public void swapBeginningAndEndingLinkPointers() {
    // System.out.println("Swapping beginning and end...");
    begin = begin.nextlink;
    end = end.prevlink;
  }
}
