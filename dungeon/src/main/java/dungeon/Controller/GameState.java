package dungeon.Controller;

/**
 * keeps track of what state the game is currently in.
 */
public enum GameState {
    ///the very first SystemControllers necessary for all future stages. the game .jar file literally just started
    INITIALIZING,
    ///the player is currently seeing and can interact with the main menu to e.g. start a new game
    MAIN_MENU,
    /// a new game is initializing and e.g. dungeon generation is currently in process
    LOADING,
    /// the player can move their character around to fulfill the objectives of the game
    HAPPENING,
    /// the game has been completed and everything is being cleaned up
    DONE

}
