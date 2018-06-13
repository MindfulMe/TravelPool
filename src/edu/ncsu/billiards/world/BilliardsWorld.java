package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.World;

public abstract class BilliardsWorld extends World {
	private ArrayList<PoolBall> currentBalls;
	private ArrayList<Pocket> pockets;
	private ArrayList<Cushion> cushions; 

	private double time;


	public BilliardsWorld() {
		currentBalls = new ArrayList<PoolBall>();
		pockets = new ArrayList<Pocket>();
		cushions = new ArrayList<Cushion>();

		time = 0;

		this.setGravity(World.ZERO_GRAVITY);
		this.getSettings().setSleepAngularVelocity(Double.MAX_VALUE);
		this.getSettings().setRestitutionVelocity(0);
	}



	@Override
	public boolean update(double elapsedTime) {
		time += elapsedTime;

		return super.update(elapsedTime);
	}



	public void addCurrentBall(PoolBall ball) {
		
		currentBalls.add(ball);
		
		addBody(ball);
	}

	public void addPocket(Pocket pocket) {
	
		pockets.add(pocket);
		// add to the world
		addBody(pocket);
	}

	public void addCushion(Cushion cushion) {
		
		cushions.add(cushion);
		
		addBody(cushion);
	}



	public ArrayList<PoolBall> getCurrentBalls() {
		return currentBalls;
	}

	public ArrayList<Pocket> getPockets() {
		return pockets;
	}

	public ArrayList<Cushion> getCushions() {
		return cushions;
	}



	public double getTime() {
		return this.time;
	}

	public void setTime(double newTime) {
		this.time = newTime;
	}



	public void removeCurrentBall(PoolBall ball) {
		
		getCurrentBalls().remove(ball);

		removeBody(ball);
	}

	public void clearCurrentBalls() {
		for (PoolBall ball : getCurrentBalls()) {
			removeBody(ball);
		}

		getCurrentBalls().clear();
	}

	public void removePocket(Pocket pocket) {
		
		getPockets().remove(pocket);

		removeBody(pocket);
	}

	public void clearPockets() {
		for (Pocket pocket : getPockets()) {
			removeBody(pocket);
		}

		getPockets().clear();
	}



	public boolean hasMovingBalls() {
		for (PoolBall ball : currentBalls) {

			if (!ball.isAsleep()) {
				
				return true;
			}
		}

		return false;
	}
}
