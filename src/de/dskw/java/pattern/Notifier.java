package de.dskw.java.pattern;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Notifier implements Disposable {
	private HashMap<String, ArrayList<Observer>> observers;

	public Notifier() {
		observers = new HashMap<String, ArrayList<Observer>>();
	}
	
	public void addObserver(Observer observer, String property) {
		ArrayList<Observer> list = getObservers().get(property);
		if (list == null) {
			list = new ArrayList<Observer>();
			getObservers().put(property, list);
		}
		list.add(observer);
	}
	
	public void removeObserver(Observer observer, String property) {
		ArrayList<Observer> list = getObservers().get(property);
		if (list == null)
			return;
		list.remove(observer);
		// maybe needs to be removed to increase performance
		if (list.isEmpty())
			getObservers().remove(property);
	}
	
	public HashMap<String, ArrayList<Observer>> getObservers() {
		return observers;
	}

	public void setObservers(HashMap<String, ArrayList<Observer>> observers) {
		this.observers = observers;
	}

	public void notifyObservers(String property, Object oldValue, Object newValue) {
		ArrayList<Observer> list = getObservers().get(property);
		if (list == null)
			return;
		for (int i = 0; i < list.size(); ++i) {
			list.get(i).update(this, property, oldValue, newValue);
		}
	}
	
}
