package com.example.program_genre_mapping_service.model;

import java.util.List;

public class TransformedProgram {

    private List<Title> titles;
    private List<Description> descriptions;
    private List<Genre> genres;
    private String originalAirDate;
    private String colorCode;
    private String sourceType;
    private Network originalNetwork;
    private String seriesPremiere;
    private String seasonNumber;
    private String originalAudiolang;
    private List<Release> releases;
    private List<Advisory> advisories;
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

    public static class Genre {
        private String genreId;
        private String value;
        
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        public String getGenreId() {
            return genreId;
        }
        public void setGenreId(String genreId) {
            this.genreId = genreId;
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

    public static class Advisory {
        private String value;
        private String ratingsBody;
        private String id;
        private String lang;
    
        // Getters and setters
        public String getValue() {
            return value;
        }
    
        public void setValue(String value) {
            this.value = value;
        }
    
        public String getRatingsBody() {
            return ratingsBody;
        }
    
        public void setRatingsBody(String ratingsBody) {
            this.ratingsBody = ratingsBody;
        }
    
        public String getId() {
            return id;
        }
    
        public void setId(String id) {
            this.id = id;
        }
    
        public String getLang() {
            return lang;
        }
    
        public void setLang(String lang) {
            this.lang = lang;
        }
    }

    public static class Credit {
        private String personId;
        private String ord;
        private String value;
        private String role;
        private String id;
        private String characterName;
        private String prefix;
        private String firstName;
        private String middleName;
        private String lastName;
        private String suffix;
        
        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        private String nameId;
        private Boolean departed;
        private String type; 

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getOrd() {
            return ord;
        }

        public void setOrd(String ord) {
            this.ord = ord;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getNameId() {
            return nameId;
        }

        public void setNameId(String nameId) {
            this.nameId = nameId;
        }

        public Boolean getDeparted() {
            return departed;
        }

        public void setDeparted(Boolean departed) {
            this.departed = departed;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
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

    public List<Advisory> getAdvisories() {
        return advisories;
    }

    public void setAdvisories(List<Advisory> advisories) {
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