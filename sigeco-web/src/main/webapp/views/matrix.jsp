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
				<br />
				<br />
				<rich:dataTable
					id="group" 
					value="#{viewMatrixFeedBean.selectedMatrix.knowledgeGroups}"
					var="group"
					rowClasses="item, altitem"
					rendered="#{viewMatrixFeedBean.selectedUser != null && not empty viewMatrixFeedBean.selectedMatrix}"
				>
					<f:facet name="header">
						<rich:columnGroup>
							<rich:column>
							</rich:column>
							<rich:column>
								<h:outputText value="#{msgs['matrices.form.knowledges.title']}" />
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					
					<rich:dataTable
					id="knowledge" 
					value="#{group.knowledges}"
					var="knowledge"
					
					
					>
					<rich:columnGroup>
						<rich:column>
							<t:commandLink action="voltar" value="Comentários">
				
							</t:commandLink>
						</rich:column>
						<rich:column>
							<h:outputText value="#{knowledge.name}" />
						</rich:column>
					</rich:columnGroup>
					</rich:dataTable>
					
				</rich:dataTable>
				
			</h:form>
		</body>
	</f:view>
</html>

