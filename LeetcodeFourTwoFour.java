class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0;
        int right = 0;
        int maxFreq = 0;
        int result = 0;
        while(right<s.length()){
            freq[s.charAt(right)-'A']++;
            maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
            if(right-left+1 - maxFreq > k){
                freq[s.charAt(left)-'A']--;
                left++;
            }
            result = Math.max(result, right-left+1);
            right++;
        }
        return result;
    }
}