export default function getFormattedNumber(numStr) {
    numStr = String(numStr);
    let formattedNumber = '';

    if (numStr.startsWith('-') || numStr.startsWith('+')) {
        formattedNumber = String(numStr[0]);
        numStr = numStr.substring(1);
    }

    const [beforeDecimal, afterDecimal] = numStr.split('.');

    formattedNumber += getFormattedInteger(beforeDecimal);
    if (afterDecimal) formattedNumber += `.${afterDecimal}`;

    return formattedNumber;
}

function getFormattedInteger(numStr) {
    if (numStr.length <= 7)
        return getFormattedNumberBelowCrore(numStr);

    const {lakh, crore} = getLakhCroreSplit(numStr);

    return getFormattedInteger(crore) + "," + getFormattedNumberBelowCrore(lakh);
}

export function getLakhCroreSplit(numStr) {
    numStr = String(numStr);
    if (numStr.length <= 7) {
        return {lakh: numStr, crore: ""};
    }
    const reversedDigit = [...numStr].reverse();
    return {
        lakh: reversedDigit.slice(0, 7).reverse().join(''),
        crore: reversedDigit.slice(7).reverse().join(''),
    }
}

function getFormattedNumberBelowCrore(numStr) {
    const reverseFormattedNumber = [];
    let charCount = 0;
    for (let i = numStr.length - 1; i >= 0; i--) {
        charCount++;
        const charAtI = numStr[i];
        if (isSeparator(charCount)) {
            reverseFormattedNumber.push(",");
        }
        reverseFormattedNumber.push(charAtI);
    }
    return reverseFormattedNumber.reverse().join('');
}

function isSeparator(charOrder) {
    return charOrder === 4
        || (charOrder > 4 && charOrder % 2 === 0);
}