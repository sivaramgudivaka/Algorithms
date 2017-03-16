/* https://leetcode.com/problems/range-sum-query-mutable */
package leet;

public class rangeSumQuery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] nums = new int[]{1, 3, 5};
		NumArray nArr = new NumArray(nums);
		nArr.update(0, 5);
		System.out.println(nArr.sumRange(0, 2));
		nArr.update(0, -1);
		System.out.println(nArr.sumRange(2, 2));
		nArr.update(2, -3);
		System.out.println(nArr.sumRange(1, 2));
	}

}

class NumArray {
    
    private SegmentTree st;
    
    public NumArray(int[] nums) {
        st = new SegmentTree(nums);
    }
    
    public void update(int i, int val) {
        st.updateSum(i, val);
    }
    
    public int sumRange(int i, int j) {
       return st.querySum(i, j);
    }
}

class SegmentTree {
	private int[] input, indices;
	private int len;

	public SegmentTree(int[] input) {
		this.len = input.length;
		if(len != 0){
		    this.input = input;
		    int height = (int)Math.ceil(Math.log(len)/Math.log(2));
            this.indices = new int[(2 << height)-1];
            constructTree(0, len-1, 0);
		}
	}
	
	private int constructTree(int low, int high, int pos) {
		if(low == high){
			indices[pos] = input[low];
		}else{
			int mid = (low + high)/2;
	        indices[pos] = constructTree(low, mid, 2*pos+1) + constructTree(mid+1, high, 2*pos+2);
		}
		return indices[pos];
	}
	
	public void updateSum(int i, int val){
	    if(len != 0)
	        updateSum(i, 0, len-1, val, 0);
	}
	
	private int updateSum(int i, int low, int high, int val, int pos){
		if(i >= low && i <= high){
		    if(low == high){
		        int temp = indices[pos];
		        indices[pos] = val;
		        return val - temp;
		    }else{
		        int mid = (low + high)/2;
		        int delta = updateSum(i, low, mid, val, 2*pos+1) + updateSum(i, mid+1, high, val, 2*pos+2);
		        indices[pos] += delta;
			    return delta;
		    }
		}
		return 0;
	}
	
	private int queryRangeSum(int low, int high, int i, int j, int pos){
		if(i > high || j < low){
			return 0;
		}
		if(low >= i && high <= j){
			return indices[pos];
		}
		int mid = (low + high)/2;
		return queryRangeSum(low, mid, i, j, 2*pos+1) + queryRangeSum(mid+1, high, i, j, 2*pos+2); 
	}
	
	public int querySum(int i, int j){
	    if(len == 0)    return 0;
		return queryRangeSum(0, len-1, i, j, 0);
	}
}