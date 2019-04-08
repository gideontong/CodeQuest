import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

public class Prob162 {

	/**
	 * An enumeration describing the six faces of the cube.  Each face token
	 * corresponds to how we view the cube as it looks in the problem picture.
	 */
	private enum Face {
		FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM
	}
	
	/**
	 * A static inner class modelling a two-dimensional Cartesian system point.
	 */
	private static class Bizarro2DPoint {
		
		//-- INSTANCE VARIABLES --//
		// Cartesian coordinates x and y.
		private double x;
		private double y;
		
		/**
		 * Constructs a Bizarro2DPoint from given coordinates x and y.
		 * 
		 * @param x the point's x coordinate
		 * @param y the point's y coordinate
		 */
		public Bizarro2DPoint(double x, double y) {
			
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Calculates the distance between two Bizarro2DPoints on the
		 * Cartesian plane.
		 * 
		 * @param that a point
		 * @return the distance between both points
		 */
		public double distanceTo(Bizarro2DPoint that) {
			
			double aSquared = Math.pow(this.x - that.x, 2);
			double bSquared = Math.pow(this.y - that.y, 2);
			double cSquared = aSquared + bSquared;
			
			return Math.sqrt(cSquared);
		}
		
		@Override
		public String toString() {
			
			return "(" + x + ", " + y + ")";
		}
	}
	
	/**
	 * A static inner class modelling a three-dimensional Cartesian system point
	 * that exists on one of the faces of a Bizarro world cube.
	 */
	private static class Bizarro3DPoint {

		//-- INSTANCE VARIABLES --//
		private String id;
		
		// Cartesian coordinates x, y, and z.
		private double x;
		private double y;
		private double z;
		
		private double e;  // the length of any given edge on the cube
		
		
		//-- CONSTRUCTORS --//
		/**
		 * Constructs a BizarroPoint from the string input line formats
		 * described in the problem text.
		 * 
		 * @param id an identifier for the point
		 * @param point a string in the format "x y z", where x, y, and z are
		 * 	numbers
		 * @param edgeLength the length on any given edge on the cube as a
		 * 	string
		 */
		public Bizarro3DPoint(String id, String point, String edgeLength) {
			
			this.id = id;
			
			String[] coordinates = point.split("\\s");
			this.x = Double.parseDouble(coordinates[0]);
			this.y = Double.parseDouble(coordinates[1]);
			this.z = Double.parseDouble(coordinates[2]);
			
			this.e = Double.parseDouble(edgeLength);
		}
		
		
		//-- METHODS --//
		/**
		 * Determines what face of the cube that point is on according to our
		 * normalised model of the cube (matching the diagram in the problem).
		 * 
		 * @return the face that the BizzaroPoint is located on
		 */
		public Face getFace() {

			double coordinateMax = e / 2;
			
			// The FRONT face contains the point (coordinateMax, 0, 0).
			if(x == coordinateMax)
				return Face.FRONT;
			
			// The BACK face contains the point (-coordinateMax, 0, 0).
			if(x == -coordinateMax)
				return Face.BACK;
			
			// The RIGHT face contains the point (0, coordinateMax, 0).
			if(y == coordinateMax)
				return Face.RIGHT;
			
			// The LEFT face contains the point (0, -coordinateMax, 0).
			if(y == -coordinateMax)
				return Face.LEFT;
			
			// The TOP face contains the point (0, 0, coordinateMax).
			if(z == coordinateMax)
				return Face.TOP;
			
			// The BOTTOM face contains the point (0, 0, -coordinateMax).
			return Face.BOTTOM;
		}
		
		/**
		 * Returns the permutations of the point to check for in a 2D context
		 * based on what face that the point is on. 
		 *
		 * @param otherPoint the other point
		 * @return a vector of 2D points
		 */
		public Vector<Bizarro2DPoint> getPermutations(Bizarro3DPoint otherPoint) {
			
			Vector<Bizarro2DPoint> permutations = new Vector<Bizarro2DPoint>();
			
			switch(getFace()) {
				case FRONT:
					/*       y
					 *       |
					 *      +-+
					 * -x --|F|-- x
					 *      +-+
					 *       |
					 *      -y
					 */
					permutations.add(new Bizarro2DPoint(y, z));
					
					/*       y
					 *       |
					 *      +-+-+-+-+-+
					 * -x --|0| | | |F|-- x
					 *      +-+-+-+-+-+
					 *       |
					 *      -y
					 */					
					if(otherPoint.getFace() == Face.TOP) {
						permutations.add(new Bizarro2DPoint(y + 4 * e, z));
					}					
				break;
					
				case TOP:
					/*       y
					 *       |
					 *      +-+
					 *      |T|
					 *      +-+
					 * -x --|0|-- x
					 *      +-+
					 *       |
					 *      -y
					 */
					rotateAnticlockwiseOnYAxis();
					permutations.add(new Bizarro2DPoint(y, z + e));

					/*       y
					 *       |
					 *      +-+-+
					 *      | |T|
					 *      +-+-+
					 * -x --|0| |-- x
					 *      +-+-+
					 *       |
					 *      -y
					 */
					rotateClockwiseOnXAxis();
					permutations.add(new Bizarro2DPoint(y + e, z + e));
					
					/*       y
					 *       |
					 *      +-+-+-+
					 *      | | |T|
					 *      +-+-+-+
					 * -x --|0| | |-- x
					 *      +-+-+-+
					 *       |
					 *      -y
					 */
					rotateClockwiseOnXAxis();
					permutations.add(new Bizarro2DPoint(y + 2 * e, z + e));
					
					/*       y
					 *       |
					 *      +-+-+-+-+
					 *      | | | |T|
					 *      +-+-+-+-+
					 * -x --|0| | | |-- x
					 *      +-+-+-+-+
					 *       |
					 *      -y
					 */
					rotateClockwiseOnXAxis();
					permutations.add(new Bizarro2DPoint(y + 3 * e, z + e));
				break;
					
				case BACK:
					rotateAnticlockwiseOnYAxis();
					rotateAnticlockwiseOnYAxis();
					
					/*       y
					 *       |
					 *      +-+
					 *      |B|
					 *      +-+
					 *      | |
					 *      +-+
					 * -x --|0|-- x
					 *      +-+
					 *       |
					 *      -y
					 */					
					permutations.add(new Bizarro2DPoint(y, z + 2 * e));
					
					/*       y
					 *       |
					 *      +-+
					 * -x --|0|-- x
					 *      +-+
					 *      | |
					 *      +-+
					 *      |B|
					 *      +-+
					 *       |
					 *      -y
					 */
					permutations.add(new Bizarro2DPoint(y, z - 2 * e));
					
					rotateAnticlockwiseOnYAxis();
					rotateAnticlockwiseOnYAxis();
					
					rotateClockwiseOnZAxis();
					rotateClockwiseOnZAxis();
					
					/*       y
					 *       |
					 *      +-+-+-+
					 * -x --|0| |B|-- x
					 *      +-+-+-+
					 *       |
					 *      -y
					 */
					permutations.add(new Bizarro2DPoint(y + 2 * e, z));
					
					/*           y
					 *           |
					 *      +-+-+-+
					 * -x --|B| |0|-- x
					 *      +-+-+-+
					 *           |
					 *          -y
					 */
					permutations.add(new Bizarro2DPoint(y - 2 * e, z));
				break;
					
				default:
					// DO NOTHING
			}
			
			return permutations;
		}
		
		/**
		 * Rotates the point anti-clockwise around the Y-axis.
		 */
		public void rotateAnticlockwiseOnYAxis() {
			
			double temp = x;
			this.x = z;
			// this.y = y;
			this.z = -temp;
		}
		
		/**
		 * Rotates the point clockwise around the X-axis.
		 */
		public void rotateClockwiseOnXAxis() {
			
			double temp = y;
			// this.x = x;
			this.y = z;
			this.z = -temp;
		}
		
		/**
		 * Rotates the point clockwise around the Z-axis.
		 */
		public void rotateClockwiseOnZAxis() {
			
			double temp = x;
			this.x = y;
			this.y = -temp;
			// this.z = z;
		}
		
		@Override
		public String toString() {
						
			return "Point " + id + " (" + x + ", " + y + ", " + z + ") on face " + getFace() + ".";
		}
	}
	
	public static double getShortestDistance(Bizarro3DPoint pointA, Bizarro3DPoint pointB) {
		
		// Rotate the cube into a normal position, moving point A to the front
		// face.
		if((pointA.getFace() == Face.TOP) || (pointA.getFace() == Face.BOTTOM)) {
			pointA.rotateClockwiseOnXAxis();
			pointB.rotateClockwiseOnXAxis();
		}
		
		while(pointA.getFace() != Face.FRONT) {
			pointA.rotateClockwiseOnZAxis();
			pointB.rotateClockwiseOnZAxis();
		}
		
		// Rotate the cube into a normal position, moving point B to the top
		// face if it is not already on the front or back face.
		while((pointB.getFace() != Face.FRONT) && (pointB.getFace() != Face.BACK) && (pointB.getFace() != Face.TOP)) {
			pointA.rotateClockwiseOnXAxis();
			pointB.rotateClockwiseOnXAxis();
		}
		
		// Determine the permutations to check for each point.
		Vector<Bizarro2DPoint> permutationsA = pointA.getPermutations(pointB);
		Vector<Bizarro2DPoint> permutationsB = pointB.getPermutations(pointA);
		
		// Determine the shortest distance.
		double length = Double.MAX_VALUE;
		for(Bizarro2DPoint a : permutationsA) {
			for(Bizarro2DPoint b : permutationsB) {
				double distance = a.distanceTo(b);
				
				if(distance < length)
					length = distance;
			}
		}
		
		return length;
	}
	
	//-- METHODS: MAIN --//
	/**
	 * Main.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) throws IOException {
		
		final String FILENAME = "Prob16.in.txt";
		
		// Prepare the file reader.
		BufferedReader reader = new BufferedReader(new FileReader(FILENAME));
		
		while(true) {
			
			// Read and check the inputs.
			String edgeLength = reader.readLine();
			String inputA = reader.readLine();
			String inputB = reader.readLine();
			
			if((edgeLength == null) || (inputA == null) || (inputB == null))
				break;
			
			// Construct the 3D points.
			Bizarro3DPoint pointA = new Bizarro3DPoint("A", inputA, edgeLength);
			Bizarro3DPoint pointB = new Bizarro3DPoint("B", inputB, edgeLength);
			
			// Determine the shortest distance.
			double distance = getShortestDistance(pointA, pointB);
			
			// Print the shortest distance as output.
			DecimalFormat outputFormat = new DecimalFormat("#0.0000");		
			System.out.println(outputFormat.format(distance));
		}
		
		// Close the file reader.
		reader.close();
	}

}