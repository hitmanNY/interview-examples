package playpen;

public class RegEx {
    public static String seed = "thisIsTheEnd234";
    
    public static String  pattern = "[a-zA-Z0-9]+";
//    public static String  negatedPattern = ".*[^a-zA-Z0-9].*";
    public static String  negatedPattern = ".*(?![a-zA-Z0-9]{1}).*";
    
    public static void main(String[] args){
        
        if ( seed.matches(pattern)){
            System.out.println(seed + " matches " + pattern);
        }
        
        if ( seed.matches(negatedPattern)){
            System.out.println(seed + " matches negated " + negatedPattern);
        } else {
            System.out.println("NOTHING...");

        }
    }

}
