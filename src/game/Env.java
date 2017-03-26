package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

import game_cat.GameObject;
import game_objects.BackgroundCube;
import game_objects.Cube;
import overlays.CameraOrientation;
import sub.Vector3D;

public class Env extends Canvas implements Runnable {
	private static final long serialVersionUID = 534748158841784372L;
	static Window window;
	static Thread thread;
	static Handler handler;
	static boolean running = false;
	
	public static final int RESSCALE = 60; // 120 = 1080p scaling
	public static final int RESWIDTH = 16 * RESSCALE;
	public static final int RESHEIGHT = 9 * RESSCALE;
	
	public static final int WORLDLENGTH = 500;
	public static final int WORLDWIDTH = 500;
	public static final int WORLDHEIGHT = 500;
	
	//public static final int CAMERASCALE = 30;
	//public static final int CAMERAWIDTH = 16 * CAMERASCALE;
	//public static final int CAMERAHEIGHT = 9 * CAMERASCALE;
	
	public static int fps = 60;
	public static long lastRenderTime = 0;
	public static long desiredRenderInterval = 1000000000 / fps;
	
	/*
	 * For normal usage
	 */
	public Env() {
		handler = new Handler();
		handler.setCamera(new Vector3D(WORLDLENGTH/2, 0, WORLDHEIGHT/2), new Vector3D(0,1,0));
		handler.addOverlay(new CameraOrientation(handler.camera.orient));
		
		handler.addObject(new BackgroundCube(new Vector3D(WORLDLENGTH/2, WORLDWIDTH/2, WORLDHEIGHT/2), new Vector3D(0,1,0).normalize(), WORLDWIDTH));
		handler.addObject(new Cube(new Vector3D(WORLDLENGTH/2, 200, WORLDHEIGHT/2), new Vector3D(0,1,0).normalize(), 51));
		handler.addObject(new Cube(new Vector3D(WORLDLENGTH/2 + 80, 200, WORLDHEIGHT/2 - 20), new Vector3D(0,1,0).normalize(), 50));
		
		this.addKeyListener(new KeyInput(handler));
		window = new Window(RESWIDTH, RESHEIGHT, "3D", this);
	}
	
	/*
	 * For testing
	 */
	public Env(Vector3D camLoc, GameObject[] gameObjects) {
		handler = new Handler();
		handler.setCamera(new Vector3D(WORLDLENGTH/2, 0, WORLDHEIGHT/2), new Vector3D(0,1,0));
		this.addKeyListener(new KeyInput(handler));
		for(int i=0; i<gameObjects.length; i++){
			handler.addObject(gameObjects[i]);
		}
		window = new Window(RESWIDTH, RESHEIGHT, "3D", this);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Main loop of execution
	 */
	public void run(){
		long timer = System.currentTimeMillis();
		//int frames = 0;
		
		while(running) {
			tick();
			render();
			lastRenderTime = System.nanoTime();
			sleepNanos((long)desiredRenderInterval);
			//frames++;

			if(System.currentTimeMillis() - timer > (1000)) {
				//System.out.println("FPS: " + frames); frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		/*
		 * TODO Instead of filling the screen with black, render the background
		 */
		
		g.setColor(Color.black);
		g.fillRect(0, 0, RESWIDTH, RESHEIGHT);

		handler.render(g);

		g.dispose();
		bs.show();
	}
	
	public static void sleepNanos (long nanoDuration) {
		try {
			long SLEEP_PRECISION = TimeUnit.MILLISECONDS.toNanos(2);
			long SPIN_YIELD_PRECISION = TimeUnit.MILLISECONDS.toNanos(2);
			final long end = System.nanoTime() + nanoDuration;
			long timeLeft = nanoDuration;
			do {
				if (timeLeft > SLEEP_PRECISION)
					Thread.sleep (1);
				else
					if (timeLeft > SPIN_YIELD_PRECISION)
						Thread.yield();

				timeLeft = end - System.nanoTime();
			} while (timeLeft > 0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Env();
	}
}
