<%@ include file="/include/include.jsp" %>
<html>
	<f:view>
		<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<rich:panel>
					<f:facet name="header">
						<t:outputText value="#{msgs['feeds.feedMatrix.title']}"></t:outputText>
					</f:facet>
					<h:panelGrid columns="2">
						<a4j:commandButton action="finish" actionListener="#{matrixFeederBean.save}" value="#{msgs['global.form.save']}"></a4j:commandButton>
						<a4j:commandButton action="finish" value="#{msgs['global.form.cancel']}"></a4j:commandButton>
					</h:panelGrid>
					<rich:dataTable id="elements" binding="#{matrixFeederBean.dataTable}" value="#{matrixFeederBean.elements}" var="element" >
						<rich:column>
							<f:facet name="header">
								<h:outputText value="#{msgs['feeds.knowledge.header']}" />	
							</f:facet>
							<h:outputText rendered="#{element.isknowledgeGroup}" style="background-color: gray" value="#{element.name}"  />
							<h:outputText rendered="#{!element.isknowledgeGroup}" value="#{element.name}"  />
						</rich:column>
						
					</rich:dataTable>
				</rich:panel>
			</h:form>		
		</body>
	</f:view>
</html>