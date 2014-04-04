/** An implementation of the interface Stack using the dynamic
 *  array technique.
 *
 * @author Marcel Turcotte
 */

public class DynamicArrayStack<E> implements Stack<E> {

    /** A reference to an array that holds the elements of the stack.
     */

    private E[] elems;

    // Declare a constant default stack increment/decrement

    private final int defaultInc = 25;

    /** An instance variable that keeps track of the position of
     *  the next free cell. For an empty stack the value is -1.
     */

    private int top = -1;
    private int inc;

    /** Creates an empty stack.
     */

    @SuppressWarnings( "unchecked" )

    public DynamicArrayStack( int increment ) {
        this.inc = increment;
        elems = (E[]) new Object[inc];
    }

    @SuppressWarnings( "unchecked" )

    public DynamicArrayStack(  ) {
        inc = defaultInc;
        elems = (E[]) new Object[inc];
    }

    /** Tests if this stack is empty.
     *
     * @return true if this stack contains no elements.
     */

    public boolean isEmpty() {
        return top == -1;
    }

    /** Returns the top element of this stack without removing it.
     *
     * @return the top element of the stack.
     */

    public E peek() {
        if (top != -1) {
            return elems[top];
        } else {
            throw new EmptyStackException("Stack is Empty");
        }
    }

    /** Returns and remove the top element of this stack.
     *
     * @return the top element of the stack.
     */

    @SuppressWarnings( "unchecked" )

    public E pop() {
        E[] newelems;
        E saved;

        if (top == -1) {
            throw new EmptyStackException("Stack is Empty");
        }

        saved = elems[top];
        // scrub the memory, then decrements top
        elems[top--] = null;
        if (top + inc + 2 < elems.length) {
            newelems = (E[]) new Object[elems.length - inc];
            System.arraycopy(elems, 0, newelems, 0, top+1);
            elems = newelems;
        }
        return saved;
    }

    /** Puts the element onto the top of this stack.
     *
     * @param elem the element that will be pushed onto the top of the stack.
     */

    @SuppressWarnings( "unchecked" )

    public void push( E elem ) {
        E[] newelems;
        // stores the element at position top, then increments top

        if (elem == null) {
            throw new IllegalStateException("Please pass a valid value");
        } else {
            if (++top == elems.length) {
                newelems = (E[]) new Object[elems.length + inc];
                System.arraycopy(elems, 0, newelems, 0, top+1);
                elems = newelems;
            }
            elems[top] = elem;
        }
    }

    /** Returns a string representation of this object.
     *
     * @return a string representation of this object.
     */

    public String toString() {
        StringBuffer b;

        b = new StringBuffer( "DynamicArrayStack: {" );
        for ( int i=top; i>-1; i-- ) {
            if ( i!=top-1 ) {
                b.append( "," );
            }
            b.append( elems[i] );
        }

        b.append( "}" );
        return b.toString();
    }
}
