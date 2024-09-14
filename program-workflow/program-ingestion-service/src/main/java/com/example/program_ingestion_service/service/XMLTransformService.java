package com.example.program_ingestion_service.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class XMLTransformService {

    private final XmlMapper xmlMapper;
    private final ObjectMapper objectMapper;

    public XMLTransformService() {
        this.xmlMapper = new XmlMapper();
        this.objectMapper = new ObjectMapper();
    }

    public String transformXMLToJSON(String xmlContent) throws Exception {
        JsonNode xmlNode = xmlMapper.readTree(xmlContent);
        JsonNode jsonNode = fixDescriptionKeys(xmlNode);
        jsonNode = fixAdvisoryKeys(jsonNode); 
        return objectMapper.writeValueAsString(jsonNode);
    }

    private JsonNode fixDescriptionKeys(JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getKey().isEmpty()) {
                    ((ObjectNode) node).set("value", field.getValue());
                    ((ObjectNode) node).remove("");
                }
                fixDescriptionKeys(field.getValue());
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                fixDescriptionKeys(arrayElement);
            }
        }
        return node;
    }

        private JsonNode fixAdvisoryKeys(JsonNode node) {
        if (node.isObject()) {
            if (node.has("advisories")) {
                JsonNode advisoriesNode = node.get("advisories");
                if (advisoriesNode.has("advisory") && advisoriesNode.get("advisory").isArray()) {
                    ArrayNode advisoryArray = (ArrayNode) advisoriesNode.get("advisory");
                    ArrayNode newAdvisoryArray = objectMapper.createArrayNode();
                    for (JsonNode advisory : advisoryArray) {
                        ObjectNode advisoryObject = objectMapper.createObjectNode();
                        advisoryObject.put("value", advisory.asText());
                        newAdvisoryArray.add(advisoryObject);
                    }
                    ((ObjectNode) advisoriesNode).set("advisory", newAdvisoryArray);
                }
            }
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                fixAdvisoryKeys(field.getValue());
            }
        } else if (node.isArray()) {
            for (JsonNode arrayElement : node) {
                fixAdvisoryKeys(arrayElement);
            }
        }
        return node;
    }
}