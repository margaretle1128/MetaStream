package com.example.program_id_mapping_service.model;

import java.util.List;

public class CommonSchemaProgram {
    
    private String programId;
    private List<Title> titles;
    private List<Description> descriptions;
    private List<String> genres;
    private String originalAirDate;
    private String colorCode;
    private String sourceType;
    private Network originalNetwork;
    private String seriesPremiere;
    private String seasonNumber;
    private String originalAudiolang;
    private List<Release> releases;
    private List<String> advisories;
    private int duration;
    private List<ExternalRef> externalRefs;
    private List<ProviderInfo> providerInfo;
    private List<Credit> credits;
    private List<String> countries;
    private String programType;
    
    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public static class Title {
        private String lang;
        private String value;
        private String type;
        private String length;
        private String subtype;

        public String getLang() {
            return lang;
        }
        public void setLang(String lang) {
            this.lang = lang;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getLength() {
            return length;
        }
        public void setLength(String length) {
            this.length = length;
        }
        public String getSubtype() {
            return subtype;
        }
        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }
    }

    public static class Description {
        private String lang;
        private String length;
        private String type;
        private String value;

        public String getLang() {
            return lang;
        }
        public void setLang(String lang) {
            this.lang = lang;
        }
        public String getLength() {
            return length;
        }
        public void setLength(String length) {
            this.length = length;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }

    }

    public static class Network {
        private String id;
        private String value;
    
        // Getters and setters for all fields
    
        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public String getValue() {
            return value;
        }
    
        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class ExternalRef {
        private String system = "gracenote";
        private String refName;
        private String id;

        // Getters and setters for all fields

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

    public static class ProviderInfo {
        private String system;
        private String key;
        private Object value; 
        private String lang;

        // Getters and setters for all fields

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }

    public static class Release {
        private String country;
        private String type;
        private String date;
        private String medium;
        private String channelId;

        // Getters and setters for all fields

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }
    }

    public static class Credit {
        private String personId;
        private String type;
        private String ord;
        private String role;
        private String characterName;
        private Boolean departed;

        // Getters and setters

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrd() {
            return ord;
        }

        public void setOrd(String ord) {
            this.ord = ord;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }

        public Boolean getDeparted() {
            return departed;
        }

        public void setDeparted(Boolean departed) {
            this.departed = departed;
        }
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getOriginalAirDate() {
        return originalAirDate;
    }

    public void setOriginalAirDate(String originalAirDate) {
        this.originalAirDate = originalAirDate;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Network getOriginalNetwork() {
        return originalNetwork;
    }

    public void setOriginalNetwork(Network originalNetwork) {
        this.originalNetwork = originalNetwork;
    }

    public String getSeriesPremiere() {
        return seriesPremiere;
    }

    public void setSeriesPremiere(String seriesPremiere) {
        this.seriesPremiere = seriesPremiere;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<ExternalRef> getExternalRefs() {
        return externalRefs;
    }

    public void setExternalRefs(List<ExternalRef> externalRefs) {
        this.externalRefs = externalRefs;
    }

    public List<ProviderInfo> getProviderInfo() {
        return providerInfo;
    }

    public void setProviderInfo(List<ProviderInfo> providerInfo) {
        this.providerInfo = providerInfo;
    }

    public String getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getOriginalAudiolang() {
        return originalAudiolang;
    }

    public void setOriginalAudiolang(String originalAudiolang) {
        this.originalAudiolang = originalAudiolang;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    public List<String> getAdvisories() {
        return advisories;
    }

    public void setAdvisories(List<String> advisories) {
        this.advisories = advisories;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}