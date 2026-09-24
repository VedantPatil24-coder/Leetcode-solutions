class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> arr = new ArrayList<>();
        int startRow = 0;
        int startColumn = 0;
        int endRow = matrix.length-1;
        int endColumn = matrix[0].length-1;

        while(startRow <= endRow && startColumn <= endColumn){
            // Top
            for(int j = startColumn; j<=endColumn; j++){ 
                arr.add(matrix[startRow][j]);
            }

            // Right
            for(int i = startRow+1; i<=endRow; i++){
                arr.add(matrix[i][endColumn]);
            }

            // Bottom
            for(int j = endColumn-1;j>=startColumn; j--){
                if(startRow==endRow){
                    break;
                }
                arr.add(matrix[endRow][j]);
            }

            // Left
            for(int i = endRow-1; i>=startRow+1; i--){
                if(startColumn==endColumn){
                    break;
                }
                arr.add(matrix[i][startColumn]);
            }   
            
            startColumn++;
            startRow++;
            endColumn--;
            endRow--;
            }
        
        return arr;
    }
}