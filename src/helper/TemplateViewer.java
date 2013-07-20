/*  WebGodot: Godot Server-Side Implementation. 
    
   Copyright 2013 Giacomo Marciani <giacomo.marciani@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   
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
