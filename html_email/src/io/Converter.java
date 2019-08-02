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
		ul();
		WIP = WIP.replace("\n\n", "</p><p>")
				.replaceAll("(?<!\n)\n(?!\n)", "<br>")
				.replaceAll("<p>(?=<[ou]l>)", "")
				.replaceAll("(?<=</[ou]l>)</p>", "");
		sb.append(WIP);
		sb.append("</p>");
		return sb.toString();
	}
	
	private void ol() {
		StringBuilder ol = new StringBuilder();
		int current;
		int start;
		int last_item = 0;
		int i;
		
		ol.append("<ol>");
		
		for(start = current = WIP.indexOf("1."), i = 1; WIP.indexOf(i + ".") >= 0  //Check if next index exists
														&& WIP.charAt(WIP.indexOf(i + ".") - 1) == '\n' //Check if next index is the start of the line
														&& ((i > 1)?countChar(WIP.substring(WIP.indexOf(i - 1 + "."), WIP.indexOf(i + ".")), '\n') == 1:true); //Check if there is only one 
																											//new line Character between the last index and this one if it's not the first index
													i++, current = WIP.indexOf(i + ".")) {
			ol.append("<li>");
			String item;
			try {
				item = WIP.substring(current + 2, WIP.indexOf('\n', current)).trim();				
			} catch(StringIndexOutOfBoundsException e) {
				try {
					item = WIP.substring(current + 2).trim();
				} catch(StringIndexOutOfBoundsException ex) {
					item = "";
				}
			}
			ol.append(item);
			ol.append("</li>");
			last_item = WIP.indexOf('\n', current);
		}
		ol.append("</ol>");
		
		if(last_item > 0) {
			WIP = WIP.replace(WIP.substring(start, last_item), ol.toString());
			ol();
		}
	}
	
	private void ul() {
		StringBuilder ul = new StringBuilder();
		int current, last_start_line = 0;
		int start;
		int last_item = 0;
		int i;
		
		ul.append("<ul>");
		
		for(start = current = WIP.indexOf("-"), i = 1; current >= 0 && WIP.indexOf("-", current) >= 0  //Check if next index exists
														&& WIP.charAt(WIP.indexOf("-", current) - 1) == '\n' //Check if next index is the start of the line
														&& ((i > 1)?countChar(WIP.substring(WIP.indexOf("-", last_start_line), WIP.indexOf("-", current)), '\n') == 1:true); //Check if there is only one 
																											//new line Character between the last index and this one if it's not the first index
													i++, current = WIP.indexOf("-", (last_start_line = current) + 1)) {
			ul.append("<li>");
			String item;
			try {
				item = WIP.substring(current + 1, WIP.indexOf('\n', current)).trim();				
			} catch(StringIndexOutOfBoundsException e) {
				try {
					item = WIP.substring(current + 1).trim();
				} catch(StringIndexOutOfBoundsException ex) {
					item = "";
				}
			}
			ul.append(item);
			ul.append("</li>");
			last_item = WIP.indexOf('\n', current);
		}
		ul.append("</ul>");
		
		if(last_item > 0) {
			WIP = WIP.replace(WIP.substring(start, last_item), ul.toString());
			ul();
		}
	}
	
	private int countChar(String str, char c)
	{
	    int count = 0;

	    for(int i=0; i < str.length(); i++)
	    {    if(str.charAt(i) == c)
	            count++;
	    }

	    return count;
	}
}
