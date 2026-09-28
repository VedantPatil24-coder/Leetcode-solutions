class Solution {
    public String removeOccurrences(String str, String part) {
        while(!str.isEmpty() && str.contains(part)){
            str = str.replaceFirst(part, "");
        }
        return str;
    }
}
