package mec.mec.excel16;

/**
 * Created by jerin on 25/7/16.
 */
public class sponsors_data {
    private String titles,description;
    private int image;
    public sponsors_data(){
    }
    public sponsors_data(String titles,String description,int image){
        this.titles = titles;
        this.description = description;
        this.image = image;

    }
    public String getTitles(){
        return titles;
    }
    public void setTitles(String titles){
        this.titles = titles;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public  int getImage(){
        return image;
    }
    public void setImage(int image){
        this.image = image;
    }

}
