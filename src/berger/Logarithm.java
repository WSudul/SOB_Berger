package berger;

public class Logarithm
{
    public static double logb( double a, double b )
    {
        return Math.log(a) / Math.log(b) ;
    }

    public  static  double  logb( Number a, Number b){
        if(b. <0 || b==1){
            throw IllegalArgumentException("Illegal logarthim base");
        }

        if(a <0){
            throw IllegalArgumentException("Illegal logarthim exponent");
        }

        return 0.0;
    }

    public static double log2( double a )
    {
        return logb(a,2);
    }

}
