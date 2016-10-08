/**
 * Created by ethan gallagheron 10/7/16.
 */

/*
We shall define a square lamina to be a square outline with a square "hole" so that the shape possesses vertical and
horizontal symmetry. For example, using exactly thirty-two square tiles we can form two different square laminae:


With one-hundred tiles, and not necessarily using all of the tiles at one time, it is possible to form forty-one
different square laminae.

Using up to one million tiles how many different square laminae can be formed?
 */
/*
To solve this problem we simply observe that to wrap a hollow of size n we need ( n * 4 + 4 ) tiles.
We can wrap a hollow multiple times by treating each successive wrap as a wrap of a n + 2 sized hollow.

The biggest hollow we can wrap with t tiles is
n = (t - 4) div 4

I use a simple nested loop algorithm here, counting the number of possible laminae with each respective
hollow size until the hollow size is too big to be wrapped even once

A more efficient algorithm should be possible but it is hardly necessary given the
small n
 */
public class pe173 {

    public static void main( String[] args ){
        long target = 1000000;

        int hollow = 1;//the actual size of our current hollow
        int current; //the size of our hypothetical hollow, used to calculate multiple wrappings
        long available; //number of tiles we currently have available
        long counter = 0; //how many laminae we can make
        boolean tooBig = false;

        while ( !tooBig ){ //while the hollow is not too large
            current = hollow;//reset current to hollow size
            available = target;//reset available tiles to target

            while ( available >= (current * 4 + 4) ){//while we have tiles left
                available -= (current * 4 + 4); //do the wrap
                current = current + 2; //we must now wrap this size
                counter += 1;
            }

            hollow += 1; //next size up
            if ( hollow * 4 + 4 > target ){//if we can't wrap this size
                tooBig = true;
            }
        }

        System.out.println( counter );
    }



}
