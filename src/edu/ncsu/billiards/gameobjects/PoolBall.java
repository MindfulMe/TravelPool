package edu.ncsu.billiards.gameobjects;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Mass;

import org.newdawn.slick.Color;

public class PoolBall extends Body {
	private static float RADIUS = .028575f; // meters
	
	private static float MASS = .17f; // kilograms
	private static float DENSITY = 217.97925f; // kg/m^3
	private static float FRICTION = .08f;
	private static float RESTITUTION = .95f;

	private static float ACCELERATION1 = -0.8977101007632834f;
	private static float ACCELERATION2 = -1.7954202015265668f;
	private static float ACCELERATION3 = -2.6931303022898500f;

	private Color color;

	private VelocityLine velocityLine;
	private double entryTime;

	private boolean isExiting;



	public PoolBall(float x, float y, Color color) {
		this.color = color;

		this.velocityLine = new VelocityLine();

		Circle ballShape = new Circle(RADIUS);
		BodyFixture ballFixture = new BodyFixture(ballShape);

		ballFixture.setDensity(DENSITY);
		ballFixture.setFriction(FRICTION);
		ballFixture.setRestitution(RESTITUTION);
		ballFixture.createMass();

		this.addFixture(ballFixture);
		this.translate(x, y);
		this.setLinearDamping(.999999);

		this.setMass(Mass.Type.NORMAL);

		this.isExiting = false;
	}

	public Circle getCircle() {
		return (Circle) this.getFixture(0).getShape();
	}

	public float getX() {
		return (float) this.getCircle().getCenter().x;
	}

	public float getY() {
		return (float) this.getCircle().getCenter().y;
	}

	public float getRadius() {
		return (float) this.getCircle().getRadius();
	}



	public Color getColor() {
		return this.color;
	}

	public VelocityLine getVelocityLine() {
		return this.velocityLine;
	}

	public double getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(double time) {
		this.entryTime = time;
	}



	public boolean isExiting() {
		return this.isExiting;
	}

	public void setExiting(boolean isExiting) {
		this.isExiting = isExiting;
	}
}
