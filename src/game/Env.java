package game;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ground.Ground;
import overlays.CameraOrientation;
import overlays.FPS;
import support_lib.Orient3D;
import support_lib.Vector3D;
import units.Cube;

public class Env extends Canvas implements Runnable {
	//private Options options = new Options();
	//public static int numCores = Runtime.getRuntime().availableProcessors();
	
	private static final long serialVersionUID = 534748158841784372L;
	
	public static Window window;
	public static Thread thread;
	public static Handler handler;
	public static Graphics g;
	static boolean running = false;
	
	public static final int resScale = 60; // 120 = 1080p scaling
	public static int resWidth = 8 * resScale;
	public static int resHeight = 10 * resScale;
	
	public static final double worldLength = 4000;
	public static final double worldWidth = 4000;
	public static final double worldHeight = 4000;
	
	public static double currentFPS = 0;
	public static int fps = 60;
	public static long lastRenderTime = 0;
	public static long desiredRenderInterval = 1000000000 / fps;
	
	public Env() {
		// handles tick render cycle
		handler = new Handler();
		
		// movement enablers
		KeyInput keyInput = new KeyInput(handler);
		this.addMouseListener(keyInput);
		this.addKeyListener(keyInput);
		
		// look around object
		Camera camera = new Camera(new Vector3D(worldLength/2, worldWidth/2, worldHeight/2), new Vector3D(0,1,0), keyInput, this);
		handler.setCamera(camera);
		
		// display all the data
		CameraOrientation camOrientOverlay = new CameraOrientation(camera);
		FPS fpsOverlay = new FPS();
		handler.addOverlay(camOrientOverlay);
		handler.addOverlay(fpsOverlay);
		
		// cubes flyin' all 'round
		int numCubes = 1;
		for(int i=0; i<numCubes; i++){
			handler.addObject(new Cube(new Vector3D((int)rand(0, worldLength), (int)rand(0, worldWidth), (int)rand(0, worldHeight)), new Vector3D(rand(0,1),rand(0,1),rand(0,1)).normalize(), (int)rand(20, 150), camera));
		}
		
		// cube to border the environment
		//Cube borderCube = new Cube(new Vector3D(0,0,0), new Vector3D(.5, 1, .5).normalize(), 10000, camera);
		// set its colors to consistent array
		//Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.PINK, Color.MAGENTA};
		//for(int i=0; i<6; i++) {
		//	borderCube.squares[i].color = colors[i];
		//}
		//handler.addObject(borderCube);
		handler.addObject(new Ground(new Vector3D(0,0,0), 10, new Orient3D(new Vector3D(1,0,0))));
		
		// draw box
		window = new Window(resWidth, resHeight, "3D", this);
		
		// begin tick render cycle
		this.start();
	}
	
	public static double rand(double low, double high){
		Random random = new Random();
		double r = Math.abs(random.nextDouble());
		r = low + (r * (high - low));
		return r;
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
		int frames = 0;
		int lastFrames = frames;
		//long myTimer = System.currentTimeMillis();
		//long myTimer2 = System.nanoTime();
		
		//int nanoSecondsAllottedPerRender = (10^9 / Env.fps);
		
		while(running) {
			tick();
			render();
			
			/*
			int nanoSecondsElapsed = (int)(System.nanoTime() - lastRenderTime);
			double fractionOfSixty = nanoSecondsElapsed / nanoSecondsAllottedPerRender;
			currentFPS = fractionOfSixty * 60;
			*/
			
			//currentFrames = frames;
			
			/*
			if((System.currentTimeMillis() - myTimer) > 100){
				currentFPS = (frames - lastFrames) * 10;
				lastFrames = frames;
				myTimer = System.currentTimeMillis();
			}
			
			/*
			if((System.nanoTime() - myTimer2) > (100 * 1000000)){
				currentFPS = (frames - lastFrames) * 10;
				lastFrames = frames;
				myTimer2 = System.nanoTime();
			}
			*/
			
			lastRenderTime = System.nanoTime();
			sleepNanos((long)desiredRenderInterval);
			frames++;

			if(System.currentTimeMillis() - timer > (1000)) {
				//System.out.println("FPS: " + frames); frames = 0;
				currentFPS = frames - lastFrames;
				timer = System.currentTimeMillis();
				lastFrames = frames;
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

		g = bs.getDrawGraphics();

		/*
		 * TODO Instead of filling the screen with black, render the background
		 */
		
		g.setColor(Color.black);
		g.fillRect(0, 0, resWidth, resHeight);
		
		Dimension dim = window.frame.getSize();
		if(dim.getWidth() != resWidth){
			resWidth = (int)dim.getWidth();
		}
		if(dim.getHeight() != resHeight){
			resHeight = (int)dim.getHeight();
		}

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
}
