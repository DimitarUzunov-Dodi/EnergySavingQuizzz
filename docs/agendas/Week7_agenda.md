# Agenda (chair Yehor)

## Agenda Meeting #7

- Location:     Online(Zoom)
- Datum:        22.03
- Time:         16:45-17:45
- Attendees:    Natalia, Alex, Raul, Ivan, Chaan, Yehor, Dimitar
- Chair:        Yehor
- Notetaker:    Ivan

## Agenda-items

* 16:45     **Opening by chair**

* 16:50     **Check-in with teammates** check contributions (gitinspector), check if everyone is fine and is working within schedule

* 17:00     **Question Round**

* 17:10     **Demo** Show the demo of the game we have and ask for a feedback

* 17:30     **Summary & action points** – Who, what, when, if?

* 17:40     **Feedback Round** Is everything going smoothly between us?

* 17:44     **Closure**

### Questions
- Should it be possible to add a new single activity?

- There are 40 corrupt images out of 1687. How it should be treated? Is it fine to use a default image instead.

### Notes
Team check-in:
Do the assingment on brightspace, DEADLINE is 1st April

Questions:

In which way should we handle the corrupt images in the activity bank. Up to us to decide if it's default image or leave out everything.

The example backlog doesn't go into detail about Admin panel in terms of adding activities.
- Too much to edit an image(Natalia will ask but she doesn't think that it's necessary).

Should be there a functionality to add a single question.
- Zip file was the main idea, it's the main source of questions adding might not be necessary.

Question about scheduling.
- Decided to have another meeting on sunday(need to update the code of conduct)

Websockets
This course is focusing on the REST Api the functionality for creating the game is implemented by websockets, but is one of the core principles of the project so it might be a violation of the project rules. Websockets for emojis, jokers, time running out - fire an event that handles the Rest API
- Natalia thinks it's a good solution

Demo of the application

We are the best team
(C) Alex

You're doing really good guys, keep up, because you do a really good job.
(C) Natalia

Summary and action points
- Waiting room functionality(Yehor)
- Raul(Match Leaderboard in-between, loading scenes)
- Alex(Continue with the questions, backend for connection everything so we can answer questions and adding other question types, random generation of 20 questions but only 1 type)
- Ivan(Help Alex on frontend and backend, upload the picture from the local computer)
- Chaan(Keep working on WebSockets, will be fully functional on the end of thursday excluding jokers)
- Dimitar - continue to help on websockets and pick up some new issues


-Natalia got the answer about the Admin thing, if adding is mentioned in the official backlog - "Yes", otherwise - "No", editing includes the picture

A lot of tasks assigned, weights, BUT most of the sprints are expired, so redistribute the issues, we can change them completely so it's clear what are the issues we are working on. Alex worked less this week and other people were catching up, Dimitar doing great progress. Testing should be added.
Code reviews, all of us had code reviews, but Chaan, create more helpful comments. Do we run a pipeline locally before pushing(we don't do that currently). Pipeline from Gradle. Alex had pipeline was passing on a local branch and develop but failed after the successful merge. For merges it's okay to have a failing pipeling(if fixed), but for pushes it is not. The only branches that matter are develop/main and they have their pipeline fine. 

For some reason checkstyle fails. We should work on that. Ivan should work more on the server. 

Meeting close

