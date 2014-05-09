package playpen;
import java.util.ArrayList;


public class StringManipulator {
    private static ArrayList<Integer> fibList = new ArrayList<Integer>();
    
    private static final String WHITESPACE = " ";
    
    public void fib(int one, int two, int max){
        if ( fibList.isEmpty()){
        	fibList.add(0);
        }
        
        if ( two <= max ){
            fibList.add(two);
            fib(two, one + two, max);
        } else {
        	System.out.println("Sequence to [" + max + "]: " + fibList);
        }
    }

    public String reverseItWithLoop(String source) {
       StringBuffer dest = new StringBuffer(source.length()); 
       
       for( int i = (source.length() - 1); i>=0; i--){
    	   dest.append(source.charAt(i));
       }
       
       return dest.toString();
    }
    
    public int computeFactorial(int num){
        int result = 0;
        
        System.out.println("num: " + num);
        
        if ( num == 1 ){
        	result = num;
        } else {
            result = computeFactorial(num - 1) * num;
        }
        
        
    	return result;
    }
    
    public String reverseItByWord(String source){
        StringBuffer result = new StringBuffer();
    	for( String word : source.split(WHITESPACE)){
            result.append(reverseItWithRecursion(word)); 
            result.append(WHITESPACE);
    	}
        return result.toString();
    }
    
    public String reverseItWithRecursion(String source){
       
       if ( source == null || source.isEmpty()){
    	   return source;
       }
       
       return reverseItWithRecursion(source.substring(1)) + source.charAt(0);
    }
	public static void main(String[] args) {
        StringManipulator test = new StringManipulator();
        test.fib(0, 1, 1000);
        System.out.println("Factorial: " + test.computeFactorial(5));
        
	}
}
