abstract public class Game {
	abstract public void setup() throws Exception;
	abstract public void loop() throws Exception;
	abstract public void close() throws Exception;

	public void run() throws Exception {
		setup();
		loop();
	}

}
