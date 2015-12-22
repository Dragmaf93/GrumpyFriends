<%@page import="com.fasterxml.jackson.databind.JsonNode,project.*,project.beans.*,com.fasterxml.jackson.*,com.fasterxml.jackson.databind.ObjectMapper, java.text.DateFormat,java.text.SimpleDateFormat,java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% boolean menuBool = false; int cont = 0;%>
<link rel="stylesheet" type="text/css" href="../css/image.css">
<link rel="stylesheet" type="text/css" href="../css/mensaCR.css">
<title>Mensa Centro Residenziale</title>

<ul id="menu">
	<li><a href="index.html">Home</a></li>
	<li><a href="map.html">Map</a></li>
	<li><a href="#">Altro</a></li>
</ul>


<div id="slideshow">
   <div>
     <img src="../images/mensaCR/image1.jpg" width="900" height="350">
   </div>
   <div>
     <img src="../images/mensaCR/image2.jpg" width="900" height="350">
   </div>
   <div>
     <img src="../images/mensaCR/image3.jpg" width="900" height="350">
   </div>
</div>

<script type="text/javascript" src=" http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	$("#slideshow > div:gt(0)").hide();

	setInterval(function() { 
	  $('#slideshow > div:first')
	    .fadeOut(1000)
	    .next()
	    .fadeIn(1000)
	    .end()
	    .appendTo('#slideshow');
	},  3000);
</script>


<ul id="menuDishes">
	<li onclick="changeVisibilityPanelMenu()" id="dayMenu">Day Menu</li>
	<li onclick="changeVisibilityPanelMenu()" id="choiseDishes">Choise Dishes</li>
	<li onclick="changeVisibilityPanelMenu()" id="bestDishes">Best Dishes</li>
</ul>

<div id="panelForMenu">
	<img src="../images/forMenuUp.png">
	<div class="quad"></div>
	<div class="closeButton"><img src="../images/closeButton.png"></div>
</div>


<script type="text/javascript">
	function changeVisibilityPanelMenu() {
		$("#panelForMenu").toggle();
		<% menuBool = true; cont++; loadImage();%>
	}
	<%-- function changeVisibilityPanelMenuInit() {
		$("#panelForMenu").toggle();
		<% menuBool = false; %>
	} --%>
</script>
		<!-- $("#panelForMenu").append("<div class=\"row\" id=\"bestDishesWrapper\">	<div id=dailyMenu>") -->

<% System.out.println(cont); %>

<%! public void loadImage() {%>
	<div class="row" id="menuDishes">	
	<div id=dailyMenu>
	
	<%
	if (cont > 1)
	{
	System.out.println(menuBool+" "+cont);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    Date date = new Date();

	    String dailyMenu = JsonDBManager.getInstance().getMenuDaily(dateFormat.format(date));
		JsonNode arrNode = new ObjectMapper().readTree(dailyMenu);
		if (arrNode.isArray())
		{
			for (final JsonNode node: arrNode)
			{
				/* String dishName = objNode.get("name").asText(); */
				String urlImage = node.get("image_url").asText();
				/* int dishID = objNode.get("id").asInt(); */
				
		%>
	<%-- 	$("#panelForMenu").append("<div><img id=\"immagine\" src=\""+<%=urlImage%>+"\" /></div>"); --%>
		
		<div><img id="immagine" src="<%=urlImage%>"/></div>
		
		<%
			}
		}
		}%>
	<%! } %>
<!-- 	$("#panelForMenu").append("</div></div>"); -->

	</div>
</div>



