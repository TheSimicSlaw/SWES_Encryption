package swesencryption;

public class CharacterChain {
  CharacterChainLink begin, end;

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
    end.nextLink = begin;
    begin.prevlink = end;
    fillChain(inText);
  }

  private void checkExists() {
    if (begin != null) {
      throw new RuntimeException("CharacterChain already exists.");
    }
  }

  private void checkLength(String inText) {
    if (inText.length() == 0) {
      throw new RuntimeException("You did not pass any text to createChain.");
    }
  }

  private void fillChain(String inText) {
    int secondToLast = inText.length() - 2;
    CharacterChainLink ccl = begin;
    CharacterChainLink newccl;
    for (int i = 1; i < secondToLast; i++) {
      newccl = new CharacterChainLink(inText.charAt(i));
      newccl.prevlink = ccl;
      ccl.nextLink = newccl;
      ccl = newccl;
    }
    newccl = new CharacterChainLink(inText.charAt(secondToLast));
    newccl.prevlink = ccl;
    ccl.nextLink = newccl;
    newccl.nextLink = end;
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
      ccl = ccl.nextLink;
    } while (ccl != begin);
    return out;
  }
}
