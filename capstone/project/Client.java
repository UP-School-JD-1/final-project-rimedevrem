package mervenurdemir.capstone.project;

import java.util.Random;

public class Client implements Runnable {
	private static int clientCount = 0;
	private String name;
	private Table table;

	public Client() {
		clientCount++;
		this.setName(String.valueOf(clientCount));
		this.getTable();
		System.out.println("Client " + this.getName() + " sat table " + this.getTable().getName());
	}

	public void tableRequest() {
		this.setTable(TableManager.getInstance().takeTable(this));
		if (this.table == null) {
			try {
				Thread.sleep(1000);
				tableRequest();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void releaseTable() {
		TableManager.getInstance().releaseTable(this.table);
	}

	public Table getTable() {
		if (this.table == null) {
			tableRequest();
		}
		return this.table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void giveOrder() {
		System.out.println(
				"Client " + this.getName() + " is giving an order from " + this.getTable().getName() + " table");
		this.getTable().giveOrder();
	}

	@Override
	public void run() {
		Random random = new Random();
		int orderCount = random.nextInt(2);

		for (int i = 0; i < orderCount; i++) {
			giveOrder();
		}
		System.out.println(this.name + ". client got up from the table " + this.table.getName());
		this.releaseTable();
	}
}