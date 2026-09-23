import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int presum = 0;
        int ans = 0;
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            presum += nums[i]; 
            ans += map.getOrDefault(presum - k, 0); 
            map.put(presum, map.getOrDefault(presum, 0) + 1); 
        }
        return ans;
    }
}