 (1) * User can connect using IP address
 (2) * Server allows up to 5 players to join before blocking new connections
 (3)* Server distributes 2 cards to every player after start button pressed
 (4)* Buttons in GUI allow players to hit, stand, and double down
 (5)* GUI displays only one of dealers cards until players are done their turns
 (6)* Every player starts with 250
 (7)* Players must bet at least 25
 (8)* Dealer must hit until 17
 (9)* A gambler will bust (lose the game) if their hand value goes over 21
 (10)* Hand values of players that haven't busted are compared with dealer hand
 (11)* Server checks for blackjack (Ace 10/face card)
 
 Results: 
 1) Pass (IP address and port)
 2) Pass
 3) Pass (cards are distributed after start is press AND bets from all players have been submitted)
 4) Pass
 5) Pass (hand values are displayed instead of cards)
 6) Pass
 7) Pass (when a player's cash is below 25, they can no longer play)
 8) Pass (in method "dealerTurn()", ran at the end of a round)
 9) Pass (using isBust() method. If all players bust, dealer automatically wins and dealerTurn() is skipped)
 10) Pass (in dealerTurn())
 11) Pass