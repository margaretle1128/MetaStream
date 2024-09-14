package com.example.program_credit_mapping_service.model;

public class CreditEntity {
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
