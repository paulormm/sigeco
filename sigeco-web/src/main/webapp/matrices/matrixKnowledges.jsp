<%@ include file="/include/include.jsp" %>


<body>
	<h:form>
		<a4j:outputPanel ajaxRendered="true">
			<rich:messages id="messages" styleClass="error"></rich:messages>
		</a4j:outputPanel>
		<rich:panel>
			<f:facet name="header">
				<h:panelGrid columns="3">
					<a4j:commandButton action="previous" value="#{msgs['global.form.previous']}" />
					<a4j:commandButton action="next" value="#{msgs['global.form.next']}" />
					<a4j:commandButton action="restart" value="#{msgs['global.form.cancel']}" reRender="matrizes" onclick="javascript:Richfaces.hideModalPanel('matrixRegistry');" />
				</h:panelGrid>
			</f:facet>
			<h:outputText styleClass="field" value="#{msgs['matrices.form.knowledge.label']}" />
			<rich:spacer width="5px" />
			<h:inputText value="#{matrixEditionBean.newKnowledgeGroupName}" />
			<rich:tree id="knowledgeTree" switchType="ajax">
			    <rich:recursiveTreeNodesAdaptor roots="#{matrixEditionBean.matrixOnFocus.knowledgeGroups}" nodes="#{group.children}" var="group">
			        <rich:treeNode>
			        	<h:outputText value="#{group.title}" />
			        	<rich:spacer width="30px" />
			        	<a4j:commandButton value="#{msgs['matrices.form.includearea']}" reRender="knowledgeTree" rendered="#{!group.active}">
			        		<t:updateActionListener value="true" property="#{group.active}" />
			        	</a4j:commandButton>
				        <a4j:commandButton actionListener="#{matrixEditionBean.insertGroup}" value="#{msgs['matrices.form.knowledgeGroup.title']}" reRender="knowledgeTree" rendered="#{group.active}">
				        	<t:updateActionListener value="#{group}" property="#{matrixEditionBean.groupOnFocus}"></t:updateActionListener>
				        </a4j:commandButton>
				        <a4j:commandButton actionListener="#{matrixEditionBean.insertKnowledge}" value="#{msgs['matrices.form.knowledge.title']}" reRender="knowledgeTree" rendered="#{group.active}">
				        	<t:updateActionListener value="#{group}" property="#{matrixEditionBean.groupOnFocus}"></t:updateActionListener>
				        </a4j:commandButton>
				        <a4j:commandButton actionListener="#{matrixEditionBean.removeGroup}" value="#{msgs['global.remove.link']}" reRender="knowledgeTree" rendered="#{!group.active && !group.superGroup}">
				        	<t:updateActionListener value="#{group}" property="#{matrixEditionBean.groupOnFocus}"></t:updateActionListener>
				        </a4j:commandButton>
			        </rich:treeNode>
			        <rich:treeNodesAdaptor nodes="#{group.knowledges}" var="knowledge">
			            <rich:treeNode>
			            	<h:outputText value="#{knowledge.name}" />
			            	<rich:spacer width="30px" />
			            	<a4j:commandButton actionListener="#{matrixEditionBean.removeKnowledge}" value="#{msgs['global.remove.link']}" reRender="knowledgeTree">
				        		<t:updateActionListener value="#{knowledge}" property="#{matrixEditionBean.knowledgeOnFocus}"></t:updateActionListener>
				        		<t:updateActionListener value="#{group}" property="#{matrixEditionBean.groupOnFocus}"></t:updateActionListener>
				        	</a4j:commandButton>
			            </rich:treeNode>
			        </rich:treeNodesAdaptor>
			    </rich:recursiveTreeNodesAdaptor>
			</rich:tree>
		</rich:panel>
	</h:form>
</body>

