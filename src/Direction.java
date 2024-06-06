/**
 * The four cardinal directions.
 * 
 * @author Sayf Elhawary
 */
public enum Direction {

	NORTH, EAST, SOUTH, WEST;

	/**
	 * Determine the direction 90 degrees to the left of this direction. NORTH is
	 * up, SOUTH is down, EAST is to the right, and WEST is to the left.
	 * 
	 * @return the direction 90 degrees to the left
	 */
	public Direction getLeftDir () {
		switch ( this ) {
		case NORTH:
			return WEST;
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		case WEST:
			return SOUTH;
		}
		// unreachable
		return null;
	}

	/**
	 * Determine the direction 90 degrees to the right of this direction. NORTH is
	 * up, SOUTH is down, EAST is to the right, and WEST is to the left.
	 * 
	 * @return the direction 90 degrees to the right
	 */
	public Direction getRightDir () {
		switch ( this ) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		case WEST:
			return NORTH;
		}
		// unreachable
		return null;
	}

	/**
	 * Get a random direction.
	 *
	 * @return a random direction
	 */
	public static Direction getRandomDir () {
		Direction[] values = values();
		return values[(int) (Math.random() * values.length)];
	}
}
