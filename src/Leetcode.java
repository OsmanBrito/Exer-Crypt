import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class Leetcode {

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        BigInteger primo = new BigInteger(bytes.length, random).abs();

//        while (!Util.isPrime(primo)) {
//            primo = new BigInteger(bytes.length, random).abs();
//        }
//        System.out.println(primo);
//        List<BigInteger> raizPrimitiva = Util.calculatePrimitiveRoots(primo);
//        System.out.println(raizPrimitiva);
//        System.out.println(raizPrimitiva.get(raizPrimitiva.size() - 1));
    }



}

