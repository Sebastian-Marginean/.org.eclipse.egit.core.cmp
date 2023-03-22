public class City 
{
	// - data - //
	private String name;
	private float latitude;
	private float longitude;
	private int population;
	
	// - getters - //
	public String getName(){		// public functions that allow us to access the data in city objects
		return name; 
	}		
	public float getLatitude(){
		return latitude; 
	}
	public float getLongitude(){
		return longitude;
	}
	public int getPopulation(){ 
		return population;
	}
	
	// - setters - //
	public void setName(String _name){ // public functions that allow us to mutate the data in city objects
		name = _name; 
	}		
	public void setLatitude(float _lat){
		latitude = _lat; 
	}
	public void setLongitude(float _lng){ 
		longitude = _lng; 
	}
	public void setPopulation(int _pop){ 
		population = _pop; 
	}
	
	// - constructor - //
	public City (String _name, float _latitude, float _longitude, int _population)	// // public functions that allow us to instantiate city objects
	{ 	
		name = _name; 
		latitude = _latitude; 
		longitude = _longitude; 
		population = _population;	
	}
	
	// - toString - //
	public String toString()		// public function that overrides the class toString and returns a String obj to be printed to console
	{ 	
		String str = "";
		
		str += name;
		
		if(name.length() < 8) {str += "			| "; }
		else if (name.length() < 16 ) {str += "		| ";}
		else {str += "	| "; }
		
		str += latitude + "	| " + longitude + "	| " + population + "	      | ";
		str += System.lineSeparator();
		
		return str;
		//return (name + "     " + latitude + "     " + longitude + "     " + population + "     " + System.lineSeparator()); 
	}
}






