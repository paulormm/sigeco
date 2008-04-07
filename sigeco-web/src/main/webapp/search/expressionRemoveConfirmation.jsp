<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel id="removeConfirmation" style="width: 100%; height: 100%">
			<f:facet name="header">
				<h:outputText value="#{msgs['search.list.expression.remove.confirmation.header']}" />
			</f:facet>
			<h:panelGrid columns="2">
				<h:outputText styleClass="field" value="#{msgs['search.list.expression.remove.confirmation.name']}" />
				<h:outputText styleClass="fieldValue" value="#{listSearchBean.expressionToRemove.name}" />
			</h:panelGrid>
			
			<h:outputText
				rendered="#{not listSearchBean.expressionToRemove.publish}" 
				styleClass="fieldValue" value="#{msgs['search.list.expression.remove.confirmation.private.warning']}" />
			<h:outputText
				rendered="#{listSearchBean.expressionToRemove.publish}" 
				styleClass="error" value="#{msgs['search.list.expression.remove.confirmation.public.warning']}" />
			
			<h:panelGrid columns="2">	
				<a4j:commandButton 
					value="#{msgs['global.remove.link']}" 
					actionListener="#{listSearchBean.removeExpression}" 
					reRender="expressions" 
					onclick="javascript:Richfaces.hideModalPanel('removeExpressionConfirmation');" />
				<a4j:commandButton 
					value="#{msgs['global.form.cancel']}" 
					reRender="expressions" 
					onclick="javascript:Richfaces.hideModalPanel('removeExpressionConfirmation');" />
			</h:panelGrid>
		</rich:panel> 
	</h:form>
</body>
