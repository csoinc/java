<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-view.tld" prefix="view"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- HEAD -->
<tiles:get name="begin" />
<!-- /HEAD -->

</head>

<body>

<div id="page_wrapper">

<div id="header_wrapper">

<div id="header">

<h1>Better<font color="#FFDF8C">Together</font></h1>
<h2>Blogger/Forum</h2>

</div>

<div id="navcontainer"><!-- TITLE --> <tiles:get name="title" /> <!-- /TITLE --></div>

</div>

<div id="left_side">
<!-- left --> <tiles:get name="left" /> <!-- /left -->

</div>

<div id="right_side">
<!-- right --> <tiles:get name="right" /> <!-- /right -->

</div>

<div id="content"><tiles:get name="top" />

<div class='featurebox_center'>
所以你们要去，使万民作我的门徒，奉父子圣灵的名，给他们施洗。（或作给他们施洗归于父子圣灵的名）凡我所吩咐你们的，都教训他们遵守，我就常与你们同在，直到世界的末了。 
(馬太28:19-20)   
</div>

<p><tiles:get name="content" /></p>

<p><!-- jsp:include page="/gwt/org.oyosoft.gwt.popup.Popup/Popup.html" flush="true" /></p -->
<p><layout:img srcName="icon-leaf.png" border="0" />
门徒互动网站</p>
</div>

<div id="footer"><!-- END --> <tiles:get name="end" /> <!-- /END --></div>

</div>

</body>
<script>
    CKEDITOR.replace( 'message' );
</script>

</html>



