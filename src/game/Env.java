package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import game_cat.GameObject;
import game_objects.Cube;
import overlays.CameraOrientation;
import overlays.Grid;
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
	
	public static final int worldLength = 500;
	public static final int worldWidth = 500;
	public static final int worldHeight = 500;
	
	public static int fps = 60;
	public static long lastRenderTime = 0;
	public static long desiredRenderInterval = 1000000000 / fps;
	
	/*
	 * For normal usage
	 */
	public Env() {
		handler = new Handler();
		
		KeyInput keyInput = new KeyInput(handler);
		this.addMouseListener(keyInput);
		this.addKeyListener(keyInput);
		
		// Using for easier location
		Camera camera = new Camera(new Vector3D(0, 0, 0), new Vector3D(0,1,0), keyInput, this);
		/* Use this normally
		 * Camera camera = new Camera(new Vector3D(WORLDLENGTH/2, 0, WORLDHEIGHT/2), new Vector3D(0,1,0));
		 */
		
		handler.setCamera(camera);
		Grid grid = new Grid(handler.camera, RESWIDTH, RESHEIGHT);
		handler.setGrid(grid);
		handler.addOverlay(new CameraOrientation(camera));
		
		/* Old background cube thing
		 * handler.addObject(new BackgroundCube(new Vector3D(WORLDLENGTH/2, WORLDWIDTH/2, WORLDHEIGHT/2), new Vector3D(0,1,0).normalize(), WORLDWIDTH));
		 */
		
		// Using easy location to make debugging easier
		handler.addObject(new Cube(new Vector3D(0, 200, 0), new Vector3D(0,1,0).normalize(), 40));
		
		for(int i=0; i<1000; i++){
			handler.addObject(new Cube(new Vector3D((int)rand(0, worldLength), (int)rand(0, worldWidth), (int)rand(0, worldHeight)), new Vector3D(rand(0,1),rand(0,1),rand(0,1)).normalize(), (int)rand(20, 100)));
		}
		
		//handler.addObject(new Cube(new Vector3D(0, 0, 0), new Vector3D(0,1,0).normalize(), 5000));
		
		/* Use this normally
		 handler.addObject(new Cube(new Vector3D(WORLDLENGTH/2, 200, WORLDHEIGHT/2), new Vector3D(0,1,0).normalize(), 51));
		*/
		
		/* Not sure what this was used for
		 * handler.addObject(new Cube(new Vector3D(WORLDLENGTH/2 + 80, 200, WORLDHEIGHT/2 - 20), new Vector3D(0,1,0).normalize(), 50));
		 */
		
		window = new Window(RESWIDTH, RESHEIGHT, "3D", this);
	}
	
	public double rand(double low, double high){
		Random random = new Random();
		double r = Math.abs(random.nextDouble());
		r = low + (r * (high - low));
		return r;
	}
	
	/*
	 * For testing
	 */
	public Env(Vector3D camLoc, GameObject[] gameObjects) {
		handler = new Handler();
		//handler.setCamera(new Vector3D(WORLDLENGTH/2, 0, WORLDHEIGHT/2), new Vector3D(0,1,0));
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
