import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")

public class Sheet extends AbstractTableModel {
    
    public final int rows;
    public final int columns;
    
    private String[][] cells; // could be some object caching the result of evaluation
    
    public Sheet(int rows, int columns) {
	this.rows = rows;
	this.columns = columns;
	cells = new String[this.rows][this.columns];
	for (int row=0; row<rows; row++) {
	    for (int col=0; col<columns; col++) {
		cells[row][col] = "";
	    }
	}
    }

    @Override
    public int getColumnCount() {
	return columns;
    }

    @Override
    public int getRowCount() {
	return rows;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return true;
    }

    @Override
    public Object getValueAt(int row, int col) {
	return cells[row][col];
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        cells[row][col] = value.toString();
        fireTableCellUpdated(row, col);
    }

}
