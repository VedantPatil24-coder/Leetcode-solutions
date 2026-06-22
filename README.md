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

# LeetCode 1781 — Beauty Sum: Study Notes

Fix a left pointer `i`, slide a right pointer `j` from `i` to end — this enumerates all O(n²) substrings. For each substring, scan the 26-length frequency array to find max and min.

This gives **O(n² × 26) = O(n²)** time, which is optimal here.

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
