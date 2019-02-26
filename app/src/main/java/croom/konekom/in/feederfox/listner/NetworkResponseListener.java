package croom.konekom.in.feederfox.listner;

public interface NetworkResponseListener {

    public void preRequest();

    public void postRequest(String result);
}
