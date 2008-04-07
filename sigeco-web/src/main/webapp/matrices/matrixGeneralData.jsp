<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel>
			<f:facet name="header">
				<h:panelGrid columns="2">
					<a4j:commandButton action="next" value="#{msgs['global.form.next']}" />
					<a4j:commandButton value="#{msgs['global.form.cancel']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRegistry');" />
				</h:panelGrid>
			</f:facet>
			<h:panelGrid columns="2">
				<t:outputText styleClass="field" value="#{msgs['matrices.form.details.name']}" />
				<t:inputText value="#{matrixEditionBean.matrixOnFocus.name}" />
			</h:panelGrid>
		</rich:panel> 
	</h:form>
</body>

