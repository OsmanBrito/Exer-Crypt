package DH;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class UtilDH{

//    public static boolean isPrime(BigInteger number){
//        return number.isProbablePrime(1);
//    }
//
//    public static List<BigInteger> calculatePrimitiveRoots(BigInteger value){
//        List<BigInteger> coprimes = coprimesOf(value);
//        List<BigInteger> primitiveRoots = new ArrayList<BigInteger>();
//        BigInteger aux = null;
//        List<BigInteger> residues = new ArrayList<BigInteger>();
//        for (BigInteger coprime : coprimes) {
//            residues.clear();
//            congruenceVerify: for (int k = 1; k < value.intValue(); k++) {
//                aux = (coprime.pow(k)).mod(value);
//                if (aux == ZERO || residues.contains(aux)) {
//                    break congruenceVerify;
//                }
//                residues.add(aux);
//            }
//            if (residues.size() == value.intValue() - 1) {
//                primitiveRoots.add(coprime);
//            }
//        }
//        return primitiveRoots;
//    }
//
//    private static List<BigInteger> coprimesOf(BigInteger value) {
//        List<BigInteger> coprimes = new ArrayList<BigInteger>();
//        BigInteger countdown = value.subtract(ONE);
//        while (countdown.compareTo(ZERO) != 0) {
//            if (countdown.gcd(value).compareTo(ONE) == 0) {
//                coprimes.add(countdown);
//            }
//            countdown = countdown.subtract(ONE);
//        }
//        return coprimes;
//    }


    public static BigInteger[] geraQA(int numBits) {
        BigInteger q;
        while(true) {
            q = BigInteger.probablePrime(numBits, new SecureRandom());
            System.out.println("Provável primo: " + q);
            if (q.isProbablePrime(400)) {
                break;
//                if (isPrime(q)) {
//                    break;
//                }
            }
            System.out.println("[" + q + "] Não é primo.");
        }
        System.out.println("[" + q + "] Encontrou número primo.");
        BigInteger a = findPrimitiveRoot(q);
        return new BigInteger[] { q, a };
    }

    public static void main(String[] args) {
        BigInteger[] QA = UtilDH.geraQA(32);
        System.out.println("q = " + QA[0].toString());
        System.out.println("a = " + QA[1].toString());
    }

    private static BigInteger findPrimitiveRoot(BigInteger n) {
        Set<BigInteger> s = new HashSet<>();

        // Check if n is prime or not
        if (!n.isProbablePrime(400))
            return null;

        // Find value of Euler Totient function of n
        // Since n is a prime number, the value of Euler
        // Totient function is n-1 as there are n-1
        // relatively prime numbers.
        BigInteger phi = n.subtract(UM);

        // Find prime factors of phi and store in a set
        findPrimefactors(s, phi);
        System.out.println("Encontrou fatores primos.");

        // Check for every number from 2 to phi
        for (BigInteger r = DOIS; r.compareTo(phi) <= 0; r = r.add(UM))
        {
            // Iterate through all prime factors of phi.
            // and check if we found a power with value 1
            boolean flag = false;
            for (BigInteger it : s) {
                // Check if r^((phi)/primefactors) mod n
                // is 1 or not
                if (Objects.equals(power(r, phi.divide(it), n), UM))
                {
                    flag = true;
                    break;
                }
            }


            // If there was no power with value 1.
            if (!flag)
                return r;
        }

        // If no primitive root found
        return null;
    }

    private static BigInteger UM = BigInteger.valueOf(1);
    private static BigInteger DOIS = BigInteger.valueOf(2);
    private static BigInteger TRES = BigInteger.valueOf(3);
    private static BigInteger ZERO = BigInteger.valueOf(0);
    private static BigInteger CINCO = BigInteger.valueOf(5);
    private static BigInteger SEIS = BigInteger.valueOf(5);


    private static boolean isPrime(BigInteger n) {
        // Corner cases
        if (n.compareTo(UM) <= 0)  return false;
        if (n.compareTo(TRES) <= 0)  return true;

        // This is checked so that we can skip
        // middle five numbers in below loop
        if (n.mod(DOIS).compareTo(ZERO) == 0 || n.mod(TRES).compareTo(ZERO) == 0) return false;

        for (BigInteger i = CINCO; i.pow(2).compareTo(n) <= 0; i=i.add(SEIS))
            if (n.mod(i).compareTo(ZERO) == 0 || n.mod(i.add(DOIS)).compareTo(ZERO) == 0)
                return false;

        return true;
    }

    /* Iterative Function to calculate (x^n)%p in
       O(logy) */
    public static BigInteger power(BigInteger x, BigInteger y, BigInteger p) {
        return x.modPow(y, p);
    }

    // Utility function to store prime factors of a number
    private static void findPrimefactors(Set<BigInteger> s, BigInteger n)
    {
        // Print the number of 2s that divide n
        while (n.mod(DOIS).compareTo(ZERO) == 0)
        {
            s.add(DOIS);
            n = n.divide(DOIS);
        }
        // n must be odd at this point. So we can skip
        // one element (Note i = i +2)
        for (BigInteger i = TRES; i.compareTo(sqrt(n)) <= 0; i = i.add(DOIS))
        {
            // While i divides n, print i and divide n
            while (n.mod(i).compareTo(ZERO) == 0)
            {
                s.add(i);
                n = n.divide(i);
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n.compareTo(DOIS) > 0) {
            s.add(n);
        }
    }

    private static BigInteger newtonIteration(BigInteger n, BigInteger x0)
    {
        final BigInteger x1 = n.divide(x0).add(x0).shiftRight(1);
        return x0.equals(x1)||x0.equals(x1.subtract(BigInteger.ONE)) ? x0 : newtonIteration(n, x1);
    }

    public static BigInteger sqrt(final BigInteger number)
    {
        if(number.signum() == -1)
            throw new ArithmeticException("We can only calculate the square root of positive numbers.");
        return newtonIteration(number, BigInteger.ONE);
    }

    public static BigInteger geraNumeroMenorQue(BigInteger q) {
        byte[] b = new byte[q.bitCount() / 8];
        SecureRandom rnd = new SecureRandom();
        BigInteger num;
        do {
            rnd.nextBytes(b);
            num = new BigInteger(b);
        } while (num.compareTo(q) >= 0);
        return num;
    }

}
