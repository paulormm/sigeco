<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<a4j:commandButton id="cadastrar" rendered="#{permissionBean.admin}" value="#{msgs['users.form.link']}" action="editUser" actionListener="#{userEditionBean.startUserInclusion}" />
				</rich:panel>
				<rich:dataTable id="users"
					var="userRow" 
					value="#{userListBean.list}" 
					rowClasses="item, altitem">
					<f:facet name="header">
						<rich:columnGroup>
							<rich:column rowspan="2" colspan="2">
							</rich:column>
							<rich:column colspan="6">
								<t:outputText value="#{msgs['users.list.title']}" />
							</rich:column>
							<rich:column breakBefore="true">
								<t:outputText value="#{msgs['users.detail.id']}" />
							</rich:column>
							<rich:column>
								<h:outputText value="#{msgs['users.detail.name']}"></h:outputText>
							</rich:column>
							<rich:column>
								<h:outputText value="#{msgs['users.detail.username']}"></h:outputText>
							</rich:column>
							<rich:column>
								<h:outputText value="#{msgs['users.detail.email']}"></h:outputText>
							</rich:column>
							<rich:column>
							</rich:column>
							<rich:column>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					<rich:columnGroup>
						<rich:column>
							<t:commandLink rendered="#{permissionBean.admin}" action="editUser" value="#{msgs['global.edit.link']}">
								<t:updateActionListener value="#{userRow}" property="#{userEditionBean.userOnFocus}"></t:updateActionListener>
							</t:commandLink>
						</rich:column>
						<rich:column>
							<a4j:commandLink id="removeUser" rendered="#{permissionBean.admin}" value="#{msgs['global.remove.link']}" onclick="javascript:Richfaces.showModalPanel('userRemoval',{width:800, height:600, top:50});" reRender="removeConfirm">
								<t:updateActionListener value="#{userRow}" property="#{userEditionBean.userOnFocus}"></t:updateActionListener>
							</a4j:commandLink>
						</rich:column>
						<rich:column>
							<t:outputText value="#{userRow.id}" />
						</rich:column>
						<rich:column>
							<t:commandLink id="showProfile" action="showProfile" value="#{userRow.name}" >
								<t:updateActionListener value="#{userRow}" property="#{userEditionBean.userOnFocus}"></t:updateActionListener>
							</t:commandLink>
						</rich:column>
						<rich:column>
							<t:outputText value="#{userRow.username}" />
						</rich:column>
						<rich:column>
							<t:outputText value="#{userRow.email}" />
						</rich:column>
						<rich:column>
							<t:commandLink action="user list" immediate="true" value="#{msgs['users.detail.matrices']}" />
						</rich:column>
						<rich:column>
							<t:commandLink id="editProfile" action="editProfile" immediate="true" value="#{msgs['users.detail.profile.edit']}" >
								<t:updateActionListener value="#{userRow}" property="#{userEditionBean.userOnFocus}"></t:updateActionListener>
							</t:commandLink>
						</rich:column>
					</rich:columnGroup>
				</rich:dataTable>
			</h:form>
			
			<rich:modalPanel id="userRemoval">
				<f:facet name="header">
					
				</f:facet>
				<a4j:include viewId="/users/userRemoveConfirmation.jsp"></a4j:include>
			</rich:modalPanel>
		</body>
	</f:view>
</html>