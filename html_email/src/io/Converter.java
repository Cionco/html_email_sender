package io;

public class Converter {

	private String original;
	private String WIP;
	
	public Converter(String source) {
		this.original = this.WIP = source;
	}
	
	public String convertTextToHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		
		WIP = WIP.replace("\n\n", "</p><p>")
				.replaceAll("(?<=[^\n])\n(?=[^\n])", "<br>");
		sb.append(WIP = WIP.replace("\n\n", "</p><p>"));
		sb.append("</p>");
		return sb.toString();
	}
}
