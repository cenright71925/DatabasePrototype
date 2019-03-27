import java.sql.*;

public class Node {
    private String nodeID;
    private int xCoord;
    private int yCoord;
    private int floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;

    public Node(String nodeID, int xCoord, int yCoord, int floor, String building,
                String nodeType, String longName, String shortName) {
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    // Getters for every field
    public String getNodeID() {
        return nodeID;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    /**
     * private void connection() establishes connection for a node
     */
    private Connection connection;
    private void connection() {
        try {
            // myDB is the default apache derby database for this prototype
            // connection is kept open for node class
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");

        } catch (SQLException e) {
            // failed to connect to database means a connection is already open
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
    }
}
