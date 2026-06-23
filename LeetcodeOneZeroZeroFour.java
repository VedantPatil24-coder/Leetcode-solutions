class Solution {
    public int longestOnes(int[] nums, int k) {
        int zeroCount = 0;
        int left = 0;
        int right = 0;
        int sum = 0;
        while(right<nums.length){
            if(nums[right] == 0){
                zeroCount++;
                while(zeroCount > k){
                    if(nums[left] == 0){
                        zeroCount--;
                    }
                    left++;
                }
            }
            
            sum = Math.max(sum, (right-left+1));
            right++;
        }
        return sum;
    }
}