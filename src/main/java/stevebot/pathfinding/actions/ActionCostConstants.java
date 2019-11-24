package stevebot.pathfinding.actions;

public abstract class ActionCostConstants {


	/*
	SETTING PRECISE ACTION COST:
	1. calculate and follow a path with the action -> actual cost in ticks will be recorded for each action in the path
	2. exec. command /actioncost -> the real cost of all executed actions will be printed in the console
	3. decide if the recorded data is representative (min,max,avg and sample-size) and set action cost to avg real cost
	 */


	// walk
	public final double WALK_SPRINT_STRAIGHT = 7.900369003690037;
	public final double WALK_SPRINT_DIAGONAL = 10.73076923076923;
	public final double WALK_SPRINT_DIAGONAL_TOUCHES = 20.0;

	// step down
	public final double STEP_DOWN_STRAIGHT = 9.285714285714286;
	public final double STEP_DOWN_DIAGONAL = 13.333333333333334;

	// step up
	public final double STEP_UP_STRAIGHT = 23.0;
	public final double STEP_UP_DIAGONAL = 26.666666666666668;

	// swim
	public final double SWIM_STRAIGHT = 18.1;
	public final double SWIM_DIAGONAL = 26.0;

	// enter water
	public final double ENTER_WATER_STRAIGHT = 8.5;
	public final double ENTER_WATER_DIAGONAL = 13.0;

	// exit water
	public final double EXIT_WATER_STRAIGHT = 28.0;
	public final double EXIT_WATER_DIAGONAL = 35.333333333333336;

	// jump
	public final double JUMP_STRAIGHT = 39.333333333333336;
	public final double JUMP_DIAGONAL = 37.55555555555556;

	// sprint jump
	public final double SPRINT_JUMP = 33.0;

	// bridge
	public final double BRIDGE_FREE = 30.0;

	// pass door
	public final double PASS_DOOR = 17.333333333333332;

	// pillar up
	public final double PILLAR_UP = 20.714285714285715;

	// drop down
	public final double DROP_DOWN_STRAIGHT = 35.0;
	public final double DROP_DOWN_DIAGONAL = 38.0;

	// break block
	public final double CONSTANT_BLOCK_BREAK_MOD = 15.0;

	/*
	TODO

	- step down diagonal touching
	- swim touching
	- exit water diagonal touching
	- enter water diagonal touching

	- drop down


	 */

//	public final double COST_SPRINTING = 20.0 / 5.612; // 3.563
//	public final double COST_WALKING = 20.0 / 4.317; // 4.632
//	public final double COST_SNEAKING = 20.0 / 1.3; // 15.384
//	public final double COST_STEP_DOWN = COST_WALKING; // 4.632
//	public final double COST_STEP_UP = 7.556;
//	public final double COST_WALK_JUMP = COST_WALKING * 2 + 1; // = 9.264 + 1
//	public final double COST_SPRINT_JUMP = COST_SPRINTING * 3 + 3;
//	public final double COST_PILLAR_UP = 7.556;
//
//	public final double COST_PLACE_BLOCK = 3;
//
//	public final double COST_SWIM = 20 / 3.0; // 6.666
//	public final double COST_ENTER_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5; // 4.815 * 2 = 7.315
//	public final double COST_EXIST_WATER = COST_WALKING * 0.5 + COST_SWIM * 0.5;
//
//
//	public final double COST_MULT_TOUCHING = 1.6;
//	public final double COST_MULT_DIAGONAL = 1.414;

	public final double COST_INFINITE = 99999999;


}


/*

walk-sprint-diagonal (260)
   avg: 10.73076923076923
   min: 10
   max: 16

walk-sprint-diagonal-touches (9)
   avg: 20.0
   min: 16
   max: 22

mine-straight (16)
   avg: 77.375
   min: 34
   max: 626

mine-up (1)
   avg: 46.0
   min: 46

   max: 46
drop-down-straight (2)
   avg: 34.0
   min: 30
   max: 38

step-down-straight (71)
   avg: 12.338028169014084
   min: 8
   max: 28

swim-diagonal (32)
   avg: 26.0
   min: 18
   max: 30

swim-straight (40)
   avg: 18.1
   min: 12
   max: 22

walk-sprint-straight (271)
   avg: 7.900369003690037
   min: 6
   max: 14

exit-water-straight (6)
   avg: 35.0
   min: 26
   max: 50

step-up-straight (82)
   avg: 23.26829268292683
   min: 20
   max: 28

enter-water-diagonal (2)
   avg: 13.0
   min: 12
   max: 14

enter-water-straight (4)
   avg: 8.5
   min: 8
   max: 10

jump-diagonal (1)
   avg: 43.0
   min: 43
   max: 43

bridge-free (1)
   avg: 30.0
   min: 30
   max: 30

sprint-jump (2)
   avg: 33.0
   min: 33
   max: 33

---------------------------
public final double walk-sprint-diagonal = 10.73076923076923;
public final double walk-sprint-diagonal-touches = 20.0;
public final double mine-straight = 77.375;
public final double mine-up = 46.0;
public final double drop-down-straight = 34.0;
public final double step-down-straight = 12.338028169014084;
public final double swim-diagonal = 26.0;
public final double swim-straight = 18.1;
public final double walk-sprint-straight = 7.900369003690037;
public final double exit-water-straight = 35.0;
public final double step-up-straight = 23.26829268292683;
public final double enter-water-diagonal = 13.0;
public final double enter-water-straight = 8.5;
public final double jump-diagonal = 43.0;
public final double bridge-free = 30.0;
public final double sprint-jump = 33.0;









walk-sprint-diagonal (14)
   avg: 11.142857142857142
   min: 10
   max: 14

step-up-straight (1)
   avg: 24.0
   min: 24
   max: 24

step-down-straight (6)
   avg: 12.666666666666666
   min: 10
   max: 16

---------------------------
public final double walk-sprint-diagonal = 11.142857142857142;
public final double step-up-straight = 24.0;
public final double step-down-straight = 12.666666666666666;





walk-sprint-diagonal (217)
   avg: 10.460829493087557
   min: 8
   max: 14

walk-sprint-diagonal-touches (4)
   avg: 21.0
   min: 20
   max: 22

step-down-straight (14)
   avg: 9.285714285714286
   min: 8
   max: 16

swim-diagonal (8)
   avg: 25.5
   min: 24
   max: 26

swim-straight (6)
   avg: 16.0
   min: 12
   max: 20

walk-sprint-straight (174)
   avg: 7.747126436781609
   min: 6
   max: 12

exit-water-straight (2)
   avg: 28.0
   min: 28
   max: 28

step-up-straight (26)
   avg: 23.0
   min: 20
   max: 24

enter-water-diagonal (1)
   avg: 12.0
   min: 12
   max: 12

step-down-diagonal (24)
   avg: 13.333333333333334
   min: 10
   max: 26

enter-water-straight (2)
   avg: 9.0
   min: 8
   max: 10

step-up-diagonal (3)
   avg: 26.666666666666668
   min: 26
   max: 28

sprint-jump (1)
   avg: 36.0
   min: 36
   max: 36

---------------------------
public final double walk-sprint-diagonal = 10.460829493087557;
public final double walk-sprint-diagonal-touches = 21.0;
public final double walk-sprint-straight = 7.747126436781609;
public final double step-down-straight = 9.285714285714286;
public final double step-down-diagonal = 13.333333333333334;
public final double step-up-straight = 23.0;
public final double step-up-diagonal = 26.666666666666668;
public final double swim-diagonal = 25.5;
public final double swim-straight = 16.0;
public final double exit-water-straight = 28.0;
public final double enter-water-diagonal = 12.0;
public final double enter-water-straight = 9.0;
public final double sprint-jump = 36.0;





walk-sprint-diagonal (5)
   avg: 10.4
   min: 10
   max: 12

enter-water-diagonal (3)
   avg: 12.666666666666666
   min: 12
   max: 14

exit-water-diagonal (3)
   avg: 35.333333333333336
   min: 30
   max: 38

swim-diagonal (2)
   avg: 25.0
   min: 20
   max: 30

swim-straight (3)
   avg: 18.0
   min: 16
   max: 20

---------------------------
public final double walk-sprint-diagonal = 10.4;
public final double enter-water-diagonal = 12.666666666666666;
public final double exit-water-diagonal = 35.333333333333336;
public final double swim-diagonal = 25.0;
public final double swim-straight = 18.0;






walk-sprint-diagonal (9)
   avg: 11.666666666666666
   min: 10
   max: 16

jump-diagonal (9)
   avg: 37.55555555555556
   min: 35
   max: 43

walk-sprint-straight (12)
   avg: 8.25
   min: 6
   max: 10

jump-straight (9)
   avg: 39.333333333333336
   min: 38
   max: 40

bridge-free (1)
   avg: 26.0
   min: 26
   max: 26

---------------------------
public final double walk-sprint-diagonal = 11.666666666666666;
public final double walk-sprint-straight = 8.25;
public final double jump-diagonal = 37.55555555555556;
public final double jump-straight = 39.333333333333336;
public final double bridge-free = 26.0;


=== ACTION COST SAMPLES ===
walk-sprint-diagonal (279)
   avg: 10.272401433691757
   min: 10
   max: 14

step-down-straight (10)
   avg: 11.4
   min: 8
   max: 18

swim-diagonal (17)
   avg: 26.823529411764707
   min: 18
   max: 46

swim-straight (8)
   avg: 18.5
   min: 14
   max: 24

walk-sprint-straight (119)
   avg: 7.5210084033613445
   min: 6
   max: 10

exit-water-straight (1)
   avg: 37.0
   min: 37
   max: 37

step-up-straight (17)
   avg: 23.88235294117647
   min: 22
   max: 24

enter-water-diagonal (3)
   avg: 14.666666666666666
   min: 14
   max: 16

step-down-diagonal (10)
   avg: 14.4
   min: 12
   max: 24

enter-water-straight (2)
   avg: 11.0
   min: 8
   max: 14

exit-water-diagonal (3)
   avg: 43.333333333333336
   min: 28
   max: 54

step-up-diagonal (2)
   avg: 24.0
   min: 22
   max: 26

sprint-jump (1)
   avg: 33.0
   min: 33
   max: 33

---------------------------
public final double walk-sprint-diagonal = 10.272401433691757;
public final double step-down-straight = 11.4;
public final double swim-diagonal = 26.823529411764707;
public final double swim-straight = 18.5;
public final double walk-sprint-straight = 7.5210084033613445;
public final double exit-water-straight = 37.0;
public final double step-up-straight = 23.88235294117647;
public final double enter-water-diagonal = 14.666666666666666;
public final double step-down-diagonal = 14.4;
public final double enter-water-straight = 11.0;
public final double exit-water-diagonal = 43.333333333333336;
public final double step-up-diagonal = 24.0;
public final double sprint-jump = 33.0;



walk-sprint-straight (7)
   avg: 8.285714285714286
   min: 8
   max: 10

pass-door (6)
   avg: 17.333333333333332
   min: 16
   max: 18

---------------------------
public final double walk-sprint-straight = 8.285714285714286;
public final double pass-door = 17.333333333333332;






=== ACTION COST SAMPLES ===
walk-sprint-diagonal (5)
   avg: 10.8
   min: 10
   max: 12

step-up-straight (4)
   avg: 21.5
   min: 20
   max: 22

walk-sprint-straight (3)
   avg: 8.0
   min: 8
   max: 8

pillar-up (14)
   avg: 20.714285714285715
   min: 20
   max: 22

step-up-diagonal (1)
   avg: 22.0
   min: 22
   max: 22

---------------------------
public final double walk-sprint-diagonal = 10.8;
public final double step-up-straight = 21.5;
public final double walk-sprint-straight = 8.0;
public final double pillar-up = 20.714285714285715;
public final double step-up-diagonal = 22.0;



walk-sprint-straight (6)
   avg: 9.0
   min: 6
   max: 10

pillar-up (12)
   avg: 20.166666666666668
   min: 20
   max: 22

---------------------------
public final double walk-sprint-straight = 9.0;
public final double pillar-up = 20.166666666666668;






drop-down-straight (4)
   avg: 69.0
   min: 67
   max: 71

walk-sprint-straight (28)
   avg: 8.535714285714286
   min: 6
   max: 11

drop-down-diagonal (3)
   avg: 79.0
   min: 73
   max: 83

---------------------------
public final double drop-down-straight = 69.0;
public final double walk-sprint-straight = 8.535714285714286;
public final double drop-down-diagonal = 79.0;

 */
