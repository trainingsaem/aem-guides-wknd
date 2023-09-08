package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "wknd/components/page",
        methods = {HttpConstants.METHOD_GET, HttpConstants.METHOD_POST},
        selectors = "default",
        extensions = {"txt", "xml"}
)
@ServiceDescription("WKND Default Servlet")
public class WKNDDefaultServlet extends SlingAllMethodsServlet {

    private final Logger LOGGER = LoggerFactory.getLogger(WKNDDefaultServlet.class);


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException{
        LOGGER.info("WKND Default Servlet is running");
        final Resource resource = request.getResource();
        response.setContentType("text/plain");
        response.getWriter().write("\n Title: " + resource.getValueMap().get(JcrConstants.JCR_TITLE)  + '\n');
        response.getWriter().write("\n Resource Type: " + resource.getResourceType() + "\n Path: " + resource.getPath());
    }

    @Override
    protected void doPost(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException{
        try{
            LOGGER.info("WKND Default Servlet: Post started");
            List<RequestParameter> requestParametersList = request.getRequestParameterList();
            for (RequestParameter requestParameter : requestParametersList){
                LOGGER.info("\n Parameters: {} : {}", requestParameter.getName(), requestParameter.getString());
            }

        } catch (Exception e){
            LOGGER.info("\n Request Error");
        }
        response.getWriter().write("WND Default Servlet Executed");
    }
}
