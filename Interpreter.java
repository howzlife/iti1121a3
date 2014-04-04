import java.awt.*;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.NoSuchMethodException;

import javax.swing.JTextArea;

/**
 * Luka Virtual Machine (LVM) -- An interpreter for the Luka programming
 * language.
 * 
 * @author Nathalie Japkowicz and Marcel Turcotte
 */

public class Interpreter {
    
    /**
     * Class variable. Newline symbol on this machine. 
     */
    
    private static final String NL = System.getProperty("line.separator");

    /**
     * Instance variable. The operands stack.
     */

    private Stack<Token> operands;

    /**
     * Instance variable. A reference to a lexical analyzer (Reader).
     */

    private Reader r;


    /**
     * Instance variable. Coordinate x of the graphics state.
     */

    private int gsX;

    /**
     * Instance variable. Coordinate y of the graphics state.
     */

    private int gsY;

    /**
     * Instance variable. Color of the pen.
     */

    private Color gsColor;

    /**
     * Instance variable. The spreadsheet.
     */
    private Sheet sheet;

    /**
     * Initializes this newly created interpreter so that the operand stack is
     * empty, the accumulator is set 0, the cursor is at (0,0), and the default
     * color is blue.
     */

    public Interpreter() {
	reset();
    }

    /**
     * Auxiliary method that resets the graphics state of this interpreter.
     */

    private void reset() {

        execute_clear();

	gsX = 0;
	gsY = 0;
	gsColor = Color.BLUE;
    }

    /**
     * Executes the input program and displays the result onto the Graphics
     * object received as an argument.
     * 
     * @param program
     *            contains the source to be executed.
     * @param g
     *            the graphics context.
     * @param output
     *            the area for textual output.
     * @param sheet
     *            the spreadsheet.
     */

    public void execute(String program, Graphics g, JTextArea output, Sheet sheet) {
        Token t;
        String symbol;

	reset();

        this.sheet = sheet;

	r = new Reader(program);

	g.setColor(gsColor);

	while (r.hasMoreTokens()) {

	    t = r.nextToken();

	    if (t.isNumber()) {

		operands.push(t);

	    } else if (t.getSymbol().startsWith("/")) {

		operands.push(new Token(t.getSymbol().substring(1)));

	    } else {
                symbol = t.getSymbol();
                try {
                    if (symbol.equals("pstack")) {

                        Interpreter.class
                            .getDeclaredMethod("execute_" + t.getSymbol(),
                                               JTextArea.class)
                            .invoke(this, output);

                    } else if (symbol.equals("lineto") || symbol.equals("arc")) {

                        Interpreter.class
                            .getDeclaredMethod("execute_" + t.getSymbol(),
                                               Graphics.class)
                            .invoke(this, g);
                    } else {
                        Interpreter.class
                            .getDeclaredMethod("execute_" + t.getSymbol())
                            .invoke(this);
                    }
                } catch (NoSuchMethodException e) {
                    throw new LukaSyntaxException(symbol);
                } catch (InvocationTargetException e) {
                    throw (RuntimeException)e.getCause();
                } catch (IllegalAccessException e) {
                    // This should never happen
                    // It is a checked exception though
                }
            }
        }

    }

    private String[] getStackContents() {
        String[] stackContents;
        stackContents = operands.toString().split("\\{")[1].split("\\}");
        if (stackContents.length > 0)
            return stackContents[0].split(",");
        return new String[0];
    }

    private void pushCell(int col, int row) {
        try {
            operands.push(new Token(Integer.parseInt(sheet.getValueAt(row, col)
                                                     .toString())));
        } catch (NumberFormatException e) {
            // I don't want arbitrary code!
            // Fail silently like a good GUI program...
        }
    }

    private void execute_add() {
	Token op1 = operands.pop();
	Token op2 = operands.pop();
	Token res = new Token(op1.getNumber() + op2.getNumber());
	operands.push(res);
    }

    private void execute_sub() {
	Token op1 = operands.pop();
	Token op2 = operands.pop();
	Token res = new Token(op2.getNumber() - op1.getNumber());
	operands.push(res);
    }

    private void execute_mul() {
	Token op1 = operands.pop();
	Token op2 = operands.pop();
	Token res = new Token(op1.getNumber() * op2.getNumber());
	operands.push(res);
    }

    private void execute_div() {
	Token op1 = operands.pop();
	Token op2 = operands.pop();
	Token res = new Token(op2.getNumber() / op1.getNumber());
	operands.push(res);
    }

    private void execute_exch() {
	Token op1 = operands.pop();
	Token op2 = operands.pop();
	operands.push(op1);
	operands.push(op2);
    }

    private void execute_pop() {
	operands.pop();
    }

    private void execute_moveto() {
	Token y = operands.pop();
	Token x = operands.pop();
	gsX = x.getNumber();
	gsY = y.getNumber();
    }

    private void execute_lineto(Graphics g) {
	Token y = operands.pop();
	Token x = operands.pop();
	g.drawLine(gsX, gsY, x.getNumber(), y.getNumber());
	gsX = x.getNumber();
	gsY = y.getNumber();
    }

    private void execute_arc(Graphics g) {
	Token a2 = operands.pop();
	Token a1 = operands.pop();
	Token r = operands.pop();
	g.drawArc(gsX, gsY, r.getNumber(), r.getNumber(), a1.getNumber(),
		a2.getNumber());
    }

    private void execute_mark() {
	operands.push(new Token("mark"));
    }

    private void execute_cleartomark() {
	boolean done = false;
	while (!done) {
	    Token current = operands.pop();
	    if (current.isSymbol() && current.getSymbol().equals("mark")) {
		done = true;
	    }
	}
    }

    private void execute_counttomark() {
	Stack<Token> tempStack = new LinkedStack<Token>();
	boolean done = false;
	int count = 0;
	while (!done) {
	    Token current = operands.pop();
	    if (current.isSymbol() && current.getSymbol().equals("mark")) {
		done = true;
	    } else {
		count++;
	    }
	    tempStack.push(current);
	}
	while (!tempStack.isEmpty()) {
	    operands.push(tempStack.pop());
	}
	operands.push(new Token(count));
    }

    private void execute_quit() {
	System.out.println("Bye!");
	System.exit(0);
    }

    private void execute_pstack(JTextArea output) {
        String[] stackContents;
        stackContents = getStackContents();
        output.append("[");
        for (int i=stackContents.length-1;i>-1;i--)
            output.append(stackContents[i] + " ");
        output.append("\n");
    }

    private void execute_clear() {
	operands = new LinkedStack<Token>();
    }

    private void execute_dup() {
        Token top;
        top = operands.pop();
        operands.push(top);
        operands.push(top);
    }

    private void execute_count() {
        operands.push(new Token(getStackContents().length));
    }

    private void execute_sumtomark() {
        String[] stackContents;
        int sum, length;

        stackContents = getStackContents();
        sum = 0;
        length = stackContents.length;
        for (int i=0;i<length;i++) {
            try {
                sum += Integer.parseInt(stackContents[i]);
            } catch (NumberFormatException e) {
                if (stackContents[i].equals("mark"))
                    break;
            }
        }
        operands.push(new Token(sum));
    }

    private void execute_roll() {
        Stack<Token> tempStack;
        Token top;
        int rolls, length;

        tempStack = new LinkedStack<Token>();
        rolls = operands.pop().getNumber();
        length = operands.pop().getNumber() - 1;
        for (int i=0;i<rolls;i++) {
            top = operands.pop();
            for (int j=0;j<length;j++) {
                tempStack.push(operands.pop());
                if (operands.isEmpty())
                    break;
            }
            operands.push(top);
            while (!tempStack.isEmpty()) {
                operands.push((Token)tempStack.pop());
            }
        }
    }

    private void execute_cell() {
        Token t1, t2;
        int col;

        t1 = operands.pop();
        t2 = operands.pop();
        // ((char) A) == 65
        col = ((int)t2.getSymbol().charAt(0)) - 65;
        pushCell(col, t1.getNumber());

    }

    private void execute_pushrow() {
        int row;

        row = operands.pop().getNumber();
        for (int col=0;col<Viewer.NUMBER_OF_COLUMN;col++) {
            pushCell(col, row);
        }
    }

    private void execute_pushcol() {
        int col;

        // ((char) A) == 65
        col = ((int)operands.pop().getSymbol().charAt(0)) - 65;
        for (int row=0;row<Viewer.NUMBER_OF_ROW;row++) {
            pushCell(col, row);
        }
    }
}
