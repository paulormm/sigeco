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
		<rich:dataTable 
			id="matrices" 
			value="#{viewMatrixFeedBean.renderableMatrices}"
			var="matrix" 
			rendered="#{viewMatrixFeedBean.selectedUser != null && not empty viewMatrixFeedBean.renderableMatrices}">
			<f:facet name="header">
				<rich:columnGroup>
					<rich:column colspan="2">
						<h:outputText value="#{msgs['viewfeeds.grid.matrix']}" />
					</rich:column>
				</rich:columnGroup>
			</f:facet>
			<rich:columnGroup>
				<rich:column>
					<h:outputText value="#{matrix.matrixName}" />
				</rich:column>
				<rich:column>
				
				<rich:dataTable 
					binding="#{viewMatrixFeedBean.matrixDataTable}" 
					value="#{viewMatrixFeedBean.matrixRows}" var="row" >
				</rich:dataTable>
				
				</rich:column>
			</rich:columnGroup>
		</rich:dataTable>
		
	</h:form>
</body>
</f:view>
</html>