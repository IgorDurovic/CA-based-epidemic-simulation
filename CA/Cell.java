import java.util.ArrayList;

public class Cell {

	private Person[][] people;
	private int carryingCapacity;
	private int cellPopulation;
	private double emigrationRate;

	Cell(int cc, int dimx, int dimy, double er) {
		carryingCapacity = cc;
		emigrationRate = er;
		people = new Person[dimx][dimy];
		for (int a = 0; a < dimx; a++) {
			for (int b = 0; b < dimy; b++) {
				int state = (int) Math.random() * 100;
				if (state > 15) {
					people[a][b] = new Person();
					cellPopulation++;
				} else {
					people[a][b] = null;
				}
			}
		}
	}

	public int getHealthy() {
		int counter = 0;
		for (int a = 0; a < people.length; a++) {
			for (int b = 0; b < people[a].length; b++) {
				if (people[a][b] != null) {
					counter++;
				}
			}
		}
		return counter / cellPopulation;
	}

	public ArrayList<Person> Movement() {
		double rand;
		ArrayList<Person> emigrants = new ArrayList();
		for (int i = 0; i < this.people.length; i++) {
			for (int j = 0; j < this.people[i].length; j++) {
				if (emigrationRate > Math.random()) {
					emigrants.add(this.people[i][j]);
					this.people[i][j] = null;
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
	
	public void addPerson(Person p){
		int xRandPlace = (int)(Math.random()*(this.people.length));
		int yRandPlace = (int)(Math.random()*(this.people[xRandPlace].length));
		if(people[xRandPlace][yRandPlace] != null){
			addPerson(p);
		}
		else{
			people[xRandPlace][yRandPlace] = p;
		}
	}
}
