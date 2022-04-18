package math_expression;
public class Test34 {

public int sideA;
public int sideB;
public int sideC;
public int perimeterTriangle;
public int radius;
public int perimeterCircle;

private External external;

public Test34(External external) {
  this.external = external;
}

  public void compute() {
sideA = 3;
			
sideB = 4;
			
sideC = ((this.external.sqrt((this.external.pow(3, 2)) + (this.external.pow(4, 2)))));
			
perimeterTriangle = 3 + 4 + ((this.external.sqrt((this.external.pow(3, 2)) + (this.external.pow(4, 2)))));
			
radius = 5;
			
perimeterCircle = ((2 * 5) * this.external.pi());
  }

public interface External {
public int pow(int n0, int n1);
public int sqrt(int n2);
public int pi();
}
}
