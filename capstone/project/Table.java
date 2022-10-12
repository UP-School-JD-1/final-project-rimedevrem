package mervenurdemir.capstone.project;

public class Table {
	private static int tableCount = 0;
	private String name;
	private Waiter waiter;

	public Table() {
		tableCount++;
		this.setName(String.valueOf(tableCount));
	}

	public void waiterRequest() {
		this.setWaiter(WaiterManager.getInstance().takeWaiter(this));
		if (this.waiter == null) {
			try {
				Thread.sleep(1000);
				waiterRequest();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void releaseWaiter() {
		WaiterManager.getInstance().releaseWaiter(this.waiter, this);
	}

	public static int getTableCount() {
		return tableCount;
	}

	public static void setTableCount(int tableCount) {
		Table.tableCount = tableCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Waiter getWaiter() {
		if (this.waiter == null)
			waiterRequest();
		return this.waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public void giveOrder() {
		getWaiter();
		System.out.println("Waiter " + this.getWaiter().getName() + " takes an order from " + this.name + " table");
		this.getWaiter().sendOrder();
		this.releaseWaiter();
	}
}