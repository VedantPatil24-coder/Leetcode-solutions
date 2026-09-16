class Solution {
    public int[] findMissingAndRepeatedValues(int[][] grid) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        int n = grid.length;
        int expectedSum = n*n*(n*n+1)/2;
        int actualSum = 0;
        int repeated = 0;
        int miss = 0;

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                actualSum += grid[i][j];
                freq.put(grid[i][j], freq.getOrDefault(grid[i][j], 0) + 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 2) {
                repeated = entry.getKey();
            }
        }
        miss = expectedSum - actualSum + repeated;
        return new int[]{repeated, miss};
    }
}