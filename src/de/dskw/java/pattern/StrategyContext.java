package de.dskw.java.pattern;

public abstract class StrategyContext {
	private Strategy strategy;
	
	public StrategyContext(Strategy strategy) {
		this.setStrategy(strategy);
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void executeStrategy() {
		this.getStrategy().execute();
	}
}
