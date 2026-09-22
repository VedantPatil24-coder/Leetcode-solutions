class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int ans[] = new int[n];
        int prefix[] = new int[n];
        int suffix[] = new int[n];
        for(int i = 1; i<nums.length; i++){
            prefix[0] = 1;
            prefix[i] = prefix[i-1]*nums[i-1];
        }
        for(int i = n-2; i>=0;i--){
            suffix[n-1] = 1;
            suffix[i] = suffix[i+1]*nums[i+1];
        }
        for(int i = 0; i<nums.length; i++){
            ans[i] = prefix[i]*suffix[i];
        }
        return ans;
    }
}