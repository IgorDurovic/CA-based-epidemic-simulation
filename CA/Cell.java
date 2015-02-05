import java.util.ArrayList;

public class Cell {
	//Variables
	private Person[][] people;
	private int carryingCapacity;
	private int cellPopulation;
	private double emigrationRate;
	//How the population with work in this Enviroment
	Cell(int cc, int dimx, int dimy, double er) {
		carryingCapacity = cc;
		emigrationRate = er;
		people = new Person[dimx][dimy];
		for (int a = 0; a < dimx; a++) {
			for (int b = 0; b < dimy; b++) {
				int state = (int) Math.random() * 100;
				if (state > 15) {
					people[a][b] = new Person(0.4);
					cellPopulation++;
				} else {
					people[a][b] = null;
				}
			}
		}
	}
	//How someone will get better from the disease
	public int getHealthy() {
		int counter = 0;
		for (int a = 0; a < this.people.length; a++) {
			for (int b = 0; b < this.people[a].length; b++) {
				if (this.people[a][b].getPersonStatus() != Person.status.INFECTED) {
					counter++;
				}
			}
		}
		return counter / cellPopulation;
	}
	// How people move in/out of the enviroment
	public ArrayList<Person> Movement() {
		double rand;
		ArrayList<Person> emigrants = new ArrayList<Person>();
		for (int i = 0; i < this.people.length; i++) {
			for (int j = 0; j < this.people[i].length; j++) {
				if (emigrationRate > Math.random()) {
					emigrants.add(this.people[i][j]);
					this.people[i][j] = null;
					cellPopulation--;
				} else {
					rand = Math.random();
					if (rand < 0.9) {
						step(this.people, i, j);
					} else {
						break;
					}
				}
			}
		}
		return emigrants;
	}
	//How people move from place to place.
	private void step(Person[][] p, int x, int y) {
		int xRand = ((int)Math.random()*3)-1;
		int yRand = ((int)Math.random()*3)-1;
		if ((x + xRand < 0) || (p.length < x + xRand)) {
			xRand = -xRand;
		}
		if ((y + yRand < 0) || (p[x].length < y + yRand)) {
			yRand = -yRand;
		}
		x += xRand;
		y += yRand;
		if (p[x][y] != null) {
			step(p, x - xRand, y - yRand);
		} else {
			p[x][y] = p[x - xRand][y - yRand];
			p[x - xRand][y - yRand] = null;
		}
	}
	
	public int getCarryingCapacity() {
		return carryingCapacity;
	}

	public void setCarryingCapacity(int carryingCapacity) {
		this.carryingCapacity = carryingCapacity;
	}

	public int getCellPopulation() {
		return cellPopulation;
	}

	public void setCellPopulation(int cellPopulation) {
		this.cellPopulation = cellPopulation;
	}

	public double getEmigrationRate() {
		return emigrationRate;
	}

	public void setEmigrationRate(double emigrationRate) {
		this.emigrationRate = emigrationRate;
	}

	public void addPerson(Person p){
		int xRandPlace = (int)(Math.random()*(this.people.length));
		int yRandPlace = (int)(Math.random()*(this.people[xRandPlace].length));
		if(people[xRandPlace][yRandPlace] != null){
			addPerson(p);
		}
		else{
			people[xRandPlace][yRandPlace] = p;
		}
		cellPopulation++;
	}
	
	public void infection(){
		for(int x = 0; x < this.people.length; x++){
			for(int y = 0; y < this.people[x].length; y++){
				if(this.people[x][y].getPersonStatus() != Person.status.INFECTED){
					int infectedNeighbors = 0;
					for(int a = x - 1; a < x + 1; a++){
						for(int b = y - 1; b < y + 1; b++){
							if(((a>=0)&&(a<this.people.length))&&((b>=0)&&(b<this.people[a].length))){
								if(this.people[a][b] != null && !((a==x)&&(b==y))){
									if(this.people[a][b].getPersonStatus() == Person.status.INFECTED){
										infectedNeighbors++;
									}
								}
							}
						}
					}
					
					boolean state = true;
					while(infectedNeighbors > 0 && state){
						double prob = Math.random();
						if(prob < this.people[x][y].getInfectionRate()){
							this.people[x][y].setPersonStatus(Person.status.INFECTED);
							state = false;
						}
					}
				}
			}
		}
	}
}
