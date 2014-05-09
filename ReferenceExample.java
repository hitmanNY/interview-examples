package playpen;

public class ReferenceExample {
    String name;
    
    public ReferenceExample(String value){
        this.setAttribute(value);
    }
    public void setAttribute(String value){
    	name = value;
    }
    public static void main(String[] args){
          ReferenceExample f = new ReferenceExample("f");
          System.out.println("FOO:  F value=" + f.name);
          changeReference(f); // It won't change the reference!
          System.out.println("FOO:  F value=" + f.name);
          modifyReference(f); // It will modify the object that the reference variable "f" refers to!
          System.out.println("FOO:  F value=" + f.name);
    }
    public static void changeReference(ReferenceExample a){
          System.out.println("FOO:  A value=" + a.name);
          ReferenceExample b = new ReferenceExample("b");
          a = b;
          System.out.println("FOO:  A value=" + a.name);
          System.out.println("FOO:  B value=" + b.name);
    }
    public static void modifyReference(ReferenceExample c){
          c.setAttribute("c");
    }
}
