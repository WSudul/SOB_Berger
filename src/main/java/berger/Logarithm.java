package berger;

public class Logarithm
{

    //#TODO rework this. Current implementation will provide incorrect results (log(a)/log(b))

    public static double logb( double a, double b )
    {
        return Math.log(a) / Math.log(b) ;
    }

    public  static  double  logb( Number a, Number b){
        if(a.doubleValue() <0 || b.intValue()==1){
            throw new IllegalArgumentException("Illegal logarthim base");
        }

        if(a.doubleValue() <0){
            throw  new IllegalArgumentException("Illegal logarthim exponent");
        }

        return logb(a.doubleValue(),b.doubleValue());
    }

    public static double log2( double a )
    {
        return logb(a,2.0);
    }

}
