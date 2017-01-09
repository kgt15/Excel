package mec.mec.excel16;


public class proshowobject {

    String title;
    String subTitle;
    int drawable;

    public proshowobject(String title, String subTitle, int drawable) {
        this.title = title;
        this.subTitle = subTitle;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}

