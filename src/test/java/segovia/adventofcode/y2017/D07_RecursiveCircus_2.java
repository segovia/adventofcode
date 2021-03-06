package segovia.adventofcode.y2017;

import segovia.adventofcode.Utils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class D07_RecursiveCircus_2 {

    @Test
    public void test() throws IOException {
        List<String> fileInputs = Utils.getInputsFromFiles(D07_RecursiveCircus_2.class);
        assertThat(run(fileInputs.get(0)), is(-60L));
        assertThat(run(fileInputs.get(1)), is(-333L));
    }

    private long run(String input) {
        Map<String, Integer> weightMap = new HashMap<>();
        Map<String, Set<String>> childMap = new LinkedHashMap<>();
        Set<String> referencedIds = new HashSet<>();
        for (String line : input.split("\\n")) {
            String[] tokens = line.split("\\s");
            String nodeId = tokens[0];
            int weight = Integer.parseInt(tokens[1].substring(1, tokens[1].length() - 1));
            weightMap.put(nodeId, weight);
            Set<String> children = new HashSet<>();
            childMap.put(nodeId, children);
            if (tokens.length <= 2) continue;
            for (int i = 3; i < tokens.length; i++) {
                int tokenLen = tokens[i].length() - (i < tokens.length - 1 ? 1 : 0);
                String childId = tokens[i].substring(0, tokenLen);
                children.add(childId);
                referencedIds.add(childId);
            }
        }
        String root = getRoot(weightMap, referencedIds);
        return getWeightSum(root, weightMap, childMap);
    }

    public static int getWeightSum(String id, Map<String, Integer> weightMap, Map<String, Set<String>> childMap) {
        List<String> children = new ArrayList<>(childMap.get(id));
        if (children.isEmpty()) {
            return weightMap.get(id);
        }
        List<Integer> weights = new ArrayList<>();
        for (String child : children) {
            int weightSum = getWeightSum(child, weightMap, childMap);
            if (weightSum < 0) return weightSum;
            weights.add(weightSum);
        }
        int weight = weights.get(0);
        boolean allSame = true;
        for (int w : weights)
            if (w != weight) {
                allSame = false;
                break;
            }
        if (allSame) {
            return weight * children.size() + weightMap.get(id);
        }
        if (children.size() == 2) {
            int diff = weights.get(0) - weights.get(1);
            return -(weightMap.get(children.get(0)) - diff);
        }
        // get majority weight
        int majorityWeight;
        if (weights.get(0).equals(weights.get(1)) || weights.get(0).equals(weights.get(2))) {
            majorityWeight = weights.get(0);
        } else {
            majorityWeight = weights.get(1);
        }

        for (int i = 0; i < children.size(); i++) {
            if (weights.get(i) != majorityWeight) {
                int diff = weights.get(i) - majorityWeight;
                return -(weightMap.get(children.get(i)) - diff);
            }
        }
        return -1;
    }

    private static String getRoot(Map<String, Integer> weightMap, Set<String> referencedIds) {
        for (String id : weightMap.keySet()) {
            if (!referencedIds.contains(id)) {
                return id;
            }
        }
        return null;
    }
}
