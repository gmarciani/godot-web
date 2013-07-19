<!--  
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
 -->

<%@ page language = "java" contentType = "text/html; charset=ISO-8859-1" pageEncoding = "ISO-8859-1" %>

<%@ page import = "helper.TemplateViewer" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

	<head>
	
		<meta http-equiv = "Content-Type" content = "text/html; charset = ISO-8859-1">
				
		<title>Godot</title>	
		
		<link href = "common/img/favicon.ico" type = "image/x-icon" rel = "icon"/>
		<link href = "common/img/favicon.ico" type = "image/x-icon" rel = "shortcut icon"/>	
			
		<script src = "common/Script/jquery-ui/js/jquery-1.9.1.js" type = "text/javascript"></script>
		<script src = "common/Script/jquery-ui/js/jquery-ui-1.10.3.custom.js" type = "text/javascript"></script>
		<link  href = "common/Script/jquery-ui/css/ui-lightness/jquery-ui-1.10.3.custom.css" type = "text/css" rel = "stylesheet">	
		
		<script src = "common/Script/Godot.js" type = "text/javascript"></script>		
		<link href = "common/css/Godot.css" type = "text/css" rel = "stylesheet">					
  		
	</head>
	
	<body>
	
		<div class = "panelContainer">
	
		<% 
			TemplateViewer templateViewer = TemplateViewer.getInstance();
		%>
	
		<div class = "panelLogoSubscribe" id = "panelLogo" align = "center">
		
			<%=
				templateViewer.getLogo()
			%>
			
		</div>		
		
		<div class = "panelMainSubscribe" id = "panelSubscribe" align = "center">
			
				<p><input class = "text ui-widget-content ui-corner-all" id = "name" name = "name" type = "text" placeholder = "Name"/></p>
				
				<p><input class = "text ui-widget-content ui-corner-all" id = "mail" name = "mail" type = "text" placeholder = "Mail"/></p>
				
				<p><button class = "buttonSubscribe buttonIconLabel" id = "buttonSubscribe" type = "button">Subscribe</button></p>
		
		</div>	
		
		</div>
		
		<div class = "dialog dialogMessage popup" id = "messageSuccessSubscribe" title = "Godot">
		
  			<p><span class = "ui-icon ui-icon-circle-check" style = "float: left; margin: 0 7px 50px 0;"></span>
    			<b>Perfect!</b> Your registration has been successful
  			</p>
  			
		</div>			
		
		<div class = "dialog dialogMessage popup" id = "messageFailureSubscribe" title = "Godot">
		
  			<p><span class = "ui-icon ui-icon-alert" style = "float: left; margin: 0 7px 50px 0;"></span>
    			<b>Ooops!</b> Something went wrong during your subscription!
  			</p>
  			
		</div>
		
		<script>
		
			$(function() {
				
				var panelLogo = $( "#panelLogo" );
				var panelSubscribe = $( "#panelSubscribe" );
				
				var name = $( "#panelSubscribe #name" );
				var mail = $( "#panelSubscribe #mail" );
				
				var fields = $( [] ).add(name).add(mail);
				
				var buttonSubscribe = $( "#panelSubscribe #buttonSubscribe" );
				
				var messageSuccessSubscribe = $( "#messageSuccessSubscribe" );
				var messageFailureSubscribe = $( "#messageFailureSubscribe" );
		
				function openingAnimation() {					
					panelLogo.hide();
					panelSubscribe.hide();
					
					panelLogo.show( "drop", {direction : "up", easing: "easeOutBounce", duration: 1500}, function() {
						panelSubscribe.show( "blind", {direction : "up", easing: "easeInOutExpo", duration: 1500});
					});					
				}
				
				function closingAnimation() {				
					panelSubscribe.hide( "blind", {direction : "up", easing: "easeInOutExpo"}, function() {
						panelLogo.hide( "drop", {direction : "up", easing: "easeOutBounce", duration: 1500});
					});				
				}
				
				$( window ).load(function() {	
					openingAnimation();	
				});
				
				function goTo(address) {				
					panelSubscribe.hide( "drop", {direction : "left", easing: "swing"}, function() {
						panelLogo.hide( "drop", {direction : "up", easing: "easeOutBounce", duration: 1500}, function() {
							window.location = address;
						});
					});				
				}			
				
				buttonSubscribe.click( function() {						
					cleanError(fields);
					subscribe();				
				});
				
				fields.keypress(function(event){
					if(event.keyCode == '13'){
						buttonSubscribe.click();
						return false;
					}			 
				});
					
				function cleanError( targetField ) {				
					targetField.removeClass( "ui-state-error" );				
				}
				
				function showError( targetField ) {				
					targetField.addClass( "ui-state-error" );				
				}
				
				function checkLength( targetField, targetParent, min, max ) {				
		    	      if ( targetField.val().length > max || targetField.val().length < min ) {	    	    	  
		    	    	  	targetField.addClass( "ui-state-error" );		 
		    	    	  	targetParent.effect( "shake" );
		    	        	return false;	    	        	
		    	      } else {		    	    	  
		    	        return true;	    	        
		    	      }	    	      
		    	}
				
				function subscribe() {
					var valid = true;
					
					valid = valid && checkLength(name, panelSubscribe, 1, 45);
					valid = valid && checkLength(mail, panelSubscribe, 5, 45);
					
					if ( valid ) {
						var mName = name.val();
						var mMail = mail.val();
						
						$.post("http://localhost:8080/WebGodot/Subscribe", {name: mName, mail : mMail})
						.done(function() {
							messageSuccessSubscribe.dialog( "open" );										
						})
						.fail(function(data) {
							messageFailureSubscribe.dialog( "open" );						
						});
						
					}				
				}
				
			});						
	
		</script>
	
	</body>
	
</html>