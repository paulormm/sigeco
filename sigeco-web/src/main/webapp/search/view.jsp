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
						<t:commandButton action="back" value="#{msgs['search.view.back']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton action="#{resultBean.fromView}" value="#{msgs['search.view.fire']}">
							<t:updateActionListener value="#{viewBean.expression}" property="#{resultBean.expression}"></t:updateActionListener>
						</t:commandButton>
					</rich:panel>
					<rich:panel>
						<f:facet name="header"><f:verbatim>&nbsp;</f:verbatim></f:facet>
						
						<rich:dataTable 
							rendered="#{not empty viewBean.expression.searchTerms}"
							id="terms" 
							var="term"
							value="#{viewBean.expression.searchTerms}"
							rowClasses="item, altitem">
							
							<f:facet name="header">
								<rich:columnGroup>
									<rich:column>
										<t:outputText value="#{msgs['search.view.expression.name.prefix']}" />
										<t:outputText value="#{viewBean.expression.name}" />
									</rich:column>
								</rich:columnGroup>
							</f:facet>
							
							<rich:columnGroup>
								<rich:column>
							        <rich:dataGrid value="#{term.searchElements}" var="element" columns="#{term.size}" border="0">
						            	<f:facet name="header">
						            		<h:panelGroup>
												<h:outputText value="#{msgs['search.new.term.label']}" />
						            		</h:panelGroup>
										</f:facet>
							            <rich:panel>
							            	<f:facet name="header">
												<h:outputText value="#{msgs['search.new.element.label']}" />
											</f:facet>
											<h:panelGrid columns="2">
												<t:outputLabel styleClass="field" value="#{msgs['search.new.matrix.label']}" />
												<t:outputText styleClass="fieldValue" value="#{element.matrix.name}" />
												
												<t:outputLabel styleClass="field" value="#{msgs['search.new.knowledge.label']}" />
												<t:outputText styleClass="fieldValue" value="#{element.knowledge.name}" />
												
												<t:outputLabel styleClass="field" value="#{msgs['search.new.ability.label']}" />
												<t:outputText styleClass="fieldValue" value="#{element.ability.name}" />
												
												<t:outputLabel styleClass="field" value="#{msgs['search.new.operator.label']}" />
												<t:outputText styleClass="fieldValue" value="#{msgs[element.operator.bundleKey]}" />
												
												<t:outputLabel styleClass="field" value="#{msgs['search.new.grade.label']}" />
												<t:outputText styleClass="fieldValue" value="#{element.grade.name}" />
											</h:panelGrid>
							            </rich:panel>
							        </rich:dataGrid>
								</rich:column>
							</rich:columnGroup>
						</rich:dataTable>
					
					</rich:panel>
				</t:panelGrid>
			</h:form>
		</body>
	</f:view>
</html>
