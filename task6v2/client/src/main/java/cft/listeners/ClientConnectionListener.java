package cft.listeners;

public interface ClientConnectionListener {
    public void onConnected(String nickName);
    public void onDisconnected();
}
