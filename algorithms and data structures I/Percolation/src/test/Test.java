
public class Test {

    public static void main(String[] args) {
        byte a = 5;
        byte b = 6;
        System.out.println(a | b);

        byte[] t = new byte[3];
        t[0] = 4;

        byte tt = (byte) (a | b);
        System.out.println(tt);
    }

}
