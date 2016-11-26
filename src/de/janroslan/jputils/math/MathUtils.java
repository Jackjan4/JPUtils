package de.janroslan.jputils.math;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author jackjan
 */
public class MathUtils {

    /**
     * Checking if a number is prime by checking if the number is divisible by
     * an odd number between 2 and sqrt(number)
     *
     * @param number
     * @return
     */
    public static boolean isPrime(int number) {

        // 
        if (number == 2) {
            return true;
        }

        // If the number is even, it is not prime
        if (number % 2 == 0) {
            return false;
        }

        // Check every odd number till the sqrt of the number
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the prime factors of a number. TODO: Rethink this algorithm on my own
     * to maybe get a more efficient solution
     *
     * @param number
     * @return - A List with the primefactors of the number
     */
    public static ArrayList<Integer> primeFactors(int number) {
        ArrayList<Integer> result = new ArrayList<>();
        int n = number;

        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                result.add(i);
                n /= i;
            }
        }

        return result;
    }

    
    /**
     * Checks if a number is a square by taking the root of it and checking
     * whether it's an integer
     *
     * @param number
     * @return
     */
    public static boolean isSquare(int number) {
        double root = Math.sqrt(number);
        int intRoot = (int) root;

        return Math.pow(intRoot, 2) == number;
    }

    
    /**
     * Creates a rectangle which has an area which equals the given count.
     * This rectangle is trying to be as quadratic as possible
     *
     * @param count
     * @return
     */
    public static int[] equalSquare(int count) {
        int[] result = new int[2];
        
        // Calculate tiling ratio
        if (MathUtils.isSquare(count)) {
            // Count of files is a square number

            int sqrt = (int) Math.sqrt(count);
            result[0] = sqrt;
            result[1] = sqrt;
        } else if (MathUtils.isPrime(count)) {
            // Count of files is prime

            result[1] = 1;
            result[0] = count;
        } else {
            // Count of files is not a prime or square

            List<Integer> factors = MathUtils.primeFactors(count);

            // if only two prime factors -> use them as tiling ratio
            if (factors.size() == 2) {
                result[0] = factors.get(0);
                result[1] = factors.get(1);
                return result;
            }

            // Get most equal tiling ratio
            int rounds = (int) Math.ceil(factors.size() / 4.0);
            result[0] = 1;
            result[1] = 1;
            for (int i = 0; i < rounds; i++) {
                // X
                result[0] *= factors.get(0) * factors.get(factors.size() - 1);
                factors.remove(0);
                factors.remove(factors.size() - 1);

                // Y
                if (factors.isEmpty()) {
                    break;
                }

                if (factors.size() % 2 == 0) {
                    int mid = factors.size() / 2;
                    result[1] *= factors.get(mid) * factors.get(mid - 1);
                    factors.remove(mid);
                    factors.remove(mid - 1);
                } else {
                    int mid = (int) Math.floor(factors.size() / 2.0);
                    result[1] *= factors.get(mid);
                    factors.remove(mid);
                }
            }
        }
        return result;
    }
}
