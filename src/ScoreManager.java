public class ScoreManager {

    private double score;

    public void increaseScore() {
        score += 0.5;
    }

    public int getScore() {
        return (int) score;
    }

    public void reset() {
        score = 0;
    }
}