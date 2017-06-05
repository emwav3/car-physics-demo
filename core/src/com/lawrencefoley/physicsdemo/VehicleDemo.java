package com.lawrencefoley.physicsdemo;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class VehicleDemo extends ApplicationAdapter
{
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Box2DDebugRenderer debugRenderer;
	Camera camera;

	Body cart;
	Body wheel1;
	Body wheel2;
	Body axle1;
	Body axle2;
	RevoluteJoint motor1;
	RevoluteJoint motor2;
	PrismaticJoint spring1;
	PrismaticJoint spring2;

	Sprite screen;
	// Input input;

	Matrix4 debugMatrix;

	Viewport viewport;

	static float DEBUG_SCALE = 50.0F;

	@Override
	public void create()
	{

		Random random = new Random();

		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera(500, 500);

		// Create a physics world, the heart of the simulation. The Vector passed in is gravity
		world = new World(new Vector2(0, -10f), true);
		debugRenderer.render(world, camera.combined);

		// Create a copy of camera projection matrix
		debugMatrix = new Matrix4(camera.combined);

		// BoxObjectManager.BOX_TO_WORLD = 100f
		// Scale it by 100 as our box physics bodies are scaled down by 100
		debugMatrix.scale(DEBUG_SCALE, DEBUG_SCALE, 1f);

		viewport = new FillViewport(500, 500);

		batch = new SpriteBatch();

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, -6));
		
		

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);
		
		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		// groundBox.setAsBox(100, 10.0f);
		groundBox.setAsBox(240, 3);
		
		// Create a fixture from our polygon shape and add it to our ground body
		Fixture groundFixture = groundBody.createFixture(groundBox, 0.0f);
		groundFixture.setFriction(1.0f);
		
		
		// Clean up after ourselves
		groundBox.dispose();

		// Random circles
		CircleShape circle = new CircleShape();
		FixtureDef circleDef = new FixtureDef();
		circleDef.shape = circle;
		circleDef.density = 1.0f;
		circleDef.friction = 0.7f;
		circleDef.restitution = 0.6f; // Make it bounce a little bit
//		circleDef.filter.groupIndex = -1;
		BodyDef bodyDef;
//		for (int i = 0; i < 30; i++)
//		{
//			circle.setRadius(random.nextFloat() / 10 + 0.02f);
//
//			bodyDef = new BodyDef();
//			bodyDef.type = BodyType.DynamicBody;
//			bodyDef.position.set(random.nextFloat() * 5, random.nextFloat() * 5 + 5);
//			bodyDef.allowSleep = true;
//			bodyDef.linearDamping = 0.1f;
//			bodyDef.angularDamping = 0.1f;
//
//			Body body = world.createBody(bodyDef);
//			body.createFixture(circleDef);
//
//		}

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.

		// Car body
		bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0f, 0.5f);
		
		cart = world.createBody(bodyDef);

		// Create a polygon shape
		PolygonShape boxShape = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		// groundBox.setAsBox(100, 10.0f);
		boxShape.setAsBox(1.5f, .5f);
		
		FixtureDef boxDef = new FixtureDef();
		boxDef.shape= boxShape;
		boxDef.shape.setRadius(1.5f);
		boxDef.density = 2f;
		boxDef.friction = 0.5f;
		boxDef.restitution = 0.2f;
		boxDef.filter.groupIndex = -1;
		
		PolygonShape cartShape = new PolygonShape();
		
		
		cartShape.setAsBox(1.5f, 0.3f);
		boxDef.shape = cartShape;
		cart.createFixture(boxDef);
		
//		cartShape.setAsBox(0.4f, 0.15f, new Vector2(-1f, -0.3f), (float) (Math.PI / 3.0f));
//		boxDef.shape = cartShape;
//		cart.createFixture(boxDef);
//
//		cartShape.setAsBox(0.4f, 0.15f, new Vector2(1f, -0.3f), (float) (-Math.PI / 3.0f));
//		boxDef.shape = cartShape;
//		cart.createFixture(boxDef);
		
		cart.resetMassData();
		
		

		

		// Axles
		axle1 = world.createBody(bodyDef);
		cartShape.setAsBox(0.1f, 0.1f, new Vector2(-1f, -1.1f), 0);
		axle1.createFixture(cartShape, 1.0f);

		PrismaticJointDef prismaticJoinDef = new PrismaticJointDef();
		prismaticJoinDef.initialize(cart, axle1, axle1.getWorldCenter(), Vector2.Y);
		prismaticJoinDef.lowerTranslation = -0.3f;
		prismaticJoinDef.upperTranslation = 0.5f;
		prismaticJoinDef.enableLimit = true;
		prismaticJoinDef.enableMotor = true;
		

		spring1 = (PrismaticJoint) world.createJoint(prismaticJoinDef);

		axle2 = world.createBody(bodyDef);

		//boxDef.SetAsOrientedBox(0.4, 0.1, new b2Vec2(1 + 0.6*Math.cos(-Math.PI/3), -0.3 + 0.6*Math.sin(-Math.PI/3)), -Math.PI/3);
		cartShape.setAsBox(0.1f, 0.1f, new Vector2(1f, -1.1f), 0);
		axle2.createFixture(cartShape, 1.0f);

		prismaticJoinDef.initialize(cart, axle2, axle2.getWorldCenter(), Vector2.Y);

		spring2 = (PrismaticJoint) world.createJoint(prismaticJoinDef);

		// wheels
		
		//circle = new CircleShape();.
		
		
		
		circle.setRadius(0.7f);
		
		
		circleDef.density = 1f;
		circleDef.friction = 0.65f;
		circleDef.restitution = 0.3f;
		circleDef.filter.groupIndex = -1;
		
		for (int i = 0; i < 2; i++)
		{
			bodyDef = new BodyDef();
			
			
			bodyDef.type = BodyType.DynamicBody;
			if (i == 0)
			{
				bodyDef.position.set((float) (axle1.getWorldCenter().x), (float) (axle1.getWorldCenter().y));
			} else
			{
				// bodyDef.position.Set(axle2.GetWorldCenter().x + 0.3*Math.cos(-Math.PI/3), axle2.GetWorldCenter().y + 0.3*Math.sin(-Math.PI/3));
				bodyDef.position.set((float) (axle2.getWorldCenter().x), (float) (axle2.getWorldCenter().y));
			}

			bodyDef.allowSleep = false;

			if (i == 0)
			{
				wheel1 = world.createBody(bodyDef);
			} else
			{
				wheel2 = world.createBody(bodyDef);
			}

			(i == 0 ? wheel1 : wheel2).createFixture(circleDef);

		}

		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.enableMotor = true;
		

		revoluteJointDef.initialize(axle1, wheel1, wheel1.getWorldCenter());
		motor1 = (RevoluteJoint) world.createJoint(revoluteJointDef);

		revoluteJointDef.initialize(axle2, wheel2, wheel2.getWorldCenter());
		motor2 = (RevoluteJoint) world.createJoint(revoluteJointDef);

	}

	@Override
	public void render()
	{
		motor1.setMaxMotorTorque(0.0f);
		motor2.setMaxMotorTorque(0.0f);
		
		motor1.setMotorSpeed(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? -150f : 0f);
		motor1.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 40f : 0f);
		
		//motor1.setMotorSpeed(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? (float)(15 * Math.PI) : 0);
		motor2.setMotorSpeed(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 150f : 0f);
		//motor1.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? (float) 15 : 0);
		motor2.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 40f : 0f);
		//motor1.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 0f : (float)(Math.PI * 17));
		
		//motor2.setMotorSpeed(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? (float)(-15 * Math.PI) : 0);
		//motor2.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? (float) 100f : 0);
		//motor2.setMaxMotorTorque(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 0f : (float)(Math.PI * 17));
		//System.out.println("left speed: " + motor1.getMotorSpeed() + ", torque: " + motor1.getMaxMotorTorque());
		
//		spring1.setMaxMotorForce((float) (30 + Math.abs(800 * Math.pow(spring1.getJointTranslation(), 2))));
//		spring1.setMotorSpeed((float) (spring1.getMotorSpeed() - 10 * spring1.getJointTranslation() * 0.4));
//		
//		spring2.setMaxMotorForce((float) (30 + Math.abs(800 * Math.pow(spring2.getJointTranslation(), 2))));
//		spring2.setMotorSpeed((float) (spring2.getMotorSpeed() - 10 * spring2.getJointTranslation() * 0.4));
//		
//		//System.out.println("spring1 speed: " + spring1.getMotorSpeed());
		spring1.setMaxMotorForce(125);
		spring2.setMaxMotorForce(125);
		
//		spring2.setMotorSpeed(100);
		// Advance the world, by the amount of time that has elapsed since the last frame
		// Generally in a real game, dont do this in the render loop, as you are tying the physics
		// update rate to the frame rate, and vice versa
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		// Now update the spritee position accordingly to it's now updated Physics body
		// sprite.setPosition(body.getPosition().x, body.getPosition().y);

		// You know the rest...
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(debugMatrix);

		batch.begin();

		// debugRenderer.render(world, camera.combined);
		debugRenderer.render(world, debugMatrix);
		// batch.draw(sprite, sprite.getX(), sprite.getY());
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		// img.dispose();
		world.dispose();
	}

	@Override
	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
	}

}
