public class NetworkPlayer implements Player {
    private boolean isWhite;

    public NetworkPlayer(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhite() { return isWhite; }

    @Override
    public boolean isHuman() { return false; }//blokujemy lokalna mysz
}