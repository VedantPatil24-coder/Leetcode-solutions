class Solution {
    public int numberOfSubstrings(String s) {
        int lastSeen[] = {-1, -1, -1};
        int count = 0;
        for(int i = 0; i<s.length(); i++){
            lastSeen[s.charAt(i) - 'a'] = i;
            if(lastSeen[0]>=0 && lastSeen[1]>=0 && lastSeen[2]>=0){
                count = count + (1+ Math.min(Math.min(lastSeen[0], lastSeen[1]), Math.min(lastSeen[0], lastSeen[2])) );
            }
        }
        return count;
    }
}

