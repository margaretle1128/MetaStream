package com.example.program_transformation_service.service;

import com.example.program_transformation_service.model.CommonSchemaProgram;
import com.example.program_transformation_service.model.InputProgram;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonSchemaTransformer {

    public CommonSchemaProgram transform(InputProgram.Program program) {
        CommonSchemaProgram commonSchemaProgram = new CommonSchemaProgram();
        ObjectMapper objectMapper = new ObjectMapper();
        

        // Transform Titles
        if (program.getTitles() != null && program.getTitles().getTitle() != null) {
            List<CommonSchemaProgram.Title> titleList = new ArrayList<>();
            for (InputProgram.Program.Titles.TitleDetail titleDetail : program.getTitles().getTitle()) {
                CommonSchemaProgram.Title commonSchemaTitle = new CommonSchemaProgram.Title();
                commonSchemaTitle.setLang(titleDetail.getLang());
                commonSchemaTitle.setValue(titleDetail.getValue());
                commonSchemaTitle.setType(titleDetail.getType());
                commonSchemaTitle.setLength(titleDetail.getSize());
                commonSchemaTitle.setSubtype(titleDetail.getSubType());
                titleList.add(commonSchemaTitle);
            }
            commonSchemaProgram.setTitles(titleList);
        }

        // Transform Descriptions
        if (program.getDescriptions() != null && program.getDescriptions().getDesc() != null) {
            List<CommonSchemaProgram.Description> descriptionList = new ArrayList<>();
            for (InputProgram.Program.Descriptions.DescriptionDetail descDetail : program.getDescriptions().getDesc()) {
                CommonSchemaProgram.Description commonSchemaDescription = new CommonSchemaProgram.Description();
                commonSchemaDescription.setLang(descDetail.getLang());
                commonSchemaDescription.setLength(descDetail.getSize());
                commonSchemaDescription.setType(descDetail.getType());
                commonSchemaDescription.setValue(descDetail.getValue());
                descriptionList.add(commonSchemaDescription);
            }
            commonSchemaProgram.setDescriptions(descriptionList);
        }

        // Transform Genres
        if (program.getGenres() != null && program.getGenres().getGenre() != null) {
            List<CommonSchemaProgram.Genre> genreList = new ArrayList<>();
            for (InputProgram.Program.Genres.GenreDetail genreDetail : program.getGenres().getGenre()) {
                CommonSchemaProgram.Genre commonSchemaGenre = new CommonSchemaProgram.Genre();
                commonSchemaGenre.setGenreId(genreDetail.getGenreId());
                commonSchemaGenre.setValue(genreDetail.getValue());
                genreList.add(commonSchemaGenre);
            }
            commonSchemaProgram.setGenres(genreList);
        }

        // Transform Network
        if (program.getOriginalNetwork() != null) {
            CommonSchemaProgram.Network commonSchemaNetwork = new CommonSchemaProgram.Network();
            commonSchemaNetwork.setId(program.getOriginalNetwork().getPrgSvcId());
            commonSchemaNetwork.setValue(program.getOriginalNetwork().getValue());
            commonSchemaProgram.setOriginalNetwork(commonSchemaNetwork);
        }

        // Set simple fields
        commonSchemaProgram.setOriginalAirDate(program.getOrigAirDate());
        commonSchemaProgram.setColorCode(program.getColorCode());
        commonSchemaProgram.setSourceType(program.getSourceType());
        commonSchemaProgram.setSeriesPremiere(program.getSeriesPremiere());
        commonSchemaProgram.setDuration(program.getDuration());

        // Transform ExternalRefs
        List<CommonSchemaProgram.ExternalRef> externalRefs = new ArrayList<>();
        if (program.getTMSId() != null) {
            CommonSchemaProgram.ExternalRef tmsIdRef = new CommonSchemaProgram.ExternalRef();
            tmsIdRef.setRefName("TMSId");
            tmsIdRef.setId(program.getTMSId());
            externalRefs.add(tmsIdRef);
        }
        if (program.getRootId() != null) {
            CommonSchemaProgram.ExternalRef rootIdRef = new CommonSchemaProgram.ExternalRef();
            rootIdRef.setRefName("rootId");
            rootIdRef.setId(program.getRootId());
            externalRefs.add(rootIdRef);
        }
        if (program.getConnectorId() != null) {
            CommonSchemaProgram.ExternalRef connectorIdRef = new CommonSchemaProgram.ExternalRef();
            connectorIdRef.setRefName("connectorId");
            connectorIdRef.setId(program.getConnectorId());
            externalRefs.add(connectorIdRef);
        }
        if (program.getSeriesId() != null) {
            CommonSchemaProgram.ExternalRef seriesIdRef = new CommonSchemaProgram.ExternalRef();
            seriesIdRef.setRefName("seriesId");
            seriesIdRef.setId(program.getSeriesId());
            externalRefs.add(seriesIdRef);
        }
        commonSchemaProgram.setExternalRefs(externalRefs);

        // Transform ProviderInfo
        List<CommonSchemaProgram.ProviderInfo> providerInfoList = new ArrayList<>();
        String lang = program.getDescriptions() != null ? program.getDescriptions().getLang() : null;

        if (program.getProgType() != null) {
            CommonSchemaProgram.ProviderInfo progTypeInfo = new CommonSchemaProgram.ProviderInfo();
            progTypeInfo.setSystem("gracenote");
            progTypeInfo.setKey("progType");
            progTypeInfo.setValue(program.getProgType());
            progTypeInfo.setLang(lang);
            providerInfoList.add(progTypeInfo);
        }

        if (program.getSubType() != null) {
            CommonSchemaProgram.ProviderInfo subTypeInfo = new CommonSchemaProgram.ProviderInfo();
            subTypeInfo.setSystem("gracenote");
            subTypeInfo.setKey("subType");
            subTypeInfo.setValue(program.getSubType());
            subTypeInfo.setLang(lang);
            providerInfoList.add(subTypeInfo);
        }
        if (program.getUpdateId() != null) {
            CommonSchemaProgram.ProviderInfo updateIdInfo = new CommonSchemaProgram.ProviderInfo();
            updateIdInfo.setSystem("gracenote");
            updateIdInfo.setKey("updateId");
            updateIdInfo.setValue(program.getUpdateId());
            updateIdInfo.setLang(lang);
            providerInfoList.add(updateIdInfo);
        }

        commonSchemaProgram.setProviderInfo(providerInfoList);

        // Set countries
        if (program.getCountries() != null && !program.getCountries().getCountry().isEmpty()) {
            commonSchemaProgram.setCountries(program.getCountries().getCountry());
        }

        // Transform Releases
        if (program.getReleases() != null && program.getReleases().getRelease() != null) {
            List<CommonSchemaProgram.Release> releaseList = new ArrayList<>();
            for (InputProgram.Program.Releases.Release release : program.getReleases().getRelease()) {
                CommonSchemaProgram.Release commonSchemaRelease = new CommonSchemaProgram.Release();
                commonSchemaRelease.setCountry(release.getCountry());
                commonSchemaRelease.setType(release.getType());
                commonSchemaRelease.setDate(release.getDate());
                commonSchemaRelease.setMedium(release.getMedium());
                commonSchemaRelease.setChannelId(release.getPrgSvcId());
                releaseList.add(commonSchemaRelease);
            }
            commonSchemaProgram.setReleases(releaseList);
        }

        // Transform Advisories
        if (program.getRatings() != null && program.getRatings().getAdvisories() != null && program.getRatings().getAdvisories().getAdvisoryList() != null) {
            List<CommonSchemaProgram.Advisory> advisoryList = new ArrayList<>();
            for (InputProgram.Program.Ratings.Advisory advisory : program.getRatings().getAdvisories().getAdvisoryList()) {
                CommonSchemaProgram.Advisory commonSchemaAdvisory = new CommonSchemaProgram.Advisory();
                commonSchemaAdvisory.setValue(advisory.getValue());
                commonSchemaAdvisory.setRatingsBody(advisory.getRatingsBody());
                commonSchemaAdvisory.setId(null); 
                commonSchemaAdvisory.setLang(null);
                advisoryList.add(commonSchemaAdvisory);
            }
            commonSchemaProgram.setAdvisories(advisoryList);
        }

        // Set season number
        if (program.getEpisodeInfo() != null) {
            commonSchemaProgram.setSeasonNumber(program.getEpisodeInfo().getSeason());
        }

        // Set original audio language
        if (program.getOrigAudioLang() != null) {
            commonSchemaProgram.setOriginalAudiolang(program.getOrigAudioLang());
        }

        
        if (program.getProgType() != null) {
            switch (program.getProgType()) {
                case "Feature Film", "Short Film", "TV Movie":
                    commonSchemaProgram.setProgramType("MOVIE");
                    break;
                case "Series":
                    commonSchemaProgram.setProgramType("SERIES");
                    break;
                case "Paid Programming", "Special", "Miniseries", "Mdusic Video":
                    commonSchemaProgram.setProgramType("SHOW");
                    break;
                case "Sports Event", "Sports non-event":
                    commonSchemaProgram.setProgramType("SPORT");
                    break;
                default:
                    if (program.getSubType() != null) {
                        if (program.getSubType() == "Episode" || program.getSubType() == "Sport-Related Episode") {
                            commonSchemaProgram.setProgramType("EPISODE");
                        }
                    }
                    break;
            }
        }

        // Transform Cast and Crew to Credits
        List<CommonSchemaProgram.Credit> creditList = new ArrayList<>();

        if (program.getCast() != null && program.getCast().getMembers() != null) {
            for (InputProgram.Program.Member castMember : program.getCast().getMembers()) {
                CommonSchemaProgram.Credit credit = new CommonSchemaProgram.Credit();
                credit.setPersonId(castMember.getPersonId());
                credit.setOrd(castMember.getOrd());
                credit.setValue(castMember.getValue());
                credit.setRole(castMember.getRole().getValue());
                credit.setCharacterName(castMember.getCharacterName());
                credit.setId(castMember.getRole().getRoleId());
                if (castMember.getName() != null) {
                    credit.setPrefix(castMember.getName().getPrefix());
                    credit.setFirstName(castMember.getName().getFirst());
                    credit.setMiddleName(castMember.getName().getMiddle());
                    credit.setLastName(castMember.getName().getLast());
                    credit.setSuffix(castMember.getName().getSuffix());
                    credit.setNameId(castMember.getName().getNameId());
                }
                credit.setDeparted(castMember.getDeparted());
                credit.setType("cast");
                creditList.add(credit);
            }
        }

        if (program.getCrew() != null && program.getCrew().getMembers() != null) {
            for (InputProgram.Program.Member crewMember : program.getCrew().getMembers()) {
                CommonSchemaProgram.Credit credit = new CommonSchemaProgram.Credit();
                credit.setPersonId(crewMember.getPersonId());
                credit.setOrd(crewMember.getOrd());
                credit.setValue(crewMember.getValue());
                credit.setRole(crewMember.getRole().getValue());
                credit.setCharacterName(crewMember.getCharacterName());
                credit.setId(crewMember.getRole().getRoleId());
                if (crewMember.getName() != null) {
                    credit.setPrefix(crewMember.getName().getPrefix());
                    credit.setFirstName(crewMember.getName().getFirst());
                    credit.setMiddleName(crewMember.getName().getMiddle());
                    credit.setLastName(crewMember.getName().getLast());
                    credit.setSuffix(crewMember.getName().getSuffix());
                    credit.setNameId(crewMember.getName().getNameId());
                }
                credit.setDeparted(crewMember.getDeparted());
                credit.setType("crew");
                creditList.add(credit);
            }
        }

        commonSchemaProgram.setCredits(creditList);

        return commonSchemaProgram;
    }
}
