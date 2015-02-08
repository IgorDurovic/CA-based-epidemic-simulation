import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Life extends PApplet 
{

	int millis = 100;
	int cellDim = 25;
	int mortalityRate 0.05;
	int population;

	Cell grid[][];
	Cell savedGrid[][];
	int lastTime = 0;

	public void setup()  //how big the enviroment is going to be
	{
		size(1280, 720);

		grid = new Cell[width / cellDim][height / cellDim];
		savedGrid = new Cell[width / cellDim][height / cellDim];

		stroke(48);

		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				grid[a][b] = new Cell(50, 10, 10, 0.1);
			}
		}
		background(256, 256, 256);
	}

	public void draw() // how big each person's area is going to be
	{
		for (int a = 0; a < width / cellDim; a++) 
		{
			for (int b = 0; b < height / cellDim; b++) 
			{
				///////////
				fill(256, 256 * grid[a][b].getHealthy(),
						256 * grid[a][b].getHealthy());
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
					int directionX;
					if(Math.random() > 0.5)
					{
						directionX = 1;
					}
					else
					{
						directionX = -1;
					}
					
					int directionY;
					if(Math.random() > 0.5)
					{
						directionY = 1;
					}
					else
					{
						directionY = -1;
					}
					
					if((x + directionX < 0) || (grid.length < x + directionX))
					{
						directionX = -directionX;
					}

					if((y + directionY < 0) || (grid.length < x + directionY))
					{
						directionY = -directionY;
					}
					x += directionX;
					y += directionY;
					
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
				grid.[a][b].recovery();
				grid.[a][b].death();
				grid.[a][b].graphing();
			}
		}

		// for(int a = 0; a < width/cellDim; a++){
		// for(int b = 0; b < height/cellDim; b++){
		// int neighbors = 0;
		// for(int c = a-1; c <= a+1; c++){
		// for(int d = b-1; d <= b+1; d++){
		// if(((c>=0)&&(c<width/cellDim))&&((d>=0)&&(d<height/cellDim))) { //
		// Make sure you are not out of bounds
		// if(!((c==a)&&(d==b))) { // Make sure to to check against self
		// if(savedCells[c][d]==1){
		// neighbors++; // Check alive neighbours and count them
		// }
		// } // End of if
		// }
		// }
		// }
		// if(savedCells[a][b] == 1){
		// if(neighbors<2||neighbors>3){
		// cells[a][b] = 0;
		// }
		// }
		// else{
		// if(neighbors == 3){
		// cells[a][b] = 1;
		// }
		// }
		// }
		// }
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
