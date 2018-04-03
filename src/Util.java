import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public class Util {

    public static boolean isPrime(BigInteger number){
        return number.isProbablePrime(1);
    }

    public static List<BigInteger> calculatePrimitiveRoots(BigInteger value){
        List<BigInteger> coprimes = coprimesOf(value);
        List<BigInteger> primitiveRoots = new ArrayList<BigInteger>();
        BigInteger aux = null;
        List<BigInteger> residues = new ArrayList<BigInteger>();
        for (BigInteger coprime : coprimes) {
            residues.clear();
            congruenceVerify: for (int k = 1; k < value.intValue(); k++) {
                aux = (coprime.pow(k)).mod(value);
                if (aux == ZERO || residues.contains(aux)) {
                    break congruenceVerify;
                }
                residues.add(aux);
            }
            if (residues.size() == value.intValue() - 1) {
                primitiveRoots.add(coprime);
            }
        }
        return primitiveRoots;
    }

    private static List<BigInteger> coprimesOf(BigInteger value) {
        List<BigInteger> coprimes = new ArrayList<BigInteger>();
        BigInteger countdown = value.subtract(ONE);
        while (countdown.compareTo(ZERO) != 0) {
            if (countdown.gcd(value).compareTo(ONE) == 0) {
                coprimes.add(countdown);
            }
            countdown = countdown.subtract(ONE);
        }
        return coprimes;
    }


}
