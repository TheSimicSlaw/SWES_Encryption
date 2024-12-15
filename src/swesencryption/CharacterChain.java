package swesencryption;

public class CharacterChain {
  private CharacterChainLink begin, end;

  public CharacterChain() {
  }

  public CharacterChain(String chainText) {
    createChain(chainText);
  }

  public void createChain(String inText) throws RuntimeException {
    checkExists();
    checkLength(inText);
    begin = new CharacterChainLink(inText.charAt(0));
    end = new CharacterChainLink(inText.charAt(inText.length() - 1));
    end.nextlink = begin;
    begin.prevlink = end;
    fillChain(inText);
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

  private void fillChain(String inText) {
    int secondToLast = inText.length() - 2;
    CharacterChainLink ccl = begin;
    CharacterChainLink newccl;
    for (int i = 1; i < secondToLast; i++) {
      newccl = new CharacterChainLink(inText.charAt(i));
      newccl.prevlink = ccl;
      ccl.nextlink = newccl;
      ccl = newccl;
    }
    newccl = new CharacterChainLink(inText.charAt(secondToLast));
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
      out += ccl.getCharacter();
      ccl = ccl.nextlink;
    } while (ccl != begin);
    return out;
  }

  public CharacterChainLink getBeginningLink() {
    return begin;
  }

  public boolean isEndingLink(CharacterChainLink inlink) {
    return inlink == end;
  }
}
