<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<rich:messages></rich:messages>
					<a4j:commandButton action="backToList" actionListener="#{userGroupEditionBean.saveUserGroup}" value="#{msgs['global.form.save']}"></a4j:commandButton>
					<a4j:commandButton action="backToList" value="#{msgs['global.form.cancel']}"></a4j:commandButton>
					
					<rich:panel>
						<h:panelGrid columns="2">
							<h:outputText value="#{msgs['userGroups.form.name']}" />
							<t:inputText value="#{userGroupEditionBean.chosenGroup.name}" />
						</h:panelGrid>
					</rich:panel>
					<h:panelGrid columns="2">
						<rich:panel>
							<f:facet name="header">
								<h:outputText value="#{msgs['userGroups.form.managers']}" />
							</f:facet>
							<h:outputText value="#{msgs['userGroups.instructions.addManager']}"/>
							<rich:listShuttle id="managerShuttle" orderControlsVisible="false" 
								fastOrderControlsVisible="false" moveControlsVisible="false"
								copyControlLabel="#{msgs['global.include.link']}" copyAllControlLabel="#{msgs['global.includeAll.link']}"
								removeControlLabel="#{msgs['global.remove.link']}" removeAllControlLabel="#{msgs['global.removeAll.link']}"
								sourceCaptionLabel="#{msgs['userGroups.form.allManagers']}" targetCaptionLabel="#{msgs['userGroups.form.managers']}"
								sourceValue="#{userGroupEditionBean.managers}" targetValue="#{userGroupEditionBean.chosenGroup.managers}" 
								var="manager" listsHeight="100" switchByClick="true" converter="userConverter">
								<rich:column>
									<h:outputText value="#{manager.name}" />
								</rich:column>
							</rich:listShuttle>
						</rich:panel>
						<rich:panel>
							<f:facet name="header">
								<h:outputText value="#{msgs['userGroups.form.users']}" />
							</f:facet>
							<h:outputText value="#{msgs['userGroups.instructions.addUser']}"/>
							<rich:listShuttle id="userShuttle" orderControlsVisible="false" 
								fastOrderControlsVisible="false" moveControlsVisible="false" 
								copyControlLabel="#{msgs['global.include.link']}" copyAllControlLabel="#{msgs['global.includeAll.link']}"
								removeControlLabel="#{msgs['global.remove.link']}" removeAllControlLabel="#{msgs['global.removeAll.link']}"
								sourceCaptionLabel="#{msgs['userGroups.form.allUsers']}" targetCaptionLabel="#{msgs['userGroups.form.users']}"
								sourceValue="#{userGroupEditionBean.users}" targetValue="#{userGroupEditionBean.chosenGroup.users}" 
								var="user" listsHeight="100" switchByClick="true" converter="userConverter">
								<rich:column>
									<h:outputText value="#{user.name}" />
								</rich:column>
							</rich:listShuttle>
						</rich:panel>
					</h:panelGrid>
				</rich:panel>
			</h:form>
		</body>
	</f:view>
</html>