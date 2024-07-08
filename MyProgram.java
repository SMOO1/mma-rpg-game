/*
Sasha, Layton and Eli
Program name: MMA fight RPG
Purpose: to create a fun text base game for people to play
Tuesday March 5th 2024
*/

import java.util.Scanner; // imports scanner
import java.util.Random;  // imports random generation
import java.util.InputMismatchException; // imports class to handle errors in scanner input

public class MyProgram {
    
    //Adding color code
    public static final String ANSI_RESET = "\u001B[0m"; //reset colour
    public static final String ANSI_RED = "\u001B[31m";  //Red
    public static final String ANSI_BLUE = "\u001B[34m"; //blue
    
    //global variables to access from anywhere
    private static Scanner scanner = new Scanner(System.in); 
    private static Random random = new Random();
    
    private static int botChosen; 
    private static int botHp;
    private static String botName;
    private static String continueChoice;
    private static String userName;
    private static int millisSpeed = 10; //millisecond speed variable to control slow print function
    
    //main ting
    public static void main(String[] args) {
        ascii.Intro();
        ContinueMessage(); 
        ClearScreen(); 
        
        //prints starting lore
        SlowPrint("You see a hole in the ground, curious about where it leads to. You go closer to get a better look. Suddenly, out of nowhere Sashas Papa comes behind you, and pushes you down into the hole. You wake up to find yourself in the world of MMA. To exit you must beat a boss in an MMA fight", millisSpeed);
        ContinueMessage(); //asks player to contiue
        ClearScreen();  // clears screen
        
        //Prints game information, introduction and uses username function to get username input
        SlowPrint("Game Information:"+"\n"+"- The fight is move by move" + "\n" +"- The player can choose how, and where to attack the boss" + "\n"+"- The player can try to block the attack from the boss"+"\n"+"- Liver shot will increase player damage done"+"\n"+"- A stun will skip the boss's move"+"\n"+"- The boss will attack the player and block their moves"+"\n"+"- The boss may perform a special move which cannot be blocked \n"+"\n", millisSpeed);
        System.out.println("Welcome to the MMA fight game made by Sasha, Eli and Layton");
        userName = GetUsername();  // Variable assigned from function - For Username
         
        // asks user for choice of who they want to fight
        System.out.println("\nWelcome " + userName +  ", you have the choice to fight 3 bosses. \neasy boss Ms Kim (press 1) \nmedium boss mr. Egan (press 2) \nhard boss Mr Partington (press 3)"+"\n"+"Exit Game (press 4)");
        
        int userChoice = CheckInput(4); 
        botChosen = userChoice;  // Variable assigned from function - For bot fight choice
        
        switch (userChoice) { // Switch statement to determine who user will fight
            case 1:
                FightSkeleton(50,50, 5, "Ms.Kim"); //Fight Against Ms.Kim
                break;
            case 2:
                FightSkeleton(100,100, 7,"Mr.Egan"); //fight against mr egan
                break;
            case 3:
                FightSkeleton(150,150, 8,"Mr.Partington"); //fight against mr partington
                break;
            case 4:
                System.out.println("Exiting program"); // exits the program
                break;
        }
        
        scanner.close(); //close scanner
    }
    //function to get username
    static String GetUsername() {
    while (true) {
        System.out.println("Enter your username: "); //prompts the user to enter a user name
        userName = scanner.nextLine();

        if (userName.length() <= 15) { //makes sure that user name is shorter than 15 charecters
            return userName; 
        } else {
            System.out.println("Username is too long, enter a shorter username."); //tells user if there name is to long
            }
        }
    }
    
    //error handling function for integer inputs
    static int CheckInput(int highestChoice) { 
        int input = 0; 
        boolean isValid = false; // Set as false
        while (!isValid) { // while there isnt a valid input
            //trying to get integer value of input
            try {
                input = Integer.valueOf(scanner.nextLine()); //getting user input
                if (input <= highestChoice && input > 0) { //If statement whether input is valid
                    isValid = true; // Will set as true if input is valid
                } else {
                    System.out.println("Input one of the options please");
                }
                //if not integer value input, make user re enter until valid
            } catch (NumberFormatException e) { 
                System.out.println("Input one of the options please");
            }
        }
        //return input if the value is valid
        return input;
    }
    
    // function that prints player and bot information
    static void PrintGameInfo(int playerHp, int playerStamina, int botHp, int botStamina, String botName) {
        if(playerHp<0){ // If player hp is less than zero
            playerHp = 0; //Player Hp will become 0
        }
        if(botHp <0){ // If bothp is less than zero
            botHp = 0; // Bot Hp will become 0
        }
        //printing statistics with color
        System.out.println("Your HP: " + ANSI_RED + playerHp + ANSI_RESET+ " | Your Stamina: " + ANSI_BLUE+ playerStamina + ANSI_RESET + "\n" + botName + " HP: " + ANSI_RED +botHp + ANSI_RESET +" | " + botName + " stamina: " + ANSI_BLUE+botStamina + ANSI_RESET+ "\n");
    }
        
    //fight skeleton    
    static void FightSkeleton(int botHp, int botStamina, int specialMoveChance, String chosenBot) {
        int playerHp = 100, playerStamina = 100, playerTimeOut = 2;  
        
        //statistic variables of player and bot
        int maxBotHp = botHp; 
        int maxBotStamina = botStamina;
        int maxPlayerHp = playerHp; 
        int maxPlayerStamina = playerStamina; 
        
        //variables for bot blocking and stunning, aswell as the bot name
        int blockChance, stunChance, playerChoice, botBlock, botSpecial; 
        String continueChoice; 
        boolean botBlocked = false, botStunned = false, playerStunned = false; 
        String botName = chosenBot; 
        
        
        //while loop to continue fight until player or bot is at 0 hp or stamina
        while (true) {
            
            botSpecial = random.nextInt(specialMoveChance); // Set random variable for Special move chance
            botBlocked = false; //set bot block to false at start of every round so no infinite blocking
            ClearScreen();
            PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
            
            //ending loop if player hp or stamina is too low
            if (playerHp <= 0 || playerStamina <= 0) { //
                ClearScreen();
                //victory message
                SlowPrint("\n"+botName + " has just knocked you out, unfortunatly for you this means that you will be stuck in the world of MMA forever",50);
                break;
            } 

            
            //set random values for a random chance for each particular variable
            blockChance = random.nextInt(3); //1 in 3 chance of being blocked
            stunChance = random.nextInt(5); //1 in 5 chance of stunning bot
            botBlock = random.nextInt(3); //1 in 3 chance of bot blocking
            
            
            //check if the bot has blocked
            if (botBlock == blockChance) { // If statement
                botBlocked = true; 
            }
            
            //let the player move
            
            // player attack
            System.out.println("\nchoose your attack: \n\npunch (press 1) \nkick (press 2) \ncall timeout (press 3) Timeouts left: " + playerTimeouts+"\n");

            playerChoice = CheckInput(3); // Check player choice
            
            //CLEARING SCREEN AND PRINTING STATISTICS
            ClearScreen();
            PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);

            // if player punches
            if (playerChoice == 1) {
            System.out.println("Choose where you would like to punch: \nhead (press 1): 20 damage, 15 stamina \ntorso (press 2): 10 damage, 10 stamina, 25% liver connection");
            
            //checking player punch target integer choice
            playerChoice = CheckInput(2); 
            
            //CLEARING SCREEN AND PRINTING STATISTICS
            ClearScreen();
            PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);

            //if bot blocks
            if (botBlocked) {
                System.out.print(botName + " has blocked your move");
                playerStamina-=15; 
                } else{//if bot does not block
                    
                    ClearScreen();
                    PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
                    //successful punch message
                    System.out.println("You have successfuly punched "+ botName+"!");
                    
                    //subtracting stamina and health based on user choice (head or torso)
                    if (playerChoice == 1) { // If Statement for player choice of 1 head
                        playerStamina -= 15;
                        botHp -=20; 
                    } else if (playerChoice == 2) { // Else if statement if it was 2 torso
                        playerStamina -= 10;
                        botHp = LiverChance(10, botName, botHp); // Variable assigned by liver chance function to determine if user hit liver
                    }
                }
                //prompting user to continue
                ContinueMessage();
            
            
            // if player kicks
            } else if (playerChoice == 2) { // If statement for the player choice of 2
                System.out.println("\nchoose where you would like to kick: \ntorso (press 1): 15 damage, 15 stamina, 25% liver connection \nlegs (press 2): 10 damage, 5 stamina, 25% stun chance");
                playerChoice = CheckInput(2);//get input, 1 or 2
                ClearScreen();
                PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
                    
                //if bot blocks
                if (botBlocked) {
                    System.out.print(botName + " has blocked your move");
                    playerStamina-=15;
                    ContinueMessage();
                    ClearScreen();
                    PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
                    
                //if bot does not block
                } else{
                    
                    ClearScreen(); // clears screen
                    PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
                    
                    System.out.println("You successfully kicked "+ botName + "!\n");
                        if (playerChoice == 1) { // If the players attack choice is 1
                            playerStamina -= 15;
                            botHp = LiverChance(15, botName, botHp); // liverchance for bot
                                
                        //if kick to stomach 
                        } else if (playerChoice == 2) { // If the players attack choice is 2
                            playerStamina -= 5;
                            botHp -= 10;
                            //setting a chance of stunning the bot
                            stunChance = random.nextInt(4);
                            //handling if the bot gets stunned
                            if(stunChance == 1){ // If the random stunChance is equal to 1, and meets the criteria
                                botStunned = true;
                                System.out.println("You have stunned "+ botName + " by kicking the shins!\n");
                                    
                            }
                        }
                        ContinueMessage();
                    }
                // player timeout choice
                } else {
                    //only let the user to gain hp and stamina if they have enough timeouts left
                    if(playerTimeouts>0){ // If the user has timeouts availible, 
                        System.out.println("You have taken a time out, regaining or maintaining HP and stamina.");
                        playerHp = TimeOutGain(playerHp, maxPlayerHp);
                        playerStamina = TimeOutGain(playerStamina, maxPlayerStamina);
                        //subtract timeouts and prompt to continue
                        playerTimeouts--;
                        ContinueMessage(); 
                    }else{ // Otherwise
                        //if no more timeouts left, print fail message
                        System.out.println("You attempted to call a timeout, but failed.");
                        ContinueMessage();
                    }
            
                }
                 
        
        ClearScreen();
        PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
        
        //end program if bot has no hp or stamina
        if (botHp <= 0 || botStamina <= 0) { // If bot has no hp or stamina statement
                ClearScreen();
                if(botStamina<=0){ //Specifically bot has no stamina
                    //message for if the botstamina is 0 or less
                    SlowPrint(userName+" has won because "+botName+" has ran out of stamina, \n\nYou are now teleported to back outside the whole where you see sashas papa looking into it. You think to yourself about what you should do.", millisSpeed);
                    GetUserEnding(); 
                    break;
                }else{ // Otherwise (Specifically if bot has no hp)
                    //message for if bot health is 0 or less
                    SlowPrint(userName+" has just knocked out " + botName + "\n\nYou are now teleported to back outside the whole where you see sashas papa looking into it. You think to yourself about what you should do", millisSpeed);
                    GetUserEnding(); 
                    break;
                }
                
            }
        
        //bot can move if not stunned
        if(!botStunned){
            if(botSpecial == 1){ // If the special move chance of the bot meets the criteria
                botStamina -=20;
                playerHp = SpecialMoveSkeleton(playerHp, chosenBot); //determining player hp based off of the chance of the special move connecting
            }else{ // Otherwise
                //prompting user to block 
            System.out.println("Choose where you would like to block: \nhead (press 1) \nbody(press 2) \nlegs (press 3)\n ");
            playerChoice = CheckInput(3); 
        
            //clearing screen and printing info
            ClearScreen();
            PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
            
            //bot's turn to move
            int botAction = random.nextInt(3)+1; //adding 1 to produce a valid random integer, since 0 is not an option for the bot
            int botTarget = random.nextInt(3)+1; 

            if (botAction == 1 || botAction == 2) { // If statement the choices the bot can make
            // Bot decides to punch or kick
                System.out.println(botName + " attempts to " + (botAction == 1 ? "punch" : "kick") +
                            " at " + (botTarget == 1 ? "head" : botTarget == 2 ? "body" : "legs"));
                       
                if (playerChoice == botTarget) { //If the input of player is equal to bots attack choice
                    System.out.println("You successfully blocked the attack!");
                } else {
                    //bot punch
                    if (botAction == 1) { // If the botaction variable is equal to 1
                        playerHp -= 10; 
                        botStamina -=10;
                        switch (chosenBot) { //switch case to determine which teacher animation to play
                            //ascii animations for bot punching
                            case "Ms.Kim":
                                ascii.MsKimPunching();
                                break;
                            case "Mr.Egan":
                                ascii.MrEganPunching();
                                break;
                            case "Mr.Partington":
                                ascii.MrPartingtonPunching();
                                break;
                    
                            }
                    } else {//bot kick
                    playerHp -= 15; 
                    botStamina -=15;
                    switch (chosenBot) {//switch case to determine which teacher animation to play
                        //ascii animation for bots kicking
                            case "Ms.Kim":
                                ascii.MsKimKicking();
                                break;
                            case "Mr.Egan":
                                ascii.MrEganKicking();
                                break;
                            case "Mr.Partington":
                                ascii.MrPartingtonKicking();
                                break;
                    
                            }
                    }
                    System.out.println(botName+"'s attack was successful, you were not able to block.");
                }
            } else { // Otherwise
                //reducing number of bot timeouts so that bot does not gain stamina and hp more than needed
                botTimeouts--;
                if(botTimeouts > 0){ // If the # of bot timouts if greater than 0
                // Bot takes a timeout, gains hp or stamina if not already at max
                    if(botHp == maxBotHp && botStamina == maxBotStamina){ // If bot hp is at max, and bot stamina is at max
                        System.out.println(botName + " takes a timeout, but did not gan any HP or stamina.");
                    }else{ // Otherwise
                        System.out.println(botName + " takes a timeout, regaining stamina and health.");
                        botHp = TimeOutGain(botHp, maxBotHp); 
                        botStamina = TimeOutGain(botStamina, maxBotStamina); 
                    }
                     
                }else{ // Otherwise
                    System.out.println(botName +" has attempted to take a timeout, but failed");
                }
            
            }
            
             
            ContinueMessage();
            ClearScreen();
            PrintGameInfo(playerHp, playerStamina, botHp, botStamina, botName);
            
            //set bothp and stamina to 0 if smaller than 0
            botHp = Math.max(botHp, 0);
            botStamina = Math.max(botStamina, 0);
            }
        }
        //set botstunned to false so that 
        botStunned = false;
        }
    
    }
    
    
    static int playerTimeouts = 2; // Global timeout variables to access anyways
    static int botTimeouts = 2; 


    //gain hp or stamina during timout, designed to not go over 100 (max value is 100) 
    static int TimeOutGain(int currentValue, int maxValue){
        return Math.min(currentValue + 15, maxValue);
    }
    
    //prompt for user to continue for organisation
    static void ContinueMessage(){
        System.out.println("\ncontinue (enter any key)");
        continueChoice = scanner.nextLine();
    }
    
    //chance of landing a liver shot when hiting the torso
    static int LiverChance(int ogDamage, String botName, int currentBotHp) {
        //random chance integer
        int chance = random.nextInt(4);
        
        //case if the user hits liver
        if (chance == 1) { // Chace has a chance to be 1-4, if the chance is equal to 1, it will continue the code
            System.out.println("\nYou hit " + botName + " in the liver!");
            currentBotHp -= 30;
             
        //case if user does not hit the liver
        } else { // Otherwise
            currentBotHp -= ogDamage;
        }
        return currentBotHp;
    }
    
    //clear screen function
    static void ClearScreen() {  
        System.out.print("\033[H\033[2J"); //Not fast enough to process only 1, so putting 2 will allow computer to process and fully clear
        System.out.print("\033[H\033[2J"); 
        System.out.flush();  
    } 
    
    //special moves for all teachers along with the damage subtracted 
    static int SpecialMoveSkeleton(int playerHp, String bot){ //Overall Skeleton for special moves for each boss
        
        switch(bot){//switch case to determine which teacher does their special move
            case "Ms.Kim":
                playerHp -= 30; //damage for ms kim special move
                System.out.println("Ms.Kim performs her special move: Behold, the mighty ruler FWOOOOOSH");
                break; 
            case "Mr.Egan":
                playerHp -= 50; //damage for mr egan special move
                System.out.println("Mr.Egan performs his special move: Projectile motion attack! BOOM BOOM BOOM");
                break;
            case "Mr.Partington":
                playerHp -= 70; //damage for mr partington special move
                System.out.println("Mr.Partington performs his special move: I will release hydrochloric acid on you you.... SPLASHHH");
                break;
        }
        ContinueMessage(); 
        return playerHp;
    }
    
    // Method to print messages slowly (we stole)
    private static void SlowPrint(String message, int millisPerChar) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(millisPerChar); //millisecond delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //function for getting the user ending
    static String GetUserEnding() {
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("Push him into the hole (press 1) \nConfront him about why he pushed you in the hole (press 2) \nMystery option (press 3)");

            int endChoice = CheckInput(3); 

            switch (endChoice) {//switch case to determine which ending maessage to play
                case 1:
                    SlowPrint("\nYou creep up behind sashas papa, hands trembeling, not knowing if you will have the mental strength to push him in. you feel weak as your stamina and health are down from the MMA fight against " + botName + " that you just did. All of a sudden a rage comes over you, how could sasha's papa do this to you. your eyes go blurry and you can feel the heat coming out of your head. You storm up behind sasha's papa, he turns around but it is too late. his cry is loud and you watch as he falls down into the very pit he pushed you into.", millisSpeed);
                    return "push him into the hole"; //push sashas papa into hole
                    
                case 2:
                    SlowPrint("\nYou creep up behind sasha's papa, then clear your throat so that he will know you are there. He slowly turns around and you notice his face turn a ghostly white. he stares at you with a look that is blank and lifeless. you ask him why he pushed you in, and he says he doesnt know why. but you wont take this for an awnser, you keep pressing him aout it until he lets it slip. he tells you he is posessed by an acid made by mr partington and that he has no choice. he is forced to bush camp by the hole until he sees someone then push them in. he tells you he hates doing it but he has no choice. you feel sorry for him and you think of a way to make this right. maybe if you were to play the game again and beat mr partington sasha's papa would be freed of mr partington's control.", millisSpeed);
                    return "confront him about why he pushed you in the hole"; //confront sashas papa
            
                case 3:
                    SlowPrint("\nYou creep up behind sasha's papa. and you think about pushing him in but decide not to. you walk away from the scene and go back to your house. when you get home you unlock your door and to your astonishment the three teachers are standing right there. you're suprised at first and then you remember how evil they are. you clench your fists and get ready to punch, all the teachers do the same. you sit and wait like a cobra waiting to strike, you're waiting for them to make the first move and then they all say 'now' and leap at you. you get ready to fight and then... you wake up and realize that this was all a dream", millisSpeed);
                    return "mystery option"; //mystery option
            }
        }
    }
    
}   
     
     
