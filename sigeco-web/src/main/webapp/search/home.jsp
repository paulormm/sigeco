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
						<t:commandButton id="novaExpressao" action="#{newSearchBean.newSearch}" value="#{msgs['search.start.new']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton id="expressoesProprias" action="#{listSearchBean.listOwnSearches}" value="#{msgs['search.list.saved']}" />
						<f:verbatim><br /></f:verbatim>
						<t:commandButton id="expressoesPublicas" action="#{listSearchBean.listPublicSearches}" value="#{msgs['search.list.public']}" />					
					</rich:panel>
					<rich:panel>
						<f:facet name="header"><f:verbatim>&nbsp;</f:verbatim></f:facet>
						<h:outputText value="#{msgs['search.start.new']}" />
						<rich:separator height="2" />
						<h:outputText value="#{msgs['search.start.new.overview']}" />
						<f:verbatim><br /></f:verbatim>
						<f:verbatim><br /></f:verbatim>
						<h:outputText value="#{msgs['search.list.saved']}" />
						<rich:separator height="2" />
						<h:outputText value="#{msgs['search.list.saved.overview']}" />
						<f:verbatim><br /></f:verbatim>
						<f:verbatim><br /></f:verbatim>
						<h:outputText value="#{msgs['search.list.public']}" />
						<rich:separator height="2" />
						<h:outputText value="#{msgs['search.list.public.overview']}" />
					</rich:panel>
				</t:panelGrid>
			</h:form>
		</body>
	</f:view>
</html>