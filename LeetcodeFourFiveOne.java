//Notes
// T.C. O(n)
// S.C. O(n)


class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        List<Character>[] buckets = new List[s.length() + 1]; 
        for (int i = 0; i <= s.length(); i++)
            buckets[i] = new ArrayList<>(); 

        for (Map.Entry<Character, Integer> e : map.entrySet())
           buckets[e.getValue()].add(e.getKey());

        StringBuilder sb = new StringBuilder();

        for (int i = s.length(); i >= 1; i--)
            for (char c : buckets[i])
                for (int j = 0; j < i; j++)
                    sb.append(c);
        return sb.toString();
    }
}