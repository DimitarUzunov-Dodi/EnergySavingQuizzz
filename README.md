## Description

Quizzz is the MMO quiz game of the year. Face challenging questions about energy awareness and develop a deeper understanding of the real-life energy consumption of our society.

## Group members
| Profile Picture                                                                                               | Name | Email |
|---------------------------------------------------------------------------------------------------------------|---|---|
| ![](https://eu.ui-avatars.com/api/?name=CVDO&length=4&size=100&color=DDD&background=777&font-size=0.400)      | Chaan van den Oudenhoven | 	C.vandenOudenhoven@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=ACC&length=4&size=100&color=FFFFF&background=ff8c00&font-size=0.400)  | Alex Cazacu | a.c.cazacu@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=RC&length=4&size=100&color=FFFFF&background=008080&font-size=0.400)   | Raul Cotar | rcotar@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=DSU&length=4&size=100&color=FFFFF&background=008000&font-size=0.400)  | Dimitar Uzunov | D.S.Uzunov-1@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=YK&length=4&size=100&color=DDDDDD&background=1e81b0&font-size=0.400)  | Yehor Kozyr | y.kozyr@student.tudelft.nl |
| ![](https://eu.ui-avatars.com/api/?name=Ivan&length=4&size=100&color=FFFFF&background=008000&font-size=0.400) | Ivan Makarov | I.Makarov@student.tudelft.nl |
<!-- Instructions (remove once assignment has been completed -->
<!-- - Add (only!) your own name to the table above (use Markdown formatting) -->
<!-- - Mention your *student* email address -->
<!-- - Preferably add a recognizable photo, otherwise add your GitLab photo -->
<!-- - (please make sure the photos have the same size) --> 

## How to run it
- clone the repository or download the project
- execute:
  - server: `gradle bootRun [--args="--port <port>"]`
  - client: `gradle run [--args="--server='http://<address>:<port>'"]`
- enjoy

## How to import activities to the activity bank
* Get correct version of activity bank
* Rename it as `oopp-activity-bank.zip`
* Place the archive in client/src/main/data
* Start the client and upload it from the admin panel

## How to connect to the database
* Open PGAdmin
* Create a new server
* Use the following credentials

```
username = doadmin
password = C9fxievExhPqJ8H6
host = db-postgresql-ams3-87857-do-user-10980855-0.b.db.ondigitalocean.com
port = 25061
database = serverdbpool
sslmode = require
```

## How to contribute to it
- Be one of the people in the contributors list

## Copyright / License (opt.)
