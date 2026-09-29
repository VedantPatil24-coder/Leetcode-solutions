class Solution {
    public String minWindow(String s, String t) {
        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        int[] freqT = new int[128];
        for (char c : t.toCharArray()) {
            freqT[c]++;
        }

        int need = 0;
        for (int f : freqT) {
            if (f > 0) {
                need++;
            }
        }

        int left = 0, right = 0;
        int have = 0;
        int[] freqW = new int[128];

        int minLen = Integer.MAX_VALUE, minStart = 0;

        while (right < s.length()) {
            char rightChar = s.charAt(right);
            freqW[rightChar]++;

            if (freqT[rightChar] > 0 && freqW[rightChar] == freqT[rightChar]) {
                have++;
            }

            right++;

            while (have == need) {
                int currLen = right - left;

                if (currLen < minLen) {
                    minLen = currLen;
                    minStart = left;
                }

                char leftChar = s.charAt(left);
                freqW[leftChar]--;

                if (freqT[leftChar] > 0 && freqW[leftChar] < freqT[leftChar]) {
                    have--;
                }

                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
}

