<rich:toolBar itemSeparator="line">
	<rich:toolBarGroup rendered="#{permissionBean.admin}">
		<a4j:commandLink action="matrix list" immediate="true" value="#{msgs['matrices.list.link']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{permissionBean.admin}">
		<a4j:commandLink action="user list" immediate="true" value="#{msgs['users.list.link.admin']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{!permissionBean.admin && permissionBean.manager}">
		<a4j:commandLink action="user list" immediate="true" value="#{msgs['users.list.link.manager']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{!permissionBean.admin && !permissionBean.manager}">
		<a4j:commandLink action="user list" immediate="true" value="#{msgs['users.list.link.user']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{permissionBean.admin}">
		<a4j:commandLink action="user group list" immediate="true" value="#{msgs['userGroups.list.link']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{permissionBean.manager}">
		<a4j:commandLink action="feed list" immediate="true" value="#{msgs['feeds.list.link']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{!permissionBean.manager && permissionBean.user}">
		<a4j:commandLink action="feed list" immediate="true" value="#{msgs['feeds.list.link2']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{permissionBean.manager}">
		<a4j:commandLink action="view feed list" immediate="true" value="#{msgs['viewfeeds.list.link']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup rendered="#{!permissionBean.manager && permissionBean.user}">
		<a4j:commandLink action="view feed list" immediate="true" value="#{msgs['viewfeeds.list.link2']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup>
		<a4j:commandLink action="view search home" value="#{msgs['search.form.new']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup>
		<a4j:commandLink action="#{loginBean.logout}" immediate="true" value="#{msgs['global.logout.link']}" />
	</rich:toolBarGroup>
	<rich:toolBarGroup location="right">
		<h:panelGroup>
			<h:outputLabel value="#{msgs['global.userid']}" />	
			<h:outputLabel value="#{loginBean.userIdentification}" />			
		</h:panelGroup>
	</rich:toolBarGroup>
</rich:toolBar>
