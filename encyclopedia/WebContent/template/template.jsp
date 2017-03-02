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

<h1>E<font color="#FFDF8C">ncyclopedia</font></h1>
<h2>Blogger/Forum&nbsp;<a href="/blogger/scripts/com.oyou.gwt.blog.Blog/Blog.html"><font color="#FFDF8C">Web2.0</font></a>
</h2>

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

<div class='featurebox_center'>This is CSOINC Encyclopedia Technologies site.</div>

<p><tiles:get name="content" /></p>

<p><!-- jsp:include page="/gwt/org.oyosoft.gwt.popup.Popup/Popup.html" flush="true" /></p -->
<p><layout:img srcName="icon-leaf.png" border="0" />
Encyclopedia 知识宝库 百科全书</p>
</div>

<div id="footer"><!-- END --> <tiles:get name="end" /> <!-- /END --></div>

</div>

</body>

<script>
    CKEDITOR.replace( 'message' );
    CKEDITOR.replace( 'description' );

</script>
</html>

