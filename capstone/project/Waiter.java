package mervenurdemir.capstone.project;

public class Waiter {
	private static int waiterCount = 0;
	private String name;
	private Cook cook;

	public Waiter() {
		waiterCount++;
		this.setName(String.valueOf(waiterCount));
	}

	public void cookRequest() {
		this.setCook(CookManager.getInstance().takeCook(this));
		if (this.cook == null) {
			try {
				Thread.sleep(1000);
				cookRequest();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void releaseCook() {
		CookManager.getInstance().releaseCook(this.cook);
	}

	public static int getWaiterCount() {
		return waiterCount;
	}

	public static void setWaiterCount(int waiterCount) {
		Waiter.waiterCount = waiterCount;
	}

	public Cook getCook() {
		if (this.cook == null)
			cookRequest();
		System.out.println("Cook " + cook.getName() + " is serving " + this.name + " waiter");
		return this.cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void sendOrder() {
		this.getCook();
		System.out.println("Waiter " + this.getName() + " says the " + this.getCook().getName() + " cook the order ");
		this.getCook().sendDelivery();
		this.releaseCook();
	}
}
