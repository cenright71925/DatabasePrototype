package main.java;


public class Node
{
    //nodeID,
    // xcoord,
    // ycoord,
    // floor,
    // building,
    // nodeType,
    // longName,
    // shortName

    //BCONF00102,
    // 2150,
    // 1025,
    // 2,
    // 45 Francis,
    // CONF,
    // Duncan Reid Conference Room,
    // Conf B0102


    private String nodeID;
    private int xCoord; //long or int???
    private int yCoord;
    private int floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;

    public Node(String nodeID, int xCoord, int yCoord, int floor, String building,
                String nodeType, String longName, String shortName)
    {
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    //all these getters are needed for the table
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

}
