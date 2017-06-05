package com.lawrencefoley.physicsdemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lawrencefoley.physicsdemo.VehicleDemo;
import com.lawrencefoley.physicsdemo.VehicleDemo2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width=1024;
		config.height=768;
		
		new LwjglApplication(new VehicleDemo(), config);
	}
}
