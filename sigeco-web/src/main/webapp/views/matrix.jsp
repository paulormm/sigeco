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
				
				<h:panelGrid columns="2">
					<h:outputText styleClass="field" value="#{msgs['viewfeeds.form.user']}" />
					<h:outputText styleClass="fieldValue" value="#{viewMatrixFeedBean.selectedUser.name}" />
					
					<h:outputText styleClass="field" value="#{msgs['viewfeeds.grid.matrix']}" />
					<h:outputText styleClass="fieldValue" value="#{viewMatrixFeedBean.selectedMatrix.name}" />
				</h:panelGrid>
				
				<br />
				<br />
				
				<rich:dataTable 
					binding="#{viewMatrixFeedBean.matrixDataTable}" 
					value="#{viewMatrixFeedBean.matrixRows}" var="row" >
				</rich:dataTable>
				
			</h:form>
		</body>
	</f:view>
</html>

