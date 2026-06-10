import java.util.*;

class Frame {
    String node;
    boolean processed;

    Frame(String node, boolean processed) {
        this.node = node;
        this.processed = processed;
    }
}

public class MavenDependencyResolver {

    static List<String> topoSortIterative(
            Map<String, List<String>> graph,
            String start) {

        Map<String, Integer> color = new HashMap<>();

        for (String node : graph.keySet())
            color.put(node, 0); // WHITE

        List<String> order = new ArrayList<>();

        Stack<Frame> stack = new Stack<>();

        stack.push(new Frame(start, false));

        while (!stack.isEmpty()) {

            Frame current = stack.pop();

            // Leave step
            if (current.processed) {

                color.put(current.node, 2); // BLACK

                order.add(current.node);

                continue;
            }

            // Already visited
            if (color.get(current.node) != 0)
                continue;

            // Enter step
            color.put(current.node, 1); // GREY

            stack.push(
                new Frame(current.node, true));

            List<String> neighbours =
                    graph.get(current.node);

            Collections.sort(neighbours,
                    Collections.reverseOrder());

            for (String next : neighbours) {

                if (color.get(next) == 0) {

                    stack.push(
                        new Frame(next, false));
                }
            }
        }

        Collections.reverse(order);

        return order;
    }

    public static void main(String[] args) {

        Map<String, List<String>> graph =
                new HashMap<>();

        graph.put("app",
                Arrays.asList("core"));

        graph.put("core",
                Arrays.asList("logging", "util"));

        graph.put("util",
                Arrays.asList("math", "serial"));

        graph.put("logging",
                Arrays.asList("filehandler", "log4j"));

        graph.put("math",
                Arrays.asList("bigint"));

        graph.put("serial",
                new ArrayList<>());

        graph.put("filehandler",
                new ArrayList<>());

        graph.put("log4j",
                new ArrayList<>());

        graph.put("bigint",
                new ArrayList<>());

        List<String> result =
                topoSortIterative(graph, "app");

        System.out.println(
                "Topological Build Order:");

        for (String module : result)
            System.out.println(module);
    }
}