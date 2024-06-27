# Requirements Brief
- You need to provide a design for a program to allow two or more players to play a card game on the computer. 
- Each player may either play an automated move or their own move.
- Assume that each player has their own deck of 52 cards, numbered 1 to 52. 
- At the beginning of the game, each deck is shuffled. 
- Each player has a ‘hand’ of cards, a collection of cards that they are ‘holding’ and are ready to play. 
- Each player will then keep drawing from their deck (moving the top card from their ‘deck’ into their ‘hand’) until they have 5 cards in their ‘hand’.
- Starting at player 1, each player will play one card from their ‘hand’ onto the virtual ‘table’, in order, first player 1, then player 2, player 3, etc. 
- Whoever plays the highest numbered card wins the ‘round’, gets a point, and the ‘round’ ends - the played cards are removed from play. 
- Each player then draws another card from their deck to their hand, so that they again have 5 cards, as long as there are cards left in their deck, otherwise they just play with the cards in hand. 
- The player who won the previous round goes first in the next round, then each other player plays in turn, keeping the same order of player numbers, and returning to player 1 after the highest numbered player. 
- E.g., with 5 players, if player 3 started, the player order would be 3, 4, 5, 1, 2. 
- Play continues until each player has played all 52 of their cards. At the end of the game, the player with the most points wins.
- UI must display the current hand of a player and the points they have won so far.
- Automated move must make the best move based on all possible outcomes.

# Requirements Testing Table
| ReqID | Name                                         | Description                                                                                                                                |
|-------|----------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| 1.0   | Test valid number of people can play         | The design must allow for 2 or more players to play the card game.                                                                         |
| 1.1   | Test invalid number of people can play       | The design must not allow for 1 person or less to play.                                                                                    |
| 2.1   | Test player can make their own move          | Each player must be able to make their own move.                                                                                           | 
| 2.2   | Test player can make automated move          | Each player must be able to make an automated move.                                                                                        |
| 3.1   | Test player has deck                         | Each player must have a deck.                                                                                                              |
| 3.2   | Test player's deck size                      | Each player must have a deck of 52 cards, numbered 1-52.                                                                                   |
| 3.3   | Test player's deck is shuffled               | At the beginning of the game, each player's deck must be shuffled.                                                                         |
| 4.1   | Test player has hand                         | Each player must have a hand of cards.                                                                                                     |
| 4.2   | Test player draws 5 cards                    | Each player must draw from deck until they have 5 cards.                                                                                   |
| 5.1   | Test highest numbered card wins              | Whoever plays the highest numbered cards win the 'round' and gets a point                                                                  |
| 5.2   | Test played cards are removed from hand      | Played cards are removed from play at the end of the round.                                                                                |
| 5.3   | Test all players draw another card from deck | Each player must draw another card from their deck to hand at the end of a round.                                                          |
| 5.4   | Test player can't draw card from deck        | In the event of a player's deck being empty, player plays with cards in hand.                                                              |
| 6.1   | Test winner is current player                | The player who wins the previous round goes first in the next round.                                                                       |
| 6.2   | Test round order from current player         | The succeeding players after the winning player should rotate circularly until the current player is the player behind the winning player. |
| 7.1   | Test game start to finish                    | Play should continue until all players have exhausted all their possible cards.                                                            |
| 7.2   | Test winning player with highest points      | At the end of the game, the player with the highest number of points should win.                                                           |
| 8.1   | Test UI displays hand                        | UI should display cards of a player's hand.                                                                                                |
| 8.2   | Test UI displays points                      | UI should display points of a player.                                                                                                      |
| 9.1   | Test AI move                                 | Automated move should make the best possible move based on current information.                                                            |