package swesencryption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ChainKey {
  private HashMap<Character, ArrayList<CharacterChainLink>> keyMap;
  private ArrayList<Character> keyCharacterList;

  public ChainKey(CharacterChain incharacterchain) {
    keyMap = new HashMap<Character, ArrayList<CharacterChainLink>>();
    keyCharacterList = new ArrayList<Character>();

    CharacterChainLink currentLink = incharacterchain.getBeginningLink();

    do {
      keyCreationStep(currentLink);
      currentLink = currentLink.nextlink;
    } while (!incharacterchain.isEndingLink(currentLink));
    keyCreationStep(currentLink);
    // while (!incharacterchain.isEndingLink(currentLink)) {
    // keyCreationStep(currentLink);
    // currentLink = currentLink.nextLink;
    // }
    // keyCreationStep(currentLink);

    sortKeyCharacterList(0, keyCharacterList.size() - 1);
  }

  private void keyCreationStep(CharacterChainLink currentLink) {
    char currentchar = currentLink.getCharacter();
    if (!keyMap.containsKey(currentchar)) {
      keyMap.put(currentchar, new ArrayList<CharacterChainLink>());
      keyCharacterList.add(currentchar);
    }
    keyMap.get(currentchar).add(currentLink);
  }

  private void sortKeyCharacterList(int low, int high) {
    if (low < high) {
      int pivotindex = hoarePartition(low, high);

      sortKeyCharacterList(low, pivotindex);

      sortKeyCharacterList(pivotindex + 1, high);
    }
  }

  private int hoarePartition(int low, int high) { // NOPMD
    selectAndPlaceMedianOfThreeAsPivot(low, high);
    int i = low - 1, j = high + 1;
    int pivotvalue = keyCharacterList.get(low);
    while (true) {
      do {
        i++;
      } while (keyCharacterList.get(i) < pivotvalue);

      do {
        j--;
      } while (keyCharacterList.get(j) > pivotvalue);

      if (i >= j)
        return j;

      Collections.swap(keyCharacterList, i, j);
    }
  }

  private void selectAndPlaceMedianOfThreeAsPivot(int low, int high) {
    int mid = (low + high) / 2;
    int lowval = keyCharacterList.get(low), midval = keyCharacterList.get(mid), highval = keyCharacterList.get(high);
    int pivot = 0;

    if ((lowval < midval) ^ (lowval < highval))
      pivot = low;
    else if ((midval < lowval) ^ (midval < highval))
      pivot = mid;
    else
      pivot = high;

    Collections.swap(keyCharacterList, low, pivot);
  }

  public ArrayList<Character> getKeyCharacterList() {
    return keyCharacterList;
  }

  public String printKeyCount() {
    String outKeyCountString = "";
    for (Character character : keyCharacterList) {
      // System.out.println(character + " " + keyMap.get(character));
      outKeyCountString += character + " " + (keyMap.get(character).size() - 1) + "\n";
    }
    System.out.println(outKeyCountString);
    return outKeyCountString;
  }

  public ArrayList<ArrayList<CharacterChainLink>> getWorkableKey() {
    ArrayList<ArrayList<CharacterChainLink>> workableKey = new ArrayList<ArrayList<CharacterChainLink>>();
    for (Character keymember : keyCharacterList) {
      workableKey.add(keyMap.get(keymember));
    }

    return workableKey;
  }
}
