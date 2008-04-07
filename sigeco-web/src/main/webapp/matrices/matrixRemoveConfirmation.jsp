<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel id="removeConfirm">
			<f:facet name="header">
				<h:outputText value="#{msgs['matrices.form.confirm.remove']}" />
			</f:facet>
			<h:panelGrid columns="2">
				<h:outputText value="#{msgs['matrices.form.details.name']}" />
				<h:outputText value="#{matrixEditionBean.matrixOnFocus.name}" />
				<a4j:commandButton value="#{msgs['global.remove.link']}" actionListener="#{matrixEditionBean.removeMatrix}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRemoval');" />
				<a4j:commandButton value="#{msgs['global.form.cancel']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRemoval');" />
			</h:panelGrid>
		</rich:panel> 
	</h:form>
</body>
