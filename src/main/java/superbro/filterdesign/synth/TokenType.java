package superbro.filterdesign.synth;

import java.util.HashMap;

public enum TokenType {
    ID,
    NumInt,
    NumFloat,
    SDot,
    SCom,
    SSem,
    SAst,
    SBrackO,
    SBrackC,
    KWscheme,
    KWusing,
    KWentity,
    KWinterface,
    KWsignals,
    KWarchitecture,
    KWend,
    KWin,
    KWout;

    public static HashMap<String, TokenType> getKeyWords(){
        HashMap<String, TokenType> result = new HashMap<>();
        result.put("scheme", TokenType.KWscheme);
        result.put("using", TokenType.KWusing);
        result.put("entity", TokenType.KWentity);
        result.put("interface", TokenType.KWinterface);
        result.put("signals", TokenType.KWsignals);
        result.put("architecture", TokenType.KWarchitecture);
        result.put("end", TokenType.KWend);
        result.put("in", TokenType.KWin);
        result.put("out", TokenType.KWout);
        return result;
    }
}
