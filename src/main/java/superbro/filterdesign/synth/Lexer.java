package superbro.filterdesign.synth;

import java.util.ArrayList;
import java.util.Map;

public class Lexer {

    private ArrayList<MessageRecord> errors, warnings;
    private ArrayList<Token> tokens;
    private State state;
    private StringBuilder curLexem;
    private int curLine, curPos, lexemLen, lexPos;

    public Lexer(ArrayList<MessageRecord> errors, ArrayList<MessageRecord> warnings) {
        this.errors = errors;
        this.warnings = warnings;
    }

    public ArrayList<Token> scan(String[] text) {
        reset();
        curLine = 0;
        for (String line : text) {
            scan(line);
            curLine++;
        }
        return tokens;
    }

    private void reset() {
        tokens.clear();
        resetLexem();
    }

    private void scan(String line) {
        boolean lineLoop = true;
        curPos = 0;
        while (lineLoop) {
            char c;
            MetaLiter w;
            if (curPos == line.length()) {
                c = '\0';
                w = MetaLiter.Space;
                lineLoop = false;
            } else {
                c = line.charAt(curPos++);
                w = MetaLiter.translit(c);
            }
            switch (state) {
                case S0:
                    lexPos = curPos - 1;
                    switch (w) {
                        case Alpha:
                            expandLex(c);
                            state = State.S1A;
                            break;
                        case Digit:
                            expandLex(c);
                            state = State.S2A;
                            break;
                        case Semicolon:
                            expandLex(c);
                            flushLexem(TokenType.SSem);
                            break;
                        case Dot:
                            expandLex(c);
                            flushLexem(TokenType.SDot);
                            break;
                        case Comma:
                            expandLex(c);
                            flushLexem(TokenType.SCom);
                            break;
                        case Asterisk:
                            expandLex(c);
                            flushLexem(TokenType.SAst);
                            break;
                        case Hash:
                            resetLexem();
                            lineLoop = false;
                            break;
                        case BracketOpen:
                            expandLex(c);
                            flushLexem(TokenType.SBrackO);
                            break;
                        case BracketClose:
                            expandLex(c);
                            flushLexem(TokenType.SBrackC);
                            break;
                        case Space:
                            break;
                        case Other:
                            flushError();
                    }
                    break;
                case S1A:
                    switch (w) {
                        case Alpha:
                        case Digit:
                            expandLex(c);
                            break;
                        default:
                            flushIDKW();
                            curPos--;
                    }
                    break;
                case S2A:
                    switch (w) {
                        case Digit:
                            expandLex(c);
                            break;
                        case Dot:
                            expandLex(c);
                            state = State.S2B;
                            break;
                        default:
                            flushLexem(TokenType.NumInt);
                            curPos--;
                    }
                    break;
                case S2B:
                    switch (w) {
                        case Digit:
                            expandLex(c);
                            break;
                        default:
                            flushLexem(TokenType.NumFloat);
                            curPos--;
                    }
            }
        }
    }

    private void flushLexem(TokenType type) {
        tokens.add(new Token(curLine, lexPos, lexemLen, curLexem.toString(), type));
        resetLexem();
    }

    private void flushIDKW() {
        String s = curLexem.toString();
        Map<String, TokenType> kw = TokenType.getKeyWords();
        if (kw.containsKey(s.toLowerCase())) {
            tokens.add(new Token(curLine, lexPos, lexemLen, s, kw.get(s)));
        } else {
            tokens.add(new Token(curLine, lexPos, lexemLen, s, TokenType.ID));
        }
        resetLexem();
    }

    private void flushError() {
        errors.add(new MessageRecord(curLine, curPos, "Unrecognized token \"" + curLexem.toString() + "\""));
        resetLexem();
    }

    private void flushError(String message) {
        errors.add(new MessageRecord(curLine, curPos, message));
        resetLexem();
    }

    private void flushWarning(String message) {
        warnings.add(new MessageRecord(curLine, curPos, message));
    }

    private void expandLex(char c) {
        curLexem.append(c);
        lexemLen++;
    }

    public void resetLexem() {
        state = State.S0;
        curLexem.setLength(0);
        lexemLen = 0;
    }

    private enum State {
        S0,
        S1A,
        S2A, S2B;
    }

}
