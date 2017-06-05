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

public class VehicleDemo2 extends ApplicationAdapter
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
		groundBody.createFixture(groundBox, 0.0f);
		
		
		// Clean up after ourselves
		groundBox.dispose();

		// Random circles
		CircleShape circle = new CircleShape();

		FixtureDef circleDef = new FixtureDef();
		
		circleDef.shape = circle;
		circleDef.density = 1.0f;
		circleDef.friction = 0.7f;
		circleDef.restitution = 0.6f; // Make it bounce a little bit
		circleDef.filter.groupIndex = 1;

		BodyDef bodyDef;

		for (int i = 0; i < 30; i++)
		{
			circle.setRadius(random.nextFloat() / 10 + 0.02f);

			bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(random.nextFloat() * 5, random.nextFloat() * 5 + 5);
			bodyDef.allowSleep = true;
			bodyDef.linearDamping = 0.1f;
			bodyDef.angularDamping = 0.1f;

			Body body = world.createBody(bodyDef);
			body.createFixture(circleDef);

		}

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
		
//		c

	}

	@Override
	public void render()
	{
		
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
