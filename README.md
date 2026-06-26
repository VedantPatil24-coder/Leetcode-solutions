# Leetcode-solutions
All the solutions of the leetcode solutions I have solved yet (With proper notes)

## Leetcode 451

Easy to understand and very simple

```java
Map<Character, Integer> map = new HashMap<>();
for (char c : s.toCharArray())
    map.put(c, map.getOrDefault(c, 0) + 1);
```
Convert the string to a char array and iterate through it. For each character, store its frequency in the map. `getOrDefault(c, 0)` returns the current count or 0 if it's the first time seeing that character.

---

```java
List<Character>[] buckets = new List[s.length() + 1];
for (int i = 0; i <= s.length(); i++)
    buckets[i] = new ArrayList<>();
```
Create an array of Lists indexed by frequency. The array size is `s.length() + 1` because the maximum possible frequency of any character is `s.length()` (e.g., `s = "aaaa"`). Index 0 is unused but included to keep indexing clean. Each slot is initialized to an empty `ArrayList` so you can safely call `.add()` later.

---

```java
for (Map.Entry<Character, Integer> e : map.entrySet())
    buckets[e.getValue()].add(e.getKey());
```
Place each character into the bucket that matches its frequency. For example, if `'a'` appears 3 times, `e.getValue()` is 3, so `'a'` goes into `buckets[3]`. This is the core idea — **frequency becomes the index**, eliminating any need to sort.

---

```java
StringBuilder sb = new StringBuilder();
for (int i = s.length(); i >= 1; i--)
    for (char c : buckets[i])
        for (int j = 0; j < i; j++)
            sb.append(c);
```
Build the result string by iterating buckets **from high to low** (highest frequency first, which is what the problem requires). For each bucket `i`, loop through every character in it, and append that character exactly `i` times. The three loops do:

- **Outer loop:** visits each frequency from high to low
- **Middle loop:** visits each character at that frequency
- **Inner loop:** appends the character `i` times (once per occurrence)

---

```java
return sb.toString();
```
Convert the `StringBuilder` to a `String` and return it.

---

## Concrete Example

For `s = "tree"`:

| Character | Frequency | Bucket |
|-----------|-----------|--------|
| `'e'` | 2 | `buckets[2]` |
| `'t'` | 1 | `buckets[1]` |
| `'r'` | 1 | `buckets[1]` |

Iterating from `i = 4` down to `i = 1`, only `buckets[2]` and `buckets[1]` are non-empty, so the output is `"eert"` or `"eetr"` — both valid.

---

## LeetCode 1781 — Beauty Sum: Study Notes

Fix a left pointer `i`, slide a right pointer `j` from `i` to end — this enumerates all O(n²) substrings. For each substring, scan the 26-length frequency array to find max and min.

This gives **O(n² × 26) = O(n²)** time, which is optimal here.

## LeetCode 424 — Character Replacement Notes

### Line-by-Line Explanation

```
int[] freq = new int[26];
```
Frequency array tracking count of each uppercase letter in the current window. Index 0 = 'A', index 25 = 'Z'.

```
int left = 0;
int right = 0;
int maxFreq = 0;
int result = 0;
```
- `left` — left boundary of the sliding window
- `right` — right boundary of the sliding window
- `maxFreq` — highest frequency of any single character seen so far in the current window
- `result` — longest valid window length found so far

```
while (right < s.length()) {
```
Expand the window by moving `right` forward each iteration.

```
freq[s.charAt(right) - 'A']++;
```
Add the new character at `right` into the window by incrementing its frequency count.

```
maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
```
Update `maxFreq` using only the character we just added. Since we add one character per iteration, only that character can increase the maximum frequency.

```
if (right - left + 1 - maxFreq > k) {
```
Check if the current window is invalid. The difference between window size and `maxFreq` is the number of replacements required. If it exceeds `k`, shrink the window.

```
    freq[s.charAt(left) - 'A']--;
    left++;
```
Shrink the window from the left by removing the leftmost character and advancing `left`.

```
result = Math.max(result, right - left + 1);
```
Record the current valid window length. This must be computed after the potential shrink so it always reflects the current `left` and `right` pointers.

```
right++;
```
Advance `right` for the next iteration.

```
return result;
```
Return the length of the longest valid substring found.

---

### The Two Bugs — What Went Wrong and Why

**Bug 1 — `while` instead of `if`**

```java
while (windowSize - maxFreq > k) {
    freq[s.charAt(left) - 'A']--;
    left++;
    // windowSize never updates here — condition uses stale value
}
```

The `while` loop keeps shrinking `left` while `maxFreq` is not decremented. This can make the condition invalid or cause the loop to behave incorrectly. The algorithm only needs to shrink the window by one position when the window becomes invalid, so `if` is the correct choice.

**Bug 2 — stale `windowSize` variable**

```java
int windowSize = (right - left + 1);  // computed before shrink
if (windowSize - maxFreq > k) {
    freq[s.charAt(left) - 'A']--;
    left++;
}
result = Math.max(result, windowSize); // records the pre-shrink size — wrong
```

`windowSize` is computed before `left` changes. If the window shrinks, the recorded size is too large. The fix is to compute the current window length from `right - left + 1` after adjusting `left`.

---

### Key Takeaway

Once you introduce pointer movement inside the loop body, avoid storing derived values like `windowSize` in a separate variable. Recompute `right - left + 1` where you actually need it so it always reflects the current state.

---

## Leetcode 1423 - Maximum Points You Can Obtain from Cards

---

### Problem Intuition

- *You pick `k` cards from either end (left or right) of the array*
- *Equivalent to: leave out a contiguous subarray of size `n-k` in the middle*
- *Maximize picked cards = Maximize totalSum - Minimize that middle window*

---

### Line-by-Line Breakdown

**Setup**

- `int totalSum = 0` → *accumulator for sum of all cards*
- `int n = cardPoints.length` → *total number of cards*
- `int windowSize = n - k` → *size of the subarray we want to EXCLUDE (the "leftover" middle window)*
- `for(int num: cardPoints) totalSum += num` → *compute total sum of all cards*

**Edge Case**

- `if(windowSize == 0) return totalSum` → *if k == n, pick all cards, answer is totalSum directly*

**Initialize First Window**

- `int windowSum = 0` → *tracks sum of current sliding window*
- `for(int i = 0; i < windowSize; i++) windowSum += cardPoints[i]` → *compute sum of first window (index 0 to windowSize-1)*
- `int minWindowSum = windowSum` → *assume first window is the minimum to start*

**Slide the Window**

- `for(int i = windowSize; i < n; i++)` → *slide window one step right each iteration*
- `windowSum += cardPoints[i]` → *add incoming right element*
- `windowSum -= cardPoints[i - windowSize]` → *remove outgoing left element (keeps window size fixed)*
- `minWindowSum = Math.min(windowSum, minWindowSum)` → *track minimum window sum seen so far*

**Answer**

- `return totalSum - minWindowSum` → *removing the minimum subarray = maximum score from picked cards*

---

**Dry Run Example**

`cardPoints = [1,2,3,4,5,6,1], k = 3`

- `n = 7, windowSize = 4, totalSum = 22`
- First window `[1,2,3,4]` → `windowSum = 10`, `minWindowSum = 10`
- Slide → `[2,3,4,5]` → `windowSum = 14`
- Slide → `[3,4,5,6]` → `windowSum = 18`
- Slide → `[4,5,6,1]` → `windowSum = 16`
- `minWindowSum = 10`
- **Answer = 22 - 10 = 12** ✅

---

### Complexity

- **Time:** `O(n)` — one pass for totalSum + one pass for sliding window
- **Space:** `O(1)` — no extra data structures

---

## Your Final Code (Annotated)

```java
class Solution {
    public int beautySum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];                  // fresh freq per left boundary
            for (int j = i; j < s.length(); j++) {
                freq[s.charAt(j) - 'a']++;             // extend window by one char

                int max = Integer.MIN_VALUE;           // ✅ outside for-each
                int min = Integer.MAX_VALUE;
                for (int f : freq) {
                    if (f > 0) {                       // ✅ guard: only present chars
                        max = Math.max(max, f);
                        min = Math.min(min, f);        // ✅ correct variable
                    }
                }
                sum += max - min;
            }
        }
        return sum;
    }
}
```

---

## Mistakes Made & Their Corrections

### Mistake 1 — `max`/`min` declared inside the for-each loop
**What you wrote:**

```java
for (int f : freq) {
    int max = Integer.MIN_VALUE;  // resets on every character
    int min = Integer.MAX_VALUE;
    ...
}
```
**The problem:** Every iteration of `for (int f : freq)` threw away the previous `max` and `min` and started fresh. You never actually compared across all 26 frequencies — you only ever saw one `f` at a time.

**The fix:** Declare them *outside* the for-each, so they accumulate across all 26 entries:

```java
int max = Integer.MIN_VALUE;
int min = Integer.MAX_VALUE;
for (int f : freq) {
    ...
}
```

**Rule to remember:** Accumulators must be declared *before* the loop they're accumulating over.

---

### Mistake 2 — Typo: `max = Math.min(...)` instead of `min = Math.min(...)`
**What you wrote:**

```java
max = Math.max(max, f);
max = Math.min(min, f);  // ❌ overwrites max, min never updated
```
**The problem:** `min` was never assigned anything. It stayed at `Integer.MAX_VALUE` forever, and `max` got overwritten with a minimum operation — both variables were wrong.

**The fix:**

```java
max = Math.max(max, f);  // ✅
min = Math.min(min, f);  // ✅
```

**Rule to remember:** When writing symmetric operations like this back to back, double-check that the left-hand side variable matches the operation.

---

### Mistake 3 — No `f > 0` guard
**What you wrote:**

```java
for (int f : freq) {
    max = Math.max(max, f);
    min = Math.min(min, f);
}
```
**The problem:** Characters absent from the current substring have `freq = 0`. Without a guard, `min` gets pulled down to `0` for every substring longer than 1 unique character, making every beauty value wrong.

**The fix:**

```java
for (int f : freq) {
    if (f > 0) {  // only consider characters actually present
        max = Math.max(max, f);
        min = Math.min(min, f);
    }
}
```

---

## Notes: Leetcode 1004 — Max Consecutive Ones III

---

### The Core Idea

The problem asks: *what's the longest subarray with at most K zeros?*

We maintain a window `[left, right]` that **only grows when we find a better (longer) window**, and shrinks by exactly 1 when it becomes invalid. Since the window never shrinks net, `right - left + 1` never decreases, so we skip tracking `max` entirely.

---

### Line-by-Line Breakdown

```
int left = 0;
int zeroCount = 0;
```

- `left` is the left boundary of our window. Starts at index 0.
- `zeroCount` tracks how many zeros are currently inside the window `[left, right]`.
- No `max` variable needed — explained at the end.

---

```
for (int right = 0; right < nums.length; right++) {
```

- `right` is the right boundary, expanding the window one step at a time.
- **Right only ever moves forward** — this is non-negotiable in sliding window.

---

```
    if (nums[right] == 0) {
        zeroCount++;
    }
```

- We just included `nums[right]` into our window, so we update our constraint tracker.
- If it's a 1, the window absorbed it for free — no bookkeeping needed.

---

```
    if (zeroCount > k) {
        if (nums[left] == 0) {
            zeroCount--;
        }
        left++;
    }
```
This is the **shrink step**. Notice it's an `if`, not a `while`. Here's why each line matters:

- `if (zeroCount > k)` — window is invalid (too many zeros). We need to shrink.
- `if (nums[left] == 0) zeroCount--` — before evicting `nums[left]`, check if it was a zero. If yes, decrement the count since that zero is leaving the window.
- `left++` — evict the leftmost element, shrinking the window from the left.

**Why `if` and not `while`?**

We only care about the *maximum* window ever seen. If the window is invalid, we shrink by exactly 1 to keep it the same size. We never shrink it *smaller* than our current best — there's no point. The window only grows when `right` outpaces `left`.

---

```
return nums.length - left;
```

- At the end of the loop, `right == nums.length`, so the window size is `nums.length - left`.
- Because the window **never shrank net** throughout the loop, this final window size equals the largest valid window we ever encountered.
- This replaces the need for `Math.max(...)` on every iteration.

---

### Why No `max` Variable?

Think about what happens to `right - left + 1` over time:

| Event | Effect on window size |
|---|---|
| `right` moves forward, window valid | size **+1** |
| `right` moves forward, window invalid | `left` also moves forward → size **stays same** |
| `left` moves without `right` moving | **never happens** |

The window size is non-decreasing. So the last window = the biggest window.

## Notes: Leetcode 1358 — `numberOfSubstrings`

```
class Solution {
    public int numberOfSubstrings(String s) {
```
Entry point. Takes the input string `s`.

---

```
        int[] lastSeen = {-1, -1, -1};
```

- Fixed array of size 3, one slot per character: `lastSeen[0]` → `'a'`, `lastSeen[1]` → `'b'`, `lastSeen[2]` → `'c'`.
- Initialized to `-1` meaning "not seen yet."

---

```
        int count = 0;
```
Accumulates the total number of valid substrings.

---

```
        for (int i = 0; i < s.length(); i++) {
```
Iterate over every character, treating `i` as the **right boundary** of the current substring.

---

```
            lastSeen[s.charAt(i) - 'a'] = i;
```

- `s.charAt(i) - 'a'` maps `'a'→0`, `'b'→1`, `'c'→2`.
- Records the **most recent index** where this character was seen.

---

```
            if (lastSeen[0] >= 0 && lastSeen[1] >= 0 && lastSeen[2] >= 0) {
```
Only proceed if **all three characters have appeared at least once** in `s[0..i]`. Before this, no valid substring ending at `i` can exist.

---

```
                int minIdx = Math.min(lastSeen[0], Math.min(lastSeen[1], lastSeen[2]));
```
Finds the **earliest** of the three last-seen positions. This is the key step — any substring ending at `i` that starts at index `≤ minIdx` is guaranteed to contain all of `a`, `b`, `c`.

---

```
                count += (minIdx + 1);
```

- Valid starting positions are `0, 1, 2, ... minIdx` — that's exactly `minIdx + 1` choices.
- Each one forms a unique valid substring ending at `i`, so add them all at once.

---

```
        return count;
```
Return the final answer after scanning the whole string.

---

## Quick Example

For `s = "abcabc"`, when `i = 5` (last `'c'`):

- `lastSeen = [3, 4, 5]`
- `minIdx = 3`
- Add `3 + 1 = 4` substrings: starting at indices `0, 1, 2, 3`

---

## Complexity

- **Time:** O(n) — single pass
- **Space:** O(1) — fixed size-3 array, no extra structures

---

### The Mental Model

```
[  valid window  ]
 ^               ^
left            right

right scouts forward → if window breaks → left takes one step to compensate
```

- **Right** is a scout that always moves forward
- **Left** is a janitor that steps forward only when the window is broken
- The janitor never asks the scout to go back *(no `right--`)*
- The janitor never over-corrects *(no `while`, only `if`)*

---

### Complexity

- **Time:** O(n) — `left` and `right` each traverse the array once
- **Space:** O(1) — only a handful of integer variables

---

# Leetcode 904- Sliding Window

## The Core Idea

You can only carry **2 types of fruit** at once. Find the **longest contiguous subarray** with at most 2 distinct values. This is a classic **variable-size sliding window** problem.

---

## Full Annotated Code

```java
class Solution {
    public int totalFruit(int[] fruits) {

        int ans = 0;      // best (longest) window length found so far
        int left = 0;     // left boundary of the sliding window
        int right = 0;    // right boundary of the sliding window

        // maps fruitType -> how many of that type are currently in the window
        Map<Integer, Integer> map = new HashMap<>();

        // expand the window rightward one step at a time
        while (right < fruits.length) {

            // --- STEP 1: Add fruits[right] into the window ---
            map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
            // getOrDefault handles the first occurrence (avoids NullPointerException)
            // map.size() now reflects how many distinct types are in the window

            // --- STEP 2: Shrink from the left while the window is INVALID ---
            while (map.size() > 2) {          // more than 2 types = invalid

                // decrement the count of the fruit at the left boundary
                map.put(fruits[left], map.get(fruits[left]) - 1);

                // if count hits 0, that type is gone from the window entirely
                // removing the key is what actually reduces map.size()
                if (map.get(fruits[left]) == 0) {
                    map.remove(fruits[left]);
                }

                left++;   // move left boundary forward (shrink the window)
            }
            // after the inner while, map.size() <= 2 → window is valid again

            // --- STEP 3: Record the best valid window length ---
            ans = Math.max(ans, right - left + 1);
            // right - left + 1 = current window length

            right++;   // expand the window for the next iteration
        }

        return ans;   // longest valid window seen across the entire array
    }
}
```

---

## Step-by-Step Logic Summary

- **Expand first** — always add `fruits[right]` before checking validity.
- **Shrink with a `while` loop, not `if`** — one removal might not be enough; loop until the window is valid.
- **Remove the key when count = 0** — this is what keeps `map.size()` accurate. If you skip this, you'll count "ghost" types that no longer exist in the window.
- **Update answer after shrinking** — only record the length once the window is guaranteed valid.

---

## Complexity

| | |
|---|---|
| **Time** | O(n) — every element is added and removed from the map at most once |
| **Space** | O(1) — the map holds at most 3 keys at any moment before shrinking brings it back to 2 |

---

## The Mental Trigger for Future Problems

Whenever you see **"longest subarray with at most K distinct elements"** → reach for this exact pattern: `HashMap` + variable sliding window.
