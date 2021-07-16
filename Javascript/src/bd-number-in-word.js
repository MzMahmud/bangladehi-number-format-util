import {constantByLanguage, numberToWord} from './constants.js'
import getFormattedNumber, {getLakhCroreSplit} from './bd-number-formatter.js'
import {findOutLanguage, convertIndex} from './char-processors.js'

export default function convertToWord(numStr) {
    numStr = String(numStr);
    const language = findOutLanguage(numStr);

    let inWord = '';

    const startsWithMinus = numStr.startsWith('-');
    const startsWithPlus = numStr.startsWith('+');
    if (startsWithPlus || startsWithMinus) {
        numStr = numStr.substring(1);
        inWord = startsWithMinus ? constantByLanguage[`${language}-`] + ' '
                                 : '';
    }

    const [beforeDecimal, afterDecimal] = numStr.split('.');

    inWord += convertToWordInteger(beforeDecimal, language);

    const afterDecimalInWord = convertToWordAfterDecimalPoint(afterDecimal, language);
    if (afterDecimalInWord !== '')
        inWord += ` ${constantByLanguage[language + '.']} ${afterDecimalInWord}`;

    return inWord;
}

function convertToWordInteger(numStr, language) {
    if(!numStr) return '';

    if (numStr.length <= 7)
        return convertToWordBelowCrore(getFormattedNumber(numStr), language);

    const {lakh, crore} = getLakhCroreSplit(numStr);

    return convertToWordInteger(crore, language)
        + " " + constantByLanguage[language + "Crore"]
        + " " + convertToWordBelowCrore(getFormattedNumber(lakh), language);
}

function convertToWordAfterDecimalPoint(numStr, language) {
    if (!numStr) return '';
    return [...String(numStr)].map(digit => convertToWordBelowHundred(digit, language))
                              .join(' ');
}

function convertToWordBelowCrore(bangladeshiCommaFormattedNumber, language) {
    language = language.toLowerCase();
    const splitByComma = bangladeshiCommaFormattedNumber.split(",").reverse();
    const numberInEng = [];
    for (let commaSplitIndex = splitByComma.length - 1; commaSplitIndex > 0; commaSplitIndex--) {
        const smallerNumInWord = convertToWordBelowHundred(splitByComma[commaSplitIndex], language);
        const indexChar = convertIndex(commaSplitIndex, language);
        if ("" !== smallerNumInWord) {
            numberInEng.push(smallerNumInWord);
            numberInEng.push(" ");
            numberInEng.push(numberToWord["comma-split-" + indexChar]);
            numberInEng.push(" ");
        }
    }
    numberInEng.push(convertToWordBelowThousand(splitByComma[0], language));
    return numberInEng.join('');
}

function convertToWordBelowThousand(numStr, language) {
    language = language.toLowerCase();

    if (numStr.length < 3)
        return convertToWordBelowHundred(numStr, language);

    const hundredthDigit = numStr[0];
    const belowHundred = numStr.substring(1);

    const const0 = constantByLanguage[language + "0"];
    if (const0 === hundredthDigit)
        return convertToWordBelowHundred(belowHundred, language);

    const const100 = constantByLanguage[language + "100"];

    let inWord = numberToWord[hundredthDigit] + numberToWord[const100];

    const belowHundredInWord = convertToWordBelowHundred(belowHundred, language);
    if (belowHundredInWord !== '') inWord += (" " + belowHundredInWord);

    return inWord;
}

function convertToWordBelowHundred(numStr, language) {
    language = language.toLowerCase();

    if (numStr in numberToWord)
        return numberToWord[numStr];

    const const00 = constantByLanguage[language + "00"];
    if (const00 === numStr) return "";

    const tenthsDigit = numStr[0];
    const unitsDigit = numStr[1];

    const const0 = constantByLanguage[language + "0"];
    if (const0 === tenthsDigit)
        return numberToWord[unitsDigit];

    return numberToWord[tenthsDigit + const0]
        + " "
        + numberToWord[unitsDigit];
}