<%@ include file="/include/include.jsp" %>

<html>
<f:view>
<%@ include file="/include/head.jsp" %>
<body>
<h:form>
	<%@ include file="/include/menu.jsp" %>
				
	<br />
	<t:commandLink action="voltar" value="#{msgs['global.form.previous']}">
	</t:commandLink>
	<br />
	<br />
	
	<h:outputText styleClass="field" value="#{msgs['viewfeeds.form.user']}" />
	<h:outputText styleClass="fieldValue" value="#{viewMatrixFeedBean.selectedUser.name}" />
	<br />
	
	<h:outputText styleClass="field" value="#{msgs['viewfeeds.grid.matrices']}" />
	<br />
	
	<br />
	<h:outputText rendered="#{viewMatrixFeedBean.renderableMatricesSize > 0}" 
		styleClass="field" value="#{viewMatrixFeedBean.renderableMatrix0.matrixName}" />
	<rich:dataTable rendered="#{viewMatrixFeedBean.renderableMatricesSize > 0}"
		value="#{viewMatrixFeedBean.renderableMatrix0.matrixRows}"
		binding="#{viewMatrixFeedBean.renderableMatrix0.matrixDataTable}" var="row">
	</rich:dataTable>
	<br />
	<h:outputText rendered="#{viewMatrixFeedBean.renderableMatricesSize > 1}" 
		styleClass="field" value="#{viewMatrixFeedBean.renderableMatrix1.matrixName}" />
	<rich:dataTable rendered="#{viewMatrixFeedBean.renderableMatricesSize > 1}"
		value="#{viewMatrixFeedBean.renderableMatrix1.matrixRows}"
		binding="#{viewMatrixFeedBean.renderableMatrix1.matrixDataTable}" var="row">
	</rich:dataTable>
	<br />
	<h:outputText rendered="#{viewMatrixFeedBean.renderableMatricesSize > 2}" 
		styleClass="field" value="#{viewMatrixFeedBean.renderableMatrix2.matrixName}" />
	<rich:dataTable rendered="#{viewMatrixFeedBean.renderableMatricesSize > 2}"
		value="#{viewMatrixFeedBean.renderableMatrix2.matrixRows}"
		binding="#{viewMatrixFeedBean.renderableMatrix2.matrixDataTable}" var="row">
	</rich:dataTable>
	<br />
	<h:outputText rendered="#{viewMatrixFeedBean.renderableMatricesSize > 3}" 
		styleClass="field" value="#{viewMatrixFeedBean.renderableMatrix3.matrixName}" />
	
	</h:form>
</body>
</f:view>
</html>