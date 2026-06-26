class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int totalSum = 0;
        int n = cardPoints.length;
        int windowSize = n-k;
        for(int num: cardPoints) totalSum +=num;
        if(windowSize == 0){
            return totalSum;
        }
        int windowSum = 0;
        for(int i = 0; i<windowSize; i++){
            windowSum += cardPoints[i];
        }

        int minWindowSum = windowSum;
        for(int i = windowSize; i<n; i++){
            windowSum += cardPoints[i];
            windowSum -= cardPoints[i-windowSize];
            minWindowSum = Math.min(windowSum, minWindowSum);
        }

        return totalSum - minWindowSum;
    }
}