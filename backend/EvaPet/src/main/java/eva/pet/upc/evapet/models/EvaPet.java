package eva.pet.upc.evapet.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "EvaPet")
public class EvaPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "name", length = 25, nullable = false)
    private String name;
    @Column(name = "description", length = 200, nullable = false)
    private String description;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "level", nullable = false)
    private int level;
    @Column(name = "experiencie", nullable = false)
    private int experiencie;
    @Column(name = "createAt", nullable = false)
    private LocalDateTime createAt;
    @Column(name = "lastInteraction", nullable = false)
    private LocalDateTime lastInteraction;
    @Column(name = "status", nullable = false)
    private String status;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperiencie() {
        return experiencie;
    }

    public void setExperiencie(int experiencie) {
        this.experiencie = experiencie;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(LocalDateTime lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
