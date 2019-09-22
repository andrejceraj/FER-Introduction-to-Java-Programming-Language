package hr.fer.zemris.java.hw17;

import java.text.DecimalFormat;

public class SearchResult {
	private double coeficient;
	private String filePath;

	public SearchResult() {
	}

	public SearchResult(double coeficient, String filePath) {
		this.coeficient = coeficient;
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public double getCoeficient() {
		return coeficient;
	}

	public void setCoeficient(double coeficient) {
		this.coeficient = coeficient;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat format = new DecimalFormat("0.0000");
		sb.append('(').append(format.format(coeficient)).append(')');
		sb.append(" ").append(filePath);
		return sb.toString();
	}

}
