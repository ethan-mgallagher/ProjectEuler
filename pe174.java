/**
 * Created by ethan gallagher on 10/10/16.
 */
/*
Prompt:
We shall define a square lamina to be a square outline with a square "hole" so that the shape possesses vertical
and horizontal symmetry.

Given eight tiles it is possible to form a lamina in only one way: 3x3 square with a 1x1 hole in the middle.
However, using thirty-two tiles it is possible to form two distinct laminae.

( a 6x6 with a 2x2 hole and a 9X9 with a 7x7 hole )

If t represents the number of tiles used, we shall say that t = 8 is type L(1) and t = 32 is type L(2).

Let N(n) be the number of t ≤ 1000000 such that t is type L(n); for example, N(15) = 832.

What is ∑ N(n) for 1 ≤ n ≤ 10?
 */
/*
Notes:

We see from the structure of any square laminae that the number of tiles used will be one square minus another square.
It is convenient to represent the length of the inner square as b, and the width of (one side )
of the enclosing outer square as x. The number of tiles used to make the square laminae, t, is thus
t = ( b + 2x )^2 - b^2 = 4x^2 + 4bx = 4(x^2 - bx) = 4(x(x+b))
so
t = 4(x(x+b))
and
t/4 = (x(x+b))

If we thus divide the number of tiles at our disposal by 4, and then consider the quantity ( x + b ) as a
single number c, we can reduce this problem to finding all the minimum-ordered factorizations (x * c) of (t/4). Note we
say minimum-ordered because as c = ( x + b ), c > x.

So for t = 32, (t/4) = 8:

 (x, c) (1,8) is valid, whereas (8,1) is not as c < x. It can be seen that it would be impossible to find an x and b
  such that (x (x + b)) reduces to (8 ( 1)) unless we admit negative side lengths, which we do not in this problem.

 The number of tiles in our problem is 1000000, so we only need to consider factorizations up to 250000. We create an
 integer array of equal size to our ceiling (250000). Then, for each number we are considering, we simply check
 to see how many valid factorizations we can find, incrementing the appropriate cell in the array for each one.

 To find the some of N(n) for 1<= n <= 10, we simply iterate through the array and increment a counter if
 the value in each cell is between 1 and 10.

 This algorithm will be inefficient ( and incorrect )if we do not carefully consider how to check factorizations.
 Since we are considering factorizations of the form (x * c) where x < c, the largest factor we have to consider is
 the one that is immediately before the square root of t in a minimum-ordering.

 */
public class pe174 {

    public static void main( String[] args ){

        int top = 250001;// one higher than the ceiling because I skip the zeroth array index for convenience
        int[] arr = new int[top];//to keep track of valid factorizations for each t
        long ceil;
        for ( int i = 2; i < top; i++ ){
            ceil = (int)Math.ceil( Math.sqrt(i) );//Tricky part. We must use Math.ceil for cases in which
            //there is a valid factor immediately before a square root.
            for ( int j = 1 ; j < ceil ; j++ ){
                if ( i % j == 0 ) arr[i] += 1;
            }
        }


        int counter = 0;

            for (int i : arr ) {//System.out.println( " size/4, count " + i + ", " + arr[i]);
                if ( i > 0 && i < 11) {// 1 <= n <= 10
                    counter += 1;
                }
            }

        System.out.println( counter ); //209566

    }
}
