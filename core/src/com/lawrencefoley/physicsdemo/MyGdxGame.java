package com.lawrencefoley.physicsdemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

public class MyGdxGame extends ApplicationAdapter
{
	// SpriteBatch batch;
	// Texture img;

	// ---------
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
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
	Input input;
	

	@Override
	public void create()
	{

		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(500, 500);
		// Create a physics world, the heart of the simulation. The Vector passed in is gravity
		world = new World(new Vector2(0, -98f), true);
		debugRenderer.render(world, camera.combined);
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		// =------------------
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(0, 200);

		// Create our body in the world using our body definition
		body = world.createBody(bodyDef);
		
		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(50f);
		

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1f; 
		fixtureDef.friction = 0.7f;
		fixtureDef.restitution = .1f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();

		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(new Vector2(0, 10));

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		//groundBox.setAsBox(100, 10.0f);
		groundBox.setAsBox(500, 1, new Vector2(0, 0),  45);
		// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);
		// Clean up after ourselves
		groundBox.dispose();
		

	}

	@Override
	public void render()
	{

		// Advance the world, by the amount of time that has elapsed since the last frame
		// Generally in a real game, dont do this in the render loop, as you are tying the physics
		// update rate to the frame rate, and vice versa
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		// Now update the spritee position accordingly to it's now updated Physics body
		//sprite.setPosition(body.getPosition().x, body.getPosition().y);

		// You know the rest...
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		debugRenderer.render(world, camera.combined);
		//batch.draw(sprite, sprite.getX(), sprite.getY());
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		img.dispose();
		world.dispose();
	}
}
