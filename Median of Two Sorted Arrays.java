public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        int k = (nums1.length + nums2.length + 1) / 2;
        
        double ret = (double)findKth(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, k);
        if((nums1.length + nums2.length) % 2 == 0) {
            int k2 = k + 1;
            double ret2 = (double)findKth(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, k2);
            ret = (ret + ret2) / 2;
        }
        return ret;
    }
    private int findKth(int[] A, int aL, int aR, int[] B, int bL, int bR, int k) {
        if(aR - aL > bR - bL)return findKth(B, bL, bR, A, aL, aR, k);
        if(aR - aL + 1 == 0)return B[bL + k - 1];              /*位置不能换*/
        if(k == 1)return Math.min(A[aL], B[bL]);
        
        int aCnt = Math.min(k / 2, aR - aL + 1);
        int bCnt = k - aCnt;
        if(A[aL + aCnt - 1] >= B[bL + bCnt - 1])
           return findKth(A, aL, aR, B, bL + bCnt, bR, k - bCnt);
        else
           return findKth(A, aL + aCnt, aR, B, bL, bR, k - aCnt);
     }
    /*上述方法1的原始解法*/
    private int findKth(int[] A, int aL, int aR, int[] B, int bL, int bR, int k) {
         if(aL > aR) return B[bL + k - 1];
         if(bL > bR) return A[aL + k - 1];
         
         int aMid = aL + (aR - aL) >> 1;
         int bMid = bL + (bR - bL) >> 1;
         
         if(A[aMid] <= B[bMid]) {
             if(k <= (aMid - aL) + (bMid - bL) + 1)
                 return findKth(A, aL, aR, B, bL, bMid - 1, k);
             else
                 return findKth(A, aMid + 1, aR, B, bL, bR, k - (aMid - aL) - 1);
         }
         else {
             if(k <= (aMid - aL) + (bMid - bL) + 1)
                 return findKth(A, aL, aMid - 1, B, bL, bR, k);
             else
                 return findKth(A, aL, aR, B, bMid + 1, bR, k - (bMid - bL) - 1);
         }
     }
    /*方法2
    the main idea is to find the approximate location of the median and compare the elements around it to get the final result.
    时间复杂度: O(log(min(n,m)))
		  1. binary search. suppose the shorter list is A with lenght n. the runtime is
		     O(logn), which means no matter how large B array is, it depends on the size 
		     of A. It make sense beacause if A has  only one element while B has 100 elements
		     the median must be one of A[0]], B[49], B[50] without checking anything else
		     if(A[0] <= B[49]), then B[49] is the answer, if B[49] < A[0] <= B[50] then A[0]
		     is the answer, otherwise, B[50] is the answer.
		  2. after binary search, we get the approximate location of median. Now we just
		     need to compare at most 4  elements to find the answer, this step is O(1)
		  3. the same solution can be applied to find kth element of 2 sorted arrays
    */
    public double findMedianSortedArrays_02(int[] A, int[] B) {
        int n = A.length;
        int m = B.length;
        if( n > m)
            return findMedianSortedArrays(B, A);
        // binary search
        int k = (n + m - 1) >> 1;
        int aL = 0;
        int aR = Math.min(k, n); /*right is n, not n - 1*/ 
        while(aL < aR) {
            int aMid = (aR + aL) >> 1;
            int bMid = k - aMid;
            if(A[aMid] < B[bMid])
                aL = aMid + 1;
            else 
                aR = aMid;
        }
        
        /*
         *after search, the median must between:
         *        A[aL - 1], A[aL], B[k - aL], B[k - aL + 1]
         */
        /*
         *if (n + m) is odd, the median is the larger one between: A[aL - 1], B[k - aL]
         *  and there are some corner cases we need to take care of
         */
        int a = Math.max(aL > 0 ? A[aL - 1] : Integer.MIN_VALUE, 
                         k - aL >= 0 ? B[k - aL] : Integer.MIN_VALUE); //为什么要等号=> A：[], B:[1]
        if(((n + m) & 1) == 1)
            return (double)a;
        /*
         *if (n + m) is even, the median is :
         *    (max(A[aL - 1], B[k - aL]) + min(A[aL], B[k - aL + 1])) / 2.0
         *  and there are some corner cases we need to take care of
         */
        int b = Math.min(aL < n ? A[aL] : Integer.MAX_VALUE, 
                         k - aL + 1 < m ? B[k - aL + 1] : Integer.MAX_VALUE);
        return (a + b) / 2.0;
    }
	}
