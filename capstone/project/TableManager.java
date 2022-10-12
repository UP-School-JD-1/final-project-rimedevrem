package mervenurdemir.capstone.project;

import java.util.ArrayList;
import java.util.*;

public class TableManager {

	private static TableManager mInstance;
	private static int availableInstance = 5;
	private List<Table> pool = new ArrayList<Table>();
	private List<Client> waiting = new ArrayList<Client>();

	private TableManager() {
	}

	public static synchronized TableManager getInstance() {
		if (mInstance == null) {
			mInstance = new TableManager();
		}
		return mInstance;
	}

	public synchronized Table takeTable(Client client) {

		if (waiting.isEmpty()) { // waiting listesi boş mu?
			if (pool.isEmpty()) { // pool boş mu?
				if (availableInstance != 0) { // pool boş durumda ve obje oluşturma hakkımız var
					return fillPoolAndReturn();
				} else { // Pool boş durumda ve obje oluşturulamaz durumdayız.
					System.out.println("Client " + client.getName() + " waiting");
					waiting.add(client);
					return null;
				}
			} else { // waiting listesi boş ve pool dolu ise verip yolluyoruz
				return getFromPool();
			}
		} else { // Waiting listesinde en az bir kişi varsa
			if (waiting.contains(client)) { // Obje isteği yapan client waiting listesinde ise
				if (pool.isEmpty()) { // Pool boş mu?
					if (availableInstance != 0) { // Pool boş ve obje oluşturma hakkımız var ise oluşturkduk gönderdik
						return fillPoolAndReturn();
					} else { // Pool boş ve obje oluşturma hakkımız yok ise beklemesini söyledik
						System.out.println("Client " + client.getName() + " please wait a little longer.");
						return null;
					}
				} else { // client waiting listesinde ve pool da obje var
					return getFromPool();
				}
			} else { // waitinge de bu client yok ise waitinge ekledim beklettim
				System.out.println("Client " + client.getName() + "\t is waiting");
				waiting.add(client);
				return null;
			}
		}
	}

	public synchronized void releaseTable(Table table) {
		pool.add(table);
	}

	private Table fillPoolAndReturn() {
		Table table = null;
		table = new Table();
		availableInstance--;
		return table;
	}

	private Table getFromPool() {
		Table response = pool.get(0);
		pool.remove(0);
		return response;
	}
}