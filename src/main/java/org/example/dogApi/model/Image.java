package org.example.dogApi.model;

public class Image {
    private int id;
    private String url;
    private int breedId;
    private int subBreedId;

    public Image() {}
    public Image(String url, int breedId, int subBreedId) {
        this.url = url;
        this.breedId = breedId;
        this.subBreedId = subBreedId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public int getBreedId() { return breedId; }
    public void setBreedId(int breedId) { this.breedId = breedId; }

    public int getSubBreedId() { return subBreedId; }
    public void setSubBreedId(int subBreedId) { this.subBreedId = subBreedId; }

    @Override
    public String toString() {
        return "Image{id=" + id + ", url='" + url + "'}";
    }
}
