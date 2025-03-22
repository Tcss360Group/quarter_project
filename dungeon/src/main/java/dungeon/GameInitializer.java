package dungeon;

import java.io.Serializable;

public class GameInitializer implements Serializable{
    private static final long serialVersionUID = 1L;

    public static void initializeGame() {
        // Create the table if it doesn't exist
        DatabaseSetup.createTable();
        
        CharacterDAO.insertCharacter(
         "Ogre",
         200,       
         45.0,       
         1.5,       
         2,        
         0.6,        
          0.1,          
          30,         
          60,         
          30,         
         60           
);
        // Gremlin stats
        CharacterDAO.insertCharacter(
            "Gremlin", 
            70, 
            22.5, 
            1.2, 
            5, 
            0.8, 
            0.4, 
            20, 
            40, 
            20, 
            40
        );
    
        // Skeleton stats
        CharacterDAO.insertCharacter(
         "Skeleton",
         100,       
         40.0,    
         1.0,      
         3,         
         0.8,     
         0.3,      
         30,        
         50,       
         30,        
         50         
       );
        System.out.println("Characters have been added to the database.");
    }
    
}