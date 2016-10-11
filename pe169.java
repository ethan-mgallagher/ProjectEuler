/**
 * Created by ethan gallagher on 9/30/16.
 */


import java.util.ArrayList;
import java.math.*;

//Project Euler Problem 169

//Problem Prompt:
/**
Define f(0)=1 and f(n) to be the number of different ways n can be expressed as a sum of integer powers of 2 using
each power no more than twice.

        For example, f(10)=5 since there are five different ways to express 10:

        1 + 1 + 8
        1 + 1 + 4 + 4
        1 + 1 + 2 + 2 + 4
        2 + 4 + 4
        2 + 8

        What is f(10^25)?
*/
//Notes:
/**There are much faster and more elegant solutions to this problem using binary
I tried solving this problem by finding a direct formula in base ten. The
 recurrence relation I found is explained in these notes.

If we represent our sum of powers in max-order as a list of integers, with each integer being
either the initial power or the most recent decrement, we get something like this:

        8 + 2                   ==      31
        8 + 1 + 1               ==      30
        4 + 4 + 2               ==      21
        4 + 4 + 1 + 1           ==      20
        4 + 2 + 2 + 1 + 1       ==      10



   We can generate all the possible sums for a given number by finding it's max composition as a sum of
   powers of two, representing these powers in the above manner and then finding all the combinations
   under a set of constraints (a) no digit goes below 0 (b) no digit will ever be decremented below the initial
   value of the digit to its right

   ex. for 20
   42
   41
   40
   32
   31
   30
   21
   20

   So 8 possible sums exist for 20. This gives the correct answer.

   To find this number for larger values in non-exponential time, we must use a recurrence relation

   We define two recurrence relations, dec(n) and ndec(n), starting from the rightmost digit, moving left.
   These recurrence relations represent the number of valid combinations where a given digit is decremented and
   not decremented respectively. It must be emphasized that we recur from the rightmost digit to the left.

   The integer value at a specific digit is represented here as d[n]

   d = { rightmost digit .... leftmost digit }

   Base cases ( rightmost digit ):

   dec(1) = d[1]
   ndec(1) = 1

   dec(n) = ( d[n] - (d[n - 1] + 1) ) * ( ndec(n-1) + dec(n-1) ) + dec(n-1)
   ndec(n) = dec(n-1) + ndec(n-1)

 The total number of combinations is
  dec(n) + ndec(n)

 This visual aid may be useful:

 Our representation of 54 = 5421. Let's see the combinations for each digit from left to right

 n=1      n=2       n=3     n=4
 1        21        421     5421
 0        20        420     5420
          10        410     5410
                    321     5321
                    320     5320
                    310     5310
                    210     4210
                            4321
                            4320
                            4310
                            4210

d = { 1, 2, 4, 5 }

The number of combinations is equal to the number of times the MSD is either decremented or not decremented for n=4.
We start with n=1 and work our way rightwards, dec(n) and ndec(n) being governed by the relations above

This lends itself very well to a dynamic programming implementation, found below with minimal further comment.
 */
public class pe169 {
    private static BigInteger target = new BigInteger("10000000000000000000000000"); // this is our target number

    //dynamic programming implementation of algorithim described above
    //this function uses int arrays whereas the function that finds
    //powers returns an arraylist. I convert the arraylist to an int[] in
    //main. Sloppy but convenient
    private static long calc(int[] powers) {

        int levels = powers.length;
        int diff;
        int prev;

        //reverse the array, now in min-order
        int[] datum = new int[levels];
        for (int i = 0; i < levels; i++) {
            datum[i] = powers[levels - 1 - i];
        }


        long[] ndec = new long[levels];
        long[] dec = new long[levels];

        for (int i = 0; i < levels; i++) {
            if (i == 0) { // special case first digit on the right
                dec[i] = datum[i]; //will be decremented x times ( down to zero )
                ndec[i] = 1; // will not be decremented one time
            } else {
                //general case
                prev = i - 1;
                diff = datum[i] - (datum[prev] + 1); //difference between left digit and right digit + 1
                dec[i] = diff * (ndec[prev] + dec[prev]) + dec[prev];
                ndec[i] = ndec[prev] + dec[prev];
            }
        }

        return dec[levels - 1] + ndec[levels - 1];
    }

    //we need to find the max power-of-two summation for our target
    //here's another point where a binary-oriented implementation would be easier
    private static ArrayList<Integer> findMaxPowers() {
        ArrayList<Integer> powers = new ArrayList<>();
        BigInteger sum = new BigInteger("0");
        BigInteger expBase = new BigInteger("2");

        BigInteger diff = new BigInteger(target.toString()); //difference between our current sum and target
        int exp = 0;
        while (!diff.equals(0) && (exp >= 0)) {//while we don't have the max power-of-two summation
            exp = (int) (Math.log(diff.doubleValue()) / Math.log(2)); //get greatest power of two less than diff
            if (exp > 0) {
                sum = sum.add(expBase.pow(exp)); // add this power of two to our sum
                diff = target.subtract(sum); //update our difference
                powers.add(exp); //add this exponent to the list
            }
        }

        return powers;
    }

    public static void main(String[] args) {

        //  int[] test20 = { 4, 2};

        int[] test14 = {3, 2, 1};

        int[] test22 = {4, 2, 1};

        int[] test54 = {5, 4, 2, 1};

        int[] test29 = {4, 3, 2, 0};

        long op;

//        int op = calc( test20 );

        // a couple of tests
        op = calc(test14);
        System.out.println(" For 14 : " + op);

        op = calc(test22);
        System.out.println(" For 22 : " + op);

        op = calc(test54);
        System.out.println(" For 54 : " + op);

        op = calc(test29);
        System.out.println(" For 29 : " + op);

    //find the max power-of-two composition of our target, 10^25
        ArrayList<Integer> ret = findMaxPowers();
     // feed these powers into an array
     int[] powwa = new int[ ret.size() ];
        for ( int i = 0; i < ret.size() ; i++ ){
            powwa[i] = ret.get(i);
        }

        op = calc( powwa );

        System.out.println( " For 10^25 : " + op );

    }

}
