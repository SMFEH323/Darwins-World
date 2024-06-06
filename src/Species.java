/**
 * A species of creature.
 * 
 * @author Sayf Elhawary
 */
public class Species {

	private String speciesName_; // The species name.

	/**
	 * Creates a new species according to the desired species name.
	 * 
	 * @param speciesName
	 *          The desired species name.
	 */
	public Species ( String speciesName ) {
		speciesName_ = speciesName;
	}

	/**
	 * Get the species name.
	 * 
	 * @return the species name
	 */
	public String getName () {
		return speciesName_;
	}

}
