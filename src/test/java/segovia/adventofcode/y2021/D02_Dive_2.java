package segovia.adventofcode.y2021;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D02_Dive_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(900L));
        assertThat(run(fileInputs.get(1)), is(1842742223L));
    }

    private long run(String input) {
        List<String> commands = Arrays.stream(input.split("\\n")).collect(Collectors.toList());
        int horizontal = 0, depth = 0, aim = 0;
        for (var command : commands) {
            String[] split = command.split(" ");
            int amount = Integer.parseInt(split[1]);
            if (command.startsWith("forward")) {
                horizontal += amount;
                depth += aim * amount;
            } else if (command.startsWith("up")) aim -= amount;
            else aim += amount;
        }
        return horizontal * depth;
    }
}
