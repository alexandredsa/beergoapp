package br.com.beergo.domain.dto;

/**
 * Created by alexandre.alves on 07/12/2016.
 */
public class UserDetail {
    private long id;
    private String name;
    private int level;
    private long experience;
    private long requiredExperience;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public long getRequiredExperience() {
        return requiredExperience;
    }

    public void setRequiredExperience(long requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    public String getSummary(UserDetail refreshedUser) {
        String summary = "Parabéns!\n";
        summary += "Você ganhou " + String.valueOf(refreshedUser.getExperience() - experience);

        if (refreshedUser.getLevel() > level)
            summary += "\nE alcançou o nível " + refreshedUser.getLevel();

        return summary;
    }
}
