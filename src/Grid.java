
import java.awt.Dimension;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 41533
 */
public class Grid
{
    private int row, col = 0;
    private int[][] grid;
    
    public Grid(int row, int col)
    {
        this.row = row;
        this.col = col;
        grid = new int[row][col];
    }

    
    public void displayGrid()
    {
        String colString = "  1 2 3 4 5 6 7 8 9 10";
        System.out.println(colString);
         for (int r = 0; r < grid.length; r++)
        {     
            String tempString = "";
            for (int c = 0; c < grid[0].length; c++)
            {
                tempString += grid[r][c] + " ";
            }
            char letter = (char)(65 + r);
            System.out.println(letter + " " + tempString);
        }
         System.out.println("");
    }
    
    public void setSlot(int row, int col, int setter)
    {
        grid[row][col] = setter;
    }
    
    public int getSlot(int row, int col)
    {
        return grid[row][col];
    }
    
    public boolean isOccupied(int row, int col)
    {
        if (grid[row][col] > 0 && grid[row][col] != 8)
        {
            return true;
        }
        return false;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
    
    
}
