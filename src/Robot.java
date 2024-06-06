import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sayf Elhawary
 */
public class Robot extends Creature {

	private List<String[]> instructions_; // The robot's instructions

	/**
	 * Creates a new robot according to the desired robot's species, creature's
	 * position, and creature's direction.
	 * 
	 * @param spieces
	 *          The desired robot's species.
	 * @param position
	 *          The desired robot's position.
	 * @param direction
	 *          The desired robot's direction.
	 * @param mood
	 *          The desired robot's mood.
	 */
	public Robot ( Species species, Position position, Direction direction,
	               int mood ) {
		super(species,position,direction,mood);
		instructions_ = new ArrayList<String[]>();
	}

	/**
	 * Sets the robot's instructions according to the desired file name.
	 * 
	 * @param fileName
	 *          The desired file name.
	 * @throws IOException
	 */
	public void importInstructions ( String fileName ) throws IOException {
		int count = 0;
		File inFile = new File(fileName);
		BufferedReader in = new BufferedReader(new FileReader(inFile));
		for ( String read = in.readLine() ; read != null ; read = in.readLine() ) {
			String[] chunks = read.strip().split("\\s+");
			for ( int i = 0 ; i < chunks.length ; i++ ) {
				if ( chunks[i].equals("halt") ) {
					setMood((int) (Math.random() * (count)));
					return;
				}
			}
			if ( chunks.length >= 3 ) {
				int index = Integer.parseInt(chunks[0]);
				String[] actions = { chunks[1], chunks[2] };
				instructions_.add(index,actions);
				count++;
			} else if ( chunks.length == 2 ) {
				int index = Integer.parseInt(chunks[0]);
				String[] actions = { chunks[1] };
				instructions_.add(index,actions);
				count++;
			} else {

			}
		}
		in.close();
	}

	/**
	 * Gets the next instruction of the robot.
	 * 
	 * @return the next instruction
	 */
	public String[] getNextInstruction () {
		return instructions_.get(getMood());
	}

}
