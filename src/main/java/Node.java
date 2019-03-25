//package main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

    // idea: might be good to have a seperate class for holding the connection instead of the file reader class
    // or connection could be established in main and be passed to methods as needed
    // but connection should be private to a class so no one can mess with it.

    // checks if the table already exists
    // passes up an exception if the table doesn't exist
    private void checkTable(Connection connection) throws java.sql.SQLException{
        // checks if the table exists
        // try: command to add the table
        try{
            Statement nodeTable = connection.createStatement();
            String addNodeTableStatement = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
            nodeTable.execute(addNodeTableStatement);
            // auto commit is turned off
            connection.commit();
        }
        // table already exists
        catch(java.sql.SQLException e){
            throw e;
        }

    }

    // adds a node to the given connection
    public void addNode(Connection connection){
        try{
            PreparedStatement nodeStatement = connection.prepareStatement("Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)");

            // can you mix prepared statement types?
            nodeStatement.setString(1, nodeID);
            nodeStatement.setString(2, String.valueOf(xCoord));
            nodeStatement.setString(3, String.valueOf(yCoord));
            nodeStatement.setString(4, String.valueOf(floor));
            nodeStatement.setString(5, building);
            nodeStatement.setString(6, nodeType);
            nodeStatement.setString(7, longName);
            nodeStatement.setString(8, shortName);

            nodeStatement.execute();
            // auto commit is turned off
            connection.commit();
        }
        // table does not exist or some other error happened
        catch (java.sql.SQLException e){
            // calls check table to make sure it exists
            try{
                checkTable(connection);
                // calls the function again now that the table exists
                addNode(connection);
            }
            // table already exists, handled here
            catch(java.sql.SQLException ie){
                System.out.println("Table already exists, a different error occurred");
            }
        }
    }

    public void deleteNode(Connection connection){

    }
}
