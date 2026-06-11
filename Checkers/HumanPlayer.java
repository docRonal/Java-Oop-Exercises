public class HumanPlayer implements Player {
    private boolean isWhite;

    public HumanPlayer(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhite() { return isWhite; }

    @Override
    public boolean isHuman() { return true; }
}