package ir.papiloo.practicewriting;

public class Item {
    private String imgAvatar;
    private String txtName;
    private String txtDate;
    private String txtMessage;

    public Item() {
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public String getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }

    public Item(String imgAvatar, String txtName, String txtDate, String txtMessage) {
        this.imgAvatar = imgAvatar;
        this.txtName = txtName;
        this.txtDate = txtDate;
        this.txtMessage = txtMessage;
    }
    public Item(String imgAvatar, String txtMessage) {
        this.imgAvatar = imgAvatar;
        this.txtMessage = txtMessage;
    }
}

