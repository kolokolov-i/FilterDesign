package superbro.filterdesign.synth;

public enum MetaLiter {
    Alpha,
    Digit,
    Space,
    Semicolon,
    Dot,
    Comma,
    Asterisk,
    Hash,
    BracketOpen,
    BracketClose,
    Other;

    public static MetaLiter translit(char c) {
        if (c > 0x40 && c < 0x5B)
        {
            return MetaLiter.Alpha;
        }
        if (c > 0x60 && c < 0x7B)
        {
            return MetaLiter.Alpha;
        }
        if (c >= 0x30 && c <= 0x39)
        {
            return MetaLiter.Digit;
        }
        switch (c)
        {
            case ' ': case '\t': return MetaLiter.Space;
            case '_': return MetaLiter.Alpha;
            case ';': return MetaLiter.Semicolon;
            case '.': return MetaLiter.Dot;
            case ',': return MetaLiter.Comma;
            case '*': return MetaLiter.Asterisk;
            case '#': return MetaLiter.Hash;
            case '(' : return MetaLiter.BracketOpen;
            case ')' : return MetaLiter.BracketClose;
            default: return MetaLiter.Other;
        }
    }
}
