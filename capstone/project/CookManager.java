package mervenurdemir.capstone.project;

import java.util.ArrayList;
import java.util.*;

public class CookManager {

	private static CookManager mInstance;
	private static int availableInstance = 2;
	private List<Cook> pool = new ArrayList<>();
	private List<Waiter> waiting = new ArrayList<>();

	private CookManager() {
	}

	public static synchronized CookManager getInstance() {
		if (mInstance == null) {
			mInstance = new CookManager();
		}
		return mInstance;
	}

	public synchronized Cook takeCook(Waiter waiter) {
		if (waiting.isEmpty()) { // waiting listesi boş mu?
			if (pool.isEmpty()) { // pool boş mu?
				if (availableInstance != 0) { // pool boş durumda ve obje oluşturma hakkımız var
					return fillPoolAndReturn();
				} else { // Pool boş durumda ve obje oluşturulamaz durumdayız.
					System.out.println("Waiter " + waiter.getName() + " waiting");
					waiting.add(waiter);
					return null;
				}
			} else { // waiting listesi boş ve pool dolu ise verip yolluyoruz
				return getFromPool();
			}
		} else { // Waiting listesinde en az bir kişi varsa
			if (waiting.contains(waiter)) { // Obje isteği yapan client waiting listesinde ise
				if (pool.isEmpty()) { // Pool boş mu?
					if (availableInstance != 0) { // Pool boş ve obje oluşturma hakkımız var ise oluşturkduk gönderdik
						return fillPoolAndReturn();
					} else { // Pool boş ve obje oluşturma hakkımız yok ise beklemesini söyledik
						System.out.println("Waiter " + waiter.getName() + " please wait a little longer.");
						return null;
					}
				} else { // client waiting listesinde ve pool da obje var
					return getFromPool();
				}
			} else { // waitinge de bu client yok ise waitinge ekledim beklettim
				System.out.println("Waiter " + waiter.getName() + "\t is waiting");
				waiting.add(waiter);
				return null;
			}
		}
	}

	public synchronized void releaseCook(Cook cook) {
		pool.add(cook);
	}

	private Cook fillPoolAndReturn() {
		Cook cook = null;
		cook = new Cook();
		availableInstance--;
		return cook;
	}

	private Cook getFromPool() {
		Cook response = pool.get(0);
		pool.remove(0);
		return response;
	}
}