import processing.core.*;

import java.util.ArrayList;
import java.util.Random;

public class Life extends PApplet 
{
	int millis = 500;
	int cellDim = 25;
	public static boolean bool = false;
	static double mortalityRate = 0.01;
	int population;

	Cell grid[][];
	Cell savedGrid[][];
	int lastTime = 0;
	int count = 0;
	
	public void setup() 
	{
		size(1280, 720);

		grid = new Cell[width / cellDim][height / cellDim];
		savedGrid = new Cell[width / cellDim][height / cellDim];

		stroke(48);

		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				if(a == (width/cellDim)/2 && b == (height/cellDim)/2){
					bool = true;
					grid[a][b] = new Cell(50, 10, 10, 0.05);
				}
				else{
					grid[a][b] = new Cell(50, 10, 10, 0.05);
				}
			}
		}
		background(256, 256, 256);
	}

	public void draw() 
	{
		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				///////////
				fill(256, Math.round(256.0*grid[a][b].getHealthy()), Math.round(256.0*grid[a][b].getHealthy()));
				rect(cellDim * a, cellDim * b, cellDim, cellDim);
				///////////
			}
		}

		if (millis() - lastTime > millis) 
		{
			cycle();
			lastTime = millis();
		}
	}

	public void keyPressed() 
	{

	}

	public void cycle() 
	{
		//movement phase
		ArrayList<Person> immigrants;
		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				immigrants = grid[a][b].Movement();
				while(!immigrants.isEmpty())
				{
					int x = a, y = b;
					Random rndobj = new Random();
					int xRand = rndobj.nextInt(3) - 1;
					int yRand = rndobj.nextInt(3) - 1;
					
					if((x + xRand < 0) || (grid.length <= x + xRand))
					{
						xRand = -xRand;
					}

					if((y + yRand < 0) || (grid[0].length <= y + yRand))
					{
						yRand = -yRand;
					}
					x += xRand;
					y += yRand;
					
					if(grid[x][y].getCellPopulation() < grid[x][y].getCarryingCapacity())
					{
						grid[x][y].addPerson(immigrants.remove(immigrants.size()-1));
					}
				}
			}
		}
		
		//infection phase
		for(int a = 0; a < width/cellDim; a++)
		{
			for(int b = 0; b < height/cellDim; b++)
			{
				grid[a][b].infection();
			}
		}

		//additional processing
		//1. recovery
		//2. death
		//3. data graphing
		for(int a = 0; a < width/cellDim; a++)
		{
			for(int b = 0; b < height/cellDim; b++)
			{
				grid[a][b].recovery();
				grid[a][b].death();
				grid[a][b].graphing();
			}
		}
	}

	static public void main(String[] passedArgs) 
	{
		String[] appletArgs = new String[] { "--full-screen",
				"--bgcolor=#666666", "--stop-color=#cccccc", "Life" };
		if (passedArgs != null) 
		{
			PApplet.main(concat(appletArgs, passedArgs));
		} 
		else 
		{
			PApplet.main(appletArgs);
		}
	}
}
