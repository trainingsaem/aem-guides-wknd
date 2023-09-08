package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.jcr.Repository;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
        service = {Servlet.class},
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes=wknd/components/page",
                "sling.servlet.selectors=default",
                "sling.servlet.extensions=html"
        }
)
@ServiceDescription("WKND Default JSON Servlet")
public class WKNDDefaultJsonServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private transient Repository repository;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String[] keys = repository.getDescriptorKeys();
        JSONObject jsonObject = new JSONObject();

        for (String key : keys) {
            try {
                jsonObject.put(key, repository.getDescriptor(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        response.getWriter().println(jsonObject.toString());
    }
}
