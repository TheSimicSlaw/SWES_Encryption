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
    charChainLink.modifyCharacter(c -> c - 2);
    assertEquals(95, (int) charChainLink.getCharacter());
  }
}
