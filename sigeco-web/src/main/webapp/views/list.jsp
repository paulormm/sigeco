<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<a4j:form>
				<%@ include file="/include/menu.jsp" %>
				
				<br />
				<br />
				
				<t:outputLabel for="user" styleClass="field">
					<t:outputText value="#{msgs['viewfeeds.form.user']}" />
				</t:outputLabel>
				
				<t:selectOneMenu
					id="user"
					value="#{viewMatrixFeedBean.selectedUser}"
					valueChangeListener="#{viewMatrixFeedBean.selectedUserChanged}">
					<f:selectItems value="#{viewMatrixFeedBean.users}" />
					<a4j:support event="onchange" id="userAjaxSupport" reRender="matricesGroup"></a4j:support>
				</t:selectOneMenu>
				
				<br />
				<br />
				<h:panelGroup id="matricesGroup">
					<rich:dataTable
						id="matrices" 
						value="#{viewMatrixFeedBean.matrices}"
						var="matrix"
						rowClasses="item, altitem"
						rendered="#{viewMatrixFeedBean.selectedUser != null && not empty viewMatrixFeedBean.matrices}"
						>
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column>
								</rich:column>
								<rich:column>
									<h:outputText value="#{msgs['viewfeeds.grid.matrix']}" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:columnGroup>
							<rich:column>
								<t:commandLink action="select" value="#{msgs['global.form.select']}">
									<t:updateActionListener value="#{matrix}" property="#{viewMatrixFeedBean.selectedMatrix}"></t:updateActionListener>
								</t:commandLink>
							</rich:column>
							<rich:column>
								<h:outputText value="#{matrix.name}" />
							</rich:column>
						</rich:columnGroup>
					</rich:dataTable>
					<br />
					
					<t:commandLink action="selectAll" value="#{msgs['matrices.list.show.all']}"
						rendered="#{viewMatrixFeedBean.selectedUser != null && not empty viewMatrixFeedBean.matrices}">
						<t:updateActionListener value="#{viewMatrixFeedBean.matrices}" property="#{viewMatrixFeedBean.renderableMatrices}"></t:updateActionListener>
					</t:commandLink>
					
					<h:outputText 
						value="#{msgs['viewfeeds.no.matrices']}" 
						rendered="#{viewMatrixFeedBean.selectedUser != null && empty viewMatrixFeedBean.matrices}" />
				</h:panelGroup>
			</a4j:form>
		</body>
	</f:view>
</html>
