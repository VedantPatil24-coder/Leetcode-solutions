class Solution {
    public int compress(char[] chars) {
        int k = 0;
        int i = 0;
        while(i<chars.length){
            char currChar = chars[i];
            int count = 0;
            while(i<chars.length && currChar == chars[i]){
                i++;
                count++;
            } 
            chars[k++] = currChar;
            if(count>1){
                String currCount = String.valueOf(count);
                for(char c: currCount.toCharArray()){
                    chars[k++] = c;
                }
            }
        }
        return k;
    }
}