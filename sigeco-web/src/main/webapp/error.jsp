<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
	<%@ include file="/include/head.jsp" %>
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<br />
				<br />
				<rich:panel>
					<f:facet name="header">
						<h:outputText value="#{msgs['global.error.occured']}" />
					</f:facet>
					<h:outputText value="#{errorBean.resolvedError}" />
				</rich:panel>
			</h:form>
		</body>
	</f:view>
</html>
