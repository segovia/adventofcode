package segovia.adventofcode.y2021;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D03_BinaryDiagnostic_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(230L));
        assertThat(run(fileInputs.get(1)), is(3277956L));
    }

    private long run(String input) {
        List<String> binaries = Arrays.stream(input.split("\\n")).collect(Collectors.toList());

        List<String> binariesWithMost = binaries;
        int bitPosition = 0;
        while (binariesWithMost.size() > 1) {
            int onesCount = countOnes(binariesWithMost, bitPosition);
            char expectedBit = onesCount >= (binariesWithMost.size() + 1) / 2 ? '1' : '0';
            int finalBitPosition = bitPosition;
            binariesWithMost = binariesWithMost.stream().filter(b -> b.charAt(finalBitPosition) == expectedBit).collect(Collectors.toList());
            ++bitPosition;
        }

        List<String> binariesWithLeast = binaries;
        bitPosition = 0;
        while (binariesWithLeast.size() > 1) {
            int onesCount = countOnes(binariesWithLeast, bitPosition);
            char expectedBit = onesCount >= (binariesWithLeast.size() + 1) / 2 ? '0' : '1';
            int finalBitPosition = bitPosition;
            binariesWithLeast = binariesWithLeast.stream().filter(b -> b.charAt(finalBitPosition) == expectedBit).collect(Collectors.toList());
            ++bitPosition;
        }
        return binaryToLong(binariesWithMost.get(0)) * binaryToLong(binariesWithLeast.get(0));
    }

    private int countOnes(List<String> binariesWithMost, int bitPosition) {
        int count = 0;
        for (var binary : binariesWithMost) {
            if (binary.charAt(bitPosition) == '1') count++;
        }
        return count;
    }

    private long binaryToLong(String binary) {
        long val = 0;
        for (int i = 0; i < binary.length(); i++) {
            val <<= 1;
            val += binary.charAt(i) - '0';
        }
        return val;
    }
}
