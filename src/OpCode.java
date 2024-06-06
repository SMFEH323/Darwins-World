/**
 * Operations for the robot programs.
 * 
 * @author Sayf Elhawary
 */
public enum OpCode {

	HOP(false), LEFT(false), RIGHT(false), INFECT(false), IFEMPTY(true),
	IFWALL(true), IFSAME(true), IFENEMY(true), IFRANDOM(true), GO(true),
	HALT(false);

	private boolean iscontrol_; // is this a flow-of-control instruction?

	/**
	 * Create a new opcode.
	 * 
	 * @param iscontrol
	 *          true if the instruction is a flow-of-control instruction, false if
	 *          it is an action
	 */
	private OpCode ( boolean iscontrol ) {
		iscontrol_ = iscontrol;
	}

	/**
	 * Get whether this instruction is an action that is performed.
	 * 
	 * @return whether this instruction is an action
	 */
	public boolean isAction () {
		return !iscontrol_;
	}

	/**
	 * Get whether this instruction is a flow-of-control instruction i.e. it
	 * affects which instruction is executed next.
	 * 
	 * @return whether this instruction is a flow-of-control instruction
	 */
	public boolean isControl () {
		return iscontrol_;
	}
}
