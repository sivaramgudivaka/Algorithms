/* https://leetcode.com/problems/longest-absolute-file-path */

package leet;

import java.util.Stack;

public class AbsolutePath {
	public static void main(String[] args){
		String in = "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext";
		String y = "a\n\tb\n\t\tc\n\t\t\td\n\t\t\t\te.txt\n\t\t\t\talsdkjf.txt\n\t\tskdjfl.txtlsdkjflsdjflsajdflkjasklfjkasljfklas\n\tlskdjflkajsflj.txt";
		System.out.println(y);
		System.out.println(lengthLongestPath(y));
	}
	
    public static int lengthLongestPath(String input) {
    	 Stack<Integer> st = new Stack<>();
         String[] words = input.split("\n");
         int level, pathLen = 0, max=0, wordLen=0;
         for(String word : words){
             level = word.lastIndexOf('\t')+1;
             while(st.size() > level){
            	 pathLen -= st.pop();
             }
             wordLen = word.length()-level;
             if(word.contains("."))
                 max = Math.max(max, pathLen+wordLen+st.size());
             else{
                 st.push(wordLen);
                 pathLen += wordLen;
             }
         }
         return max;
    }
}