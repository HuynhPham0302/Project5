package huynhpham;

//
//	Name: Pham, Huynh
//	Project: 5
//	Due: 12/08/2023
//	Course: cs-2400-02-f23
//
//	Description:
//		The project uses a Graph to store airport and distance information. 
//		Use Dijkstra's algorithm to find the shortest distances between airports
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The AirportApp class represents a simple command-line application for querying airport information
 * and finding the minimum distance between two airports using a directed graph.
 * It uses an adjacency list representation for the graph and reads data from airports.csv and distances.csv files.
 * The user can interact with the application through a console menu.
 * 
 *
 * The commands supported by the application are:
 * 
 *   Q: Query the airport information by entering the airport code.
 *   D: Find the minimum distance between two airports.
 *   H: Display the command menu.
 *   E: Exit the application.
 * 
 */
public class AirportApp {
    public static void main(String[] args) {
    	
    	System.out.println("Airport v0.1 by H. Pham\n");
    	try (Scanner sc = new Scanner(System.in)) {
			GraphInterface<String> airportGraph = new DirectedGraph<>();
			DictionaryInterface<String, String> airportInformation = new HashedDictionary<>();
			
			
			readDataFiles(airportGraph);
			readAirportInformation(airportInformation);

			
			while (true) {
			    System.out.print("Command? ");
			    String command = sc.nextLine();
			    
			    switch (command) {
			        case "Q":
			        	System.out.print("Airport code? ");
			            String airportCodeLine = sc.nextLine();
			            
			            String[] code = airportCodeLine.split("\\s+");
			            
			            if (code.length == 1) {
			                String airportCode = code[0];
			                queryAirportInfo(airportCode, airportInformation);
			              
			            } else {
			                System.out.println("Invalid command");
			            }
			            break;
			        case "D":
			        	System.out.print("Airport codes from to? ");
			        	String airportsLine = sc.nextLine();
			            
			            // Split the line into individual airport codes
			            String[] airportCodes = airportsLine.split("\\s+");

			            if (airportCodes.length == 2) {
			                String fromAirport = airportCodes[0];
			                String toAirport = airportCodes[1];

			                findMinimumDistance(fromAirport, toAirport, airportGraph, airportInformation);
			            } else {
			                System.out.println("Invalid command");
			            }
			            break;
			        case "H":
			            displayMenu();
			            break;
			        case "E":
			            System.exit(0);
			            break;
			        default:
			            System.out.println("Invalid command.");
			    }
			}
		}
    } 

    /**
     * Displays the command menu for the Airport application.
     * The menu includes options for querying airport information, finding the minimum distance between two airports,
     * displaying the command menu again, and exiting the application.
     */
    private static void displayMenu() {
        System.out.println("Q Query the airport information by entering the airport code.");
        System.out.println("D Find the minimum distance between two airports.");
        System.out.println("H Display this message.");
        System.out.println("E Exit.");
    }
    
    
    /**
     * Queries and prints information about an airport based on the provided airport code.
     * 
     * If the airport code is found in the provided airport information dictionary, the corresponding
     * airport information is printed to the console. Otherwise, a message indicating that the airport code
     * is unknown is displayed.
     * 
     * @param airportCode The code of the airport to query.
     * @param airportInformation The dictionary containing airport information, where the keys are airport codes,
     *                           and the values are corresponding airport information.
     */
    private static void queryAirportInfo(String airportCode, DictionaryInterface<String, String> airportInformation) {
        if (airportInformation.contains(airportCode)) {
            System.out.println(airportInformation.getValue(airportCode));
        } else {
            System.out.println("Airport code unknown");
        }
    }
    
    
    /**
     * Finds and prints the cheapest distance and route between two airports in the provided airport graph.
     * 
     * @param fromAirport The code of the source airport.
     * @param toAirport The code of the destination airport.
     * @param airportGraph The graph representing the connections and distances between airports.
     * @param airportInformation The dictionary containing airport information, where the keys are airport codes,
     *                           and the values are corresponding airport information.
     */
    private static void findMinimumDistance(String fromAirport, String toAirport, GraphInterface<String> airportGraph, DictionaryInterface<String, String> airportInformation) {
        if(!airportInformation.contains(fromAirport) || !airportInformation.contains(toAirport)) {
        	System.out.println("Airport code unknown");
        }else {
        	StackInterface<String> path = new LinkedStack<>();
            double distance = airportGraph.getCheapestPath(fromAirport, toAirport, path);

            if (distance != 0) {
                System.out.println("The minimum distance between " + airportInformation.getValue(fromAirport) + " and " + airportInformation.getValue(toAirport) +
                        " is " + distance + " through the route:");
                while (!path.isEmpty()) {
                    String airport = path.pop();
                    System.out.println(airportInformation.getValue(airport));
                }
            } else {
                System.out.println("Airports not connected");
            }
        }
    }
    
    /**
     * Reads airport information from the "airports.csv" file and populates the provided dictionary.
     * 
     * The method reads each line from the "airports.csv" file, where each line contains airport code and information
     * separated by a comma. It then adds the airport code as the key and the corresponding information as the value
     * to the provided dictionary.
     * 
     * @param dictionary The dictionary to populate with airport information, where keys are airport codes,
     *                   and values are corresponding airport information.
     * @throws IOException If an I/O error occurs while reading the "airports.csv" file.
     */
    private static void readAirportInformation(DictionaryInterface<String, String> dictionary) {
    	try {
            // Read airports.csv
            BufferedReader airportsReader = new BufferedReader(new FileReader("airports.csv"));
            String airportsLine;
            while ((airportsLine = airportsReader.readLine()) != null) {
                String[] airportData = airportsLine.split(",");
                String airportCode = airportData[0];
                String airportInfo = airportData[1];
                dictionary.add(airportCode, airportInfo);
            }
            airportsReader.close();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reads airport and distance information from "airports.csv" and "distances.csv" files and populates the provided graph.
     * 
     * The method reads airport codes from "airports.csv" and adds them as vertices to the graph.
     * It then reads distance information from "distances.csv" and adds directed edges between airports with corresponding distances.
     * 
     * @param airportGraph The graph to populate with airport and distance information.
     * @throws IOException If an I/O error occurs while reading the "airports.csv" or "distances.csv" files.
     */
    private static void readDataFiles(GraphInterface<String> airportGraph) {
        try {
            // Read airports.csv
            BufferedReader airportsReader = new BufferedReader(new FileReader("airports.csv"));
            String airportsLine;
            while ((airportsLine = airportsReader.readLine()) != null) {
                String[] airportData = airportsLine.split(",");
                String airportCode = airportData[0];
                airportGraph.addVertex(airportCode);
            }
            airportsReader.close();

            // Read distances.csv
            BufferedReader distancesReader = new BufferedReader(new FileReader("distances.csv"));
            String distancesLine;
            while ((distancesLine = distancesReader.readLine()) != null) {
                String[] distanceData = distancesLine.split(",");
                String sourceAirport = distanceData[0];
                String destinationAirport = distanceData[1];
                double distance = Double.parseDouble(distanceData[2]);
                airportGraph.addEdge(sourceAirport, destinationAirport, distance);
            }
            distancesReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
