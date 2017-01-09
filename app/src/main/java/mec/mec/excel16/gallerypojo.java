package mec.mec.excel16;

/**
 * Created by jerin on 21/5/16.
 */
public class gallerypojo {
    String imageurl;
    String uploader;
    int nooflikes;
    String uploaderscomment;
    Boolean liked;

//    public gallerypojo(String image, String name, String comment){
//        this.imageurl=image;
//        this.uploader=name;
//        this.uploaderscomment=comment;
//
//    }
    public Boolean getImageliked(){
        return liked;
    }
    public void setimageliked(Boolean uploader){
        this.liked=uploader;
    }

    public String getImage(){
        return imageurl;
    }
    public void setimage(String uploader){
        this.imageurl=uploader;
    }


    public String getUploader(){
        return uploader;
    }
    public void setUploader(String uploader){
        this.uploader=uploader;
    }

   public  int getNooflikes(){
        return nooflikes;
    }
    public  void setnooflikes(int nooflikes){
        this.nooflikes=nooflikes;
    }

    public String getUploaderscomment(){
        return uploaderscomment;
    }
    public void setUploaderscomment(String uploaderscomment){
        this.uploaderscomment=uploaderscomment;
    }

}
