package mervenurdemir.capstone.project;

public class Cook {
	private static int cookCount = 0;
	private String name;

	public Cook() {
		cookCount++;
		this.name = String.valueOf(cookCount);
	}

	public static int getCookCount() {
		return cookCount;
	}

	public static void setCookCount(int cookCount) {
		Cook.cookCount = cookCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void sendDelivery() {
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.getName() + " Cook send delivery");
	}
}