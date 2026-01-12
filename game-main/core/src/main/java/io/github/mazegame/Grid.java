package io.github.mazegame;

//#region imports

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import io.github.mazegame.entities.PlayerEntity;
import io.github.mazegame.tiles.DoorTile;
import io.github.mazegame.tiles.ExitTile;
import io.github.mazegame.tiles.FloorTile;
import io.github.mazegame.tiles.Tile;
import io.github.mazegame.tiles.WallTile;

//#endregion

public class Grid {
    
    //#region Variables

    // ==============Grid Values==============
    static Tile[][] tiles;
    static int rows;
    static int columns;
    static Vector2 worldBounds;
    public final static int GRID_SQUARE_SIZE = 50;

    //#endregion


    //#region Initialisation

    public Grid(String levelFilePath) {
        loadLevel(levelFilePath);
        rows = tiles.length;
        columns = tiles[0].length;

        worldBounds = new Vector2(columns, rows).scl(GRID_SQUARE_SIZE);
    }

    
    /** Loads the the level from the file path given.
     * 
     * @param filePath the location of the level in the assets folder
     */
    private void loadLevel(String filePath) {
        // Opens the file and reads the text on it.
        FileHandle file = Gdx.files.internal(filePath);
        String text = file.readString();

        // Splits the text into its lines and gets the length all lines should be
        String[] lines = text.split("\\r?\\n");
        int width = lines[0].length();

        // Reverses the list and raises an error if any lines are a different length
        ArrayList<String> reverseLines = new ArrayList<String>();   
        for (String line : lines) {
            reverseLines.add(0, line);
            if (line.length() != width) {
                throw new RuntimeException("line "+ reverseLines.size() +" has incorrect width of "+ line.length());
            }
        }

        // Sets the size of the tiles 2d array and adds each tile into the array
        tiles = new Tile[lines.length][width];

        for (int y = 0; y < lines.length; y++){
            for (int x = 0; x < width; x++){
                char character = reverseLines.get(y).charAt(x);
                tiles[y][x] = getTileType(character, x ,y);
            }
        }
    }

    /** Gets the {@code Tile} corresponding to the character from the file
     * 
     * @param tile the character to check
     * @param x the x-coordinate for the tile on the grid
     * @param y the y-coordinate for the tile on the grid
     * @return the {@link Tile} from the character
     */
    private Tile getTileType(char tile, int x, int y){
        switch (tile){
            // Blank space
            case '.':
                return new FloorTile();
            // Wall
            case '#':
                return new WallTile();
            // Player
            case '@':
                loadPlayer(x, y);
                return new FloorTile();
            // Exit
            case '$':
                return new ExitTile();
            // Door
            case 'D':
                return new DoorTile();
            default:
                throw new RuntimeException("Character "+ tile +" not supported");
        }
    }

    /** Loads the player in the position given.
     * 
     * @param x the x-coordinate on the grid
     * @param y the y-coordinate on the grid
     */
    private void loadPlayer(int x, int y) {
        Vector2 playerGridPosition = new Vector2(x, y);
        EntityManager.add(new PlayerEntity(playerGridPosition));
    }

    //#endregion


    //#region Getters & Setters

    /** Gets the {@code Tile} of the tile in the given position.
     * 
     * @param x the x-coordinate of the tile
     * @param y the y-coordinate of the tile
     * @return the {@code Tile} of the tile in the given position
     */
    public static Tile getTile(int x, int y){
        return tiles[y][x];
    }

    /** Gets the {@code Tile} of the tile in the given position.
     * 
     * @param position the xy-coordinate of the tile
     * @return the {@code Tile} of the tile in the given position
     */
    public static Tile getTile(Vector2 position){
        return tiles[(int)position.y][(int)position.x];
    }

    //#endregion


    //#region Drawing

    public void draw(SpriteBatch batch) {
        // Draws all the tiles in the maze
        for (int y = 0; y < rows; y++){
            for (int x = 0; x < columns; x++){
                Vector2 position = new Vector2(x, y).scl(GRID_SQUARE_SIZE);
                tiles[y][x].drawTo(batch, position, GRID_SQUARE_SIZE);
            } 
        }
    }

    /** Dispose of all tile textures. */
    public void dispose() {
        for (int y = 0; y < rows; y++){
            for (int x = 0; x < columns; x++){
                Tile tile = tiles[y][x];
                tile.dispose();
            } 
        }
    }

    //#endregion
}
