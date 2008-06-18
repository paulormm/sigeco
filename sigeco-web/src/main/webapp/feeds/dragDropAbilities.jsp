<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<rich:dragIndicator id="indicator">
				<f:facet name="single">
					<rich:panel>
						
					</rich:panel>
				</f:facet>
			</rich:dragIndicator>
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
					<f:verbatim><br /></f:verbatim>
					<h:outputText value="#{msgs['feeds.instructions']}"></h:outputText>
					<f:verbatim><br /></f:verbatim>
					<rich:dataTable id="elements" binding="#{matrixFeederBean.dataTable}" value="#{matrixFeederBean.elements}" var="element" >z
					</rich:dataTable>
				</rich:panel>
			</h:form>		
		</body>
	</f:view>
</html>
