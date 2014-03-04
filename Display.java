import java.awt.*;

/** <code>Display</code> is a specialized <code>Canvas</code> to display the
 *  result of the evaluation of our <b>Luka</b> programs.
 *  
 *  <ol>
 *  <li>It is a subclass of <code>java.awt.Canvas</code>;</li>
 *  <li>Has an instance variable used to store a reference to
 *    the <code>Viewer</code> object who created this <code>Display</code> object.</li>
 *  <li>Implements the method <code>public void paint(Graphics g)</code>.
 *    It calls the method <code>paint(Graphics g)</code> of its viewer.</li>
 *  </ol>
 *  
 *  The super-class <code>Canvas</code> defines several methods, including
 *  <code>repaint()</code>, <code>update(g)</code> and
 *  <code>paint(g)</code>.  The sub-class <code>Display</code> overrides
 *  <code>only</code> the method <code>paint(g)</code>, and here is the
 *  sequence of events leading to a call to the method <code>paint</code>:
 *  
 *  <ol>
 *  <li> A call to the method <code>repaint()</code> forces a call to the
 *    method <code>update(g)</code>.  The call to the method
 *    <code>repaint()</code> occurs when the user clicks the ``execute'' button;</li>
 *  <li> The method <code>update</code> fills the <code>Canvas</code> with the
 *    default background color, and then calls the method <code>paint(g)</code>;</li>
 *  <li> Our implementation of the method <code>paint(g)</code> simply passes
 *    the reference to the <code>Graphics</code> object of this <code>Canvas</code>
 *    to the method <code>paint</code> of the viewer who created this
 *    <code>Canvas</code>, the latter method will obtain the source code of
 *    the <code>Luka</code> program, and call the method <code>execute</code> of
 *    the interpreter, with the program and the reference to the
 *    <code>Graphics</code> object.</li>
 *  </ol>
 *
 * @author Marcel Turcotte
 */

public class Display extends Canvas {

    private static final long serialVersionUID = 1L;
    
    /** Instance variable. A reference to the frame that contains this
     * <code>Canvas</code>. The method <code>paint</code> calls the
     * method <code>paint</code> of the frame.
     */

    private Viewer myFrame;

    /** Initializes the instance variable of this object.
     * 
     * @param myFrame the frame that contains this <code>Canvas</code>.
     */

    public Display( Viewer myFrame ) {
        this.myFrame = myFrame;
        setSize(800,300);
    }

    /**	The super-class Canvas defines several methods, including
     * 	repaint(), update(g) and paint(g).  The sub-class Display
     * 	overrides the method paint(g).  
     *
     * 	Here is the sequence of events leading to a call to the method paint:
     *  <ol>
     * 	<li> A call to the method repaint() forces a call to the method update(g);</li>
     * 	<li> The method update clears the Canvas and then calls the method paint(g);</li>
     * 	<li> Our implementation of the method paint(g) is simply passing a 
     * 	   reference to the Graphics object of this Canvas to the method paint 
     * 	   the viewer that created this Canvas.</li>
     *  </ol>
     *
     * @param g the specified Graphics context.
     */

    public void paint( Graphics g ) {
        myFrame.execute( g );
    }

}
