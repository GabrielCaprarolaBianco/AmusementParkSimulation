Creator: Gabriel Caprarola-Bianco

Hello,
This project was created for a 202 computer science class.
I made a model of an amusement park that allows the simulated jobs to openly travel between each attraction by use a decision algorithm.
The decision Algorithm is based off the game theory equation of Expected Value. 
EV = Positive EV - Negative EV
the decision algorithm equation is Destination Decision = Incentives for ride 1 - Incentives for ride 2(disincentives for ride 1 by comparison)
The incentives for ride 1 are RideAffinity(RA) X (1-Flexibility) X (1 - (WaitTime for ride 1 / 90)).
Flexibility is an attribute given to each Job; (1 - flexibility) models stubborness since it is inversely related to flexibility.
The incentives for ride 2 are RA X Flexibility X (1 - (WaitTime for ride 2 / 90)).
If the equation is negative then the second ride is selected, if it is positive the first ride is selected.
This equation can be run multiple times when picking a destination for the job to travel to.

The Job flow starts in the wandering queue with arrivals following a normal distribution.
Next the jobs are moved to the attraction of their choosing, and the rides that are a part of that attraction.
After the jobs finish service they are returned to the wandering queue to either continue riding rides are exit the simulation.
all exiting jobs are captured within a finished queue for future processing
