package mervenurdemir.capstone.project;

import java.util.ArrayList;
import java.util.List;

public class WaiterManager {

	private static WaiterManager mInstance;
	private static int availableInstance = 3;
	private List<Waiter> pool = new ArrayList<>();
	private List<Table> waiting = new ArrayList<>();

	private WaiterManager() {
	}

	public static synchronized WaiterManager getInstance() {
		if (mInstance == null) {
			mInstance = new WaiterManager();
		}
		return mInstance;
	}

	public synchronized Waiter takeWaiter(Table table) {

		if (waiting.isEmpty()) { // waiting listesi boş mu?
			if (pool.isEmpty()) { // pool boş mu?
				if (availableInstance != 0) { // pool boş durumda ve obje oluşturma hakkımız var
					return fillPoolAndReturn();
				} else { // Pool boş durumda ve obje oluşturulamaz durumdayız.
					System.out.println("Table " + table.getName() + " waiting");
					waiting.add(table);
					return null;
				}
			} else { // waiting listesi boş ve pool dolu ise verip yolluyoruz
				return getFromPool();
			}
		} else { // Waiting listesinde en az bir kişi varsa
			if (waiting.contains(table)) { // Obje isteği yapan client waiting listesinde ise
				if (pool.isEmpty()) { // Pool boş mu?
					if (availableInstance != 0) { // Pool boş ve obje oluşturma hakkımız var ise oluşturkduk gönderdik
						return fillPoolAndReturn();
					} else { // Pool boş ve obje oluşturma hakkımız yok ise beklemesini söyledik
						System.out.println("Table " + table.getName() + " please wait a little longer.");
						return null;
					}
				} else { // client waiting listesinde ve pool da obje var
					return getFromPool();
				}
			} else { /// waitinge de bu client yok ise waitinge ekledim beklettim
				System.out.println("Table " + table.getName() + "\t is waiting");
				waiting.add(table);
				return null;
			}
		}

	}

	public synchronized void releaseWaiter(Waiter waiter, Table table) {
		pool.add(waiter);
	}

	private Waiter fillPoolAndReturn() {
		Waiter waiter = null;
		waiter = new Waiter();
		availableInstance--;
		return waiter;
	}

	private Waiter getFromPool() {
		Waiter response = pool.get(0);
		pool.remove(0);
		return response;
	}
}
