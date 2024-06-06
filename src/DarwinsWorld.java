import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Darwin's World - a simulation of creatures in a grid world.
 * 
 * @author Sayf Elhawary
 */
public class DarwinsWorld extends Application {

	// grid dimensions for world
	private static final int NUMROWS = 40, NUMCOLS = 50;
	// pixel dimensions for grid cells in the display
	private static final int CELLDIM = 20;

	private static final long DELAY = 200; // time (ms) between animation steps

	// configuration for display
	private static final String CONFIG_FILE = "config.txt";

	private Simulation sim_; // simulation

	private Canvas canvas_; // drawing canvas
	private Map<String,Color> colormap_; // display color for each species
	private Map<String,String> labels_; // display label for each species

	private Timer timer_; // animation timer

	public static void main ( String[] args ) {
		launch(args);
	}

	@Override
	public void start ( Stage stage ) throws Exception {
		stage.setTitle("Darwin's World");

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: snow");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		// keep the window from being resized by the user
		stage.setResizable(false);

		// drawing area
		canvas_ = new Canvas(NUMCOLS * CELLDIM,NUMROWS * CELLDIM);
		canvas_.setOnMouseClicked(e -> toggleTimer());
		root.setCenter(canvas_);

		colormap_ = new HashMap<String,Color>();
		labels_ = new HashMap<String,String>();
		sim_ = new Simulation(NUMROWS,NUMCOLS);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
			for ( ; true ; ) {
				String line = reader.readLine();
				if ( line == null ) {
					break;
				}
				String[] parts = line.strip().split("\\s+");
				labels_.put(parts[0],parts[1]);
				colormap_.put(parts[0],Color.valueOf(parts[2]));
			}
			reader.close();
		} catch ( IOException e ) {
			System.out.println("error reading configuration file " + CONFIG_FILE
			    + ": " + e.getMessage());
			System.exit(1);
		}

		drawWorld(sim_,canvas_);

		timer_ = null;

		// terminate when window is closed - needed because timer is still running
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		stage.show();
	}

	class SimTask extends TimerTask {
		@Override
		public void run () {
			try {
				sim_.step();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			drawWorld(sim_,canvas_);
		}
	}

	/**
	 * Toggle whether the animation timer is running.
	 */
	private void toggleTimer () {
		if ( timer_ == null ) {
			timer_ = new Timer();
			timer_.schedule(new SimTask(),0,DELAY);
		} else {
			timer_.cancel();
			timer_ = null;
		}
	}

	/**
	 * Draw the world and its contents on the canvas.
	 * 
	 * @param sim
	 *          the simulation
	 * @param canvas
	 *          drawing canvas
	 */
	private void drawWorld ( Simulation sim, Canvas canvas ) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		double width = canvas.getWidth(), height = canvas.getHeight();

		World world = sim.getWorld();

		// clear window
		g.clearRect(0,0,width,height);

		// draw grid
		g.setStroke(Color.DARKGRAY);
		for ( int row = 0 ; row <= world.getNumRows() ; row++ ) {
			g.strokeLine(0,row * height / world.getNumRows(),width,
			             row * height / world.getNumRows());
		}
		for ( int col = 0 ; col <= world.getNumCols() ; col++ ) {
			g.strokeLine(col * width / world.getNumCols(),0,
			             col * width / world.getNumCols(),height);
		}

		// draw creatures
		for ( Creature creature : sim_.getCreatures() ) {
			String speciesname = creature.getSpecies().getName();
			Color color = colormap_.get(speciesname);
			if ( color == null ) {
				color = Color.LIGHTGRAY;
			}
			String label = labels_.get(speciesname);
			if ( label == null ) {
				label = "?";
			}
			Position pos = creature.getPosition();
			Direction dir = creature.getDirection();

			double x = pos.getCol() * width / world.getNumCols();
			double y = pos.getRow() * height / world.getNumRows();
			double w = width / world.getNumCols();
			double h = height / world.getNumRows();

			g.setStroke(Color.BLACK);
			g.setFill(color);
			g.fillPolygon(getPolyX(dir,x,y,w,h),getPolyY(dir,x,y,w,h),5);
			g.strokePolygon(getPolyX(dir,x,y,w,h),getPolyY(dir,x,y,w,h),5);
			g.strokeText(label,getLabelX(label,dir,x,y,w,h),
			             getLabelY(label,dir,x,y,w,h));
		}
	}

	private double getLabelX ( String label, Direction dir, double x, double y,
	                           double w, double h ) {
		double labelwidth = (new Text(label)).getLayoutBounds().getWidth();
		switch ( dir ) {
		case EAST:
			return x + 3;
		case WEST:
			return x + w - 3 - labelwidth - 1;
		case NORTH:
			return x + w / 2 - labelwidth / 2;
		case SOUTH:
			return x + w / 2 - labelwidth / 2;
		}
		return -1; // unreachable
	}

	private double getLabelY ( String label, Direction dir, double x, double y,
	                           double w, double h ) {
		double labelheight = (new Text(label)).getLayoutBounds().getHeight();
		switch ( dir ) {
		case EAST:
			return y + h - 5;
		case WEST:
			return y + h - 5;
		case NORTH:
			return y + h - 3;// -labelheight;
		case SOUTH:
			return y + labelheight - 2;
		}
		return -1; // unreachable
	}

	private double[] getPolyX ( Direction dir, double x, double y, double w,
	                            double h ) {
		switch ( dir ) {
		case EAST:
			return new double[] { x + 2, x + w / 2, x + w - 2, x + w / 2, x + 2 };
		case WEST:
			return new double[] { x + 2, x + w / 2, x + w - 2, x + w - 2, x + w / 2,
			                      x + 2 };
		case NORTH:
			return new double[] { x + 2, x + w / 2, x + w - 2, x + w - 2, x + 2 };
		case SOUTH:
			return new double[] { x + 2, x + w - 2, x + w - 2, x + w / 2, x + 2 };
		}
		return null; // unreachable
	}

	private double[] getPolyY ( Direction dir, double x, double y, double w,
	                            double h ) {
		switch ( dir ) {
		case EAST:
			return new double[] { y + 2, y + 2, y + h / 2, y + h - 2, y + h - 2 };
		case WEST:
			return new double[] { y + h / 2, y + 2, y + 2, y + h - 2, y + h - 2 };
		case NORTH:
			return new double[] { y + h / 2, y + 2, y + h / 2, y + h - 2, y + h - 2 };
		case SOUTH:
			return new double[] { y + 2, y + 2, y + h / 2, y + h - 2, y + h / 2 };
		}
		return null; // unreachable
	}
}
