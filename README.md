Welcome to Blackjack!

Besides the rules you'll see once running the program, heres some info on how to get started after cloning the repository...

  1. Open up the folder in VSCode
  2. Open a new terminal and compile the code using "javac *.java"
  3. Execute "java BlackjackServer + port you desire to run on"

The Server is now running on the port you entered. Next...

  1. Open a new terminal (keep the other open)
  2. Run java TableGUI

This will open the program GUI and you may begin playing! If you are the first player to enter the server, be sure to select ID 0 as you will be the "host"

The host must press "start" before performing any actions in order for the game to begin for ALL PLAYERS.
After the "start" button is pressed, the betting phase begins in which all players must insert their bet (within valid range) and press bet (players with ID that isnt 0 may need to press
"bet" multiple times in order for game to process the bet.

The BlackjackServer terminal updates the player running the server when bets have gone through if you are curious about confirming that bets have gone through to the server.

After everyone has bet, the game phase begins, in which all players will have the opportunity to hit, stand or double down at their own discretion (in order from ID 0 to 4). Each players
turns ends when they either get 21, bust, double down, or stand. 

After this, the dealer then draws cards until exceeding 16.

If a player stands or double downs and did not bust AND the dealer has not busted either, the faceoff phase will begin in which player hands will be compared with the dealer in order 
to see who has the higher value. 

After each round, the player with ID 0 MUST press start to begin the bet phase again.

Thank you for playing and HAVE FUN!!!
