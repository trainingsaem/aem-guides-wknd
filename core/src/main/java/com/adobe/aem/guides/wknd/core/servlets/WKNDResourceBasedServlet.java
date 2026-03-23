package com.adobe.aem.guides.wknd.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "wknd/components/content/resourcebasedservlet",
        methods = HttpConstants.METHOD_GET,
        extensions = "txt"
)
@ServiceDescription("WKND Resource Based Servlet for WKND Project")
@ServiceVendor("WKND")
public class WKNDResourceBasedServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WKNDResourceBasedServlet.class);

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        final Resource resource = request.getResource();
        String title = resource.getValueMap().get(JcrConstants.JCR_TITLE).toString();
        response.setContentType("text/plain");
        response.getWriter().write("WKND Resource Based Servlet triggered:");
        response.getWriter().write("Title: " + title);
        LOGGER.info("WKND Resource Based Servlet triggered: {}", title);
    }

}
