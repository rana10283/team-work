import java.util.ArrayList;

public class PipeManager {

    private ArrayList<Pipe> pipes;

    public PipeManager() {
        pipes = new ArrayList<>();
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public void clearPipes() {
        pipes.clear();
    }
}