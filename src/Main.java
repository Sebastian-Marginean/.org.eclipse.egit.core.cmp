import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	public static Scanner sk = new Scanner(System.in);				// public scanner accesible to all methods
	public static ArrayList<City> cities = new ArrayList<>();		// public array list of type City to hold our objs
	public static String path = null;								// directory path of our database txt 
	public static File fl = null;									// our .txt file
	
	public static void main(String[] args) throws FileNotFoundException // main method / driver
	{
		FindFile();
		txtToObj();
		while(true){
			mainMenu();		// infinite loop calling main menu
		}								
		//objToTxt();
	}
	
	// - database File Methods - //
	public static void FindFile() throws FileNotFoundException			// Find File Method
	{
		path = new File(".project").getAbsolutePath();					// find .project file in project
		path = path.replace(".project", "database.txt");				// replace the path portion of .project with database.txt
		fl = new File(path);											// set File = path
		
		if(!fl.exists()) {System.out.println("File Not found");}		// if could not locate file, say, file not found 
	}
	
	public static void txtToObj() throws FileNotFoundException			// txt to object method
	{
		Scanner skf = new Scanner(fl);									// new scanner that takes fl as param
		while(skf.hasNextLine())										// while loop that keeps iterating as long as there is a next line
		{
			String[] obj = skf.nextLine().split("\\s+");				// a String array called obj, split each line anywhere where there is 1 or more spaces
			
			City c = null;												// instantiate a new city object set to null
			String cityName = "";										// String to store name
			float latitude = 0, longitude = 0;										// float to store lat and lng
			int population = 0;												// int to store pop
			
			try															// try to parse String in array to data for object
			{
				cityName = obj[0];										// name = obj element in pos 0
				latitude = Float.parseFloat(obj[1]);							// lat = obj element in pos 1
				longitude = Float.parseFloat(obj[2]);							// lng = obj element in pos 2
				population = Integer.parseInt(obj[3]);							// pop = obj element in pos 3
			}
			
			catch(Exception e)											// our catch block
			{
				System.out.println("Error during conversion: could not parse String objects");		// prints error to user
			}
			
			finally														// finally block to create city and store it in array list
			{
				c = new City(cityName, latitude, longitude, population);					// create city using city constructor and data gathered from txt file
				cities.add(c);											// add created city to our array list of cities
			}
		}
	}
	
	public static void objToTxt() throws FileNotFoundException		// obj to txt method
	{
		String data = "";											// instantiate new string object to store our data in (to be written to file)
		
		for(int i = 0; i < cities.size(); i++)						// loop through all cities in our array list and gather data to store in data String
		{
			data += cities.get(i).getName() + " "
					+ cities.get(i).getLatitude() + " "
					+ cities.get(i).getLongitude() + " "
					+ cities.get(i).getPopulation() + " "
					+ System.lineSeparator();
		}
		
		try															// overwrite our database.txt file with new data gathered from loop
		{
			FileWriter writer = new FileWriter(path);				// create a new file writter obj
			writer.write(data);										// write our data to that file
			writer.close();											// close our writer
		}
		
		catch(Exception e) { e.printStackTrace(); }					// error handling catch block 
	}
	
	// - helper methods - //
	public static void print(String _str) {	System.out.print(_str);	}	// simple print method
	
	public static void saveChanges() {									// save changes method
		print("   1) save changes " + System.lineSeparator());			// ensure user wants to save changes
		print("   2) discard changes "+ System.lineSeparator());		
		
		if(sk.nextInt() == 1) {					// if user want to save, then call obj to txt to save data
			try{
				objToTxt(); 
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}
	
	public static double deg2rad(double deg) {
		return deg*(Math.PI/180);
	}
	
	public static double getDistance(double _latitude1,double _latitude2,double _longitude1,double _longitude2) {
		
		double r = 6371;
		double latitudeD = deg2rad(_latitude1 - _latitude2);
		double longitudeD = deg2rad(_longitude1 - _longitude2);
		
		double a = Math.sin(latitudeD/2)*Math.sin(latitudeD/2) + Math.cos(_latitude2)*Math.sin(longitudeD/2) * Math.sin(longitudeD/2);
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = r*c;
		
		d = (Math.round(d*100));
		d = d/100;
		
		return d;
	}
	
	// - methods - //
	public static void mainMenu()									// main menu
	{
		print("Main Menu: " + System.lineSeparator()					// print statement
		+ "   1) display all cities " + System.lineSeparator()
		+ "   2) search for a city " + System.lineSeparator()
		+ "   3) Insert a new city " + System.lineSeparator()
		+ "   4) delete a city " + System.lineSeparator()
		+ "   5) update a city " + System.lineSeparator()
		+ "   6) find distance between 2 cities " + System.lineSeparator()
		+ "   7) find nearby cities " + System.lineSeparator()
		+ "   8) exit city database " + System.lineSeparator());
		

		boolean valid = false;										// boolean condition used in while
		
		while(!valid)												// invalid input loop
		{
			int input = sk.nextInt();								// accept user input
			
			valid = true;											// set valid to true
			
			switch(input)											// switch statement
			{
				case 1:												// to do: display all cities
					displayAllCities();
					break;
					
				case 2:												// to do: search for a city
					searchForCity();
					break;
					
				case 3:												// to do: insert a new city
					insertNewCity();
					break;
					
				case 4:												// to do: delete a city
					deleteCity();
					break;
					
				case 5:												// update a city
					updateCity();
					break;
					
				case 6:												// find distance between 2 cities
					distance1();
					break;
					
				case 7:												// find nearby cities
					distance2();
					break;
					
				case 8:												// exit database
					print("You have successfully exited the program" + System.lineSeparator());
					System.exit(0);									// terminate program with exit code 0 = succesful termination
					break;
					
				default:											// prints invalid input to console and throws us back in loop
					print("Invalid Input" + System.lineSeparator());	// by setting valid = false
					valid = false;
					break;
			}
		}
	}

	public static void displayAllCities()			// loop through cities array list and print all values of city from city toString method
	{
		print("______________________________________________________________________"+System.lineSeparator());
		print("City:			| Latitude	| Longitude	| population  |"+System.lineSeparator());
		for(int i = 0; i < cities.size(); i++)
		{
			print(cities.get(i)+"");
		}
		print("______________________________________________________________________|"+System.lineSeparator()+System.lineSeparator());
	}

	public static void searchForCity(){			// search city method
		print("Enter City name: ");					// output to user
		
		String CityName = sk.next().toLowerCase();	// City and city are not equal to each other, therefore, we lowercase then to match
		
		boolean exists = false;						// boolean to ensure city was found
			
		for(int i = 0; i < cities.size(); i++) {	// loop through cities in array list to find city given by user
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName)) {			// if content equals since we want to compare contents and not memory locations	
				exists = true;						// set exists = true since we found the city
				
				System.out.println("City: " + cities.get(i).getName() + System.lineSeparator()
									+"Latitude: " + cities.get(i).getLatitude() + System.lineSeparator()
									+ "Longitude: " + cities.get(i).getLongitude() + System.lineSeparator()
									+ "Population: " + cities.get(i).getPopulation() + System.lineSeparator());		// print
				break; // break out of loop so that we do not keep iteration after we find city
			}
		}
		if(exists == false) {						// if exists is still false at this point, we have not found the city, so let use know that
			print("City does not Exist: " + System.lineSeparator());
		}
	}

	public static void insertNewCity() {
		print("Enter a City you want to insert: ");			// output to user
		
		String CityInsertName =  sk.next().toLowerCase();	// City and city are not equal to each other, therefore, we lowercase then to match
		
		boolean exists = false;								// boolean to ensure city was found
			
		for(int i = 0; i < cities.size(); i++) {			// loop through cities in array list to find city given by user
			if(cities.get(i).getName().toLowerCase().contentEquals(CityInsertName)) {		// if content equals since we want to compare contents and not memory locations	
				exists = true;								// set exists = true since we found the city
				print("City already exsits!");
				break;				// break out of loop so that we do not keep iteration after we find city
			}
		}
		if(exists == false) {		// if exists is still false at this point, we have not found the city, so let use know that
			print("Enter Latitude: " + System.lineSeparator());		// ask user for latitude, longitude and population
			float latitude = sk.nextFloat();
				
			print("Enter Longitude: " + System.lineSeparator());
			float longitude = sk.nextFloat();
				
			print("Enter Population: " + System.lineSeparator());
			int population = sk.nextInt();
				
			City c = new City(CityInsertName, latitude, longitude, population);		// create new city using user input
			cities.add(c);															// add city to arrayList
				
			print(System.lineSeparator());
				
			saveChanges();															// save changes
			}
	}
	
	public static void deleteCity(){			// search city method
		print("Enter City name: ");					// output to user
		
		String CityName = sk.next().toLowerCase();	// City and city are not equal to each other, therefore, we lowercase then to match
		
		boolean exists = false;						// boolean to ensure city was found
			
		for(int i = 0; i < cities.size(); i++) {	// loop through cities in array list to find city given by user
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName)) {			// if content equals since we want to compare contents and not memory locations	
				exists = true;						// set exists = true since we found the city
				
				print("Delete a city: " + cities.get(i).getName() + System.lineSeparator());				
				print("  1) Delete it!");			// simple print
				print("  2) Spare it!");
				
				if(sk.nextInt() == 1) {
					cities.remove(i);			// remove city from arrayList
					saveChanges();				// call, save changed method
				}
				
				break;				// break out of loop so that we do not keep iteration after we find city
			}
		}
		if(exists == false) {						// if exists is still false at this point, we have not found the city, so let use know that
			print("City does not Exist" + System.lineSeparator() + System.lineSeparator());
		}
	}
	
	public static void updateCity() {
		print("Enter City name: ");					// output to user
		
		String CityName = sk.next().toLowerCase();	// City and city are not equal to each other, therefore, we lowercase then to match
		
		boolean exists = false;						// boolean to ensure city was found
			
		for(int i = 0; i < cities.size(); i++) {	// loop through cities in array list to find city given by user
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName)) {			// if content equals since we want to compare contents and not memory locations	
				exists = true;						// set exists = true since we found the city
				
				print("Update name?      1) Yes    2) No " + System.lineSeparator());		//ask user if he wants to change the name of the city
				if(sk.nextInt() == 1) {
					print("Name of the city you want to update: ");
					cities.get(i).setName(sk.next());			// update name
					print(System.lineSeparator());
				}
				
				print("Update Population?      1) Yes    2) No " + System.lineSeparator());		//ask user if he wants to change the population of the city
				if(sk.nextInt() == 1) {
					print("Population of the city you want to update: ");
					cities.get(i).setPopulation(sk.nextInt());
					print(System.lineSeparator());
				}
				
				saveChanges();		// call saveChanges method
				
				break;				// break out of loop so that we do not keep iteration after we find city
			}
		}
		if(exists == false) {						// if exists is still false at this point, we have not found the city, so let use know that
			print("City does not Exist" + System.lineSeparator() + System.lineSeparator());
		}
	}
	
	public static void distance1()					// get distance between 2 cities method
	{
		print("City 1: ");								// ask for city 1
		String CityName1 = sk.next().toLowerCase();		// store input in cn1
		
		print("City 2: ");								// ask for city 2
		String CityName2 = sk.next().toLowerCase();		// store input in cn2
		
		boolean City1 = false, City2 = false;				// c1 and c2 booleans
		
		double[] LatitudeAndLongitude = new double[4];				// array of doubles that store lat1, lat2, lng1, lng2 (in this order)
		
		for(int i = 0; i<cities.size(); i++)		// loop through array list
		{
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName1))	// check if city 1 exists
			{
				City1 = true;													// set c1 = true
				LatitudeAndLongitude[0] = cities.get(i).getLatitude();								// store latitude in LatitudeAndLongitude position 0
				LatitudeAndLongitude[2] = cities.get(i).getLongitude();								// store longitude in LatitudeAndLongitude position 2
			}
			
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName2))	// check if city 1 exists
			{
				City2 = true;													// set c1 = true
				LatitudeAndLongitude[1] = cities.get(i).getLatitude();								// store latitude in LatitudeAndLongitude position 1
				LatitudeAndLongitude[3] = cities.get(i).getLongitude();								// store longitude in LatitudeAndLongitude position 2
			}
		}
		
		if(City1&&City2)	// if found both c1 and c2, print distance between them
		{
			print("Distance: " + getDistance(LatitudeAndLongitude[0], LatitudeAndLongitude[1], LatitudeAndLongitude[2], LatitudeAndLongitude[3]) + "KM" + System.lineSeparator() + System.lineSeparator());
		}
		
		else	// otherwise, print city not found
		{
			print("City does not exist " + System.lineSeparator() + System.lineSeparator());
		}
	}
	public static void distance2()					// find nearby cities method
	{
		print("City: ");								// prints city
		String CityName = sk.next();						// store city name input
		
		print("Distance: ");							// prints distance
		float distance = sk.nextFloat();					// stores distance input
		
		float latitudeFL = 0, longitudeFL = 0;						// floats lat and long set to 0
		
		boolean exists = false;						// exists boolean
		
		for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
		{
			if(cities.get(i).getName().toLowerCase().contentEquals(CityName.toLowerCase()))		// if content equals since we want to compare contents and not memory locations
			{
				exists = true;							// set exists = true since we found city
				latitudeFL = cities.get(i).getLatitude();			// set l1 to city lat
				longitudeFL = cities.get(i).getLongitude();			// set l2 to city lng
				break;									// break out of loop so that we do not keep iteration after we find city
			}
		}
		
		if(exists)	// if we find the city	
		{
			for(int i = 0; i < cities.size(); i++)		// loop through array of cities
			{
				double distance2 = getDistance(latitudeFL, cities.get(i).getLatitude(), longitudeFL, cities.get(i).getLongitude());	// get distance between city of choice and all other cities in array
				
				if(distance2<=distance && distance2>0)	// if that distance is less than distance from user input, and not equal to zero, since we dont want distance to our own city
				{
					print(cities.get(i).getName() + ": " + distance2 + "Km " + System.lineSeparator());	// print that city and its distance
				}
			}
			
			print(System.lineSeparator());	// skip line
		}
		else // if exists is still false at this point, we have not found city, so let user know
		{
			print("City does not exist" + System.lineSeparator()+System.lineSeparator());	
		}
	}
}
