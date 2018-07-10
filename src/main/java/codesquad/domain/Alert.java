package codesquad.domain;

public class Alert {
    private String msg;
    private String url;

    public Alert() {
    }

    public Alert(String msg, String url) {
        this.msg = msg;
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
