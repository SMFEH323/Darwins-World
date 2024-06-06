/**
 * An individual creature.
 * 
 * @author Sayf Elhawary
 */
public class Creature {

	private Species species_; // The creature's species.

	private Position position_; // The creature's position.

	private Direction direction_; // The creature's direction.

	private int mood_; // The creature's mood.

	/**
	 * Creates a new creature according to the desired creature's species,
	 * creature's position, and creature's direction.
	 * 
	 * @param spieces
	 *          The desired creature's species.
	 * @param position
	 *          The desired creature's position.
	 * @param direction
	 *          The desired creature's direction.
	 * @param mood
	 *          The desired creature's mood.
	 */
	public Creature ( Species species, Position position, Direction direction,
	                  int mood ) {
		species_ = species;
		position_ = position;
		direction_ = direction;
		mood_ = mood;
	}

	/**
	 * Get the creature's species.
	 * 
	 * @return the species
	 */
	public Species getSpecies () {
		return species_;
	}

	/**
	 * Set the creature's species.
	 * 
	 * @param species
	 *          The desired species.
	 */
	public void setSpecies ( Species species ) {
		species_ = species;
	}

	/**
	 * Get the creature's position.
	 * 
	 * @return the position
	 */
	public Position getPosition () {
		return position_;
	}

	/**
	 * Set the creature's position.
	 * 
	 * @param position
	 *          The desired creature's position.
	 */
	public void setPosition ( Position position ) {
		position_ = position;
	}

	/**
	 * Get the direction the creature is facing.
	 * 
	 * @return the direction
	 */
	public Direction getDirection () {
		return direction_;
	}

	/**
	 * Set the creature's direction.
	 * 
	 * @param direction
	 *          The desired creature's direction.
	 */
	public void setDirection ( Direction direction ) {
		direction_ = direction;
	}

	/**
	 * Get the mood of the creature.
	 * 
	 * @return the mood
	 */
	public int getMood () {
		return mood_;
	}

	/**
	 * Set the creature's mood.
	 * 
	 * @param mood
	 *          The desired creature's mood.
	 */
	public void setMood ( int mood ) {
		mood_ = mood;
	}
}
