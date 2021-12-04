package segovia.adventofcode.y2021;

import org.junit.Test;
import segovia.adventofcode.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class D04_GiantSquid_1 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(this.getClass());
        assertThat(run(fileInputs.get(0)), is(4512L));
        assertThat(run(fileInputs.get(1)), is(12796L));
    }

    private long run(String input) {
        List<String> lines = Arrays.stream(input.split("\\n")).collect(Collectors.toList());
        int[] nums = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        int[][][] boards = new int[lines.size()/6][5][5];
        boolean[][][] marked = new boolean[lines.size()/6][5][5];
        for (int b = 0; b < boards.length; b++) {
            for (int i = 0; i < 5; i++) {
                boards[b][i] = Arrays.stream(lines.get(b * 6 + i + 2).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
            }
        }
        int winningNum = -1;
        int winningBoard = -1;
        numLoop: for(var num: nums) {
            for (int b = 0; b < boards.length; b++) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (num == boards[b][i][j] && !marked[b][i][j]) {
                            marked[b][i][j] = true;
                            boolean isWin = true;
                            for (int k = 0; k < 5; k++) {
                                if (!marked[b][i][k]) {
                                    isWin = false;
                                    break;
                                }
                            }
                            if(!isWin) {
                                isWin = true;
                                for (int k = 0; k < 5; k++) {
                                    if (!marked[b][k][j]) {
                                        isWin = false;
                                        break;
                                    }
                                }
                            }
                            if (isWin) {
                                winningBoard = b;
                                winningNum = num;
                                break numLoop;
                            }
                        }
                    }
                }
            }
        }
        int unmarkedSum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!marked[winningBoard][i][j]) {
                    unmarkedSum += boards[winningBoard][i][j];
                }
            }
        }

        return unmarkedSum * winningNum;
    }
}
