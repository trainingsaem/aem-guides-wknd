package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletPaths("/bin/wknd-path-based-servlet")
@ServiceDescription("WKND Path Based Servlet for WKND Project")
@ServiceVendor("WKND")
public class WKNDPathBasedServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WKNDPathBasedServlet.class);

    @Override
    public void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().write("WKND Path Based Servlet triggered:");
        LOGGER.info("WKND Path Based Servlet triggered:");
    }

}
