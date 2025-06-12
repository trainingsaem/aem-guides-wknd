package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.WKNDCustomService;
import com.adobe.aem.guides.wknd.core.config.WKNDCustomServiceConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = WKNDCustomService.class,
        immediate = true
)
@Designate(ocd = WKNDCustomServiceConfig.class)
public class WKNDCustomServiceImpl implements WKNDCustomService{

    static final Logger LOGGER = LoggerFactory.getLogger(WKNDCustomServiceImpl.class);
    private String apiEndpoint;

    @Activate
    protected void activate() {
        LOGGER.info("\n Invoked by @Activate annotation");
    }

    @Modified
    protected void modified(WKNDCustomServiceConfig config) {
        apiEndpoint = config.api_endpoint();
        LOGGER.info("\n Invoked by @Modified annotation");
        LOGGER.info("\n API Endpoint URL: {}", apiEndpoint);
    }

    @Deactivate
    protected void deactivate() {
        LOGGER.info("\n Invoked by @Deactivate annotation");
    }

    @Override
    public String performAction() {
        LOGGER.info("Performing action in WKNDCustomService");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiEndpoint);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    LOGGER.info("JSON Response received from API Endpoint from Service: {}", result);

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject profile = jsonObject.getJSONObject("profile");
                    String name = profile.getString("name");
                    LOGGER.info("Name from JSON Object: {}", name);
                }

            }
        } catch (Exception e) {
            LOGGER.error("Error fetching endpoint", e);
        }
        return "Failed to fetch profile data";
    }

    @Override
    public String apiEndpoint() {
        return apiEndpoint;
    }
}
