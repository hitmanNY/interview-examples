package playpen;

public class TowerOfHanoi {
    private int totalDisks;
    private int count;

    public TowerOfHanoi(int disks) {
        totalDisks = disks;
        count = 0;
    }

    public void solve() {
        moveToTower(totalDisks, 1, 3, 2);
    }

    private void moveToTower(int numberOfDisks, int start, int end, int tempTower) {
        if (numberOfDisks == 1) {
            moveDisk(start, end);
        } else {
            moveToTower(numberOfDisks - 1, start, tempTower, end);
            moveDisk(start, end);
            moveToTower(numberOfDisks - 1, tempTower, end, start);
        }
    }

    private void moveDisk(int start, int end) {
        count = count + 1;
        System.out.println("Move disk: " + start + " to " + end + " : Move count=" + count);
    }

    public static void main(String args[]) {
        TowerOfHanoi tower = new TowerOfHanoi(3);
        tower.solve();
        System.out.println("Total moves to complete: " + tower.count);
    }
}
