package superbro.filterdesign.synth;

public class Token {
    private TokenType type;
    private int posLine, posCol, length;
    private String image;

    public Token(int posLine, int posCol, int length, String image, TokenType type) {
        this.type = type;
        this.posLine = posLine;
        this.posCol = posCol;
        this.length = length;
        this.image = image;
    }

    public TokenType getType() {
        return type;
    }

    public int getPosLine() {
        return posLine;
    }

    public int getPosCol() {
        return posCol;
    }

    public int getLength() {
        return length;
    }

    public String getImage() {
        return image;
    }
}
