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
						<t:commandButton immediate="true" action="#{newSearchBean.cancel}" value="#{msgs['search.new.cancel']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton 
							action="#{newSearchBean.save}" 
							value="#{msgs['search.new.save']}" />
						<f:verbatim><br /></f:verbatim>
						<h:commandButton action="#{resultBean.fromForm}" value="#{msgs['search.new.search']}" immediate="true">
							<t:updateActionListener value="#{newSearchBean.expression}" property="#{resultBean.expression}"></t:updateActionListener>
						</h:commandButton>
						<f:verbatim><br /></f:verbatim>
					</rich:panel>
					<rich:panel>
						<f:facet name="header"><f:verbatim>&nbsp;</f:verbatim></f:facet>
						<h:panelGrid columns="2">
							<h:outputText value="#{msgs['search.new.name']}" styleClass="field" />
							<h:panelGroup>
								<h:inputText id="name" value="#{newSearchBean.expression.name}" required="true" />
								<h:message for="name" styleClass="error" />
							</h:panelGroup>
							
							<h:outputText value="#{msgs['search.new.publish']}" styleClass="field" />
							<h:selectBooleanCheckbox value="#{newSearchBean.expression.publish}" />
							
							<h:outputText value="#{msgs['search.new.terms']}" styleClass="field" />
							<h:panelGroup>
								<h:commandButton rendered="#{not newSearchBean.editingTerm}" action="#{newSearchBean.newTerm}" value="#{msgs['search.new.term.new']}" />
								<h:commandButton 
									rendered="#{newSearchBean.editingTerm}"
									disabled="#{empty newSearchBean.term.searchElements}"
									action="#{newSearchBean.saveTerm}" 
									value="#{msgs['search.new.term.save']}" />
								<h:commandButton 
									rendered="#{newSearchBean.editingTerm}"
									action="#{newSearchBean.cancelTerm}" 
									value="#{msgs['search.new.term.cancel']}" />
							</h:panelGroup>
							
							<h:outputText value="" rendered="#{newSearchBean.editingTerm}" />
							<h:panelGrid columns="2" rendered="#{newSearchBean.editingTerm}">
								<t:outputLabel value="#{msgs['search.new.term.matrix']}" styleClass="field" />
								<t:selectOneMenu 
									id="matrix"
									value="#{newSearchBean.element.matrix}"
									immediate="true" 
			         				onclick="submit()"
			         				valueChangeListener="#{newSearchBean.matrixSelected}"
			         				converter="matrixConverter">
										<f:selectItems value="#{newSearchBean.matrices}" />
								</t:selectOneMenu>
								
								<t:outputLabel for="knowledge" styleClass="field" rendered="#{newSearchBean.someMatrixSelected}">
									<t:outputText value="#{msgs['search.new.term.knowledge']}" />
								</t:outputLabel>
						        <t:selectOneMenu 
						        	id="knowledge"
						         	value="#{newSearchBean.element.knowledge}"
						         	rendered="#{newSearchBean.someMatrixSelected}">
						           <f:selectItems value="#{newSearchBean.elementKnowledges}"/>
						        </t:selectOneMenu>
						        
								<t:outputLabel for="ability" value="#{msgs['search.new.term.ability']}" styleClass="field" rendered="#{newSearchBean.someMatrixSelected}" />
						        <t:selectOneMenu 
						        	id="ability" 
						         	value="#{newSearchBean.element.ability}" rendered="#{newSearchBean.someMatrixSelected}">
						           <f:selectItems value="#{newSearchBean.elementAbilities}"/>
						        </t:selectOneMenu>
								
								<t:outputLabel for="operator" value="#{msgs['search.new.term.operator']}" styleClass="field" rendered="#{newSearchBean.someMatrixSelected}" />
						        <t:selectOneMenu 
						        	id="operator" 
						         	value="#{newSearchBean.element.operator}" rendered="#{newSearchBean.someMatrixSelected}">
						           <f:selectItems value="#{newSearchBean.elementOperators}"/>
						        </t:selectOneMenu>
						        
								<t:outputLabel for="grade" value="#{msgs['search.new.term.grade']}" styleClass="field" rendered="#{newSearchBean.someMatrixSelected}" />
						        <t:selectOneMenu 
						        	id="grade" 
						         	value="#{newSearchBean.element.grade}" rendered="#{newSearchBean.someMatrixSelected}">
						           <f:selectItems value="#{newSearchBean.elementGrades}"/>
						        </t:selectOneMenu>
						        
						        <t:outputLabel value="" rendered="#{newSearchBean.someMatrixSelected}" />
								<t:commandButton 
									action="#{newSearchBean.saveElement}" 
									value="#{msgs['search.new.element.save']}" rendered="#{newSearchBean.someMatrixSelected}">
								</t:commandButton>
							</h:panelGrid>	
							<h:outputText value="" rendered="#{not empty newSearchBean.term.searchElements}" />
							
							<h:panelGrid columns="1" rendered="#{not empty newSearchBean.term.searchElements}">
					        	<rich:panel>
						       		<f:facet name="header">
						       			<h:outputText value="Expressão de Busca" />
									</f:facet>
								<t:outputText value="#{newSearchBean.stringExpression}" />
							</rich:panel>
							</h:panelGrid>
							<rich:dataGrid 
					        	value="#{newSearchBean.term.searchElements}" 
					        	var="element" 
					        	columns="3"
					        	rendered="#{not empty newSearchBean.term.searchElements}"
					        	border="0"
					        	rules="none">
					            <h:panelGrid columns="1">
						            <rich:panel>
						            	<f:facet name="header">
						            		<h:panelGroup style="width:100%;">
												<h:outputText value="#{msgs['search.new.element.label']}" />
												<h:outputText value=" " />
						            			<t:commandLink 
						            				action="#{newSearchBean.removeElement}"
						            				value="[#{msgs['search.new.element.remove']}]">
						            				<t:updateActionListener value="#{element}" property="#{newSearchBean.elementToRemove}"></t:updateActionListener>
						            			</t:commandLink>
						            		</h:panelGroup>
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
					            </h:panelGrid>
					        </rich:dataGrid>
					        
    						<h:outputText value="" rendered="#{not empty newSearchBean.expression.searchTerms}" />
							<rich:dataTable 
								rendered="#{not empty newSearchBean.expression.searchTerms}"
								id="terms" 
								var="term"
								value="#{newSearchBean.expression.searchTerms}"
								rowClasses="item, altitem">
								
								<f:facet name="header">
									<rich:columnGroup>
										<rich:column>
											<t:outputText value="#{msgs['search.new.terms.label']}" />
										</rich:column>
									</rich:columnGroup>
								</f:facet>
								
								<rich:columnGroup>
									<rich:column>
								        <rich:dataGrid value="#{term.searchElements}" var="element" columns="#{term.size}" border="0">
							            	<f:facet name="header">
							            		<h:panelGroup>
													<h:outputText value="#{msgs['search.new.term.label']}" />
													<h:outputText value=" " />
							            			<t:commandLink 
							            				action="#{newSearchBean.removeTerm}"
							            				value="[#{msgs['search.new.term.remove']}]">
							            				<t:updateActionListener value="#{term}" property="#{newSearchBean.termToRemove}"></t:updateActionListener>
							            			</t:commandLink>
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
    						
						</h:panelGrid>
					</rich:panel>
				</t:panelGrid>
			</h:form>
		</body>
	</f:view>
</html>