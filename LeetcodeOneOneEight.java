class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        for(int i = 0; i<numRows; i++){
            List<Integer> row = new ArrayList<>();
            if(i == 0){
                row.add(1);
            } else{
                List<Integer> previousRow = triangle.get(i-1);
                row.add(1);

                for(int j = 1; j < previousRow.size(); j++){
                int value = previousRow.get(j - 1) + previousRow.get(j);
                row.add(value);
                }

                row.add(1);
            }
            triangle.add(row);
        }
        return triangle;
    }
}


















//         for (int r = 0; r < numRows; r++) {
//             List<Integer> row = new ArrayList<>();
            
//             List<Integer> previousRow = triangle.get(r - 1);
//             for (int j = 1; j < previousRow.size(); j++) {
//                 row.add(1);
//                 int value = previousRow.get(j - 1) + previousRow.get(j);
//                 row.add(value);
//                 row.add(1);
//             }
//             // for (int i = 0; i <= r; i++) {
//             // long value = (factorial(r) / (factorial(r - i) * factorial(i)));
//             // row.add((int)value);
//             // }

//             triangle.add(row);
//         }

//         return triangle;
//     }
// }
//     public static long factorial(int n) {
//         if (n == 1 || n == 0) {
//             return 1;
//         }
//         return n * factorial(n - 1);
//     }
// }
