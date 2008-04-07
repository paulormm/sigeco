<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<a4j:commandButton value="#{msgs['matrices.form.link']}" onclick="javascript:Richfaces.showModalPanel('matrixRegistry',{width:800, height:600, top:50});" actionListener="#{matrixEditionBean.startMatrixInclusion}" />
				</rich:panel>
				<rich:dataTable id="matrizes"
					var="matrixRow" 
					value="#{matrixManagerBean.all}" 
					rowClasses="item, altitem">
					<f:facet name="header">
						<rich:columnGroup>
							<rich:column rowspan="2">
							</rich:column>
							<rich:column>
								<t:outputText value="#{msgs['matrices.list.title']}" />
							</rich:column>
							<rich:column breakBefore="true">
								<h:outputText value="#{msgs['matrices.detail.name']}"></h:outputText>
							</rich:column>
						</rich:columnGroup>
					</f:facet>
					<rich:columnGroup>
						<rich:column>
							<a4j:commandLink value="#{msgs['global.remove.link']}" onclick="javascript:Richfaces.showModalPanel('matrixRemoval',{width:800, height:600, top:50});" reRender="removeConfirm">
								<t:updateActionListener value="#{matrixRow}" property="#{matrixEditionBean.matrixOnFocus}"></t:updateActionListener>
							</a4j:commandLink>
						</rich:column>
						<rich:column>
							<t:outputText value="#{matrixRow.name}" />
						</rich:column>
					</rich:columnGroup>
				</rich:dataTable>
			</h:form>
			<rich:modalPanel id="matrixRegistry">
				<f:facet name="header">
					<h:outputText value="#{msgs['matrices.form.label']}" />
				</f:facet>
				<a4j:include viewId="/matrices/matrixGeneralData.jsp"></a4j:include>
			</rich:modalPanel>
			
			<rich:modalPanel id="matrixRemoval">
				<f:facet name="header">
					
				</f:facet>
				<a4j:include viewId="/matrices/matrixRemoveConfirmation.jsp"></a4j:include>
			</rich:modalPanel>
		</body>
	</f:view>
</html>
