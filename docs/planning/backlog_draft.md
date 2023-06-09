# Backlog draft 17-02-22

## Must have:

1. **Persistent databse**
The application shall be connected to a persistent database of TBD type using TBD. It shall store questions and answers.

2. **Singleplayer and Multiplayer modes**
As an user, I can play a game alone or in multiplayer mode.
The application shall support at least [TBD] concurrent multiplayer games.

3. **Waiting room**
As an user, I can join a waiting area for a multiplayer game and I can start a match from the waiting area if there enough players.

4. **Display answers**
As a user I am shown the correct answer as well as other players' answers at the end of each round.

5. **Rounds are synchronized**
As a user, I may be sure that all players get the same question at the same time and no two users can get different questions at the same round.

## Should have:

6. **Custom usernames**
As an user, I can choose my own display name before each game.

7. **Inactivity kick**
As a user I could be kicked out of the match automatically for being inactive.

8. **Final score table**
As a user, I am shown the final score table at the end of each match.

9. **Can't join ongoing matches**
As an user I cannot join an ongoing match.

10. **Points are rewarded based on correctness and time**
As a user, I get my points based on the correctness on my answer and how fast it was given (in case it's the correct one).

11. **~20 multiple choice questions**
As a user, during the game, I answer ~20 multiple choice questions where each question contains 3 potential answers and only one correct.

## Could have:

12. **Player reactions**
As a user, I can send a reaction with an emoji to everyone during each question.

13. **Joker cards**
As a user, I have a set of one-time-use jokers per match that I can use before I answer which can give me an advantage in the quiz.
	- Decrease round duration for all other players by a certain percentage.
	- Cross out one incorrect answer from the 3 available.
	- Double the points gained from the current question.

14. **Runtime-generated content**
The application can generate questions and answers at runtime using data from the db.

15. **All-time leaderboards**
As a user I can see a record of the all-time best leaderboards when outside matches.

16. **Additional jokers**
The application could support more jokers which could be added later in development.

17. **All-time Sp and Mp stats and scores**
As a user, I can access singleplayer and multiplayer stats separately.

18. **Text and images for questions and answers**
As a user, I see questions and answers that contain both images and text.

19. **Fast-paced rounds and matches**
As a user, I should have not have to think about the questions for too long.

20. **Background themes**
As a user, I can choose between different background themes.

## Won't have:

* The database won't contain player data (except perhaps name-highscore records).
* Players cannot kick other player out of the match.
* Players do not need to authenticate to play.
