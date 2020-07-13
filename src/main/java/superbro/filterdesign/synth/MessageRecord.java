package superbro.filterdesign.synth;

public class MessageRecord {
    public int line, column;
    public String message;

    public MessageRecord(int line, int column, String message) {
        this.line = line;
        this.column = column;
        this.message = message;
    }

    public MessageRecord(String message) {
        this(0, 0, message);
    }
}
