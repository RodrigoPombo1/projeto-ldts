# project-l12gr05

## Game Description
Snake is a classic phone game where the player controls a snake and eats fruits spawning randomly throughout the map to grow. To win the game the snake needs to occupy the entirety of the map without hitting the map’s border head on or hitting itself, which kills it.

Our game is basically snake but in hard mode, where monsters that move randomly and can kill the snake if hit directly appear, some of the fruits move randomly which makes them relatively harder to eat and two teleporters are placed on determined locations on the map, enabling the option to travel from one place to another but making it possible to died directly after the teleport if the way is obstructed.

This project was developed by:
- Liess Pereira Aouimeur (up202206296@up.pt)
- Rodrigo Pinto Pesqueira Gaspar Pombo (up202105374@up.pt)
- Fernando Manuel de Melo Oliveira (up201005231@up.pt)

## Game Help

Due to not having a menu all the explanations about the game are going to be made in this section.

To start things off, the player needs to know what each of the letters appearing on the game mean:
- Black X: head of the snake, the character controlled by the player;
- Grey s: tail of the snake, which moves accordingly to the head, following the steps of the previous character to them specifically;
- Black M: monsters, which moves randomly and kills the snake if the head of the snake collides directly  with it;
- Red o: static fruit, which can be eaten by the player and doesn’t move;
- Red F: moving fruit, which can be eaten by the player and moves randomly;
- Black T: teleporters: which have a fixed spawn location and teleport any moving object, that being the snake, the monsters or the moving fruit, accordingly to the position one entered the teleporter;
- Grey #: wall, defines the border of the map and kills the snake if the head of the snake collides directly with it;

Secondly, the player needs to know the game controls:
- w or up arrow: makes the snake go up if it wasn’t going down beforehand;
- s or down arrow: makes the snake go down if it wasn’t going up beforehand;
- a or left arrow: makes the snake go left if it wasn’t going right beforehand;
- d or right arrow: makes the snake go right if it wasn’t going left beforehand;
- e: exits the game and closes the window;

In addition, the player needs to know some of the mechanics, them being the fact that each time a fruit is eaten, independently of the type of fruit, the snake grows one block, the fact that the snake can’t go in the opposite direction it is going at the moment, which means that if the snake is going up for example, it cannot go directly down because it would go inside it’s tail, the fact that a fruit and a monster cannot be in the same block and the fact that there is always five static fruits and three moving fruits in the arena, being replaced immediately after being eaten (is there is place for the fruit to be spawned).

Finally, the game is infinite, there is basically no ending, the objective is to get as big as possible without dying, which is the case for the normal snake as well.

## Implemented Features

“Go up”: if the snake wasn’t going down beforehand, it will go one block up if the up key or the w key is pressed; 

“Go down”: if the snake wasn’t going up beforehand, it will go one block down if the down key or the s key is pressed;

“Go right”: if the snake wasn’t going left beforehand, it will go one block right if the right key or the d key is pressed;

“Go left”: if the snake wasn’t going right beforehand, it will go one block left if the left key or the a key is pressed;

“Eat fruits”: when the head of the snake is on the same block as a fruit, the snake eats it;

“Snake collision”: if the snake collides with the border, itself or a monster it dies; 

“Close game”: closes the game if the e key is pressed; 

“Snake grows”: for each fruit or moving fruit the snake eats it grows bigger by one block, and we also covered the edge case scenario where the moving fruit would go against the snake just after the snake ate another fruit or moving fruit;

“Monster movement”: monster move randomly;

“Fruit movement”: moving fruit move randomly;

“Teleporters Interaction”: depending on which side the player, the moving fruit or the monster enter the portal they’ll leave the other portal in the corresponding direction (entering on the right side of one portal and leaving on the left side of the other)

## Planned Features

“Menu selection”: moves the menu selector by using the up and down or w and s keys and selects by pressing the enter key;

“Choose position of wall block”: in the level creation menu, select a case in the grid to place a wall using the up, down, left and right keys or the w, a, s and d keys;

“Choose speed of the game”: in the level creation menu, in the speed selection area, select the speed of the game using the up and down keys;

## Design

### Façade

#### Problem in Context:

We couldn't create a mock for the Random class, so we needed to call it directly in the Arena class, which is not good practice. To solve this problem we created a façade class that calls the Random class and returns the value we need.

#### The Pattern:

By implementing the façade pattern we were able to hide the Random class from the Arena class, making it easier to test the Arena class.

#### Implementation:

The RandomNumberGenerator class has a method called nextInt that returns a random integer between 0 and the parameter passed to the method. This class also implements the GenericRandomNumberGenerator interface, which has the nextInt method.

## Code Smells

The Arena class and Game class have too many methods, many should have been created in different classes;

The methods CheckInput, isValidCharacterInput and processKey in the Game class are too long due to switch statements and if statements;

Some methods have really long conditions, making them harder to read, an example would be the canElementMove method in the Arena Class;

## Self-evaluation

Liess Aouimeur and Rodrigo Pombo contributed an equal share to the work. We (Liess and Rodrigo) worked together so it is not possible to differentiate who did what. In percentages it would equal to:

- Liess Pereira Aouimeur - 50%
- Rodrigo Pinto Pesqueira Gaspar Pombo - 50%
- Fernando Manuel de Melo Oliveira - 0%

Fernando did not contribute to the project, he was not present in any of the meetings and did not do any work.

In the intermediate delivery we had initially put that everyone had contributed equally, because the delivery was small and the work was done in a single meeting where only Liess and Rodrigo were present. So we decided that it was unfair to put that he had done nothing in the intermediate delivery, because Fernando could have simply not been available at that specific time and we decided it wasn't fair to penalize him for not begin able to attend one specific meeting.

However we did not expect for this situation to happen again and him to not attend any of the meetings and not do any work, so we decided to put that he did not contribute to the project in the final delivery.