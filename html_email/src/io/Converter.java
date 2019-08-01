package io;

public class Converter {

	private String WIP;
	
	public Converter(String source) {
		this.WIP = source;
	}
	
	public String convertTextToHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>");
		
		ol();
		WIP = WIP.replace("\n\n", "</p><p>")
				.replaceAll("(?<!\n)\n(?!\n)", "<br>");
		sb.append(WIP);
		sb.append("</p>");
		return sb.toString();
	}
	
	private boolean ol() {
		StringBuilder ol = new StringBuilder();
		int current;
		int start;
		int last_item = 0;
		int i;
		
		ol.append("<ol>");
		
		for(start = current = WIP.indexOf("1."), i = 1; WIP.indexOf(i + ".") >= 0 && WIP.charAt(WIP.indexOf(i + ".") - 1) == '\n'; i++, current = WIP.indexOf(i + ".")) {
			ol.append("<li>");
			String item = WIP.substring(current + 2, WIP.indexOf('\n', current)).trim();
			ol.append(item);
			ol.append("</li>");
			last_item = WIP.indexOf('\n', current);
		}
		ol.append("</ol>");
		
		if(last_item > 0) {
			WIP = WIP.replace(WIP.substring(start, last_item), ol.toString());
			ol();
		}
		return false;
	}
}
