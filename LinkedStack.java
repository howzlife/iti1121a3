/**
 * Linked implementation of the interface Stack.
 *
 * @author Marcel Turcotte
 * @see    Stack
 */

public class LinkedStack<E> implements Stack<E> {

    /** Instances of the nested class Elem are used to store the
     *  elements of this LinkedStack.
     */

    private static class Elem<E> {

        private E info;
        private Elem<E> next;

        private Elem( E info, Elem<E> next) {
            this.info = info;
            this.next = next;
        }
    }

    /** The instance variable <code>top</code> designates the top
     *  element of the stack.
     */

    private Elem<E> top;

    /** Constructs an empty stack.
     */

    public LinkedStack() {
        top = null;
    }

    /** Tests if this stack is empty.
     *
     * @return true if this stack contains no elements.
     */

    public boolean isEmpty() {
        return top == null;
    }

    /** Returns the top element of this stack without removing it.
     *
     * @return the top element of the stack.
     */

    public E peek() {
        return top.info;
    }

    /** Returns and remove the top element of this stack.
     *
     * @return the top element of the stack.
     */

    public E pop() {
        E savedInfo = top.info;
        top = top.next;
        return savedInfo;
    }

    /** Puts the element onto the top of this stack.
     *
     * @param elem the element that will be pushed onto the top of the stack.
     */

    public void push( E elem ) {
        top = new Elem<E>( elem, top );
    }

    /** Returns a string representation of this object.
     *
     * @return a string representation of this object.
     */

    public String toString() {

	StringBuffer b;
	b = new StringBuffer( "LinkedStack: {" );

	Elem<E> p;
	p = top;
	while ( p!=null ) {
	    if ( p!=top ) {
		b.append( "," );
	    }
	    b.append( p.info );
	    p = p.next;
	}

	b.append( "}" );
	return b.toString();
    }

}
