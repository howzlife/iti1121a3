import java.util.StringTokenizer;

/**
 * The class <code>Reader</code> implements the <b>lexical analysis</b> of our
 * <b>Luka</b> programs so that you do not have to be concerned by details, such
 * as skipping white spaces, when implementing the interpreter. The intended use
 * of the <code>Reader</code> follows:
 * 
 * <pre>
 * Reader r = new Reader(program);
 * 
 * while (r.hasMoreTokens()) {
 *     Token t = r.nextToken();
 * 
 *     if (!t.isSymbol()) {
 * 	// ...
 *     } else if (t.sValue().equals(&quot;plus&quot;)) {
 * 	// ...
 *     }
 * }
 * </pre>
 * 
 * @see StringTokenizer
 * @author Marcel Turcotte
 */

public class Reader {

    /**
     * Instance variable. Reader uses a StringTokenizer for the lexical
     * analysis.
     */

    private StringTokenizer st;

    /**
     * Create a Reader object for the parameter string.
     * 
     * @param s
     *            the expression to be analyzed.
     */

    public Reader(String s) {
	st = new StringTokenizer(s);
    }

    /**
     * Tests if the end of the program has been reached.
     * 
     * @return true if the Reader has reached the end of the input (program).
     */

    public boolean hasMoreTokens() {
	return st.hasMoreTokens();
    }

    /**
     * Reads and returns the next token.
     * 
     * @return The next Token from the input.
     */

    public Token nextToken() {

	String t = st.nextToken();

	try {
	    return new Token(Integer.parseInt(t));
	} catch (NumberFormatException e) {
	    return new Token(t);
	}

    }

}
