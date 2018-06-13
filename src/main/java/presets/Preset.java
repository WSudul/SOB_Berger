package presets;

import berger.BergerCode;
import berger.BitContainerInterface;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Preset {

    private static Random generator = new Random();

    public static BergerCode PresetSingleZero(BergerCode bergerCode) {
        ChangeSingleBit(bergerCode, (bit) -> bit, false);
        return bergerCode;
    }


    public static BergerCode PresetSingleOne(BergerCode bergerCode) {
        ChangeSingleBit(bergerCode, (bit) -> !bit, true);

        return bergerCode;
    }

    private static void ChangeSingleBit(BergerCode bergerCode, Function<Boolean, Boolean> function, boolean
            targetValue) {

        BitContainerInterface codeWord = bergerCode.getCodeWord();
        BitContainerInterface checkBits = bergerCode.getCheckBits();

        Set<Integer> IndexesOfOnesCodeWord = GetSpecificIndexes(codeWord, function);
        Set<Integer> IndexesOfOnesCheckBits = GetSpecificIndexes(checkBits, function);

        boolean useCodeWord = generator.nextBoolean();
        BitContainerInterface choseContainer;
        Set<Integer> indexesOfOnes;
        if (IndexesOfOnesCodeWord.isEmpty() && IndexesOfOnesCheckBits.isEmpty()) {
            System.out.println("No bits with desired value are presnent");
            return;
        } else if (useCodeWord) {
            choseContainer = codeWord;
            indexesOfOnes = IndexesOfOnesCodeWord;
        } else {

            choseContainer = checkBits;
            indexesOfOnes = IndexesOfOnesCheckBits;
        }
        int randomInt = generator.nextInt(indexesOfOnes.size());
        int randomIndex = indexesOfOnes.stream().collect(Collectors.toList()).get(randomInt);

        if (targetValue)
            choseContainer.setBit(randomIndex);
        else
            choseContainer.clearBit(randomIndex);

    }

    public static BergerCode PresetMultipleZeros(BergerCode bergerCode) {
        BitContainerInterface codeWord = bergerCode.getCodeWord();
        BitContainerInterface checkBits = bergerCode.getCheckBits();

        SetMultipleOnesToZero(codeWord, generator.nextDouble());
        SetMultipleOnesToZero(checkBits, generator.nextDouble());

        return bergerCode;
    }

    public static BergerCode PresetMultipleOnes(BergerCode bergerCode) {

        BitContainerInterface codeWord = bergerCode.getCodeWord();
        BitContainerInterface checkBits = bergerCode.getCheckBits();

        SetMultipleZerosToOnes(codeWord, generator.nextDouble());
        SetMultipleZerosToOnes(checkBits, generator.nextDouble());

        return bergerCode;
    }

    public static BergerCode PresetMultipleMixed(BergerCode bergerCode) {
        BitContainerInterface codeWord = bergerCode.getCodeWord();
        BitContainerInterface checkBits = bergerCode.getCheckBits();

        final int totalLength = codeWord.length() + checkBits.length();
        final int min = (totalLength / 8);
        final int numberOfBitsToFlip = min + generator.nextInt(totalLength - min);

        Set<Integer> uniqueIndexes = GenerateUniqueIndexes(numberOfBitsToFlip, totalLength);

        final int codeWordMaxIndex = codeWord.length() - 1;
        final int offset = codeWord.length();
        for (Integer index : uniqueIndexes) {
            if (index > codeWordMaxIndex) {
                checkBits.flipBit(index - offset);
            } else
                codeWord.flipBit(index);

        }
        return bergerCode;

    }

    private static void SetMultipleOnesToZero(BitContainerInterface bitContainer, double fraction) {
        Set<Integer> IndexesOfOnes = GetSpecificIndexes(bitContainer, bit -> (bit));
        Set<Integer> IndexesToBeCleared = SelectIndexes(IndexesOfOnes, (int) (IndexesOfOnes.size() * fraction));
        for (Integer index : IndexesToBeCleared) {
            bitContainer.clearBit(index);
        }
    }

    private static void SetMultipleZerosToOnes(BitContainerInterface bitContainer, double fraction) {
        Set<Integer> IndexesOfZeros = GetSpecificIndexes(bitContainer, bit -> (!bit));
        Set<Integer> IndexesToBeSet = SelectIndexes(IndexesOfZeros, (int) (IndexesOfZeros.size() * fraction));
        for (Integer index : IndexesToBeSet) {
            bitContainer.setBit(index);
        }
    }

    private static Set<Integer> GetSpecificIndexes(BitContainerInterface bitContainer, Function<Boolean, Boolean>
            function) {
        Set<Integer> specificIndexes = new TreeSet<>();
        for (int i = 0; i < bitContainer.length(); ++i) {
            if (function.apply(bitContainer.getBit(i)))
                specificIndexes.add(i);
        }
        return specificIndexes;
    }

    private static Set<Integer> SelectIndexes(Set<Integer> indexes, int selectSize) {
        if (selectSize > indexes.size())
            return null;
        List<Integer> indexesList = new ArrayList<>(indexes);
        Set<Integer> selection = new TreeSet<>();

        while (selectSize > 0) {
            int index = generator.nextInt(indexes.size());
            selection.add(indexesList.get(index));
            --selectSize;
        }

        return selection;
    }

    private static Set<Integer> GenerateUniqueIndexes(int n, int maxIndex) {
        Set<Integer> uniqueIndexes = new TreeSet<>();
        while (uniqueIndexes.size() < n) {
            int index = generator.nextInt(maxIndex);
            uniqueIndexes.add(index);
        }
        return uniqueIndexes;
    }

}
