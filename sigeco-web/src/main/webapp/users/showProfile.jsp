<%@ include file="/include/include.jsp" %>

<html>
<f:view>
<%@ include file="/include/head.jsp" %>
<body>
<h:form>
<%@ include file="/include/menu.jsp" %>
	<a4j:outputPanel ajaxRendered="true">
		<rich:messages />
	</a4j:outputPanel>
	<rich:panel>
		<f:facet name="header">
			<h:panelGrid columns="1">
				<a4j:commandButton id="Voltar" action="restart" immediate="true" value="#{msgs['global.form.back']}"/>			
			</h:panelGrid>
		</f:facet>
		<a4j:region>
						
			
			<h:panelGrid columns="2">
			
											
				<h:outputLabel for="address" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.address']}" />
				</h:outputLabel>
				<h:outputText id="address" value="#{userEditionBean.userOnFocus.address}"/>
				
				<h:outputLabel for="homePhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.homePhone']}" />
				</h:outputLabel>
				<h:outputText id="homePhone" value="#{userEditionBean.userOnFocus.homePhone}"/>
				
				<h:outputLabel for="businessPhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.businessPhone']}" />
				</h:outputLabel>
				<h:outputText id="businessPhone" value="#{userEditionBean.userOnFocus.businessPhone}"/>
				
				<h:outputLabel for="cellPhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.cellPhone']}" />
				</h:outputLabel>
				<h:outputText id="cellPhone" value="#{userEditionBean.userOnFocus.cellPhone}"/>
				
				<h:outputLabel for="secondEmail" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.secondEmail']}" />
				</h:outputLabel>
				<h:outputText id="secondEmail" value="#{userEditionBean.userOnFocus.secondEmail}"/>
				
				
				<h:outputLabel for="birthDate" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.birthDate']}" />
				</h:outputLabel>
				<h:outputText id="birthDate" value="#{userEditionBean.userOnFocus.birthDate}"/>
				
				
				<h:outputLabel for="lattesLink" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.lattesLink']}" />
				</h:outputLabel>
				<h:outputText id="lattesLink" value="#{userEditionBean.userOnFocus.lattesLink}"/>
				
				<h:outputLabel for="photo" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.photo']}" />
				</h:outputLabel>
				<h:outputText id="photo" value="#{userEditionBean.userOnFocus.photo}"/>
				
				<h:outputLabel for="selfDescription" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.selfDescription']}" />
				</h:outputLabel>
				<h:outputText id="selfDescription" value="#{userEditionBean.userOnFocus.selfDescription}"/>
				
			
			</h:panelGrid>
		</a4j:region>
	</rich:panel>
</h:form>
</body>
</f:view>
</html>