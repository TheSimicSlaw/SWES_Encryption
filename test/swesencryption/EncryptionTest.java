package swesencryption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertTrue(!charChainLink.modifyCharacter(c -> c / 2));
  }

  @Test
  public void modifyCharacterReturns95For97Minus2() {
    charChainLink = new CharacterChainLink('a');
    assertTrue(charChainLink.modifyCharacter(c -> c - 2));
    assertEquals(95, (int) charChainLink.getCharacter());
  }

  @Test
  public void modifyCharacterReturns99For97Plus2() {
    charChainLink = new CharacterChainLink('a');
    assertTrue(charChainLink.modifyCharacter(c -> c + 2));
    assertEquals(99, (int) charChainLink.getCharacter());
  }
}
