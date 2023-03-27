public class Main {
    public static void main(String[] args) {
        String test = "(234 + 245 * (asdf - 246) - 345 * (546+264*963)/(-24566))"; //= 81722.0513718147
        //String test = "-(5446-645*(5613*641/3422)/(53421-(56431/434)))"; //= -5433.2743750978725
        //String test = "-(5446-sd-645*(5613*641/3422)/(-53421-(56431/434)))";
        //(4357)-1+3*(-2+(3456))*75*(-3457)-2/2*(-(-3457))*(0)
        MathParser parser = new MathParser();
        System.out.println(parser.mathParser(test));
    }
}