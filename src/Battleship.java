
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Ethan McKinnon
 */
public class Battleship
{

    private static final int GRIDSIZE = 10;
    private static final int SHIPCOUNT = 5;
    private static final int TOTALHITS = 17;

    private Grid playerOcean;
    private Grid computerOcean;
    private Grid playerGuess;
    private Grid compGuess;

    private boolean gameOver = false;

    public Battleship()
    {
        playerOcean = new Grid(GRIDSIZE, GRIDSIZE);
        computerOcean = new Grid(GRIDSIZE, GRIDSIZE);

        playerGuess = new Grid(GRIDSIZE, GRIDSIZE);
        compGuess = new Grid(GRIDSIZE, GRIDSIZE);
    }

    public void playGame()
    {
        System.out.println("Your Grid:");
        playerOcean.displayGrid();
        setupPlayer();
        setupAI();
        while (!gameOver)
        {
            playerMove();
            compMove();
        }

    }

    public void playerMove()
    {
        System.out.println("Your Grid");
        playerOcean.displayGrid();
        System.out.println("Your Guesses");
        playerGuess.displayGrid();
        Scanner in = new Scanner(System.in);
        System.out.println("Where would you like to shoot?: ");
        String userInput = in.nextLine().toUpperCase();
        char letter = userInput.charAt(0);
        int row = (int) letter - 65;
        int col = Integer.parseInt(userInput.substring(1)) - 1;

        if (computerOcean.isOccupied(row, col))
        {
            playerGuess.setSlot(row, col, 9);
            checkForWin(0);
        } else
        {
            playerGuess.setSlot(row, col, 8);
        }
    }

    public void compMove()
    {
        Random rand = new Random();
        int col = rand.nextInt(10);
        int row = rand.nextInt(10);
        if (playerOcean.isOccupied(row, col))
        {
            compGuess.setSlot(row, col, 9);
            playerOcean.setSlot(row, col, 9);
            checkForWin(1);
        } else
        {
            playerOcean.setSlot(row,col,8);
            compGuess.setSlot(row, col, 8);
        }
    }

    public void placePlayerShip(int length)
    {
        Scanner in = new Scanner(System.in);
        int[] bowPos = new int[2];
        System.out.println("Place your " + length + " long ship. Enter Port Location (EX: A5): ");
        in = new Scanner(System.in);
        String userInput = in.nextLine().toUpperCase();
        char letter = userInput.charAt(0);
        int row = (int) letter - 65;
        int col = Integer.parseInt(userInput.substring(1)) - 1;
        
        bowPos[0] = row;
        bowPos[1] = col;
        System.out.println("Ship Direction (S,N,E): ");
        String direction = in.nextLine().toUpperCase();
        addPlayerShip(bowPos, direction, length);
    }

    public void setupPlayer()
    {

        for (int i = 0; i < SHIPCOUNT; i++)
        {
            int length;
            switch (i)
            {
                case 0:
                    length = 2;
                    placePlayerShip(length);
                    break;
                case 1:
                    length = 3;
                    placePlayerShip(length);
                    break;
                case 2:
                    length = 3;
                    placePlayerShip(length);
                    break;
                case 3:
                    length = 4;
                    placePlayerShip(length);
                    break;
                case 4:
                    length = 5;
                    placePlayerShip(length);
                    break;
            }
        }
    }

    public void placeAIShips(int length)
    {
        int col, row;
        int[] bowPos = new int[2];
        Random rand = new Random();
        col = rand.nextInt(10);
        row = rand.nextInt(10);
        String direction = chooseAIDirection(row, col, length);
        bowPos[0] = row;
        bowPos[1] = col;
        while (!validShip(bowPos, direction, length))
        {
            col = rand.nextInt(10);
            row = rand.nextInt(10);
            direction = chooseAIDirection(row, col, length);
            bowPos[0] = row;
            bowPos[1] = col;
        }
        if (col + length > GRIDSIZE)
        {
            col -= length;
        }
        addAIShip(bowPos, direction, length);
    }

    public void setupAI()
    {
        for (int i = 0; i < SHIPCOUNT; i++)
        {
            int length;
            switch (i)
            {
                case 0:
                    length = 2;
                    placeAIShips(length);
                    break;
                case 1:
                    length = 3;
                    placeAIShips(length);
                    break;
                case 2:
                    length = 3;
                    placeAIShips(length);
                    break;
                case 3:
                    length = 4;
                    placeAIShips(length);
                    break;
                case 4:
                    length = 5;
                    placeAIShips(length);
                    break;
            }
        }

    }

    public String chooseAIDirection(int row, int col, int length)
    {
        Random rand = new Random();
        String direction = "";
        int decider;
        while (direction.equals(""))
        {
            decider = rand.nextInt(3);
            if (decider == 0 && row > length)
            {
                direction = "North";
            } else if (decider == 1 && row + length < GRIDSIZE)
            {
                direction = "South";
            } else if (decider == 2)
            {
                direction = "East";
            }
        }
        return direction;
    }

    public void addPlayerShip(int[] bowPos, String direction, int length)
    {
        switch (direction)
        {
            case "N":
                for (int i = 0; i < length; i++)
                {
                    playerOcean.setSlot(bowPos[0] - i, bowPos[1], length - 1);
                }
                break;
            case "S":
                for (int i = 0; i < length; i++)
                {
                    playerOcean.setSlot(bowPos[0] + i, bowPos[1], length - 1);
                }
                break;
            case "E":
                for (int i = 0; i < length; i++)
                {
                    playerOcean.setSlot(bowPos[0], bowPos[1] + i, length - 1);
                }
                break;
        }
    }

    public void addAIShip(int[] bowPos, String direction, int length)
    {
        switch (direction)
        {
            case "North":
                for (int i = 0; i < length; i++)
                {
                    computerOcean.setSlot(bowPos[0] - i, bowPos[1], length - 1);
                }
                break;
            case "South":
                for (int i = 0; i < length; i++)
                {
                    computerOcean.setSlot(bowPos[0] + i, bowPos[1], length - 1);
                }
                break;
            case "East":
                for (int i = 0; i < length; i++)
                {
                    computerOcean.setSlot(bowPos[0], bowPos[1] + i, length - 1);
                }
                break;
        }
    }

    public boolean validShip(int[] bowPos, String direction, int length)
    {
        int unOccupied = 0;
        switch (direction)
        {
            case "North":
                for (int i = 0; i < length; i++)
                {
                    if (computerOcean.isOccupied(bowPos[0] - i, bowPos[1]) && bowPos[0] > length)
                    {
                        return false;
                    } else
                    {
                        unOccupied++;
                    }
                }
                break;
            case "South":
                for (int i = 0; i < length; i++)
                {
                    if (computerOcean.isOccupied(bowPos[0] + i, bowPos[1]) && bowPos[0] + length < GRIDSIZE)
                    {
                        return false;
                    } else
                    {
                        unOccupied++;
                    }

                }
                break;
            case "East":
                for (int i = 0; i < length; i++)
                {
                    if (bowPos[1] + length > GRIDSIZE || computerOcean.isOccupied(bowPos[0], bowPos[1] + i))
                    {
                        return false;
                    } else
                    {
                        unOccupied++;
                    }
                }
                break;
        }
        if (unOccupied != length)
        {
            return false;
        }
        return true;
    }

    private void checkForWin(int player)
    {
        int amountOfHits = 0;
        for (int r = 0; r < GRIDSIZE; r++)
        {
            for (int c = 0; c < GRIDSIZE; c++)
            {
                if (playerGuess.isOccupied(r, c) && computerOcean.isOccupied(r, c))
                {
                    amountOfHits++;
                }
            }
        }
        if (amountOfHits == TOTALHITS && player == 0)
        {
            gameOver = true;
            System.out.println("YOU WIN!");
            System.exit(0);
        } else if (amountOfHits == TOTALHITS && player == 1)
        {
            gameOver = true;
            System.out.println("YOU LOSE!");
            System.exit(0);
        }
    }
}
