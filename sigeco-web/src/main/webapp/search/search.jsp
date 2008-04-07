<%@ include file="/include/include.jsp" %>

<html>
	<f:view>
		<%@ include file="/include/head.jsp" %>
		
		<body>
			<h:form>
				<%@ include file="/include/menu.jsp" %>
				<br />
				<br />
				<t:panelGrid columns="2" rowClasses="valigntop" columnClasses="w20, w80" styleClass="w100">
					<rich:panel>
						<f:facet name="header"><f:verbatim>&nbsp;</f:verbatim></f:facet>
						<t:commandButton action="#{resultBean.back}" value="#{msgs['search.result.back']}" />
					</rich:panel>
					<rich:panel>
						<f:facet name="header"><f:verbatim>&nbsp;</f:verbatim></f:facet>
						<h:outputText styleClass="error" value="#{msgs['search.result.empty']}" rendered="#{empty resultBean.users}" />
						<rich:dataTable rendered="#{not empty resultBean.users}" value="#{resultBean.users}" var="user" rowClasses="item, altitem">
						
							<f:facet name="header">
								<rich:columnGroup>
									<rich:column>
										<h:outputText value="#{msgs['search.result.user']}" />
									</rich:column>
									<rich:column>
										<h:outputText value="#{msgs['search.result.email']}" />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
							
							<rich:columnGroup>
								<rich:column>
									<h:outputText value="#{user.name}" />
								</rich:column>
								<rich:column>
									<h:outputText value="#{user.email}" />
								</rich:column>
							</rich:columnGroup>
							
						</rich:dataTable>
					
					</rich:panel>
				</t:panelGrid>
			</h:form>
		</body>
	</f:view>
</html>