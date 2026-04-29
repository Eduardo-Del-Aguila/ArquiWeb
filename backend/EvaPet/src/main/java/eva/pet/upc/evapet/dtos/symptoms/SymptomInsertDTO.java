package eva.pet.upc.evapet.dtos.symptoms;

import eva.pet.upc.evapet.enums.Severity;

public class SymptomInsertDTO {
    private String name;
    private Severity severity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}

