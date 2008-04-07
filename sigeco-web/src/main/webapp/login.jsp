<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form id="loginForm">
	            <rich:panel style="width:300px;">
	            	<f:facet name="header">
						<h:outputText value="#{msgs['login.form.header']}" />
					</f:facet>
					<h:panelGrid columns="3">
						<h:outputLabel for="username" styleClass="field">
							<h:outputText value="#{msgs['login.form.username']}" />
						</h:outputLabel>
						<h:inputText id="username" value="#{loginBean.username}" required="true">
						</h:inputText>
						<h:message for="username" styleClass="error"  />
						
						<h:outputLabel for="password" styleClass="field">
							<h:outputText value="#{msgs['login.form.password']}" />
						</h:outputLabel>
						<h:inputSecret id="password" value="#{loginBean.password}" required="true">
						</h:inputSecret>
						<h:message for="password" styleClass="error"  />					
					</h:panelGrid>
					<h:panelGrid columns="1">
						<h:message for="loginForm" styleClass="error"  />
						<h:commandButton id="login" action="#{loginBean.login}" value="#{msgs['login.form.submit']}" />
					</h:panelGrid>
				</rich:panel>
			</h:form>
		</body>
	</f:view>
</html>