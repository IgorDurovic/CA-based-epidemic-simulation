
public class Cell {
	
	private Person[][] people;
	private int carryingCapacity;
	private int cellPopulation;
	private double emigrationRate;
	
	Cell(int cc, int dimx, int dimy){
		carryingCapacity = cc;
		people = new Person[dimx][dimy];
		for(int a = 0; a < dimx; a++){
			for(int b = 0; b < dimy; b++){
				int state = (int) Math.random();
				if(state > 15){
					people[a][b] = new Person();
					cellPopulation++;
				}
				else{
					people[a][b] = null;
				}
			}
		}
	}
	
	public int getHealthy(){
		int counter = 0;
		for(int a = 0; a < people.length; a++){
			for(int b = 0; b < people[a].length; b++){
				if(people[a][b] != null){
					counter++;
				}
			}
		}
		return counter/cellPopulation;
	}
}
