package eva.pet.upc.evapet.dtos.eva;

public class EvaPetDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private int experiencie;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLevel(int level){this.level = level;}
    public int getLevel(){
        return this.level;
    }

    public int getExperiencie() {
        return experiencie;
    }

    public void setExperiencie(int experiencie) {
        this.experiencie = experiencie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
