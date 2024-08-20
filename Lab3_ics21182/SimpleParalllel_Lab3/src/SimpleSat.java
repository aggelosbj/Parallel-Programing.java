import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class SimpleSat {
    static final int SIZE = 28; // Μέγεθος εισόδου (αριθμός bits)

    static class CheckCircuitTask extends RecursiveAction {
        private int start;
        private int end;

        public CheckCircuitTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        protected void compute() {
            for (int i = start; i <= end; i++) {
                checkCircuit(i);
            }
        }
    }

    public static void main(String[] args) {
        int iterations = (int) Math.pow(2, SIZE); // Αριθμός πιθανών εισόδων (συνδυασμοί bit)

        // Ξεκίνα το χρονομέτρημα
        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new CheckCircuitTask(0, iterations - 1));

        // Σταμάτησε το χρονομέτρημα
        long elapsedTimeMillis = System.currentTimeMillis() - start;

        System.out.println("All done\n");
        System.out.println("Time in ms = " + elapsedTimeMillis);
    }

    static void checkCircuit(int z) {
        boolean[] v = new boolean[SIZE];

        for (int i = SIZE - 1; i >= 0; i--) {
            v[i] = (z & (1 << i)) != 0;
        }

        boolean value =
                (v[0] || v[1]) &&
                        (!v[1] || !v[3]) &&
                        (v[2] || v[3]) &&
                        (!v[3] || !v[4]) &&
                        (v[4] || !v[5]) &&
                        (v[5] || !v[6]) &&
                        (v[5] || v[6]) &&
                        (v[6] || !v[15]) &&
                        (v[7] || !v[8]) &&
                        (!v[7] || !v[13]) &&
                        (v[8] || v[9]) &&
                        (v[8] || !v[9]) &&
                        (!v[9] || !v[10]) &&
                        (v[9] || v[11]) &&
                        (v[10] || v[11]) &&
                        (v[12] || v[13]) &&
                        (v[13] || !v[14]) &&
                        (v[14] || v[15]) &&
                        (v[14] || v[16]) &&
                        (v[17] || v[1]) &&
                        (v[18] || !v[0]) &&
                        (v[19] || v[1]) &&
                        (v[19] || !v[18]) &&
                        (!v[19] || !v[9]) &&
                        (v[0] || v[17]) &&
                        (!v[1] || v[20]) &&
                        (!v[21] || v[20]) &&
                        (!v[22] || v[20]) &&
                        (!v[21] || !v[20]) &&
                        (v[22] || !v[20]);

        if (value) {
            printResult(v, z);
        }
    }

    static void printResult(boolean[] v, int z) {
        String result = String.valueOf(z);

        for (boolean value : v) {
            result += value ? " 1" : " 0 ";
        }

        System.out.println(result);
    }
}
