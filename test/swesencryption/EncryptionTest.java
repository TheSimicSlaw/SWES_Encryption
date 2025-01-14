package swesencryption;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
  public void setAlphaCharacterToNonAlphabetCharacterReturns0() {
    assertAll(
        () -> assertEquals(0, (new CharacterChainLink('#', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink('/', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink('}', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink('&', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink('.', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink(')', true)).getCharValue()),
        () -> assertEquals(0, (new CharacterChainLink(' ', true)).getCharValue()));
  }

  @Test
  public void modifyCharacterReturnsFalseForInvalidOptionDivide() {
    charChainLink = new CharacterChainLink('a', false);
    assertTrue(!charChainLink.modifyCharacterValue(c -> c / 2));
  }

  @Test
  public void modifyCharacterReturnsFalseForInvalidOptionNegative() {
    charChainLink = new CharacterChainLink('a', false);
    assertTrue(!charChainLink.modifyCharacterValue(c -> c - 200));
  }

  @Test
  public void modifyCharacterReturns99For101Minus2() {
    charChainLink = new CharacterChainLink('e', false);
    assertTrue(charChainLink.modifyCharacterValue(c -> c - 2));
    assertEquals(99, (int) charChainLink.getCharacter());
  }

  @Test
  public void modifyCharacterReturns71For69Plus2() {
    charChainLink = new CharacterChainLink('E', false);
    assertTrue(charChainLink.modifyCharacterValue(c -> c + 2));
    assertEquals(71, (int) charChainLink.getCharacter());
  }

  @Test
  public void modifyAlphaCharacterReturnsFalseForInvalidOptionDivide() {
    charChainLink = new CharacterChainLink('a', true);
    assertTrue(!charChainLink.modifyCharacterValue(c -> c / 2));
  }

  @Test
  public void modifyAlphaCharacterReturnsFalseForInvalidOptionNegative() {
    charChainLink = new CharacterChainLink('a', true);
    assertTrue(!charChainLink.modifyCharacterValue(c -> c - 2));
  }

  @Test
  public void modifyAlphaCharacterReturns3For5Minus2() {
    charChainLink = new CharacterChainLink('e', true);
    assertTrue(charChainLink.modifyCharacterValue(c -> c - 2));
    assertEquals(3, (int) charChainLink.getCharacter());
  }

  @Test
  public void modifyAlphaCharacterReturns7For5Plus2() {
    charChainLink = new CharacterChainLink('E', true);
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
  public void createOneLinkChainThrowsException() {
    CharacterChain characterChain = new CharacterChain();
    RuntimeException exception = assertThrows(RuntimeException.class, () -> characterChain.createChain("a"));
    assertEquals("Cannot create Character Chain with only one character.", exception.getMessage());
  }

  @Test
  public void createOneLinkChainInConstructorThrowsException() {
    RuntimeException exception = assertThrows(RuntimeException.class, () -> new CharacterChain("a"));
    assertEquals("Cannot create Character Chain with only one character.", exception.getMessage());
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
      ccl = ccl.nextlink;
    }
    assertTrue(testChain.isEndingLink(ccl) && testChain.getBeginningLink().equals(ccl.nextlink));
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
        () -> assertEquals(new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')),
            new ChainKey(new CharacterChain("qwertyuiopasdfghjklzxcvbnm")).getKeyCharacterList()));

  }

  @Test
  public void chainKeyReturnsCorrectHashmap() {
    ChainKey sleevelessKey = new ChainKey(new CharacterChain("sleeveless"));
    ArrayList<ArrayList<CharacterChainLink>> sleevelessWorkableKey = sleevelessKey.getWorkableKey();
    ChainKey messageKey = new ChainKey(new CharacterChain("this is the message"));
    ArrayList<ArrayList<CharacterChainLink>> messageWorkableKey = messageKey.getWorkableKey();
    ChainKey mixedCharsKey = new ChainKey(new CharacterChain("a1bb#h#ll1160)()%^.;`+"));
    ArrayList<ArrayList<CharacterChainLink>> mixedCharsWorkableKey = mixedCharsKey.getWorkableKey();

    assertAll(
        () -> assertEquals(sleevelessWorkableKey.get(0).get(0).getCharacter(), 'e'),
        () -> assertEquals(sleevelessWorkableKey.get(0).size(), 4),
        () -> assertEquals(sleevelessWorkableKey.get(1).get(0).getCharacter(), 'l'),
        () -> assertEquals(sleevelessWorkableKey.get(1).size(), 2),
        () -> assertEquals(sleevelessWorkableKey.get(2).get(0).getCharacter(), 's'),
        () -> assertEquals(sleevelessWorkableKey.get(2).size(), 3),
        () -> assertEquals(sleevelessWorkableKey.get(3).get(0).getCharacter(), 'v'),
        () -> assertEquals(sleevelessWorkableKey.get(3).size(), 1),

        () -> assertEquals(messageWorkableKey.get(0).get(0).getCharacter(), ' '),
        () -> assertEquals(messageWorkableKey.get(0).size(), 3),
        () -> assertEquals(messageWorkableKey.get(1).get(0).getCharacter(), 'a'),
        () -> assertEquals(messageWorkableKey.get(1).size(), 1),
        () -> assertEquals(messageWorkableKey.get(2).get(0).getCharacter(), 'e'),
        () -> assertEquals(messageWorkableKey.get(2).size(), 3),
        () -> assertEquals(messageWorkableKey.get(3).get(0).getCharacter(), 'g'),
        () -> assertEquals(messageWorkableKey.get(3).size(), 1),
        () -> assertEquals(messageWorkableKey.get(4).get(0).getCharacter(), 'h'),
        () -> assertEquals(messageWorkableKey.get(4).size(), 2),
        () -> assertEquals(messageWorkableKey.get(5).get(0).getCharacter(), 'i'),
        () -> assertEquals(messageWorkableKey.get(5).size(), 2),
        () -> assertEquals(messageWorkableKey.get(6).get(0).getCharacter(), 'm'),
        () -> assertEquals(messageWorkableKey.get(6).size(), 1),
        () -> assertEquals(messageWorkableKey.get(7).get(0).getCharacter(), 's'),
        () -> assertEquals(messageWorkableKey.get(7).size(), 4),
        () -> assertEquals(messageWorkableKey.get(8).get(0).getCharacter(), 't'),
        () -> assertEquals(messageWorkableKey.get(8).size(), 2),

        () -> assertEquals(mixedCharsWorkableKey.get(0).get(0).getCharacter(), '#'),
        () -> assertEquals(mixedCharsWorkableKey.get(0).size(), 2),
        () -> assertEquals(mixedCharsWorkableKey.get(1).get(0).getCharacter(), '%'),
        () -> assertEquals(mixedCharsWorkableKey.get(1).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(2).get(0).getCharacter(), '('),
        () -> assertEquals(mixedCharsWorkableKey.get(2).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(3).get(0).getCharacter(), ')'),
        () -> assertEquals(mixedCharsWorkableKey.get(3).size(), 2),
        () -> assertEquals(mixedCharsWorkableKey.get(4).get(0).getCharacter(), '+'),
        () -> assertEquals(mixedCharsWorkableKey.get(4).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(5).get(0).getCharacter(), '.'),
        () -> assertEquals(mixedCharsWorkableKey.get(5).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(6).get(0).getCharacter(), '0'),
        () -> assertEquals(mixedCharsWorkableKey.get(6).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(7).get(0).getCharacter(), '1'),
        () -> assertEquals(mixedCharsWorkableKey.get(7).size(), 3),
        () -> assertEquals(mixedCharsWorkableKey.get(8).get(0).getCharacter(), '6'),
        () -> assertEquals(mixedCharsWorkableKey.get(8).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(9).get(0).getCharacter(), ';'),
        () -> assertEquals(mixedCharsWorkableKey.get(9).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(10).get(0).getCharacter(), '^'),
        () -> assertEquals(mixedCharsWorkableKey.get(10).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(11).get(0).getCharacter(), '`'),
        () -> assertEquals(mixedCharsWorkableKey.get(11).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(12).get(0).getCharacter(), 'a'),
        () -> assertEquals(mixedCharsWorkableKey.get(12).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(13).get(0).getCharacter(), 'b'),
        () -> assertEquals(mixedCharsWorkableKey.get(13).size(), 2),
        () -> assertEquals(mixedCharsWorkableKey.get(14).get(0).getCharacter(), 'h'),
        () -> assertEquals(mixedCharsWorkableKey.get(14).size(), 1),
        () -> assertEquals(mixedCharsWorkableKey.get(15).get(0).getCharacter(), 'l'),
        () -> assertEquals(mixedCharsWorkableKey.get(15).size(), 2));

  }

  @Test
  public void getScrambleChainReturnsScrambleChain() {
    CharacterChain chain = new CharacterChain("this is the message");
    ChainKey chainkey = new ChainKey(chain);
    ChainScrambler chainscrambler1 = new ChainScrambler("this is the message");
    ChainScrambler chainscrambler2 = new ChainScrambler(chain);
    ChainScrambler chainscrambler3 = new ChainScrambler(chain, chainkey);
    assertAll(
        () -> assertEquals(chain.getChainText(), chainscrambler1.getScrambleChain().getChainText()),
        () -> assertEquals(chain, chainscrambler2.getScrambleChain()),
        () -> assertEquals(chain, chainscrambler3.getScrambleChain()));
  }

  @Test
  public void chainScramblerThrowsExceptionIfScrambledMoreThanOnce() {
    ChainScrambler chainscrambler = new ChainScrambler("testing");
    chainscrambler.scrambleChain();

    RuntimeException secondScrambleException = assertThrows(RuntimeException.class,
        () -> chainscrambler.scrambleChain());
    assertEquals(secondScrambleException.getMessage(), "This chain has already been scrambled.");
  }

  @Test
  public void scrambleChainThrowsNoExceptions() {
    assertAll(
        () -> assertDoesNotThrow(() -> (new ChainScrambler("sleeveless")).scrambleChain()),
        () -> assertDoesNotThrow(() -> (new ChainScrambler("this is a test message")).scrambleChain()),
        () -> assertDoesNotThrow(() -> (new ChainScrambler("123abc!@# how's it goin', folks?")).scrambleChain()),
        () -> assertDoesNotThrow(() -> (new ChainScrambler(
            "It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness, it was the epoch of belief, it was the epoch of incredulity, it was the season of Light, it was the season of Darkness, it was the spring of hope, it was the winter of despair, we had everything before us, we had nothing before us, we were all going direct to Heaven, we were all going direct the other way—in short, the period was so far like the present period, that some of its noisiest authorities insisted on its being received, for good or for evil, in the superlative degree of comparison only."))
            .scrambleChain())

    );
  }

  // test if scrambles correct

  // test for cases when the letter is moving to the end and to the start (work
  // out on paper first)
}
