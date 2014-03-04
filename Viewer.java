import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;

/**
 * Viewer implements the graphical aspect of this application.
 * <ul>
 * <li>The viewer has a <code>Display</code> to render the result of the
 * execution of a <b>Luka</b> program;</li>
 * <li>It has a <code>TextArea</code> that allows the user to input a (valid)
 * program;</li>
 * <li>Has an interpreter that will be rendering the result of the execution of
 * a program onto the <code>Display</code>;</li>
 * <li>Has a button labeled ``execute''. The <code>Viewer</code> registers
 * itself as the event-handler of the button.</li>
 * </ul>
 * 
 * @author Marcel Turcotte
 */

public class Viewer extends JFrame implements TableModelListener {

    public static final int NUMBER_OF_COLUMN = 10;

    public static final int NUMBER_OF_ROW = 100;

    private static final long serialVersionUID = 1L;
    
    private static final String NL = System.getProperty("line.separator");

    /**
     * A reference to the interpreter.
     */

    private Interpreter lvm; // Luka Virtual Machine

    /**
     * A reference to the display, where the result of the execution of the Luka
     * programs will be displayed.
     */

    private Display display;

    private JTable table;

    private JTextArea output;
    
    private Sheet s;

    private int selectedRow, selectedColumn;

    /**
     * Creates the visual display of the application. Creates a Display, Button
     * and TextArea.
     * 
     * @param lvm
     *            a reference to an interpreter.
     */

    public Viewer() {
	super("Lecxe - SpreadSheet + Postfix Notation");

	selectedRow = selectedColumn = 0;

	this.lvm = new Interpreter();

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBackground(Color.WHITE);

	// Display will be calling our method paint
	display = new Display(this);

	display.setBackground(Color.WHITE);

	add(display, BorderLayout.NORTH);

	s = new Sheet(NUMBER_OF_ROW, NUMBER_OF_COLUMN);
	s.addTableModelListener(this);

	table = new JTable(s);

	table.setCellSelectionEnabled(true);

	table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
		    return;
		}
		for (int c : table.getSelectedRows()) {
		    selectedRow = c;
		}
		for (int c : table.getSelectedColumns()) {
		    selectedColumn = c;
		}
		display.repaint();
	    }
	});

	JScrollPane sp = new JScrollPane(table);

	add(sp, BorderLayout.CENTER);

	output = new JTextArea(4, 80);
	output.setForeground(Color.RED);
	output.setEditable(false);

	JScrollPane controls = new JScrollPane(output); 

	add(controls, BorderLayout.SOUTH);

	pack();
	setVisible(true);
    }

    /**
     * Obtains the Luka program from the text area. Calls the method
     * <code>execute</code> of the interpreter, passing the program and
     * graphical context.
     * 
     * @param g
     *            the graphical context
     */

    public void execute(Graphics g) {
	String program = s.getValueAt(selectedRow, selectedColumn).toString();
	lvm.execute(program, g, output, s);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
	selectedRow = e.getFirstRow();
	selectedColumn = e.getColumn();
	display.repaint();
    }

}
