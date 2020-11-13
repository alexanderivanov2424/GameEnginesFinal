Fill out this README before turning in this project. 
Make sure to fill this out again for each assignment!

--------------------------------------------------------------

Banner ID: B01540800

Already uploaded demo onto Slack: YES 

--------------------------------------------------------------

Primary Requirements:
    ● Your handin must meet all global requirements.
    - Refer to Handin
    ● Your handin only crashes under exceptional circumstances (edge cases).
    - Through play testing one can verify the game does not crash.
    ● Your engine must correctly implement saving and loading through serialization.
    - This can be seen in the loadFromXML() function in GameWorld, and similar functions in the game objects and components.
    Each component "saves and loads itself"
    ● Your engine must correctly support raycasting for polygons and AABs.
    - This can be verified in the debugger or through shooting the laser in game. Debugger may be better because the rendering
    of the laser is a little off.
    ● You must complete the debugger to demonstrate correct raycasting for polygons
    for AABs
    This can be checked by running the Display class in the debugger.

.
    ● Your player must be able to fire projectiles.
    - When in game clicking on the screen should allow the player to fire a laser at objects in the level.
    ● Your game must be loaded from a file. For this requirement, you can save your
    game using any file type, formatted as you please. You must provide at least one
    file that we can load in your game successfully.
    When you start the program and press the start button you are taken into the default level. At this point if you press
    the load button you will load in the level in the saves folder. Note that there is only one save for the game.
    ● You must be able to save your game state, restart the game, and then load that
    game state.
    This can be tested by starting the game and pressing the corresponding buttons on the game screen. One should be able
    to see that game objects return to the correct locations.
    ● The player must always be in view.
    The viewport is always centered on the palyer. This can be checked by jumping off the platforms and by running around.
    ● It must be possible to start a new game without restarting the program.
    This can be accomplished by pressing the restart button on the game screen. 




Secondary Requirements:
    ● Your engine must meet all primary engine requirements.
    - refer above
    ● Your engine must correctly support raycasting for circles.
    - This can be verified in the debugger, The code from the debugger has been directly moved to the engine with 
    essentially just some name changes to functions/variables. Each shape object has a collidesRay() function that mimics
    the function in the debugger.
    ● You must complete the debugger to demonstrate correct raycasting for circles.
    - This can be verified in the debugger.

.   
    ● Your game must meet all primary game requirements.
    - Refer above
    ● There must be a polished UI for saving and loading.
    ● Save files must be written in XML format. This will help organize your saves, and
    also java has code for parsing these files.
    ● The player must be able to fire projectiles that travel instantly using raycasting.
    Projectiles must apply an impulse to whatever they hit in the direction of that ray.
    ● Your game must meet at least two of the extra game requirements.



--------------------------------------------------------------

Known bugs:
    None
Hours spent on assignment:
    15