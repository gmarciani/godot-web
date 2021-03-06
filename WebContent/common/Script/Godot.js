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

$(function() {

	$( document ).tooltip();
	
	$( "button" ).button();	
	
	$( ".buttonSubscribe" ).button({
		icons: { primary: "ui-icon-pin-s" }
	});
	
	$( ".dialog" ).keypress(function(event) {				
		if(event.keyCode == '13'){	
			event.preventDefault();
		}
	});
	
	$( ".dialogMessage" ).dialog({
		autoOpen: false,
		draggable: false,
		resizable: false,
		modal: true,
		height: "200",
	    width: "250",
		show: {
			effect: "blind"
		},
		hide: {
			effect: "blind"
		},
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
			}
		}
	}); 
	
	$( ".dialogMessageReload" ).dialog({
		autoOpen: false,
		draggable: false,
		resizable: false,
		modal: true,
		height: "200",
	    width: "250",
		show: {
			effect: "blind"
		},
		hide: {
			effect: "blind"
		},
		buttons: {
			Ok: function() {
				$( this ).dialog( "close" );
				setTimeout(function() {
					location.reload();
				}, 1250);
			}
		}
	});
	
});