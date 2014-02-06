<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<TITLE>Datagrid - Book Listing</TITLE>
</HEAD>
<BODY>
<P>List books.</P>

<layout:html>
	<layout:form action="/datagrid"  reqCode="update" styleClass="PANEL">
	<div>
		<layout:datagrid property="datagrid" selectionAllowed="true" multipleSelectionAllowed="false" model="datagrid">
			<layout:datagridColumn title="ID" property="id"/>
			<layout:datagridColumn title="Code" property="code"/>			
			<layout:datagridColumn title="Name(EN)" property="nameEN"/>			
			<layout:datagridColumn title="Name(GB)" property="nameGB"/>			
		</layout:datagrid>
				
		<span>
			<layout:button onclick="StrutsLayout.addDatagridLine('datagrid')">Add</layout:button>
			<layout:button onclick="StrutsLayout.setDatagridLineState('datagrid', 'removed')">Remove</layout:button>
			<layout:submit reqCode="update" >Save</layout:submit>
		</span>

	</div>

	<div>
		<layout:collection property="books" id="BibleBook" styleClass="ARRAY">
			<layout:collectionItem title="ID" property="id"/>
			<layout:collectionItem title="Code" property="code"/>
			<layout:collectionItem title="Name(EN)" property="nameEN"/>
		</layout:collection>
	</div>

	</layout:form>
</layout:html>

</BODY>
</html:html>

