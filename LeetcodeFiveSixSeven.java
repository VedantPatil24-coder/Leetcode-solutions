class Solution {
    public boolean checkInclusion(String s1, String s2) {

        int[] freq1 = new int[26];
        for(char c : s1.toCharArray()){
            freq1[c-'a']++;
        }

        int n = s1.length(); 
        int m = s2.length();

        int[] freq2 = new int[26];
        if(n<=m){
            for(int i = 0; i<n; i++){
                freq2[s2.charAt(i) - 'a']a++;
            }
        }
        

        int i = 0;
        while(i+n <= m){
            if(Arrays.equals(freq1, freq2)) return true;
            if(i+n<m){
                freq2[s2.charAt(i) - 'a']--;
                freq2[s2.charAt(i+n) - 'a']++;
            }
            i++;
        }
        return false;
    }
}
