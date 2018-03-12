package support_lib;

public class EffTracker {
	long[] times;
	long sum = 0;
	int index = 0;
	boolean full = false;
	
	long currentStart;
	
	public EffTracker(int maxSampleSize) {
		times = new long[maxSampleSize];
	}
	
	public void start() {
		currentStart = System.nanoTime();
	}
	
	public void end() {
		addTime(currentStart, System.nanoTime());
	}
	
	private void addTime(long startTime, long endTime) {
		sum -= times[index];
		
		long duration = (endTime - startTime) / 1;
		sum += duration;
		times[index] = duration;
		
		index = (index + 1) % times.length;
		if(index == 0) {
			full = true;
		}
	}
	
	public int getAverage() {
		int samples = getSamples();
		return (int)sum / samples;
	}
	
	public int getSamples() {
		if(full) {
			return times.length;
		}else {
			return index;
		}
	}
	
	public void printAverage(String name) {
		int avg = getAverage();
		System.out.println("'" + name + "' averaged " + avg + "ms over " + getSamples() + " samples");
	}
}
