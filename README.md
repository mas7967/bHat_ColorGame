# bHat_ColorGame
First attempt at the color game app

## Basic Features / Premise

The user is presented with an n x n grid of squares. All but one of the squares will be the same color, and the other square will be slightly different in color. The user needs to click on the square that is of a different color. Each click awards points and more time on the clock. As the user progresses, the "n" value increases, and the colors become closer and closer together. Run out of time and you lose!

### Power Ups

The user has options for temporary power ups which make the game easier. There are four possible choices:

Upgrade Name | Length (sec) | Brief Description
—————— | ————— | ————————
plus Sixty | N/A | Adds 60 seconds to the time remaining
no Grid | 10 | emoves the grid that separates the game pieces (lasts for 10 seconds)
better Contrast | N/A | Increases the contrast between the correct and incorrect game pieces
no Penalty | 10 | Removes the -1 second penalty for all incorrect guesses (spam those fingers!)


## To do List v1.0

    - [x] Make the program use a proper GridView and Adapter
    - [x] Implement a timer for the game
    - [x] Implement a scoring mechanism
    - [x] Implement the power ups feature
    - [x] Implement a high score system (Moved to v2.0)

## To do List for v2.0

    - [ ] Study game dynamics some, make the game somehow harder, or start with less upgrades?
    - [ ] Consider changing the +60 seconds to +30, or make the penalty higher? (see above)
    - [ ] Implement a high score system, local
    - [ ] Make a more interesting scoring mechanism: the quicker you click correct pieces, the higher the score goes
    - [x] Better contrast and no grid upgrades redraw the screen, is it possible to get rid of that?
    - [ ] When the game is over, actually stop the game (runs indefinitely for now)
    - [ ] Work on the action bar and settings

### To do List for v3.0
    
    - [ ] Re-evaluate the status of the app and the to-do list at this point...
    - [ ] Implement a global high score system, one for when upgrades are used, one when no upgrades used
    