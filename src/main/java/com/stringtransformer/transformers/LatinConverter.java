package com.stringtransformer.transformers;

import com.stringtransformer.annotations.TransformerComponent;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Map;

@Component
@TransformerComponent(groupId = "language", transformerId = "latin")
public class LatinConverter implements SimpleTransformer{
    @Override
    public String transform(String value) {

        String normalizedInput = Normalizer.normalize(value, Normalizer.Form.NFC);
        StringBuilder sb = new StringBuilder();

        for(char ch : normalizedInput.toCharArray()) {
            int codePoint = ch;
            if ((codePoint >= 0x0410 && codePoint <= 0x042F) // Uppercase Serbian Cyrillic
                    || (codePoint >= 0x0430 && codePoint <= 0x044F) // Lowercase Serbian Cyrillic
                    || codePoint == 0x0409 || codePoint == 0x0459 // Љ, љ
                    || codePoint == 0x040A || codePoint == 0x045A // Њ, њ
                    || codePoint == 0x040B || codePoint == 0x045B // Ћ, ћ
                    || codePoint == 0x040F || codePoint == 0x045F // Џ, џ
                    || codePoint == 0x0408 || codePoint == 0x0458) {// J, j
                sb.append(convertCyrillicToLatin(ch));
            } else if(codePoint >= 0x0370 && codePoint <= 0x03FF) {
                sb.append(convertGreekToLatin(ch));
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    //it wasn't specified in the requirements which cyrillic letters,
    // so I used Serbian alphabet
    private String convertCyrillicToLatin(char ch) {
        switch (ch) {
            case 'А': return "A";
            case 'Б': return "B";
            case 'В': return "V";
            case 'Г': return "G";
            case 'Д': return "D";
            case 'Ђ': return "Đ";
            case 'Е': return "E";
            case 'Ж': return "Ž";
            case 'З': return "Z";
            case 'И': return "I";
            case 'Ј': return "J";
            case 'К': return "K";
            case 'Л': return "L";
            case 'Љ': return "Lj";
            case 'М': return "M";
            case 'Н': return "N";
            case 'Њ': return "Nj";
            case 'О': return "O";
            case 'П': return "P";
            case 'Р': return "R";
            case 'С': return "S";
            case 'Т': return "T";
            case 'Ћ': return "Ć";
            case 'У': return "U";
            case 'Ф': return "F";
            case 'Х': return "H";
            case 'Ц': return "C";
            case 'Ч': return "Č";
            case 'Џ': return "Dž";
            case 'Ш': return "Š";

            case 'а': return "a";
            case 'б': return "b";
            case 'в': return "v";
            case 'г': return "g";
            case 'д': return "d";
            case 'ђ': return "đ";
            case 'е': return "e";
            case 'ж': return "ž";
            case 'з': return "z";
            case 'и': return "i";
            case 'ј': return "j";
            case 'к': return "k";
            case 'л': return "l";
            case 'љ': return "lj";
            case 'м': return "m";
            case 'н': return "n";
            case 'њ': return "nj";
            case 'о': return "o";
            case 'п': return "p";
            case 'р': return "r";
            case 'с': return "s";
            case 'т': return "t";
            case 'ћ': return "ć";
            case 'у': return "u";
            case 'ф': return "f";
            case 'х': return "h";
            case 'ц': return "c";
            case 'ч': return "č";
            case 'џ': return "dž";
            case 'ш': return "š";

            default:
                return String.valueOf(ch); // Default for any character not handled
        }
    }
    private String convertGreekToLatin(char ch) {
        switch (ch) {
            // Uppercase Greek to Uppercase Latin
            case 'Α': return "A"; // Greek 'Alpha' to Latin 'A'
            case 'Β': return "B"; // Greek 'Beta' to Latin 'B'
            case 'Γ': return "G"; // Greek 'Gamma' to Latin 'G'
            case 'Δ': return "D"; // Greek 'Delta' to Latin 'D'
            case 'Ε': return "E"; // Greek 'Epsilon' to Latin 'E'
            case 'Ζ': return "Z"; // Greek 'Zeta' to Latin 'Z'
            case 'Η': case 'Ή': return "I"; // Greek 'Eta' to Latin 'I'
            case 'Θ': return "Th"; // Greek 'Theta' to Latin 'Th'
            case 'Ι': case 'Ί': case 'Ϊ': return "I"; // Greek 'Iota' to Latin 'I'
            case 'Κ': return "K"; // Greek 'Kappa' to Latin 'K'
            case 'Λ': return "L"; // Greek 'Lambda' to Latin 'L'
            case 'Μ': return "M"; // Greek 'Mu' to Latin 'M'
            case 'Ν': return "N"; // Greek 'Nu' to Latin 'N'
            case 'Ξ': return "X"; // Greek 'Xi' to Latin 'X'
            case 'Ο': case 'Ό': return "O"; // Greek 'Omicron' to Latin 'O'
            case 'Π': return "P"; // Greek 'Pi' to Latin 'P'
            case 'Ρ': return "R"; // Greek 'Rho' to Latin 'R'
            case 'Σ': return "S"; // Greek 'Sigma' to Latin 'S'
            case 'Τ': return "T"; // Greek 'Tau' to Latin 'T'
            case 'Υ': case 'Ύ': case 'Ϋ': return "Y"; // Greek 'Upsilon' to Latin 'Y'
            case 'Φ': return "F"; // Greek 'Phi' to Latin 'Ph'
            case 'Χ': return "Ch"; // Greek 'Chi' to Latin 'Ch'
            case 'Ψ': return "Ps"; // Greek 'Psi' to Latin 'Ps'
            case 'Ω': case 'Ώ': return "O"; // Greek 'Omega' to Latin 'O'

            // Lowercase Greek to Lowercase Latin
            case 'α': case 'ά': return "a"; // Greek 'alpha' to Latin 'a'
            case 'β': return "b"; // Greek 'beta' to Latin 'b'
            case 'γ': return "g"; // Greek 'gamma' to Latin 'g'
            case 'δ': return "d"; // Greek 'delta' to Latin 'd'
            case 'ε': case 'έ': return "e"; // Greek 'epsilon' to Latin 'e'
            case 'ζ': return "z"; // Greek 'zeta' to Latin 'z'
            case 'η': case 'ή': return "i"; // Greek 'eta' to Latin 'i'
            case 'θ': return "th"; // Greek 'theta' to Latin 'th'
            case 'ι': case 'ί': case 'ϊ': case 'ΐ': return "i"; // Greek 'iota' to Latin 'i'
            case 'κ': return "k"; // Greek 'kappa' to Latin 'k'
            case 'λ': return "l"; // Greek 'lambda' to Latin 'l'
            case 'μ': return "m"; // Greek 'mu' to Latin 'm'
            case 'ν': return "n"; // Greek 'nu' to Latin 'n'
            case 'ξ': return "x"; // Greek 'xi' to Latin 'x'
            case 'ο': case 'ό': return "o"; // Greek 'omicron' to Latin 'o'
            case 'π': return "p"; // Greek 'pi' to Latin 'p'
            case 'ρ': return "r"; // Greek 'rho' to Latin 'r'
            case 'σ': case 'ς': return "s"; // Greek 'sigma' to Latin 's'
            case 'τ': return "t"; // Greek 'tau' to Latin 't'
            case 'υ': case 'ύ': case 'ϋ': case 'ΰ': return "u"; // Greek 'upsilon' to Latin 'y'
            case 'φ': return "ph"; // Greek 'phi' to Latin 'ph'
            case 'χ': return "ch"; // Greek 'chi' to Latin 'ch'
            case 'ψ': return "ps"; // Greek 'psi' to Latin 'ps'
            case 'ω': case 'ώ': return "o"; // Greek 'omega' to Latin 'o'

            default: return String.valueOf(ch); // Default for any character not handled
        }
    }
}
