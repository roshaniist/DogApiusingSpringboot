package org.example.dogApi.model;

public class SubBreed {
    private int id;
    private String name;
    private int breedId;

    public SubBreed() {}
    public SubBreed(String name, int breedId) {
        this.name = name;
        this.breedId = breedId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getBreedId() { return breedId; }
    public void setBreedId(int breedId) { this.breedId = breedId; }

    @Override
    public String toString() {
        return "SubBreed{id=" + id + ", name='" + name + "', breedId=" + breedId + "}";
    }
}
