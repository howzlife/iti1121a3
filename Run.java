/**
 * Runs the application.
 *
 * @author Marcel Turcotte
 */

public class Run {

    /**
     * Starts the application by creating an Interpreter and a Viewer.
     *
     * @param args unused.
     */

    public static void main( String[] args ) {

	StudentInfo.display();

        new Viewer();
    }
}
