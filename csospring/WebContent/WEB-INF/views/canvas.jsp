<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title><fmt:message key="canvas.title"/></title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link rel="stylesheet" href="<c:url value="/resources/css/screen.css" />" type="text/css" media="screen, projection">
	<link rel="stylesheet" href="<c:url value="/resources/css/print.css" />" type="text/css" media="print">
	<link rel="stylesheet" href="<c:url value="/resources/css/popup.css" />" type="text/css" media="print">
	<script src="<c:url value="/resources/js/jquery-1.7.2.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.maskedinput-1.3.min.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.jec-1.3.4.js" />" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/module.js" />" type="text/javascript"></script>
</head>
<body>
  <h1>${message} </h1>
  
  <img id="frontImage" src="/spring3/resources/images/default-front.jpg" alt="Front" width="300" height="300" hidden="true" />
	
  <canvas id="frontCanvas"  width="300" height="300" style="border:1px solid #d3d3d3;" ></canvas>

  <script>

	var c=document.getElementById("frontCanvas");
	var ctx=c.getContext("2d");
	ctx.beginPath();
	var img=document.getElementById("frontImage");
	ctx.drawImage(img,2,2);
	ctx.font="30px Arial";
	//ctx.moveTo(145,120);
	ctx.fillText("8",145,120);
	//ctx.moveTo(140,145);
	ctx.strokeText("10",140,145);
	
	ctx.arc(145,140,40,0,2*Math.PI);
	ctx.stroke();

	function drawText() {
		
		/*
		var svalue = "";
		if(!sels) return "";
		 var radioLength = sels.length;
		 if(radioLength == undefined) {
			if(sels.checked) svalue = sels.value;
	     } else {	
	    	 for(var i = 0; i < radioLength; i++) {
				if(sels[i].checked) {
					svalue = sels[i].value;
					break;
				}
			}
	     }
		 */
		 
	}
	
	
  </script>

  <center> 

  <table id="frontDesign" style="width: 300; height: 300; background-image:url('/spring3/resources/images/default-front.jpg'); border: 0">

	<tr><td id="f11" style="width: 100; height: 100; text-align: right; vertical-align: bottom;">LOGO11</td>
		<td id="f12" style="width: 100; height: 100; text-align: center; vertical-align: bottom;">LOGO12</td>
		<td id="f13" style="width: 100; height: 100; text-align: left; vertical-align: bottom;">LOGO13</td>
	</tr>  
	
	<tr><td id="f21" style="width: 100; height: 100; text-align: right; vertical-align: middle; font-size: small;">#21</td>
		<td id="f22" style="width: 100; height: 100; text-align: center; vertical-align: middle; font-size: medium;">LOGO22</td>
		<td id="f23" style="width: 100; height: 100; text-align: left; vertical-align: middle; font-size: large;">#23</td>
	</tr>  

	<tr><td id="f31" style="width: 100; height: 100; text-align: right; vertical-align: top; font-size: x-large;">#31</td>
		<td id="f32" style="width: 100; height: 100; text-align: center; vertical-align: top; font-size: xx-large;">#32 </td>
		<td id="f33" style="width: 100; height: 100; text-align: left; vertical-align: top; font-size: x-small;">#33</td>
	</tr>  

  </table> 

  <table id="rearDesign" style="width: 300; height: 300; background-image:url('/spring3/resources/images/default-rear.jpg'); border: 0">

	<tr><td id="r11" style="width: 100; height: 100; text-align: right; vertical-align: bottom;">LOGO11</td>
		<td id="r12" style="width: 100; height: 100; text-align: center; vertical-align: bottom;">LOGO12</td>
		<td id="r13" style="width: 100; height: 100; text-align: left; vertical-align: bottom;">LOGO13</td>
	</tr>  
	
	<tr><td id="r21" style="width: 100; height: 100; text-align: right; vertical-align: middle;">#21</td>
		<td id="r22" style="width: 100; height: 100; text-align: center; vertical-align: middle;">LOGO22</td>
		<td id="r23" style="width: 100; height: 100; text-align: left; vertical-align: middle;">#23</td>
	</tr>  

	<tr><td id="r31" style="width: 100; height: 100; text-align: right; vertical-align: top;">#31</td>
		<td id="r32" style="width: 100; height: 100; text-align: center; vertical-align: top;">#32 </td>
		<td id="r33" style="width: 100; height: 100; text-align: left; vertical-align: top;">#33</td>
	</tr>  

  </table> 

  <table id="shortDesign" style="width: 300; height: 300; background-image:url('/spring3/resources/images/default-short.jpg'); border: 0">

	<tr><td id="s11" style="width: 100; height: 100; text-align: right; vertical-align: bottom;">LOGO11</td>
		<td id="s12" style="width: 100; height: 100; text-align: center; vertical-align: bottom;">LOGO12</td>
		<td id="s13" style="width: 100; height: 100; text-align: left; vertical-align: bottom;">LOGO13</td>
	</tr>  
	
	<tr><td id="s21" style="width: 100; height: 100; text-align: right; vertical-align: middle;">#21</td>
		<td id="s22" style="width: 100; height: 100; text-align: center; vertical-align: middle;">LOGO22</td>
		<td id="s23" style="width: 100; height: 100; text-align: left; vertical-align: middle;">#23</td>
	</tr>  

	<tr><td id="s31" style="width: 100; height: 100; text-align: right; vertical-align: top;">#31</td>
		<td id="s32" style="width: 100; height: 100; text-align: center; vertical-align: top;">#32 </td>
		<td id="s33" style="width: 100; height: 100; text-align: left; vertical-align: top;">#33</td>
	</tr>  

  </table> 

  
  </center>
</body>
</html>