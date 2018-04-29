# Player of team5

```plantuml
@startuml

[PlayerTeam5] -> [4ConnectTree]
[4ConnectMaxMinTree] -> [HeuristicValueDeterm]

[AlphaBetaAlgorithm]

@enduml
```

## PlayerTeam5

Contains the actual play and will create the MinMaxTree to learn best repsonse.

## 4ConnectActions

Implements the AlphaBetaAlgorithm and setups minMaxTree on the go to save resources.

## HeuristicValueDeterm

Returns the probability of winning the round by given board. Returns a number which determens the winning possibility
for the given player.

### Algorithm

Simple algorithm, which counts the number of possible winningmoves and summes them togehter. 
