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
						<t:commandButton action="#{newSearchBean.newSearch}" value="#{msgs['search.start.new']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton action="#{listSearchBean.listOwnSearches}" value="#{msgs['search.list.saved']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton action="#{listSearchBean.listPublicSearches}" value="#{msgs['search.list.public']}" />					
					</rich:panel>
					<rich:panel>
						<f:facet name="header">
							<h:panelGroup>
								<h:outputText value="#{msgs['search.list.private']}" rendered="#{not listSearchBean.publicSearches}" />
								<h:outputText value="#{msgs['search.list.public']}" rendered="#{listSearchBean.publicSearches}" />
							</h:panelGroup>
						</f:facet>
						<h:outputText value="#{msgs['search.list.empty']}" rendered="#{empty listSearchBean.expressions}" styleClass="error">
						</h:outputText>
						<rich:dataTable
							rendered="#{not empty listSearchBean.expressions}"
							id="expressions" 
							var="exp"
							value="#{listSearchBean.expressions}"
							rowClasses="item, altitem">
							
							<f:facet name="header">
								<rich:columnGroup>
									<rich:column>
										<h:outputText value="#{msgs['search.list.expression.name']}" />
									</rich:column>
									<rich:column>
										<h:outputText value="#{msgs['search.list.expression.user']}" />
									</rich:column>
									<rich:column />
								</rich:columnGroup>
							</f:facet>
							
							<rich:columnGroup>
								<rich:column>
									<h:outputText value="#{exp.name}" />
								</rich:column>
								<rich:column>
									<h:outputText value="#{exp.user.name}" />
								</rich:column>
								<rich:column>
									<h:commandButton action="view" value="#{msgs['search.list.expression.view']}">
										<t:updateActionListener value="#{exp}" property="#{viewBean.expression}"></t:updateActionListener>
									</h:commandButton>
									<h:outputText value=" " />
									<h:commandButton action="#{resultBean.fromList}" value="#{msgs['search.list.expression.fire']}">
										<t:updateActionListener value="#{exp}" property="#{resultBean.expression}"></t:updateActionListener>
									</h:commandButton>
									<h:outputText value=" " />
									<a4j:commandButton 
										onclick="javascript:Richfaces.showModalPanel('removeExpressionConfirmation',{width:400, height:150});" 
										reRender="removeConfirmation" 
										value="#{msgs['search.list.expression.remove']}" 
										rendered="#{not listSearchBean.publicSearches}">
										<t:updateActionListener value="#{exp}" property="#{listSearchBean.expressionToRemove}"></t:updateActionListener>
									</a4j:commandButton>
								</rich:column>
							</rich:columnGroup>
									
							
						</rich:dataTable>
					</rich:panel>
				</t:panelGrid>
			</h:form>
			
			<rich:modalPanel id="removeExpressionConfirmation">
				<f:facet name="header">
				</f:facet>
				<a4j:include viewId="/search/expressionRemoveConfirmation.jsp" />
			</rich:modalPanel>
			
		</body>
	</f:view>
</html>