package edu.frostburg.cosc310.RyanBrisbane.PokedexProject;

/**
 * Interface for the Pokemap project. Your main class should implement this
 * interface.
 * 
 * @author Steve K
 */
public interface Pokedex {
	
	/**
	 * Search.
	 * @param pokemon The name of the Pokemon
	 * @return The full entry associated with this Pokemon.
	 */
	String find(String pokemon);

	/**
	 * Add a new entry.
	 * @param pokemon The name will be the table key.
	 * @param entry The value that will be inserted.
	 * @return true if insertion was successful
	 */
	boolean add(String pokemon, String entry);

	/**
	 * Remove an entry.
	 * @param pokemon  The key for the entry to remove.
	 * @return true if this entry was deleted.
	 */
	boolean delete(String pokemon);

	/**
	 * Print the entire table in the format below:
	 * 	KEY : ENTRY
	 */
	void printHT();
	
	/**
	 * How full is this table?  Count how many entries you have and divide by the size of the table.  
	 * @return count / size
	 */
	double getLoadFactor();
	
	/**
	 * How full can this table be? 
	 * @return maximum load factor
	 */
	double getMaxLoadFactor();
	
	/**
	 * The number of items in this table. 
	 * @return n
	 */
	int count();

	/**
	 * The author of this particular Pokedex.
	 */
	void who();

	/**
	 * Print all the instructions along with any helpful information.
	 */
	void help();

	/**
	 * Print an exit message and close out the Pokedex program.
	 */
	void exit();
}
