class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int n = arr.length;
        int left = 0;
        int right = n-1;
        int soln = 0;
        while(left<right){
            int mid = left+(right-left)/2;
            if(arr[mid]>arr[soln]){
                soln = mid;
            }
            else if(arr[mid]>arr[mid+1]){
                right--;
            }
            else{
                left++;
            }
        }
        return soln;
    }
}
