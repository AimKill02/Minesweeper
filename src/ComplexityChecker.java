public class ComplexityChecker {
    public static void main(String[] args) {
        int rows = 100, cols = 100;
        int[][] arr = new int[rows][cols];

        // Time complexity check: measure time to fill the array
        long startTime = System.nanoTime();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                arr[r][c] = r * c;
        long endTime = System.nanoTime();
        System.out.println("Time to fill array: " + (endTime - startTime) / 1_000_000.0 + " ms");

        // Space complexity check: measure memory before and after allocation
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        runtime.gc();
        long memAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Approximate memory used for array: " + (memAfter - memBefore) / 1024.0 + " KB");
    }
}
