<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<rich:panel>
			<f:facet name="header">
				<h:panelGrid columns="3">
					<a4j:commandButton action="previous" value="#{msgs['global.form.previous']}" />
					<a4j:commandButton action="next" actionListener="#{matrixEditionBean.prepareCellMatrix}" value="#{msgs['global.form.next']}" />
					<a4j:commandButton action="restart" value="#{msgs['global.form.cancel']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRegistry');" />
				</h:panelGrid>
			</f:facet>
			<h:panelGrid columns="2">
				<h:panelGrid columns="1">
					<h:panelGrid columns="2">
						<h:outputText styleClass="field" value="#{msgs['abilities.detail.name']}" />
						<h:inputText value="#{matrixEditionBean.abilityName}" />
						<a4j:commandButton value="#{msgs['global.include.link']}" actionListener="#{matrixEditionBean.insertAbility}" reRender="habilidades" />
					</h:panelGrid>
					<rich:dataTable id="habilidades" value="#{matrixEditionBean.matrixOnFocus.abilities}" var="ability">
						<rich:column>
							<f:facet name="header">
								<h:outputText value=""/>
							</f:facet>
							<a4j:commandButton actionListener="#{matrixEditionBean.removeAbility}" value="#{msgs['global.remove.link']}" reRender="habilidades">
				        		<t:updateActionListener value="#{ability}" property="#{matrixEditionBean.abilityOnFocus}"></t:updateActionListener>
				        	</a4j:commandButton>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="#{msgs['matrices.form.abilities.title']}"/>
							</f:facet>
							<h:outputText value="#{ability.name}" />
						</rich:column>
					</rich:dataTable>
				</h:panelGrid>
				
				<h:panelGrid columns="1">
					<h:panelGrid columns="2">
						<h:outputText styleClass="field" value="#{msgs['grades.detail.name']}" />
						<h:inputText value="#{matrixEditionBean.gradeName}" />
						<a4j:commandButton value="#{msgs['global.include.link']}" actionListener="#{matrixEditionBean.insertGrade}" reRender="grades" />
					</h:panelGrid>
					<rich:dataTable id="grades" value="#{matrixEditionBean.matrixOnFocus.grades}" var="grade">
						<rich:column>
							<f:facet name="header">
								<h:outputText value=""/>
							</f:facet>
							<a4j:commandButton actionListener="#{matrixEditionBean.removeGrade}" value="#{msgs['global.remove.link']}" reRender="grades">
				        		<t:updateActionListener value="#{grade}" property="#{matrixEditionBean.gradeOnFocus}"></t:updateActionListener>
				        	</a4j:commandButton>
						</rich:column>
						<rich:column>
							<f:facet name="header">
								<h:outputText value="#{msgs['matrices.form.grades.title']}"/>
							</f:facet>
							<h:outputText value="#{grade.name}" />
						</rich:column>
					</rich:dataTable>
				</h:panelGrid>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</body>