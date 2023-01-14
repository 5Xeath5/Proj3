package WorldGen;

public class GameMech {

    private TestWorld GEN;

    private int totalLamps;
    public int onLamps;

    public GameMech(TestWorld gen) {
        GEN = gen;
        totalLamps = GEN.getTotalLamps();
    }
    public void addOnLamps() {
        onLamps += 1;
    }
    public void subOnLamps() {
        onLamps -= 1;
    }

    public boolean win() {
        if (onLamps == totalLamps) {
            return true;
        }
        return false;
    }

    public boolean lightTile() {
        if (GEN.storeAvatarTile == PersonalTiles.pFloor1 ||
                GEN.storeAvatarTile  == PersonalTiles.pFloor2 ||
                GEN.storeAvatarTile  == PersonalTiles.pFloor3 ||
                GEN.storeAvatarTile  == PersonalTiles.pFloor4 ||
                GEN.storeAvatarTile  == PersonalTiles.onLamp) {
            return true;
        }
        return false;
    }
}
