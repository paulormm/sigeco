<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel>
			<f:facet name="header">
				<h:panelGrid columns="2">
					<a4j:commandButton action="restart" actionListener="#{matrixEditionBean.save}" value="#{msgs['global.form.save']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRegistry');" />
					<a4j:commandButton action="restart" value="#{msgs['global.form.cancel']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRegistry');" />
				</h:panelGrid>
			</f:facet>
			<br />
			<h:outputText value="#{msgs['matrices.form.cells.instructions']}" />
			<rich:dataTable 
				binding="#{matrixEditionBean.matrixCellsDataTable}" 
				value="#{matrixEditionBean.matrixCellsRows}" var="row" >
			</rich:dataTable>
		</rich:panel>
	</h:form>
</body>