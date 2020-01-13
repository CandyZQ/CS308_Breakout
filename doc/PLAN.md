# Game Plan
## NAME
**B-Breaker**  
Cady Zhou (zz160)
### Breakout Variant
- *Jet Ball:* Instead of the normal grid alignment of bricks, this game uses concentric circles to align its "bricks". The circular "bricks" also move during the game, which makes the game more interesting and unpredictable. 

- *Circus*: In this game, a see-saw and two characters are used to represent the ball and paddle in other versions. The fact that the characters dance in the makes the game a lot of fun to play. By using a see-saw, it is also implying that the position where the character falls affects the height the other character rises. 

### General Level Descriptions
The player has three lives at the beginning of the game. Each time the player fails to catch the ball, he/she loses one life. The game is over if the player loses all three lives. 

In the configurations below, the number (maximum 5) represents how many times they player needs to hit the bricks before it disappears. A successful hit awards 1 point (i.e. eliminates a 5 bricks will give the player 5 points). 9 means the brick cannot be eliminated.

- **Level 1:**  
1 1 1 1 1 1   
1 1 1 1 1 1   
1 1 1 1 1 1   
0 1 0 1 0 1   
0 0 0 0 0 0   
0 0 0 0 0 0   
0 0 0 0 0 0  
0 0 0 0 0 0  

- **Level 2:**  
3 3 2 2 3 3  
2 2 2 2 2 2  
1 1 1 1 1 1   
1 1 1 1 1 1   
0 1 0 1 0 1   
0 0 0 0 0 0   
0 0 0 0 0 0   
0 0 0 0 0 0  

- **Level 3:**  
3 3 3 3 3 3  
3 3 2 2 3 3  
2 2 2 2 2 2  
1 1 1 1 1 1   
0 1 0 1 0 1   
0 0 0 0 0 0  
9 9 0 9 9 0   
0 0 0 0 0 0  

- **Level 4:**  
4 3 3 2 3 5  
3 3 4 1 3 3  
2 1 2 3 2 2  
1 3 1 2 1 3   
0 1 0 1 0 1   
5 4 0 0 3 0  
0 3 2 1 0 3   
0 2 0 0 2 1

- **Level 5:**  
4 4 5 5 4 4  
3 2 9 9 2 3  
2 3 2 2 3 2  
9 1 2 2 1 9   
2 1 2 1 2 1   
9 0 2 2 0 9  
1 0 0 0 0 1   
0 0 0 0 0 0  


### Bricks Ideas
- Bricks have different points and are distinguished by a gradient of color
- Bricks with different images on them have power-ups, which are elaborated in the next section
- Gray bricks (represented with number 9) cannot be eliminated, and only disappears when all other bricks on the same line are eliminated 

### Power Up Ideas
- Bricks with a heart inside give the player another life
- Bricks with a bomb inside eliminate the 8 bricks around it
- Bricks with a lightening increases the speed of the ball
- Bricks with a question mark randomly draws one of the three power-ups

### Cheat Key Ideas
- "R": restart this level
- "M": have 99 lives
- "1-5": jump to that level  
 
### Something Extra
Instead of controlling a horizontal paddle, the player is allowed to rotate the paddle around the center of the paddle at any angle with "A" and "D". This function allows the ball to bounce back in a more dramatic angle.
