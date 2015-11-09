package prueba;

/**
 * @author JCORREA
 */
public class Simpson {
    
    
    //Simpsons 1/3
    public static void main(String [] args){
        System.out.println("f(x) = xlnx" + "\n");
        trapezoidal(Funcion.fun_xlnx, 4, 1, 2,0.63629);
        simpson1_3(Funcion.fun_xlnx, 4, 1, 2,0.63629);
        
        System.out.println("f(x) = x/x2+4" + "\n");
        trapezoidal(Funcion.fun_x_x24, 8, 1, 3,0.47776);
        simpson1_3(Funcion.fun_x_x24, 8, 1, 3,0.47776);
        
        System.out.println("f(x) = tanx" + "\n");
        trapezoidal(Funcion.fun_tan, 8, 0, 1.1781,0.96055);
        simpson1_3(Funcion.fun_tan, 8, 0, 1.1781,0.96055);
    }
    
    private static void simpson1_3(Funcion f, int n, double a, double b, double real){
        //f(x) = x³

        double h = (b-a)/n;
        double res = 0;

        //sumamos primer y ultimo termino
        switch(f){
                case fun_x3: res = Math.pow(a, 3) + Math.pow(b, 3);
                    break;
                case fun_xlnx:  res += ((a)* Math.log((a))) + ((b)* Math.log((b)));
                    break;
                case fun_x_x24: res += ((a)/(Math.pow((a),2)+4)) + ((b)/(Math.pow((b),2)+4));
                    break;
                case fun_tan:   res += (Math.tan(a)) + (Math.tan(b));
                    break;
        }
        
        for(int i = 1; i < n; i++){
            switch(f){
                case fun_x3: res += (i%2==0?(2*(Math.pow(a+(i*h), 3))):(4*(Math.pow(a+(i*h), 3))));
                    break;
                case fun_xlnx:  res += (i%2==0?(2*((a+(i*h))* Math.log((a+(i*h))))):(4*((a+(i*h))* Math.log((a+(i*h))))));
                    break;
                case fun_x_x24: res += (i%2==0?(2*((a+(i*h))/(Math.pow((a+(i*h)),2)+4))):(4*((a+(i*h))/(Math.pow((a+(i*h)),2)+4))));
                    break;
                case fun_tan:   res += (i%2==0?(2*(Math.tan(a+(i*h)))):(4*(Math.tan(a+(i*h)))));
                    break;
            }
                    
        }
        
        System.out.println("\tRespuesta[simpson]: " + ((h/3)*res)); 
        System.out.println("\tError Relativo: " + getErrorRelativo(real, ((h/3)*res)) + "%");
    }
    
    private static void trapezoidal(Funcion f, int n, double a, double b, double real){

        double h = (b-a)/n;
        double res = 0;

        //sumamos primer y ultimo termino
        switch(f){
                case fun_x3: res = Math.pow(a, 3) + Math.pow(b, 3);
                    break;
                case fun_xlnx:  res += ((a)* Math.log((a))) + ((b)* Math.log((b)));
                    break;
                case fun_x_x24: res += ((a)/(Math.pow((a),2)+4)) + ((b)/(Math.pow((b),2)+4));
                    break;
                case fun_tan:   res += (Math.tan(a)) + (Math.tan(b));
                    break;
        }
        
        
        for(int i = 1; i < n; i++){
            switch(f){
                case fun_x3: res += 2*(Math.pow(a+(i*h), 3));
                    break;
                case fun_xlnx:  res += 2*((a+(i*h))* Math.log((a+(i*h))));
                    break;
                case fun_x_x24: res += 2*((a+(i*h))/(Math.pow((a+(i*h)),2)+4));
                    break;
                case fun_tan:   res += 2*(Math.tan(a+(i*h)));
                    break;
            }
        }
        System.out.println("\tRespuesta[trapezoidal]: " + ((h/2)*res)); 
        System.out.println("\tError Relativo: " + getErrorRelativo(real, ((h/2)*res)) + "%");
    }
    
    private static double getErrorRelativo(double real, double aproximacion){
        return ((aproximacion-real)/real)*100;
    }
    
    private enum Funcion{
        fun_x3,
        fun_xlnx,
        fun_x_x24,
        fun_tan
    }
}
