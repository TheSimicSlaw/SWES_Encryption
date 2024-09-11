package swesencryption;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.ArrayList;

public class EncryptionTest {
  CharacterChainLink charChainLink;

  @BeforeEach
  public void init() {

  }

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void modifyCharacterReturnsFalseForInvalidOptionDivide() {
    charChainLink = new CharacterChainLink('a');
    assertTrue(!charChainLink.modifyCharacterValue(c -> c / 2));
  }

  @Test
  public void modifyCharacterReturnsFalseForInvalidOptionNegative() {
    charChainLink = new CharacterChainLink('a');
    assertTrue(!charChainLink.modifyCharacterValue(c -> c - 2));
  }

  @Test
  public void modifyCharacterReturns3For5Minus2() {
    charChainLink = new CharacterChainLink('e');
    assertTrue(charChainLink.modifyCharacterValue(c -> c - 2));
    assertEquals(3, (int) charChainLink.getCharacter());
  }

  @Test
  public void modifyCharacterReturns7For5Plus2() {
    charChainLink = new CharacterChainLink('E');
    assertTrue(charChainLink.modifyCharacterValue(c -> c + 2));
    assertEquals(7, (int) charChainLink.getCharacter());
  }

  @Test
  public void characterChainIsCorrectForStringThisisthemessage() {
    assertEquals("thisisthemessage", new CharacterChain("thisisthemessage").getChainText());
  }

  @Test
  public void characterChainIsCorrectForStringThisIsTheMessage() {
    assertEquals("this is the message", new CharacterChain("this is the message").getChainText());
  }

  @Test
  public void blankCharacterChainGivesBlankString() {
    assertEquals("", new CharacterChain().getChainText());
  }

  @Test
  public void secondCreateChainThrowsException() {
    CharacterChain characterChain = new CharacterChain("thisisthemessage");
    RuntimeException exception = assertThrows(RuntimeException.class, () -> characterChain.createChain("new message"));
    assertEquals("CharacterChain already exists.", exception.getMessage());
  }

  @Test
  public void createBlankChainThrowsException() {
    CharacterChain characterChain = new CharacterChain();
    RuntimeException exception = assertThrows(RuntimeException.class, () -> characterChain.createChain(""));
    assertEquals("You did not pass any text to createChain.", exception.getMessage());
  }

  @Test
  public void createBlankChainInConstructorThrowsException() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> new CharacterChain(""));
    assertEquals("You did not pass any text to createChain.", exception.getMessage());
  }

  @Test
  public void abcdeGetBeginningLinkReturnsA() {
    assertEquals('a', new CharacterChain("abcde").getBeginningLink().getCharacter());
  }

  @Test
  public void beginningLinkIsConnectedToEnd() {
    CharacterChain testChain = new CharacterChain("this is the message");
    assertTrue(testChain.isEndingLink(testChain.getBeginningLink().prevlink));
  }

  @Test
  public void endingLinkIsConnectedToBeginning() {
    CharacterChain testChain = new CharacterChain("this is the message");
    CharacterChainLink ccl = testChain.getBeginningLink();
    for (int i = 0; i < 18; i++) {
      ccl = ccl.nextLink;
    }
    assertTrue(testChain.isEndingLink(ccl) && testChain.getBeginningLink().equals(ccl.nextLink));
  }

  @Test
  public void chainKeyCreatesCorrectKeyCharacterList() {
    assertAll(
        () -> assertTrue(new ArrayList<Character>(Arrays.asList('a', 'c', 'e', 'h', 'r', 's', 't'))
            .equals(new ChainKey(new CharacterChain("characters")).getKeyCharacterList())),
        () -> assertTrue(new ArrayList<Character>(Arrays.asList('a', 'e', 'g', 'h', 'i', 'm', 's', 't'))
            .equals(new ChainKey(new CharacterChain("thisisthemessage")).getKeyCharacterList())),
        () -> assertTrue(new ArrayList<Character>(Arrays.asList(' ', 'a', 'e', 'g', 'h', 'i', 'm', 's', 't'))
            .equals(new ChainKey(new CharacterChain("this is the message")).getKeyCharacterList())),
        () -> assertTrue(new ArrayList<Character>(Arrays.asList('e', 'l', 's', 'v'))
            .equals(new ChainKey(new CharacterChain("sleeveless")).getKeyCharacterList())),
        () -> assertTrue(new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'))
            .equals(new ChainKey(new CharacterChain("qwertyuiopasdfghjklzxcvbnm")).getKeyCharacterList())));

  }

  @Test
  public void getScrambleChainReturnsScrambleChain() {
    CharacterChain chain = new CharacterChain("this is the message");
    assertEquals(chain, new ChainScrambler(chain).getScrambleChain());
  }
}
