/**
 * Author: Mike Trinka (mike.r.trinka@lmco.com)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Prob14 {
    private static final String INPUT_FILE_NAME = "Prob14.in.txt";
    
    static String departureCity = null;
    static String arrivalCity = null;
    
    // destinationMap uses a city name to get an ArrayList of the cities you can get to from that city
    static HashMap<String, ArrayList<String>> destinationMap = new HashMap<String, ArrayList<String>>();
    
    // weightMap uses a city name to get an ArrayList of leg weights for each destination
    static HashMap<String, ArrayList<Integer>> weightMap = new HashMap<String, ArrayList<Integer>>();
    
    // routes and distances are all the possible routes we've found and their corresponding distances
    // this is the list we will loop through, and we will add to it while looping
    // when we get to the end, we will have exhausted all possible paths
    static ArrayList<ArrayList<String>> routes = new ArrayList<ArrayList<String>>();
    static ArrayList<Integer> distances = new ArrayList<Integer>();
    
    public static void main(String[] args) {
        readInput();
        
        // for keeping the best route info
        int minDistance = Integer.MAX_VALUE;
        ArrayList<String> bestRoute = null;
        Integer bestDistance = null;
        String output = null;
        
        // add the original departure city and distance of 0 to routes and distances
        ArrayList<String> newRoute = new ArrayList<String>();
        newRoute.add(departureCity);
        routes.add(newRoute);
        Integer newDistance = new Integer(0);
        distances.add(newDistance);
        
        // continue adding to routes as long as we can
        for (int i=0; i<routes.size(); i++) {
            // get the current route and cumulative distance
            ArrayList<String> currentRoute = routes.get(i);
            Integer currentDistance = distances.get(i);
            
            // find the current city we're in on this route
            String currentCity = currentRoute.get(currentRoute.size() - 1);
            
            // are we there yet?
            if (!currentCity.equals(arrivalCity)) {
                // not there yet - get this list of possible destinations and weights
                ArrayList<String> destinationList = destinationMap.get(currentCity);
                ArrayList<Integer> weightList = weightMap.get(currentCity);
                
                if (destinationList != null) {
                    // go through this list and find cities that we haven't been to yet on this route
                    for (int j=0; j<destinationList.size(); j++) {
                        String nextCity = destinationList.get(j);
                        Integer nextDistance = weightList.get(j);
                        
                        if (!currentRoute.contains(nextCity)) {
                            // haven't been there - create a new route and add it to the end of the routes list
                            newRoute = new ArrayList<String>();
                            for (int k=0; k<currentRoute.size(); k++) {
                                newRoute.add(currentRoute.get(k));
                            }
                            newRoute.add(nextCity);
                            routes.add(newRoute);
                            
                            // add the next distance to the running total
                            newDistance = new Integer(currentDistance.intValue() + nextDistance.intValue());
                            distances.add(newDistance);
                            
                            if (nextCity.equals(arrivalCity)) {
                                // we can get to our final destination from the city we're in
                                if (newDistance.intValue() < minDistance) {
                                    // found a new best route - save the data
                                    bestRoute = newRoute;
                                    bestDistance = newDistance;
                                    minDistance = bestDistance.intValue();
                                    output = printRoute(bestRoute, bestDistance);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(output);
    }
    
    private static void readInput() {
        try {
            File inFile = new File(INPUT_FILE_NAME);
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            
            String inLine = null;
            boolean firstLine = true;
            
            while ((inLine = br.readLine()) != null) {
                // split on the semicolons
                String[] tokens = inLine.split(";");
                
                if (firstLine) {
                    // the first line gives us our start and end points
                    departureCity = tokens[0];
                    arrivalCity = tokens[1];
                    firstLine = false;
                } else {
                    // every other line gives route data
                    String tempDepartureCity = tokens[0];
                    String tempArrivalCity = tokens[1];
                    Integer legWeight = new Integer(tokens[2]);
                    
                    // get the destinations and weights for the departure city
                    ArrayList<String> destinationList = destinationMap.get(tempDepartureCity);
                    ArrayList<Integer> weightList = weightMap.get(tempDepartureCity);
                    
                    if (destinationList == null) {
                        // first destination for this departure city, create the lists
                        destinationList = new ArrayList<String>();
                        destinationMap.put(tempDepartureCity, destinationList);
                        
                        weightList = new ArrayList<Integer>();
                        weightMap.put(tempDepartureCity, weightList);
                    }
                    
                    // now add the new entries
                    destinationList.add(tempArrivalCity);
                    weightList.add(legWeight);
                }
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String printRoute(ArrayList<String> route, Integer distance) {
        StringBuffer buf = new StringBuffer();
        
        for (int i=0; i<route.size(); i++) {
            buf.append(route.get(i));
            
            // add an arrow if there are more hops
            if (i < (route.size() - 1)) {
                buf.append("->");
            }
        }
        
        // add the distance
        buf.append("=");
        buf.append(distance.intValue());
        
        return buf.toString();
    }
}
