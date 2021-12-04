package segovia.adventofcode.y2021;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D01_SonarSweep_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(7L));
        assertThat(run(fileInputs.get(1)), is(1696L));
    }

    private long run(String input) {
        long[] nums = Arrays.stream(input.split("\\s+")).mapToLong(Long::parseLong).toArray();
        int count = 0;
        for (int i = 1; i < nums.length; i++) if (nums[i - 1] < nums[i]) count++;
        return count;
    }
}
