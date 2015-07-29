# Welcome to the bHat Color Game!

### Basic Features / Premise

The player is presented with a grid of colored squares. All the squares will be the same color except for one. The player needs to click on the square that is a different color. Each click awards a point and more time on the clock. As the user progresses, the squares get smaller, and the colors get closer together. Run out of time and you lose! Test how good your eyes are, and see how high of a score you can get!

### Power Ups

The user has options for temporary power ups which make the game easier. There are four possible choices:

Upgrade | Length (sec) | Brief Description
:------:|:------------:| -----------------
![plus Sixty](app/src/main/res/drawable-mdpi/plus_sixty_upgrade.png) | N/A | Adds 60 seconds to the time remaining
![no Grid](app/src/main/res/drawable-mdpi/no_grid_upgrade.png) | 10 | Removes the grid that separates the game pieces (lasts for 10 seconds)
![better Contrast](app/src/main/res/drawable-mdpi/better_contrast_upgrade.png) | N/A | Increases the contrast between the correct and incorrect game pieces
![no Penalty](app/src/main/res/drawable-mdpi/no_penalty_upgrade.png) | 10 | Removes the -1 second penalty for all incorrect guesses (spam those fingers!)


### To do List v1.0

- [x] Make the program use a proper GridView and Adapter
- [x] Implement a timer for the game
- [x] Implement a scoring mechanism
- [x] Implement the power ups feature

### To do List for v2.0

- [x] Better contrast and no grid upgrades redraw the screen, is it possible to get rid of that?
- [ ] When the game is over, actually stop the game (runs indefinitely for now)
- [ ] Implement a high score system, local
- [ ] Work on the action bar and settings
- [x] Study game dynamics some, make the game somehow harder, or start with less upgrades?
- [x] Consider changing the +60 seconds to +30, or make the penalty higher? (see above)
- [x] Make a more interesting scoring mechanism: the quicker you click correct pieces, the higher the score goes


### To do List for v3.0
    
- [ ] Re-evaluate the status of the app and the to-do list at this point...
- [ ] Implement a global high score system, one for when upgrades are used, one when no upgrades used
- [ ] Work on allowing for both possible screen orientations
    
