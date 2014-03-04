/** An implementation of the interface Stack using the dynamic
 *  array technique.
 * 
 * @author Marcel Turcotte
 */

public class DynamicArrayStack<E> implements Stack<E> {

    /** A reference to an array that holds the elements of the stack.
     */

    private E[] elems;

    /** An instance variable that keeps track of the position of
     *  the next free cell. For an empty stack the value is 0.
     */

    private int top = 0;

    /** Creates an empty stack.
     */

    @SuppressWarnings( "unchecked" )

    public DynamicArrayStack( int increment ) {
	elems = (E[]) new Object[ 25 ];
    }

    /** Tests if this stack is empty.
     *
     * @return true if this stack contains no elements.
     */

    public boolean isEmpty() {
	return top == 0;
    }

    /** Returns the top element of this stack without removing it.
     *
     * @return the top element of the stack.
     */

    public E peek() {
	return elems[ top-1 ];
    }

    /** Returns and remove the top element of this stack.
     *
     * @return the top element of the stack.
     */

    public E pop() {
	// save the top element
	E saved = elems[ --top ];
	// scrub the memory, then decrements top
	elems[ top ] = null; 
	return saved;
    }

    /** Puts the element onto the top of this stack.
     *
     * @param elem the element that will be pushed onto the top of the stack.
     */

    public void push( E elem ) {
	// stores the element at position top, then increments top
	elems[ top++ ] = elem;
    }

    /** Returns a string representation of this object.
     *
     * @return a string representation of this object.
     */

    public String toString() {

	StringBuffer b;
	b = new StringBuffer( "DynamicArrayStack: {" );

	for ( int i=top-1; i>=0; i-- ) {
	    if ( i!=top-1 ) {
		b.append( "," );
	    }
	    b.append( elems[ i ] );
	}

	b.append( "}" );

	return b.toString();
    }

}
