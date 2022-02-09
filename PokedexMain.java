package edu.frostburg.cosc310.RyanBrisbane.PokedexProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main class of the pokedex project. Contains methods for insertion, deletion, searching, among
 * other things.
 * Please forgive my sparse in line comments. I'm tired and feel that a lot of this is pretty
 * self explanatory. As you say: If it's too complicated and needs a ton of comments rewrite it
 */
public class PokedexMain extends HashMap implements Pokedex {

    /**
     * This is the same method I've used for reading csv files for the past two projects.
     * Yes, I copied it. No, I'm not ashamed.
     * @param line row of table values from the csv
     * @return List of table values that were previously comma separated
     */
    public List<String> getLines(String line){
        List<String> values = new ArrayList<>();
        try(Scanner rowReader = new Scanner(line)){
            rowReader.useDelimiter(",");
            while(rowReader.hasNext()){
                values.add(rowReader.next());
            }
        }
        return values;
    }
    //what even am i doing???
    /**
     * Formats the data for a given pokemon for easy access and printing
     * @param entry
     * @return a nicely formatted list of the pokemon's data values. the moveset gave me a hard time
     * because some of them have quotes around the brackets and some don't. This method exists because
     * I tried to pull the name (key) from one specific index every time but the variable number of moves
     * kept pushing that index around so I'd get numbers and stuff.
     */
    public String[] formatString(String entry){
        entry = entry.replace("\"", "");
        String moves = entry.substring(entry.indexOf('['), entry.indexOf(']')+1);
        String otherStuff = entry.substring(entry.indexOf(']')+1);
        String[] dataEntry = otherStuff.split(",");
        String[] dataEntry2 = new String[20];
        dataEntry2[0] = moves;
        for(int i = 1; i < dataEntry.length; i++){
            dataEntry2[i] = dataEntry[i-1];
        }
        return dataEntry2;
    }

    /**
     * Searches through the table to locate the pokemon with the provided name
     * @param pokemon The name of the Pokemon
     * @return  The data for the given pokemon, null if it doesn't exist within the table
     */
    public String find(String pokemon){
        if(contains(pokemon)){
            return search(pokemon, 0).entry;
        }
        return null;
    }

    /**
     * Inserts a new pokemon into the table
     * @param pokemon The name will be the table key. (thanks for these! makes my life a little bit easier)
     * @param entry The value that will be inserted.
     * @return true if insertion was successful, false if not
     */
    public boolean add(String pokemon, String entry){
        return insert(pokemon, entry);
    }

    /**
     * Deletes the provided pokemon from the table (if it exists)
     * @param pokemon  The key for the entry to remove.
     * @return  True if deletion succeeds, false otherwise
     */
    public boolean delete(String pokemon){
        return remove(pokemon);
    }

    /**
     * Prints the table in it's entirety. I have NEVER EVER EVER been able to nicely execute a printf
     * statement so PLEASE forgive me because the spacing of the headers is a little weird. It's the
     * closest I could get.
     */
    public void printHT(){
        for(HashEntry pokemon: hashtable){
            if(pokemon != null){
                System.out.printf("%-15s", pokemon.getKey() + " : ");
                String[] formatted = formatString(pokemon.getEntry());
                System.out.printf("%-85.85s", formatted[0]);
                for (int i = 1; i < 14; i++){
                    System.out.printf("%-50.50s", formatted[i]);
                }
                System.out.println();
            }
        }
    }

    /**
     * Provides the current load factor of the table
     * @return the load factor
     */
    public double getLoadFactor(){
        return getLF();
    }

    /**
     * Provides the maximum capacity of the table
     * @return max load factor
     */
    public double getMaxLoadFactor(){
        return getMLF();
    }

    /**
     * provides the current quantity of values stored in the table
     * @return number of entries
     */
    public int count(){
        return getCount();
    }

    /**
     * easy one. Prints the project name and author
     */
    public void who(){
        System.out.println("Pokedex Project - Ryan Brisbane");
    }

    /**
     * Prints a list of commands for the user
     */
    public void help(){
        System.out.println("add – insert an entry into the table");
        System.out.println("delete – remove an entry");
        System.out.println("find - enter a name. if found, the entry will be printed");
        System.out.println("printHT – prints the entire table");
        System.out.println("getLF - prints the current load factor");
        System.out.println("getMLF - prints the max load factor");
        System.out.println("who – prints the project name and author");
        System.out.println("help - prints this list  of commands");
        System.out.println("exit - closes the program");
    }

    /**
     * Exits the program
     */
    public void exit(){
        System.out.println("Thank you, have a nice day!");
        System.exit(0);
    }

    /**
     * Heh. The big one. Contains the do/while loop that accepts user input and executes the
     * different methods based on that.
     * @param args (what does this even mean? I never actually remember)
     */
    public static void main(String[] args){
        PokedexMain pokedex = new PokedexMain();
        //reading from the input file
        List<List<String>> inFile = new ArrayList<>();
        try {
            //I used the original input file because I enjoy pain.
            Scanner read = new Scanner(new File("/Users/rpbris11/IdeaProjects/RBPokedexProject/src/edu/frostburg/cosc310/RyanBrisbane/PokedexProject/pokemon_pokedex.csv"));
            while (read.hasNextLine()) {
                inFile.add(pokedex.getLines(read.nextLine()));
            }
        } catch (IOException e) {
            System.err.println(e);
        }
        //saving the column headers for easy access when we print data out later
        inFile.remove(0);
        List<String> columnHeaders = inFile.get(0);
        inFile.remove(0);
        //adding the actual pokemon into the table
        for(List<String> l: inFile){
            String key;
            String entry = "";
            for(int i = 0; i < l.size(); i++){
                entry += l.get(i);
                entry += ",";
            }
            String[] formattedEntry = pokedex.formatString(entry);
            key = formattedEntry[7];
            pokedex.add(key, entry);
        }

        //user input begins here
        Scanner in = new Scanner(System.in);
        String token;
        System.out.println("\nHello!\n");
        do {
            System.out.print("Please enter a command (enter 'help' to see the list): ");
            token = in.nextLine();
            //insertion
            if(token.equals("add")){
                System.out.print("Enter the name of the pokemon you'd like to insert (case sensitive): ");
                String key = in.nextLine();
                System.out.println("Enter the values of your pokemon's data, separated by commas:");
                String entry = in.nextLine();
                pokedex.add(key, entry);
            }
            //deletion
            else if(token.equals("delete")){
                System.out.print("Enter the name of the pokemon you wish to delete (case sensitive): ");
                String deletion = in.nextLine();
                pokedex.delete(deletion);
            }
            //searching
            else if(token.equals("find")){
                System.out.print("Enter the name of the pokemon you wish to find (case sensitive - capitalize first letter): ");
                String search = in.nextLine();
                String value = pokedex.find(search);
                if(value != null){
                    System.out.printf("%-85.85s", columnHeaders.get(0));
                    for (int i = 1; i < columnHeaders.size(); i++){
                        System.out.printf("%50.60s", columnHeaders.get(i));
                    }
                    //THIS STUPID LINE had me struggling to find what was wrong for 45 minutes.
                    //Kept thinking to myself WHY ISN'T THIS PRINTING THE VALUES??? and it was. at the end of the header row.
                    System.out.println(); //curse you.
                    String[] formatted = pokedex.formatString(value);
                    System.out.printf("%-85.85s", formatted[0]);
                    for (int i = 1; i < 14; i++){
                        System.out.printf("%-50.50s", formatted[i]);
                    }
                    System.out.println();
                }

            }
            //printing whole table
            else if(token.equals("printHT")){
                System.out.printf("%-15s", "key");
                System.out.printf("%-85.85s", columnHeaders.get(0));
                for (int i = 1; i < columnHeaders.size(); i++){
                    System.out.printf("%50.60s", columnHeaders.get(i));
                }
                System.out.println();
                pokedex.printHT();
                System.out.println();
            }
            //current load factor
            else if(token.equals("getLF")){
                System.out.println("Current Load Factor: "+ pokedex.getLoadFactor());
            }
            //max load factor
            else if(token.equals("getMLF")){
                System.out.println("Max Load Factor: "+ pokedex.getMaxLoadFactor());
            }
            //current number of values in table
            else if(token.equals("count")){
                System.out.println("Current count: " + pokedex.count());
            }
            //project info
            else if(token.equals("who")){
                pokedex.who();
            }
            //command list
            else if(token.equals("help")){
                pokedex.help();
            }
            //unrecognized command default case
            else if(!token.equals("find") && !token.equals("add") &&
                    !token.equals("delete") && !token.equals("printHT")
                    &&!token.equals("getLF") && !token.equals("getMLF") && !token.equals("count")
                    && !token.equals("who") && !token.equals("help") && !token.equals("exit")){
                System.out.println("Command not recognized. Try again.");
            }
        //exit case
        }while(!token.equals("exit"));
        pokedex.exit();
    }
}
