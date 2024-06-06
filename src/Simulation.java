import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage the simulation.
 * 
 * @author Sayf Elhawary
 */
public class Simulation {

	private World world_; // The simulation's world.

	private List<Creature> creatures_; // The simulation's creatures.

	/**
	 * Create a simulation for a world with the specified dimensions.
	 * 
	 * @param numrows
	 *          number of rows in the world
	 * @param numcols
	 *          number of columns in the world
	 * @throws IOException
	 */
	public Simulation ( int numrows, int numcols ) throws IOException {
		world_ = new World(numrows,numcols);
		creatures_ = new ArrayList<Creature>();

		for ( int i = 0 ; i < world_.getNumRows() ; i++ ) {
			for ( int j = 0 ; j < world_.getNumCols() ; j++ ) {
				int random = (int) (Math.random() * 200);
				if ( random == 2 ) {
					Creature creature =
					    new Creature(new Species("flytrap"),new Position(i,j),
					                 Direction.getRandomDir(),(int) (Math.random() * 4));
					creatures_.add(creature);
				} else if ( random == 5 ) {
					Creature creature =
					    new Creature(new Species("rover"),new Position(i,j),
					                 Direction.getRandomDir(),(int) (Math.random() * 4));
					creatures_.add(creature);
				} else if ( random == 25 ) {
					Creature creature =
					    new Creature(new Species("hopper"),new Position(i,j),
					                 Direction.getRandomDir(),(int) (Math.random() * 4));
					creatures_.add(creature);
				} else if ( random == 50 ) {
					Creature creature =
					    new Creature(new Species("food"),new Position(i,j),
					                 Direction.getRandomDir(),(int) (Math.random() * 4));
					creatures_.add(creature);
				} else if ( random == 70 ) {
					Robot robot =
					    new Robot(new Species("robot-creepingdeath"),new Position(i,j),
					              Direction.getRandomDir(),(int) (Math.random() * 4));
					robot.importInstructions("programs/CreepingDeath.txt");
					creatures_.add(robot);
				} else if ( random == 90 ) {
					Robot robot =
					    new Robot(new Species("robot-landmine"),new Position(i,j),
					              Direction.getRandomDir(),(int) (Math.random() * 4));
					robot.importInstructions("programs/Landmine.txt");
					creatures_.add(robot);
				} else if ( random == 100 ) {
					Robot robot =
					    new Robot(new Species("robot-SE"),new Position(i,j),
					              Direction.getRandomDir(),(int) (Math.random() * 4));
					robot.importInstructions("programs/SE.txt");
					creatures_.add(robot);
				} else {

				}
			}
		}
	}

	/**
	 * In one step of the simulation, each creature takes a turn.
	 * 
	 * @throws IOException
	 */
	public void step () throws IOException {
		for ( int i = 0 ; i < creatures_.size() ; i++ ) {
			if ( creatures_.get(i) instanceof Robot ) {
				Robot robot = (Robot) (creatures_.get(i));
				if ( !robot.getSpecies().getName().equals("robot-creepingdeath")
				    && !robot.getSpecies().getName().equals("robot-landmine")
				    && !robot.getSpecies().getName().equals("robot-SE") ) {
					creatureBehavior(creatures_.get(i));
				} else {
					robotBehavior(robot);
				}
			} else {
				creatureBehavior(creatures_.get(i));
			}
		}

	}

	/**
	 * Get the world for this simulation.
	 * 
	 * @return the world
	 */
	public World getWorld () {
		return world_;
	}

	/**
	 * Get the creatures in this simulation.
	 * 
	 * @return the creatures
	 */
	public List<Creature> getCreatures () {
		return creatures_;
	}

	/**
	 * @param row
	 *          The desired row.
	 * @param col
	 *          The desired column.
	 * @return
	 */
	private Creature posOccupied ( int row, int col ) {
		for ( int i = 0 ; i < creatures_.size() ; i++ ) {
			Position currCreaturePos = creatures_.get(i).getPosition();
			if ( currCreaturePos.getRow() == row
			    && currCreaturePos.getCol() == col ) {
				return creatures_.get(i);
			}
		}
		return null;
	}

	/**
	 * Gets weather or not the desired creature is facing a wall.
	 * 
	 * @param creature
	 *          The desired creature.
	 * @return true if the desired creature is facing a wall, false otherwise.
	 */
	private boolean isWall ( Creature creature ) {
		int currCreatureRow = creature.getPosition().getRow();
		int currCreatureCol = creature.getPosition().getCol();
		if ( creature.getDirection() == Direction.EAST
		    && currCreatureCol == world_.getNumCols() - 1 ) {
			return true;
		} else if ( creature.getDirection() == Direction.NORTH
		    && currCreatureRow == 0 ) {
			    return true;
		    } else
		  if ( creature.getDirection() == Direction.SOUTH
		      && currCreatureRow == world_.getNumRows() - 1 ) {
			      return true;
		      } else
		    if ( creature.getDirection() == Direction.WEST
		        && currCreatureCol == 0 ) {
			        return true;
		        } else {
			        return false;
		        }
	}

	/**
	 * Gets the next possible position of the desired creature.
	 * 
	 * @param creature
	 *          The desired creature.
	 * @return the next position for the desired creature.
	 */
	private Position getNextPos ( Creature creature ) {
		int currCreatureRow = creature.getPosition().getRow();
		int currCreatureCol = creature.getPosition().getCol();
		if ( creature.getDirection() == Direction.EAST && !isWall(creature) ) {
			return new Position(currCreatureRow,currCreatureCol + 1);
		} else
		  if ( creature.getDirection() == Direction.NORTH && !isWall(creature) ) {
			  return new Position(currCreatureRow - 1,currCreatureCol);
		  } else
		    if ( creature.getDirection() == Direction.SOUTH && !isWall(creature) ) {
			    return new Position(currCreatureRow + 1,currCreatureCol);
		    } else if ( creature.getDirection() == Direction.WEST
		        && !isWall(creature) ) {
			        return new Position(currCreatureRow,currCreatureCol - 1);
		        } else {
			        return new Position(currCreatureRow,currCreatureCol);
		        }
	}

	/**
	 * Activates the desired creature's special behaviors.
	 * 
	 * @param creature
	 *          The desired creature.
	 */
	private void creatureBehavior ( Creature creature ) {
		Species creatureSpec = creature.getSpecies();
		int nextRow = getNextPos(creature).getRow();
		int nextCol = getNextPos(creature).getCol();
		Creature enemy = posOccupied(nextRow,nextCol);
		Species enemySpec;
		String enemySpecName;
		if ( enemy == null ) {
			enemySpec = null;
			enemySpecName = null;
		} else {
			enemySpec = enemy.getSpecies();
			enemySpecName = enemySpec.getName();
		}
		if ( creatureSpec.getName().equals("flytrap") ) {
			if ( enemy != null && !enemySpecName.equals("flytrap") ) {
				enemy.setSpecies(new Species("flytrap"));
			} else {
				creature.setDirection(creature.getDirection().getLeftDir());
			}
		} else if ( creatureSpec.getName().equals("rover") ) {
			if ( enemy != null && !enemySpecName.equals("rover") ) {
				enemy.setSpecies(new Species("rover"));
			} else if ( (enemy != null && enemySpecName.equals("rover"))
			    || isWall(creature) ) {
				    int random = (int) (Math.random() * 2);
				    if ( random == 1 ) {
					    creature.setDirection(creature.getDirection().getLeftDir());
				    } else {
					    creature.setDirection(creature.getDirection().getRightDir());
				    }
			    } else {
				    creature.setPosition(getNextPos(creature));
			    }

		} else if ( creatureSpec.getName().equals("hopper") ) {
			if ( enemy == null ) {
				creature.setPosition(getNextPos(creature));
			}
		} else if ( creatureSpec.getName().equals("food") ) {
			creature.setDirection(creature.getDirection().getLeftDir());
		} else {

		}

	}

	/**
	 * Activates the desired robot's special behaviors.
	 * 
	 * @param robot
	 *          The desired robot.
	 * @throws IOException
	 */
	private void robotBehavior ( Robot robot ) throws IOException {
		int nextRow = getNextPos(robot).getRow();
		int nextCol = getNextPos(robot).getCol();
		Creature enemy = posOccupied(nextRow,nextCol);
		Species enemySpec;
		String enemySpecName;
		if ( enemy == null ) {
			enemySpec = null;
			enemySpecName = null;
		} else {
			enemySpec = enemy.getSpecies();
			enemySpecName = enemySpec.getName();
		}
		String[] instruction = robot.getNextInstruction();
		String action = "";
		int nextStep = 0;
		for ( int i = 0 ; i < instruction.length ; i++ ) {
			if ( i == 1 ) {
				nextStep = Integer.parseInt(instruction[i]);
			} else {
				action = instruction[i];
			}
		}
		if ( action.equals("ifempty") ) {
			if ( enemy == null ) {
				robot.setMood(nextStep);
			} else {
				robot.setMood(robot.getMood() + 1);
			}
		} else if ( action.equals("ifwall") ) {
			if ( isWall(robot) ) {
				robot.setMood(nextStep);
			} else {
				robot.setMood(robot.getMood() + 1);
			}
		} else if ( action.equals("ifsame") ) {
			if ( enemy != null
			    && enemySpecName.equals(robot.getSpecies().getName()) ) {
				robot.setMood(nextStep);
			} else {
				robot.setMood(robot.getMood() + 1);
			}
		} else if ( action.equals("ifenemy") ) {
			if ( enemy != null
			    && !enemySpecName.equals(robot.getSpecies().getName()) ) {
				robot.setMood(nextStep);
			} else {
				robot.setMood(robot.getMood() + 1);
			}
		} else if ( action.equals("ifrandom") ) {
			int random = (int) (Math.random() * 2);
			if ( random == 1 ) {
				robot.setMood(nextStep);
			} else {
				robot.setMood(robot.getMood() + 1);
			}
		} else if ( action.equals("go") ) {
			robot.setMood(nextStep);
		} else if ( action.equals("hop") ) {
			if ( enemy == null ) {
				robot.setPosition(getNextPos(robot));
			}
			robot.setMood(robot.getMood() + 1);
		} else if ( action.equals("left") ) {
			robot.setDirection(robot.getDirection().getLeftDir());
			robot.setMood(robot.getMood() + 1);
		} else if ( action.equals("right") ) {
			robot.setDirection(robot.getDirection().getRightDir());
			robot.setMood(robot.getMood() + 1);
		} else if ( action.equals("infect") ) {
			if ( enemy != null && !enemySpecName.equals(robot.getSpecies().getName())
			    && !(enemy instanceof Robot) ) {
				enemy.setSpecies(robot.getSpecies());
				creatures_.remove(enemy);
				Robot friend = new Robot(enemy.getSpecies(),enemy.getPosition(),
				                         enemy.getDirection(),robot.getMood());
				if ( friend.getSpecies().getName().equals("robot-creepingdeath") ) {
					friend.importInstructions("programs/CreepingDeath.txt");
				} else {
					friend.importInstructions("programs/Landmine.txt");
				}
				creatures_.add(friend);
			}
			robot.setMood(robot.getMood() + 1);
		} else {

		}
	}

}
