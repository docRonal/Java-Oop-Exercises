public class Main {
    public static void main(String[] args) {
        if(args.length < 3){
            System.out.println("less arguments");
            return;
        }
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double c = Double.parseDouble(args[2]);
        double delta = b*b - 4*(a*c);
        if(delta > 0 ){
            if(a == 0.0){
                System.out.println("argument a = 0");
                return;
            }
           double x1 =  (-b + Math.sqrt(delta)) / (2 * a);
           double x2 =  (-b - Math.sqrt(delta)) / (2 * a);
           System.out.println(x1);
           System.out.println(x2);
           return;
        }
        if (delta < 0) {
        System.out.println("No answers");
        return;
        }
        if(delta == 0.0){
            if(a == 0.0){
                System.out.println("argument a = 0");
                return;
            }
            double x = -b/(2*a);
            System.out.println("1 rozwiazanie");
            System.out.println(x);
        }
        return;
    }
}
