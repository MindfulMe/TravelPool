package ivanov.ncsu.billiards.setups;

import ivanov.ncsu.billiards.gameobjects.Cushion;
import ivanov.ncsu.billiards.gameobjects.Pocket;
import ivanov.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class GlancingBlowSetup extends Setup {
	public ArrayList<Pocket> generatePockets() {
		ArrayList<Pocket> pockets = new ArrayList<Pocket>();

		Pocket pocket1 = new Pocket(1, .317f, new Vector2(-1, 0));
		Pocket pocket2 = new Pocket(2, .617f, new Vector2(-1, .6));

		pocket1.setDestination(pocket2);
		pocket1.setTimeDifference(-0.5);

		pocket2.setDestination(pocket1);
		pocket2.setTimeDifference(-0.5);

		pockets.add(pocket1);
		pockets.add(pocket2);

		return pockets;
	}

	public ArrayList<PoolBall> generateBalls() {
		ArrayList<PoolBall> balls = new ArrayList<PoolBall>();

		PoolBall ball = new PoolBall(.738f, .317f, Color.black);
		PoolBall ball2 = new PoolBall(.438f, .317f, Color.yellow);	
		PoolBall ball3 = new PoolBall(.338f, .317f, Color.blue);
		PoolBall ball4 = new PoolBall(.238f, .317f, Color.white);
		PoolBall ball5 = new PoolBall(.438f, .517f, Color.green);
		PoolBall ball6 = new PoolBall(.438f, .117f, Color.orange);
		balls.add(ball);
		balls.add(ball2);
		balls.add(ball3);
		balls.add(ball4);
		balls.add(ball5);
		balls.add(ball6);
		return balls;
	}
}
