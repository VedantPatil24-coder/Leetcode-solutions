import java.util.Stack;

class Solution {
    public int largestRectangleArea(int[] arr) {
        int maxArea = 0;
        int nsr[] = new int[arr.length];
        int nsl[] = new int[arr.length];

        // Next smaller right 
        Stack<Integer> s = new Stack<>();

        for(int i = arr.length-1; i>=0; i--){
            while(!s.isEmpty() && arr[s.peek()] >= arr[i]){
                s.pop();
            }
            if(s.isEmpty()){
                nsr[i] = arr.length;  //-1
            }
            else{
                nsr[i] = s.peek(); //top
            }
            s.push(i);
        }

        // Next smaller left
        s = new Stack<>();

        for(int i = 0; i<arr.length; i++){
            while(!s.isEmpty() && arr[s.peek()] >= arr[i]){
                s.pop();
            }
            if(s.isEmpty()){
                nsl[i] = -1;  //-1
            }
            else{
                nsl[i] = s.peek(); //top
            }
            s.push(i);
        }

        // Current area : width = j-i-1 = nsr[i] - nsl[i] - 1 
        for(int i = 0; i<arr.length; i++){
            int height = arr[i];
            int width = nsr[i] - nsl[i] - 1;
            int currArea = height*width;
            maxArea = Math.max(currArea, maxArea);
        }
        return maxArea;
    }
}