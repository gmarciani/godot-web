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