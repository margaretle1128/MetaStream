package com.example.program_genre_mapping_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "genreMasterData")
public class GenreEntity {

    @Id
    private String id;
    private List<Name> names;
    private List<ExternalRef> externalRefs;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Name> getNames() {
        return names;
    }

    public void setNames(List<Name> names) {
        this.names = names;
    }

    public List<ExternalRef> getExternalRefs() {
        return externalRefs;
    }

    public void setExternalRefs(List<ExternalRef> externalRefs) {
        this.externalRefs = externalRefs;
    }

    public static class Name {
        private String value;
        private String language;

        // Getters and setters

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    public static class ExternalRef {
        private String system;
        private String refName;
        private String id;

        // Getters and setters

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getRefName() {
            return refName;
        }

        public void setRefName(String refName) {
            this.refName = refName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
