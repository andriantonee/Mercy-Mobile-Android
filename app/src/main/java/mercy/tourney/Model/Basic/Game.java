package mercy.tourney.Model.Basic;

/**
 * Created by Andrianto on 12/15/2016.
 */
public class Game {
    private Integer id;
    private String name;
    private String image_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
