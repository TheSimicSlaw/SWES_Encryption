package swesencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
