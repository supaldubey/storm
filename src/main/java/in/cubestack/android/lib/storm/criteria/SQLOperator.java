package in.cubestack.android.lib.storm.criteria;

public enum SQLOperator {

	LESS_THEN("<"), GREATER_THEN(">"), EQUALS("="), IN(" IN "), NOT_EQUAL("!=");

	private SQLOperator(String symbol) {
		this.symbol = symbol;
	}

	private String symbol;

	public String symbol() {
		return symbol;
	}

}
