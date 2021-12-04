package segovia.adventofcode.y2021;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D03_BinaryDiagnostic_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(198L));
        assertThat(run(fileInputs.get(1)), is(2498354L));
    }

    private long run(String input) {
        List<String> binaries = Arrays.stream(input.split("\\n")).collect(Collectors.toList());
        int[] counts = new int[binaries.get(0).length()];
        for(var binary : binaries) {
            for (int i = 0; i < counts.length; i++) {
                if (binary.charAt(i) == '1') counts[i]++;
            }
        }
        long gamma = 0, epsilon = 0;
        for (int count : counts) {
            gamma <<= 1;
            epsilon <<= 1;
            if (count > binaries.size() / 2) gamma++;
            else epsilon++;
        }
        return gamma * epsilon;
    }
}
