/**
 * The grid world and its contents. (0,0) is the upper left corner of the grid.
 * 
 * @author Sayf Elhawary
 */
public class World {

	private int gridRow_; // The world grid row

	private int gridCol_; // The world grid column

	/**
	 * Creates a new world grid according to the desired world grid row and
	 * column.
	 * 
	 * @param row
	 *          The desired world grid row.
	 * @param col
	 *          The desired world grid column.
	 */
	public World ( int gridRow, int gridCol ) {
		gridRow_ = gridRow;
		gridCol_ = gridCol;
	}

	/**
	 * Get the number of rows in the world grid.
	 * 
	 * @return the number of rows in the world grid
	 */
	public int getNumRows () {
		return gridRow_;
	}

	/**
	 * Get the number of columns in the world grid.
	 * 
	 * @return the number of columns in the world grid
	 */
	public int getNumCols () {
		return gridCol_;
	}

}
