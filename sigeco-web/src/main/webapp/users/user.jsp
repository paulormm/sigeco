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
			<h:panelGrid columns="2">
				<a4j:commandButton action="restart" actionListener="#{userEditionBean.save}" value="#{msgs['global.form.finish']}" />
				<a4j:commandButton action="restart" immediate="true" value="#{msgs['global.form.cancel']}"/>			
			</h:panelGrid>
		</f:facet>
		<a4j:region>
			<h:panelGrid columns="3">
				<h:outputLabel for="name" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.name']}" />
				</h:outputLabel>
				<h:inputText id="name" value="#{userEditionBean.userOnFocus.name}"
					required="true" />
				<h:message for="name" styleClass="error" />
				
				<h:outputLabel for="username" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.username']}" />
				</h:outputLabel>
				<h:inputText id="username" value="#{userEditionBean.userOnFocus.username}"
					required="true" />
				<h:message for="username" styleClass="error" />
				
				<h:outputLabel for="email" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.email']}" />
				</h:outputLabel>
				<h:inputText id="email" value="#{userEditionBean.userOnFocus.email}"
					required="true" />
				<h:message for="email" styleClass="error" />
				
				<h:outputLabel for="password" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.password']}" />
				</h:outputLabel>
				<h:inputSecret id="password" value="#{userEditionBean.password}"
					required="true" />
				<h:message for="password" styleClass="error" />
				
				<h:outputLabel for="permissionType" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.permission.general']}" />
				</h:outputLabel>
				<h:selectOneRadio id="permissionType" layout="lineDirection" value="#{userEditionBean.permissionType}">
					<f:selectItem itemValue="SPECIAL" itemLabel="#{msgs['users.form.details.permission.special']}" />
					<f:selectItem itemValue="GENERAL" itemLabel="#{msgs['users.form.details.permission.general']}" />
					<a4j:support id="permTpSup" event="onchange" reRender="permissionGrid" />
				</h:selectOneRadio>
				<h:message for="permissionType" styleClass="error" />
			</h:panelGrid>
			
			<h:panelGrid id="permissionGrid" columns="3">
				<h:outputLabel for="permission" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.permission.general']}" />
				</h:outputLabel>
				<h:selectManyCheckbox id="permission" disabled="#{userEditionBean.permissionType == 'SPECIAL'}" value="#{userEditionBean.permission}">
					<f:selectItem itemValue="USER" itemLabel="#{msgs['global.security.user']}" />
					<f:selectItem itemValue="MANAGER" itemLabel="#{msgs['global.security.manager']}" />
					<f:selectItem itemValue="ADMIN" itemLabel="#{msgs['global.security.admin']}" />
				</h:selectManyCheckbox>
				<h:message for="permission" styleClass="error" />
				<h:outputLabel for="grant" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.grant']}" />
				</h:outputLabel>
				<h:selectManyCheckbox id="grant" disabled="#{userEditionBean.permissionType == 'SPECIAL'}" value="#{userEditionBean.grants}" layout="pageDirection">
					<f:selectItem itemValue="FILL_OWN" itemLabel="#{msgs['global.security.configuration.fillOwn']}"/>
					<f:selectItem itemValue="FILL_OTHERS" itemLabel="#{msgs['global.security.configuration.fillOthers']}"/>
				</h:selectManyCheckbox>
				<h:message for="grant" styleClass="error" />
			</h:panelGrid>
			
			<h:panelGrid columns="2">
				<h:outputLabel for="address" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.address']}" />
				</h:outputLabel>
				<h:inputText id="address" value="#{userEditionBean.userOnFocus.address}"/>
				
				<h:outputLabel for="homePhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.homePhone']}" />
				</h:outputLabel>
				<h:inputText id="homePhone" value="#{userEditionBean.userOnFocus.homePhone}"/>
				
				<h:outputLabel for="businessPhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.businessPhone']}" />
				</h:outputLabel>
				<h:inputText id="businessPhone" value="#{userEditionBean.userOnFocus.businessPhone}"/>
				
				<h:outputLabel for="cellPhone" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.cellPhone']}" />
				</h:outputLabel>
				<h:inputText id="cellPhone" value="#{userEditionBean.userOnFocus.cellPhone}"/>
				
				<h:outputLabel for="secondEmail" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.secondEmail']}" />
				</h:outputLabel>
				<h:inputText id="secondEmail" value="#{userEditionBean.userOnFocus.secondEmail}"/>
				
				
				<h:outputLabel for="birthDate" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.birthDate']}" />
				</h:outputLabel>
				<t:inputDate id="birthDate" value="#{userEditionBean.userOnFocus.birthDate}" popupCalendar="false"/>
				
				
				<h:outputLabel for="lattesLink" styleClass="field">
					<h:outputText value="#{msgs['users.form.details.lattesLink']}" />
				</h:outputLabel>
				<h:inputText id="lattesLink" value="#{userEditionBean.userOnFocus.lattesLink}"/>
				
			
			</h:panelGrid>
			
			
			
		</a4j:region>
	</rich:panel>
</h:form>
</body>
</f:view>
</html>
