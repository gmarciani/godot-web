/*  WebGodot: Godot Server-Side Implementation. 
    Copyright (C) 2013  Giacomo Marciani <giacomo.marciani@gmail.com>.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package helper;

public class TemplateViewer {
	
	private static TemplateViewer singletonTemplateViewer;
	
	private static String PATH_LOGO = "common/img/Godot.png";

	private TemplateViewer() {}
	
	public static synchronized TemplateViewer getInstance() {
		if(singletonTemplateViewer == null) {
			singletonTemplateViewer = new TemplateViewer();
		}
		
		return singletonTemplateViewer;
	}
	
	public String getLogo() {
		String html = new String();
		
		html += "<img class = \"logo\" id = \"logoGodot\" border = \"0\" src = \"" + PATH_LOGO + "\">";
		
		return html;
	}

}
