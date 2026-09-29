class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for(int i = 0; i<strs.length; i++){
            String s = strs[i];
            char[] charArr = s.toCharArray();
            Arrays.sort(charArr);
            String key = new String(charArr);
            if(!map.containsKey(key)){
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<>(map.values());
    }
}
