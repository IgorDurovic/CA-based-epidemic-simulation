import java.util.ArrayList;
import java.util.Random;

public class Cell 
{

	//Variables
	private Person[][] people;
	private int carryingCapacity;
	private int cellPopulation;
	private double emigrationRate;
	private int deaths;

	//How the population with work in this Environment
	Cell(int cc, int dimx, int dimy, double er) 
	{
		carryingCapacity = cc;
		emigrationRate = er;
		deaths = 0;
		people = new Person[dimx][dimy];
		cellPopulation = 0;
		boolean bool = true;
		for (int a = 0; a < dimx; a++) 
		{
			for (int b = 0; b < dimy; b++) 
			{
				Random rndobj = new Random();
				int state = rndobj.nextInt(100);
				if (state > 75) 
				{
					people[a][b] = new Person(0.4);
					cellPopulation++;
					if(state > 97 && bool)
					{
						bool = false;
						people[a][b].setPersonStatus(Person.status.INFECTED, 25);
					}
				} 
				else 
				{
					people[a][b] = null;
				}
			}
		}
	}

	//How someone will get better from the disease
	public double getHealthy() 
	{
		double counter = 0.0;
		for (int a = 0; a < this.people.length; a++) 
		{
			for (int b = 0; b < this.people[a].length; b++) 
			{
				if(this.people[a][b] != null)
				{
					if (this.people[a][b].getPersonStatus() != Person.status.INFECTED) 
					{
						counter++;
					}
				}
			}
		}
		return counter / (double)cellPopulation;
	}

	// How people move in/out of the environment
	public ArrayList<Person> Movement() 
	{
		double rand;
		ArrayList<Person> emigrants = new ArrayList<Person>();
		for (int i = 0; i < this.people.length; i++) 
		{
			for (int j = 0; j < this.people[0].length; j++) 
			{
				if(this.people[i][j] != null)
				{
					if (emigrationRate > Math.random()) 
					{
						//person e = new Person(this.people[i][j]);
						emigrants.add(new Person(this.people[i][j]));
						this.people[i][j] = null;
						this.cellPopulation--;
					} 
					else 
					{
						rand = Math.random();
						if (rand < 0.9 && countNeighbors(this.people, i, j) < 8) 
						{
							step(this.people, i, j);
						} 
						else 
						{
							break;
						}
					}
				}
			}
		}
		return emigrants;
	}

	//How people move from place to place.
	private void step(Person[][] p, int x, int y) 
	{
		Random rndobj = new Random();
		int x_i = x;
		int y_i = y;
		int counter = 0;
		while(true)
		{
			counter++;
			x = x_i;
			y = y_i;
			int xRand = rndobj.nextInt(3) - 1;
			int yRand = rndobj.nextInt(3) - 1;
	
			if ((x + xRand < 0) || (p.length <= x + xRand)) 
			{
				xRand = -xRand;
			}
	
			if ((y + yRand < 0) || (p[x].length <= y + yRand)) 
			{
				yRand = -yRand;
			}
	
			x += xRand;
			y += yRand;
			
			if(p[x][y] == null || counter > 15)
			{
				break;
			}
		}
		p[x][y] = new Person(p[x_i][y_i]);
		p[x_i][y_i] = null;
	}

	public void addPerson(Person p)
	{
		Random rand = new Random();
		int xRandPlace = rand.nextInt(this.people.length);
		int yRandPlace = rand.nextInt(this.people[0].length);
		while(people[xRandPlace][yRandPlace] != null)
		{
			xRandPlace = rand.nextInt(this.people.length);
			yRandPlace = rand.nextInt(this.people[0].length);
		}
		people[xRandPlace][yRandPlace] = p;
		cellPopulation++;
	}
	
	public void infection()
	{
		for(int x = 0; x < this.people.length; x++)
		{
			for(int y = 0; y < this.people[x].length; y++)
			{
				if(this.people[x][y] != null)
				{
					if(this.people[x][y].getPersonStatus() == Person.status.SUSCEPTIBLE)
					{
						int infectedNeighbors = countNeighbors(this.people, x, y, Person.status.INFECTED);
						boolean state = true;
						while(infectedNeighbors > 0 && state)
						{
							double prob = Math.random();
							if(prob < this.people[x][y].getInfectionRate())
							{
								this.people[x][y].setPersonStatus(Person.status.INFECTED, 10);
								state = false;
							}
							infectedNeighbors--;
						}
					}
					else
					{
						this.people[x][y].recoveryTime--;
					}
				}
			}
		}
	}

	public int countNeighbors(Person[][] people, int x, int y, Person.status p)
	{
		int neighbors = 0;
		for(int a = x - 1; a <= x + 1; a++)
		{
			for(int b = y - 1; b <= y + 1; b++)
			{
				if(((a>=0)&&(a<people.length))&&((b>=0)&&(b<people[a].length)))
				{
					if(people[a][b] != null && !((a==x)&&(b==y)))
					{
						if(people[a][b].getPersonStatus() == p)
						{
							neighbors++;
						}
					}
				}
			}
		}
		return neighbors;
	}

	public int countNeighbors(Person[][] people, int x, int y)
	{
		int neighbors = 0;
		for(int a = x - 1; a <= x + 1; a++)
		{
			for(int b = y - 1; b <= y + 1; b++)
			{
				if(((a>=0)&&(a<people.length))&&((b>=0)&&(b<people[a].length)))
				{
					if(people[a][b] != null && !((a==x)&&(b==y)))
					{
						neighbors++;
					}
				}
			}
		}
		return neighbors;
	}

	//after a given period of time each person who is infected becomes
	//immune to the disease
	public void recovery()
	{
		for(int x = 0; x < this.people.length; x++)
		{
			for(int y = 0; y < this.people[x].length; y++)
			{
				if(this.people[x][y] != null && this.people[x][y].getPersonStatus() == Person.status.INFECTED)
				{
					if(this.people[x][y].recoveryTime == 0)
					{
						this.people[x][y].setPersonStatus(Person.status.REMOVED, 15);
					}
				}
			}
		}
	}

	//the people that are infected have a possibility of dying based on
	//the mortality rate defined in the Life class
	public void death()
	{
		for(int x = 0; x < this.people.length; x++)
		{
			for(int y = 0; y < this.people[x].length; y++)
			{
				double rand = Math.random();
				if(this.people[x][y] != null)
				{
					if(this.people[x][y].getPersonStatus() == Person.status.INFECTED && rand < Life.mortalityRate)
					{
						this.people[x][y] = null;
						this.deaths++;
						this.cellPopulation--;
					}
				}
			}
		}
	}

	//JFreeChart library for graphing
	public void graphing()
	{
		
	}

	public int getCarryingCapacity() 
	{
		return carryingCapacity;
	}

	public void setCarryingCapacity(int carryingCapacity) 
	{
		this.carryingCapacity = carryingCapacity;
	}

	public int getCellPopulation() 
	{
		return cellPopulation;
	}

	public void setCellPopulation(int cellPopulation) 
	{
		this.cellPopulation = cellPopulation;
	}

	public double getEmigrationRate() 
	{
		return emigrationRate;
	}

	public void setEmigrationRate(double emigrationRate) 
	{
		this.emigrationRate = emigrationRate;
	}

	public int getDeaths(){
		return deaths;
	}
}
