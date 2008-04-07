<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<f:facet name="header">
						<t:outputText value = "#{msgs['matrices.list.title']}" />
					</f:facet>				
					<t:outputLabel for="user" styleClass="field">
						<t:outputText value="#{msgs['feeds.form.selectUser']}" />
					</t:outputLabel>
					
					<t:selectOneMenu
						id="user" required="true"
						value="#{matrixFeederBean.selectedUser}">
						<f:selectItems value="#{matrixFeederBean.users}" />
					</t:selectOneMenu>
					<t:message for="user" />
					<rich:spacer height="30px" />	
					<rich:dataTable id="matrizes"
						var="matrixRow" 
						value="#{matrixManagerBean.all}" 
						rowClasses="item, altitem">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column rowspan="2">
								</rich:column>
								<rich:column colspan="2">
									<t:outputText value="#{msgs['matrices.list.title']}" />
								</rich:column>
								<rich:column breakBefore="true">
									<h:outputText value="#{msgs['matrices.detail.name']}"></h:outputText>
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:columnGroup>
							<rich:column>
								<t:commandLink action="select" value="#{msgs['global.form.select']}">
									<t:updateActionListener value="#{matrixRow}" property="#{matrixFeederBean.matrixOnFocus}"></t:updateActionListener>
								</t:commandLink>
							</rich:column>
							<rich:column>
								<t:outputText value="#{matrixRow.name}" />
							</rich:column>
						</rich:columnGroup>
					</rich:dataTable>
				</rich:panel>
			</h:form>
		</body>
	</f:view>
</html>
