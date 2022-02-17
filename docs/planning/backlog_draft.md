# Backlog draft 10-02-22

## Must haves:
1. The application supports and arbitrary number of cuncurrent matches with an arbitrary number of players in each.
*  The application shall support at least \[TBD\] concurrent multiplayer games.
2. Players can choose to play offline singleplayer or online multiplayer at the start screen.
* As an user I can play a game in multiplayer mode.
3. An offline match just means that the server is hosted locally and no other player may join.
* As an user, I can play a game alone.
4. Players can choose a name (unique per room) before each match.
* As an user, I can choose my own display name before each game.
5. Players that chose multiplayer are put together in a common waiting room and a match starts when one of them presses the start button.
* As an user, I can join a waiting area for a multiplayer game.
* As an user, I can start a game from the waiting area if there enough players.
6. Players cannot join in the middle of the match.
* As an user I cannot join an ongoing match.
7. During a match players answer \~20 multiple choice question with 3 possible answers.
* As a user, during the game I answer ~20 multiple choice question where each question contains 3 potential answers and only one correct.
8. Question rounds are synchronised across all players in the same match.
* As a user, I may be sure that all players get the same question at the same round and no two users can get different questions at the same round.
9. Points are rewarded based on player response time and correctness.
* As a user, I get my points based on the correctness on my answer and how fast it was given(in case it's the correct one).
10. Question rounds are designed to make the game fast-paced.
* As a user, I might be sure that if another player doesn't give an answer, the game wouldn't freeze, because for each round there is a timer.
11. Players have a set of one-time use jokers available each match. (see list below)
12. Players can use jokers after they gave an answer and before the round ends.
13. Players can react with emoticons during each question round.
14. Question data is stored in the database.
15. Players are shown the correct answer and other people's answers at the end of each round.
* As a user I am shown the correct answer as well as other players' answers at the end of each round.
16. Players are shown the final score table at the end of the match.
* As a user, I am shown the final score table at the end of each match.
17. Singleplayer and multiplayer stats and score boards are separate.
* As a user, I can access singleplayer and multiplayer stats separately.

## Should haves:
18. Questions and answers should contain images along the text.
* As a user, I see questions and answers that contain both images and text.
19. While being in a room players are shown the player count and list.
20. Players are shown the score table after every question.
21. Some stats like answer accuracy and all-time score (sp/mp).

## Could haves:
22. Players can change their answer before the round timer runs out.
23. Questions and answers can contain runtime-generated data (based on db records).
* Questions and answers can be generated during the runtime.
24. Inactive players are automatically kicked out of the match.
* As a user I could be kicked out of the match automatically for being inactive.
25. Players can see the all-time leaderboard when outside matches. (names my not be unique)
* As a user I could see a record of the all-time best leaderboards when outside matches. 
26. More jokers can be added to the game.
* The application could support more jokers who  could be added later in development.

## Won't haves
* The database won't contain player data (except perhaps name-highscore records).
* If a players disconnects in the middle of the match, they cannot rejoin.
* Players cannot kick other player out of the match.
* Players do not need to authenticate to play.

### Jokers:
* Decrease round duration for all other players by a certain percentage.
* Cross out one incorrect answer from the 3 available.
* Double the points gained from the current question.
