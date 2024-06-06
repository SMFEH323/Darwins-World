/**
 * A (row,col) position within a grid.
 * 
 * @author Sayf Elhawary
 */
public class Position {

	private int row_; // The position's row.

	private int col_; // The position's column.

	/**
	 * Creates a new position according to the desired row and column.
	 * 
	 * @param row
	 *          The desired row.
	 * @param col
	 *          The desired column.
	 */
	public Position ( int row, int col ) {
		row_ = row;
		col_ = col;
	}

	/**
	 * Get the position's row.
	 * 
	 * @return the row
	 */
	public int getRow () {
		return row_;
	}

	/**
	 * Get the position's column.
	 * 
	 * @return the column
	 */
	public int getCol () {
		return col_;
	}

}
