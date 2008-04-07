<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<a4j:commandButton action="editGroup" actionListener="#{userGroupEditionBean.startNewGroupInclusion}" value="#{msgs['global.include.link']}"></a4j:commandButton>
				</rich:panel>
				<rich:dataTable id="groups" value="#{userGroupManagerBean.all}" var="group" rowClasses="item, altitem">
					<f:facet name="header">
						<rich:columnGroup>
							<rich:column rowspan="2" colspan="2">
							</rich:column>
							<rich:column>
								<h:outputText value="#{msgs['userGroups.detail.title']}" />
							</rich:column>
							<rich:column breakBefore="true">
								<h:outputText value="#{msgs['userGroups.detail.name']}"/>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					<rich:column>
						<a4j:commandLink action="editGroup" actionListener="#{userGroupEditionBean.startGroupEdition}" value="#{msgs['global.edit.link']}">
							<t:updateActionListener value="#{group}" property="#{userGroupEditionBean.chosenGroup}"></t:updateActionListener>
						</a4j:commandLink>
					</rich:column>
					<rich:column>
						<a4j:commandLink actionListener="#{userGroupEditionBean.removeGroup}" value="#{msgs['global.remove.link']}" reRender="groups">
							<t:updateActionListener value="#{group}" property="#{userGroupEditionBean.chosenGroup}"></t:updateActionListener>
						</a4j:commandLink>
					</rich:column>
					<rich:column>
						<h:outputText value="#{group.name}" />
					</rich:column>
				</rich:dataTable>
			</h:form>
		</body>
	</f:view>
</html>