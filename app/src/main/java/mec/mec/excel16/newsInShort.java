package mec.mec.excel16;

/**
 * Created by Hassan on 17-06-2016.
 */
public class newsInShort
{
    private String title;
    private int imageId;
    private String imageUrl;
    private String description;
    public String date;

    public newsInShort(){}

    public newsInShort(String title, int imageId)
    {
        this.title = title;
        this.imageId = imageId;
    }

    public void setTitles(String title){ this.title = title; }

    public void setImageIds(int imageId){    this.imageId = imageId; }

    public void setImageUrl(String imageUrl){   this.imageUrl = imageUrl;   }

    public void setDescription(String description){ this.description = description; }

    public void setDate(String date){   this.date = date;   }

    public String getDate() {   return date;    }

    public String getTitles(){  return title;   }

    public int getImageId(){    return imageId; }

    public String getImageUrl(){   return imageUrl;    }

    public String getDescription(){ return description; }

}

