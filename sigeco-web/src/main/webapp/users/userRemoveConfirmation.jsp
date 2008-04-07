<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel id="removeConfirm">
			<f:facet name="header">
				<h:outputText value="#{msgs['users.form.confirm.remove']}" />
			</f:facet>
			<h:panelGrid columns="2">
				<h:outputText value="#{msgs['users.form.details.name']}" />
				<h:outputText value="#{userEditionBean.userOnFocus.name}" />
				<a4j:commandButton value="#{msgs['global.remove.link']}" actionListener="#{userEditionBean.remove}" reRender="users" onclick="javascript:Richfaces.hideModalPanel('userRemoval');" />
				<a4j:commandButton value="#{msgs['global.form.cancel']}" reRender="users" onclick="javascript:Richfaces.hideModalPanel('userRemoval');" />
			</h:panelGrid>
		</rich:panel> 
	</h:form>
</body>
