/**
 * A class to represent the elements of the language (tokens): integer and
 * symbols.
 * 
 * @author Marcel Turcotte
 */

public class Token {

    private enum TokenType {
	NUMBER, SYMBOL;
    }

    /**
     * An instance variable to store the value of a token of type NUMBER.
     */

    private int number;

    /**
     * An instance variable to store the value of a token of type SYMBOL.
     */

    private String symbol;

    /**
     * An instance variable to store the type of this token (either NUMBER or
     * SYMBOL).
     */

    private TokenType type;

    /**
     * Creates a token that represents a NUMBER.
     * 
     * @param iValue
     *            the NUMBER of the token
     */

    public Token(int value) {
	this.number = value;
	type = TokenType.NUMBER;
    }

    /**
     * Creates a token that represents a SYMBOL.
     * 
     * @param sValue
     *            the SYMBOL represented by the token
     */

    public Token(String value) {
	this.symbol = value;
	type = TokenType.SYMBOL;
    }

    /**
     * Returns the number that is stored in this token; this method is only
     * applicable if this token is a number.
     * 
     * @return The number stored in this token.
     */

    public int getNumber() {

	// pre-condition

	if (type != TokenType.NUMBER) {
	    throw new IllegalStateException("not an integer");
	}

	return number;
    }

    /**
     * Returns the symbol (a String) that is stored in this token; this method
     * is only applicable if this token isSymbol.
     * 
     * @return The symbol stored in this token.
     */

    public String getSymbol() {

	// pre-condition

	if (type != TokenType.SYMBOL) {
	    throw new IllegalStateException("not a symbol");
	}

	return symbol;
    }

    /**
     * Returns true if this token represents a NUMBER.
     * 
     * @return true if this token is a number.
     */

    public boolean isNumber() {
	return type == TokenType.NUMBER;
    }

    /**
     * Returns true if this token represents a symbol.
     * 
     * @return true if this token is a symbol.
     */

    public boolean isSymbol() {
	return type == TokenType.SYMBOL;
    }


    /**
     * 
     */
    
    public Token copy() {
	
	Token copy;
	
	if (isNumber()) {
	    copy = new Token(number);
	} else {
	    copy = new Token(symbol);
	}
	
	return copy;

    }
    
    
    /**
     * Returns a String representation of this token.
     * 
     * @return A String representation of this token.
     */

    public String toString() {

	switch (type) {
	case NUMBER:
	    return Integer.toString(number);
	case SYMBOL:
	    return symbol;
	default:
	    throw new IllegalStateException(
		    "internal error: this statement should never be executed");
	}
    }

    /**
     * Overrides the method equals from the class Object
     * 
     * @param obj
     *            the other object
     * @return true if both tokens are of the same type and store the same value
     */

    @Override
    
    public boolean equals(Object obj) {

	boolean answer;

	if (obj == null) {
	    answer = false;
	} else if (!(obj instanceof Token)) {
	    answer = false;
	} else {
	    Token other = (Token) obj;
	    if (type != other.type) {
		answer = false;
	    } else if (type == TokenType.NUMBER) {
		answer = number == other.number;
	    } else {
		answer = symbol.equals(other.symbol);
	    }
	}
	return answer;
    }

}
