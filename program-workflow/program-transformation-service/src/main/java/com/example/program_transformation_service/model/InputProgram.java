package com.example.program_transformation_service.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InputProgram {
    private Programs programs;

    // Getter and Setter for Programs
    public Programs getPrograms() {
        return programs;
    }

    public void setPrograms(Programs programs) {
        this.programs = programs;
    }

    public static class Programs {
        private List<Program> program;

        // Getter and Setter for Program
        public List<Program> getProgram() {
            return program;
        }

        public void setProgram(List<Program> program) {
            this.program = program;
        }
    }

    public static class Program {
        @JsonProperty("TMSId")
        private String TMSId;
        @JsonProperty("rootId")
        private String rootId;
        @JsonProperty("connectorId")
        private String connectorId;
        @JsonProperty("seriesId")
        private String seriesId;
        @JsonProperty("seasonId")
        private String seasonId;
        @JsonProperty("updateId")
        private String updateId;
        @JsonProperty("updateDate")
        private String updateDate;
        @JsonProperty("titles")
        private Titles titles;
        @JsonProperty("descriptions")
        private Descriptions descriptions;
        @JsonProperty("progType")
        private String progType;
        @JsonProperty("subType")
        private String subType;
        @JsonProperty("genres")
        private Genres genres;
        @JsonProperty("origAirDate")
        private String origAirDate;
        @JsonProperty("colorCode")
        private String colorCode;
        @JsonProperty("sourceType")
        private String sourceType;
        @JsonProperty("originalNetwork")
        private Network originalNetwork;
        @JsonProperty("seriesPremiere")
        private String seriesPremiere;
        @JsonProperty("duration")
        private int duration;
        @JsonProperty("countries")
        private Countries countries;
        @JsonProperty("episodeInfo")
        private EpisodeInfo episodeInfo;
        @JsonProperty("releases")
        private Releases releases;
        @JsonProperty("origAudioLang")
        private String origAudioLang;
        @JsonProperty("ratings")
        private Ratings ratings;
        @JsonProperty("cast")
        private Cast cast;
        @JsonProperty("crew")
        private Crew crew;

        // Getters and setters for all fields

        public Ratings getRatings() {
            return ratings;
        }

        public void setRatings(Ratings ratings) {
            this.ratings = ratings;
        }

        public String getTMSId() {
            return TMSId;
        }

        public void setTMSId(String TMSId) {
            this.TMSId = TMSId;
        }

        public String getRootId() {
            return rootId;
        }

        public void setRootId(String rootId) {
            this.rootId = rootId;
        }

        public String getConnectorId() {
            return connectorId;
        }

        public void setConnectorId(String connectorId) {
            this.connectorId = connectorId;
        }

        public String getSeriesId() {
            return seriesId;
        }

        public void setSeriesId(String seriesId) {
            this.seriesId = seriesId;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public Titles getTitles() {
            return titles;
        }

        public void setTitles(Titles titles) {
            this.titles = titles;
        }

        public Descriptions getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(Descriptions descriptions) {
            this.descriptions = descriptions;
        }

        public String getProgType() {
            return progType;
        }

        public void setProgType(String progType) {
            this.progType = progType;
        }

        public String getSubType() {
            return subType;
        }

        public void setSubType(String subType) {
            this.subType = subType;
        }

        public Genres getGenres() {
            return genres;
        }

        public void setGenres(Genres genres) {
            this.genres = genres;
        }

        public String getOrigAirDate() {
            return origAirDate;
        }

        public void setOrigAirDate(String origAirDate) {
            this.origAirDate = origAirDate;
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

        public String getSeasonId() {
            return seasonId;
        }

        public void setSeasonId(String seasonId) {
            this.seasonId = seasonId;
        }

        public Countries getCountries() {
            return countries;
        }

        public void setCountries(Countries countries) {
            this.countries = countries;
        }

        public EpisodeInfo getEpisodeInfo() {
            return episodeInfo;
        }

        public void setEpisodeInfo(EpisodeInfo episodeInfo) {
            this.episodeInfo = episodeInfo;
        }

        public Releases getReleases() {
            return releases;
        }

        public void setReleases(Releases releases) {
            this.releases = releases;
        }

        public String getOrigAudioLang() {
            return origAudioLang;
        }

        public void setOrigAudioLang(String origAudioLang) {
            this.origAudioLang = origAudioLang;
        }

        public Cast getCast() {
            return cast;
        }

        public void setCast(Cast cast) {
            this.cast = cast;
        }

        public Crew getCrew() {
            return crew;
        }

        public void setCrew(Crew crew) {
            this.crew = crew;
        }

        // Nested classes for Titles, Descriptions, Genres, Network, Countries, EpisodeInfo, Releases, and AudioOriginalLangs

        public static class Titles {
            @JsonProperty("lang")
            private String lang;
            @JsonProperty("title")
            private List<TitleDetail> title;

            // Getters and setters for all fields

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public List<TitleDetail> getTitle() {
                return title;
            }

            public void setTitle(List<TitleDetail> title) {
                this.title = title;
            }

            public static class TitleDetail {
                @JsonProperty("size")
                private String size;
                @JsonProperty("type")
                private String type;
                @JsonProperty("subType")
                private String subType;
                @JsonProperty("lang")
                private String lang;
                @JsonProperty("value")
                private String value;

                // Getters and setters for all fields

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getSubType() {
                    return subType;
                }

                public void setSubType(String subType) {
                    this.subType = subType;
                }

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
            }
        }

        public static class Descriptions {
            @JsonProperty("lang")
            private String lang;

            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("desc")
            private List<DescriptionDetail> desc;

            public String getLang() {
                return lang;
            }

            public void setLang(String lang) {
                this.lang = lang;
            }

            public List<DescriptionDetail> getDesc() {
                return desc;
            }

            public void setDesc(List<DescriptionDetail> desc) {
                this.desc = desc;
            }

            public static class DescriptionDetail {
                @JsonProperty("size")
                private String size;
                @JsonProperty("type")
                private String type;
                @JsonProperty("lang")
                private String lang;
                @JsonProperty("value")
                private String value;

                // Getters and setters for all fields

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

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
            }
        }

        public static class Genres {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("genre")
            private List<GenreDetail> genre;

            // Getters and setters for all fields

            public List<GenreDetail> getGenre() {
                return genre;
            }

            public void setGenre(List<GenreDetail> genre) {
                this.genre = genre;
            }

            public static class GenreDetail {
                @JsonProperty("genreId")
                private String genreId;
                @JsonProperty("value")
                private String value;

                // Getters and setters for all fields

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
        }

        public static class Network {
            @JsonProperty("prgSvcId")
            private String prgSvcId;
            @JsonProperty("value")
            private String value;

            // Getters and setters for all fields

            public String getPrgSvcId() {
                return prgSvcId;
            }

            public void setPrgSvcId(String prgSvcId) {
                this.prgSvcId = prgSvcId;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class Countries {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("country")
            private List<String> country;

            public List<String> getCountry() {
                return country;
            }

            public void setCountry(List<String> country) {
                this.country = country;
            }
        }

        public static class EpisodeInfo {
            @JsonProperty("season")
            private String season;
            @JsonProperty("title")
            private TitleDetail title;

            public String getSeason() {            
                return season;
            }

            public void setSeason(String season) {
                this.season = season;
            }

            public TitleDetail getTitle() {
                return title;
            }

            public void setTitle(TitleDetail title) {
                this.title = title;
            }

            public static class TitleDetail {
                @JsonProperty("size")
                private String size;
                @JsonProperty("type")
                private String type;
                @JsonProperty("subType")
                private String subType;
                @JsonProperty("lang")
                private String lang;
                @JsonProperty("title")
                private String title;

                // Getters and setters for all fields

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getSubType() {
                    return subType;
                }

                public void setSubType(String subType) {
                    this.subType = subType;
                }

                public String getLang() {
                    return lang;
                }

                public void setLang(String lang) {
                    this.lang = lang;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }

        public static class Releases {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("release")
            private List<Release> release;

            public List<Release> getRelease() {
                return release;
            }

            public void setRelease(List<Release> release) {
                this.release = release;
            }

            public static class Release {
                @JsonProperty("country")
                private String country;
                @JsonProperty("type")
                private String type;
                @JsonProperty("date")
                private String date;
                @JsonProperty("medium")
                private String medium;
                @JsonProperty("prgSvcId")
                private String prgSvcId;

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

                public String getPrgSvcId() {
                    return prgSvcId;
                }

                public void setPrgSvcId(String prgSvcId) {
                    this.prgSvcId = prgSvcId;
                }
            }
        }

        public static class AudioOriginalLangs {
            @JsonProperty("audioOriginalLang")
            private List<AudioOriginalLangDetail> audioOriginalLang;

            // Getters and setters for all fields

            public List<AudioOriginalLangDetail> getAudioOriginalLang() {
                return audioOriginalLang;
            }

            public void setAudioOriginalLang(List<AudioOriginalLangDetail> audioOriginalLang) {
                this.audioOriginalLang = audioOriginalLang;
            }

            public static class AudioOriginalLangDetail {
                @JsonProperty("ord")
                private String ord;
                @JsonProperty("lang")
                private String lang;

                // Getters and setters for all fields

                public String getOrd() {
                    return ord;
                }

                public void setOrd(String ord) {
                    this.ord = ord;
                }

                public String getLang() {
                    return lang;
                }

                public void setLang(String lang) {
                    this.lang = lang;
                }
            }
        }

        public static class Ratings {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("advisories")
            private Advisories advisories;
        
            public Advisories getAdvisories() {
                return advisories;
            }
        
            public void setAdvisories(Advisories advisories) {
                this.advisories = advisories;
            }
        
            public static class Advisories {
                @JsonProperty("advisory")
                private List<Advisory> advisoryList;
        
                public List<Advisory> getAdvisoryList() {
                    return advisoryList;
                }
        
                public void setAdvisoryList(List<Advisory> advisoryList) {
                    this.advisoryList = advisoryList;
                }
            }
        
            public static class Advisory {
                @JsonProperty("value")
                private String value;
                @JsonProperty("ratingsBody")
                private String ratingsBody;
        
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
            }
        }

        public static class Member {
            @JsonProperty("personId")
            private String personId;
            @JsonProperty("ord")
            private String ord;
            @JsonProperty("value")
            private String value;
            @JsonProperty("characterName")
            private String characterName;
            @JsonProperty("role")
            private Role role;
            @JsonProperty("name")
            private Name name;
            @JsonProperty("departed")
            private Boolean departed;

            public static class Name {
                @JsonProperty("prefix")
                private String prefix;
                @JsonProperty("first")
                private String first;
                @JsonProperty("middle")
                private String middle;
                @JsonProperty("last")
                private String last;
                @JsonProperty("suffix")
                private String suffix;
                @JsonProperty("nameId")
                private String nameId;

                public String getPrefix() {
                    return prefix;
                }
                public void setPrefix(String prefix) {
                    this.prefix = prefix;
                }
                public String getFirst() {
                    return first;
                }
                public void setFirst(String first) {
                    this.first = first;
                }
                public String getMiddle() {
                    return middle;
                }
                public void setMiddle(String middle) {
                    this.middle = middle;
                }
                public String getLast() {
                    return last;
                }
                public void setLast(String last) {
                    this.last = last;
                }
                public String getSuffix() {
                    return suffix;
                }
                public void setSuffix(String suffix) {
                    this.suffix = suffix;
                }
                public String getNameId() {
                    return nameId;
                }
                public void setNameId(String nameId) {
                    this.nameId = nameId;
                }
            }

            public static class Role {
                @JsonProperty("roleId")
                private String roleId;
                @JsonProperty("value")
                private String value;

                public String getRoleId() {
                    return roleId;
                }

                public void setRoleId(String roleId) {
                    this.roleId = roleId;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

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
            public String getCharacterName() {
                return characterName;
            }
            public void setCharacterName(String characterName) {
                this.characterName = characterName;
            }
            public Role getRole() {
                return role;
            }
            public void setRole(Role role) {
                this.role = role;
            }
            public Boolean getDeparted() {
                return departed;
            }
            public void setDeparted(Boolean departed) {
                this.departed = departed;
            }
            public Name getName() {
                return name;
            }
            public void setName(Name name) {
                this.name = name;
            }
        }

        public static class Cast {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("member")
            private List<Member> members;

            public List<Member> getMembers() {
                return members;
            }

            public void setMembers(List<Member> members) {
                this.members = members;
            }
        }

        public static class Crew {
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            @JsonProperty("member")
            private List<Member> members;

            public List<Member> getMembers() {
                return members;
            }

            public void setMembers(List<Member> members) {
                this.members = members;
            }
        }
    }
}
