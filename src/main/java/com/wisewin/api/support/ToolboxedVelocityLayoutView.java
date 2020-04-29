package com.wisewin.api.support;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.Toolbox;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.LinkTool;
import org.apache.velocity.tools.view.ParameterTool;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ToolboxedVelocityLayoutView extends VelocityLayoutView {

    private List<Toolbox> tools = new ArrayList<Toolbox>(Scope.values().size());

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ViewToolContext velocityContext = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());
        velocityContext.setUserCanOverwriteTools(false);
        velocityContext.putAll(model);
        for (Toolbox t : tools) {
            velocityContext.addToolbox(t);
        }
        return velocityContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        EasyFactoryConfiguration config = new EasyFactoryConfiguration();

        config.toolbox(Scope.APPLICATION)
                .tool(EscapeTool.class)
                .tool("DateTool",DateTool.class);


        config.toolbox(Scope.REQUEST)
                .tool(ParameterTool.class)
                .tool(LinkTool.class);

        ToolboxFactory f = config.createFactory();
        for (String s : Scope.values()) {
            tools.add(f.createToolbox(s));
        }
        tools = Collections.unmodifiableList(tools);
    }

}
