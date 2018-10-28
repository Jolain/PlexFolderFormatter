package plexFolderFormatter.pathHandler;

public class Path {
    // Members
    private String value;       // The full directory this object represents.
    private Path[] contents;    // The contents of the directory. Can be other paths or files.

    public Path() {
        // Nothing.
    }

    public Path(String path) {

    }

    @Override
    public String toString() {
        return this.value;
    }
}