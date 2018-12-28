package guopuran.bwie.com.space04;

public class MsgBean {
    private Object msg;
    private String flag;

    public MsgBean(Object msg, String flag) {
        this.msg = msg;
        this.flag = flag;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
