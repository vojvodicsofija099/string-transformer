package com.stringtransformer.transformers;

import com.stringtransformer.annotations.TransformerComponent;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

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
        return switch (ch) {
            case 'А' -> "A";
            case 'Б' -> "B";
            case 'В' -> "V";
            case 'Г' -> "G";
            case 'Д' -> "D";
            case 'Ђ' -> "Đ";
            case 'Е' -> "E";
            case 'Ж' -> "Ž";
            case 'З' -> "Z";
            case 'И' -> "I";
            case 'Ј' -> "J";
            case 'К' -> "K";
            case 'Л' -> "L";
            case 'Љ' -> "Lj";
            case 'М' -> "M";
            case 'Н' -> "N";
            case 'Њ' -> "Nj";
            case 'О' -> "O";
            case 'П' -> "P";
            case 'Р' -> "R";
            case 'С' -> "S";
            case 'Т' -> "T";
            case 'Ћ' -> "Ć";
            case 'У' -> "U";
            case 'Ф' -> "F";
            case 'Х' -> "H";
            case 'Ц' -> "C";
            case 'Ч' -> "Č";
            case 'Џ' -> "Dž";
            case 'Ш' -> "Š";
            case 'а' -> "a";
            case 'б' -> "b";
            case 'в' -> "v";
            case 'г' -> "g";
            case 'д' -> "d";
            case 'ђ' -> "đ";
            case 'е' -> "e";
            case 'ж' -> "ž";
            case 'з' -> "z";
            case 'и' -> "i";
            case 'ј' -> "j";
            case 'к' -> "k";
            case 'л' -> "l";
            case 'љ' -> "lj";
            case 'м' -> "m";
            case 'н' -> "n";
            case 'њ' -> "nj";
            case 'о' -> "o";
            case 'п' -> "p";
            case 'р' -> "r";
            case 'с' -> "s";
            case 'т' -> "t";
            case 'ћ' -> "ć";
            case 'у' -> "u";
            case 'ф' -> "f";
            case 'х' -> "h";
            case 'ц' -> "c";
            case 'ч' -> "č";
            case 'џ' -> "dž";
            case 'ш' -> "š";
            default -> String.valueOf(ch); // Default for any character not handled
        };
    }
    private String convertGreekToLatin(char ch) {
        return switch (ch) {
            // Uppercase Greek to Uppercase Latin
            case 'Α' -> "A"; // Greek 'Alpha' to Latin 'A'
            case 'Β' -> "B"; // Greek 'Beta' to Latin 'B'
            case 'Γ' -> "G"; // Greek 'Gamma' to Latin 'G'
            case 'Δ' -> "D"; // Greek 'Delta' to Latin 'D'
            case 'Ε' -> "E"; // Greek 'Epsilon' to Latin 'E'
            case 'Ζ' -> "Z"; // Greek 'Zeta' to Latin 'Z'
            case 'Η', 'Ή' -> "I"; // Greek 'Eta' to Latin 'I'
            case 'Θ' -> "Th"; // Greek 'Theta' to Latin 'Th'
            case 'Ι', 'Ί', 'Ϊ' -> "I"; // Greek 'Iota' to Latin 'I'
            case 'Κ' -> "K"; // Greek 'Kappa' to Latin 'K'
            case 'Λ' -> "L"; // Greek 'Lambda' to Latin 'L'
            case 'Μ' -> "M"; // Greek 'Mu' to Latin 'M'
            case 'Ν' -> "N"; // Greek 'Nu' to Latin 'N'
            case 'Ξ' -> "X"; // Greek 'Xi' to Latin 'X'
            case 'Ο', 'Ό' -> "O"; // Greek 'Omicron' to Latin 'O'
            case 'Π' -> "P"; // Greek 'Pi' to Latin 'P'
            case 'Ρ' -> "R"; // Greek 'Rho' to Latin 'R'
            case 'Σ' -> "S"; // Greek 'Sigma' to Latin 'S'
            case 'Τ' -> "T"; // Greek 'Tau' to Latin 'T'
            case 'Υ', 'Ύ', 'Ϋ' -> "Y"; // Greek 'Upsilon' to Latin 'Y'
            case 'Φ' -> "F"; // Greek 'Phi' to Latin 'Ph'
            case 'Χ' -> "Ch"; // Greek 'Chi' to Latin 'Ch'
            case 'Ψ' -> "Ps"; // Greek 'Psi' to Latin 'Ps'
            case 'Ω', 'Ώ' -> "O"; // Greek 'Omega' to Latin 'O'

            // Lowercase Greek to Lowercase Latin
            case 'α', 'ά' -> "a"; // Greek 'alpha' to Latin 'a'
            case 'β' -> "b"; // Greek 'beta' to Latin 'b'
            case 'γ' -> "g"; // Greek 'gamma' to Latin 'g'
            case 'δ' -> "d"; // Greek 'delta' to Latin 'd'
            case 'ε', 'έ' -> "e"; // Greek 'epsilon' to Latin 'e'
            case 'ζ' -> "z"; // Greek 'zeta' to Latin 'z'
            case 'η', 'ή' -> "i"; // Greek 'eta' to Latin 'i'
            case 'θ' -> "th"; // Greek 'theta' to Latin 'th'
            case 'ι', 'ί', 'ϊ', 'ΐ' -> "i"; // Greek 'iota' to Latin 'i'
            case 'κ' -> "k"; // Greek 'kappa' to Latin 'k'
            case 'λ' -> "l"; // Greek 'lambda' to Latin 'l'
            case 'μ' -> "m"; // Greek 'mu' to Latin 'm'
            case 'ν' -> "n"; // Greek 'nu' to Latin 'n'
            case 'ξ' -> "x"; // Greek 'xi' to Latin 'x'
            case 'ο', 'ό' -> "o"; // Greek 'omicron' to Latin 'o'
            case 'π' -> "p"; // Greek 'pi' to Latin 'p'
            case 'ρ' -> "r"; // Greek 'rho' to Latin 'r'
            case 'σ', 'ς' -> "s"; // Greek 'sigma' to Latin 's'
            case 'τ' -> "t"; // Greek 'tau' to Latin 't'
            case 'υ', 'ύ', 'ϋ', 'ΰ' -> "u"; // Greek 'upsilon' to Latin 'y'
            case 'φ' -> "ph"; // Greek 'phi' to Latin 'ph'
            case 'χ' -> "ch"; // Greek 'chi' to Latin 'ch'
            case 'ψ' -> "ps"; // Greek 'psi' to Latin 'ps'
            case 'ω', 'ώ' -> "o"; // Greek 'omega' to Latin 'o'

            default -> String.valueOf(ch); // Default for any character not handled
        };
    }
}
