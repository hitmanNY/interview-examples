package playpen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateExpression {

    // using enum to prevent run time bugs
	public enum Filter {
        NON_LATIN_CHARS("[^a-zA-Z]"),
        NON_NUMBERS("[^0-9]"),
        NONE;
            
        private String regex;
            
        private Filter(){
        	this.regex = "";
        }
        private Filter(String value){
            this.regex = value;
        }
            
        @Override
        public String toString(){
            return regex;
        }
    };
    
    public boolean isRotated(String source, String modified){
        boolean validated = false;
        
        if (source == null || modified == null){
        	return validated;
        }
        
        int startingPoint = modified.indexOf(source.charAt(0));
        if ( startingPoint > 0){
            StringBuffer buffer = new StringBuffer(modified.substring(startingPoint));
            buffer.append(modified.substring(0, startingPoint));
                
            if (source.equalsIgnoreCase(buffer.toString())){
            	validated = true;
            }
        }
        
        return validated;
    }
    
    public boolean isShifted(String source, String modified){
        boolean validated = false;
        
        if ( source == null || modified == null){
        	return validated;
        }
        
        Pattern pattern = Pattern.compile(modified + "$");
        Matcher matcher = pattern.matcher(source);
        if ( matcher.find()){
        	System.out.println(modified + " is just shifted from " + source );
            validated = true;
        }
    	
        return validated;
    }
    
    public boolean isRotatedOrShifted( String source, String modified, Filter patternToExtract){
        final String NOTHING = "";
        boolean validated = false;
        
        if ( source == null || modified == null){
        	return validated;
        }
        
        // pull matches
        String sourceMatch = source.replaceAll(patternToExtract.toString(), NOTHING);
        String modifiedMatch = modified.replaceAll(patternToExtract.toString(), NOTHING);
        
        System.out.println("Extracted:  source=" + sourceMatch + " modified=" + modifiedMatch);
      
        // check for equality
        if ( sourceMatch.equalsIgnoreCase(modifiedMatch)){
            System.out.println("equal, which is neither rotated nor shifted.");
        	return validated;
        }
       
        // check if they are rotated
        else if ( isRotated(sourceMatch, modifiedMatch)){
            System.out.println("is rotated");
        	validated = true;
            
        // check if they are shifted
        } else if ( isShifted(sourceMatch, modifiedMatch)){
            System.out.println("is shifted");
        	validated = true;
        }
        
        return validated;
    }
    
	public static void main(String[] args) {
        ValidateExpression stringValidator = new ValidateExpression();
        
        String s1 = "abcd#12";
        String s2 = "bcda#12";
        
        System.out.println(s1 + " : " + s2 + " : Shifted or rotated latin characters: " + 
        		stringValidator.isRotatedOrShifted(s1, s2, ValidateExpression.Filter.NON_LATIN_CHARS));
	}
}

