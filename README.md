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
