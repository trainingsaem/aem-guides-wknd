package com.adobe.aem.guides.wknd.core.workflows;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class,
        property = {
                "process.label=WKND Add ID Property to Asset Metadata",
                "process.description=Creates an ID property in asset metadata if it is missing."})
public class WKNDAssetMetadataIdCreation implements WorkflowProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(WKNDAssetMetadataIdCreation.class);
    private static final java.util.Random RANDOM = new java.util.Random();

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {

        String payload = workItem.getWorkflowData().getPayload().toString();
        LOGGER.info("Workflow Payload: {}", payload);

        ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
        try {
            if (resourceResolver == null) {
                throw new WorkflowException("Workflow Resource Resolver is null");
            }
            Resource metadataResource = resourceResolver.getResource(payload + "/jcr:content/metadata");

            if (metadataResource != null) {
                LOGGER.info("Metadata Resource: {}", metadataResource.getPath());

                boolean updated = false;
                ModifiableValueMap properties = metadataResource.adaptTo(ModifiableValueMap.class);

                assert properties != null;
                String id = properties.get("id", String.class);

                if (id == null || id.isEmpty()) {
                    String randomID = generateRandomId();
                    properties.put("id", randomID);
                    updated = true;
                    LOGGER.info("Created ID Property for Asset Metadata with value: {}", randomID);
                } else {
                    LOGGER.info("ID Property already exists for Asset Metadata: {}", id);
                }

                updated = addProcessArgsToMetadata(metaDataMap, properties) || updated;
                if (updated) {
                    resourceResolver.commit();
                }

            } else {
                LOGGER.info("Metadata node not found at path: {}", payload);
            }
        } catch (Exception e) {
            throw new WorkflowException("Error creating ID for Metadata Resource", e);
        }
    }

    private String generateRandomId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = RANDOM.nextInt(characters.length());
            id.append(characters.charAt(index));
        }

        return "ID-" + id;
    }

    private boolean addProcessArgsToMetadata(MetaDataMap metaDataMap, ModifiableValueMap properties) {

        boolean updated = false;
        String processArgs = metaDataMap.get("PROCESS_ARGS", String.class);

        if (processArgs != null && !processArgs.isEmpty()) {
            for (String pair : processArgs.split(",")) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    if (!key.isEmpty()) {
                        properties.put(key, value);
                        updated = true;
                    }
                }
            }
        }
        return updated;
    }
}
