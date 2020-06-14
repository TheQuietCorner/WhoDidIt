### The Idea
Every start of a round, a killer will be picked from the players. For the killer to win the round, they must kill all players before the time run out. For the players to win the round, they must either survive until the time run out OR identify and kill the killer before the time run out.

### Commands
| Command      	| Description                        	| Permission                    	|
|--------------	|------------------------------------	|-------------------------------	|
| `list`       	| List all players currently playing 	| `whodidit.command.list`       	|
| `join`       	| Join the next round                	| `whodidit.command.join`       	|
| `spectate`   	| Change to spectate mode            	| `whodidit.command.spectate`   	|
| `forcestart` 	| Force start the round              	| `whodidit.command.forcestart` 	|

### Terminologies
| Term     	| Description                                                                                                    	|
|----------	|----------------------------------------------------------------------------------------------------------------	|
| Round    	| The current game. A round requires a minimum of 5 players, and lasts a maximum of 10 minutes (*configurable*). 	|
| Map      	| An schematic file that is loaded every start of a new round.                                                   	|
| Killer   	| A player given a knife tasked to kill all other players                                                        	|
| Survivor 	| A player trying to escape from the killer                                                                      	|

### Scenarios
#### Player Left Scenario
1. A player joins the server
2. Player spawns as an spectator
3. Player executes the `join` command to queue for the next round
4. Next round started, waiting for more players
5. Another played joined and spawns
6. Round reached the minimum required number of players
7. Round starts countdown starting from 30
8. A player left the round, countdown stopped
9. Round waits for more players

#### Player Join Scenario
1. A player joins the server
2. Player spawns as an spectator
3. Player executes the `join` command to queue for the next round
4. Next round started, waiting for more players
5. Another played joined and spawns
6. Round reached the minimum required number of players
7. Round starts countdown starting from 30
8. No new players joined, round started the game
9. A killer is picked from the players

#### Killer Win Scenario
10. Killer equips their knife and killed all players
11. Round ended in favor of the killer

#### Survivors Win Scenario
10. Killer equips their knife and failed to kill a survivor
11. Survivors noticed the killer
12. Killer identified and killed by players
13. Round ended in favor of the survivors

#### Suicide Scenario
10. Survivors killed each other before the killer killed one of them
11. Round ended as a suicide
