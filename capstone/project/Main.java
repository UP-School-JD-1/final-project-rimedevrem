package mervenurdemir.capstone.project;

public class Main {

	public static void main(String[] args) {
		// write your code here
		int clientCount = 10;
		for (int i = 0; i < clientCount; i++) {
			clientComeToRestaurant();
		}
	}

	static void clientComeToRestaurant() {
		Client client = new Client();
		Thread thread = new Thread(client);
		thread.start();
	}
}