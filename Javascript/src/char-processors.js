Set.prototype.isSubsetOf = function (otherSet) {
    return [...this].map(e => otherSet.has(e))
                    .every(cond => cond === true);
}

const digitsByLanguage = {
    "bangla": ['০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'],
    "english": ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
}

export function findOutLanguage(numStr) {
    const numStrDigitSet = new Set([...numStr]);
    numStrDigitSet.delete('.');
    numStrDigitSet.delete('+');
    numStrDigitSet.delete('-');

    for (const language in digitsByLanguage) {
        const digitSet = new Set(digitsByLanguage[language]);
        if (numStrDigitSet.isSubsetOf(digitSet))
            return language;
    }

    throw new Error(`supported languages ${Object.keys(digitsByLanguage)}`);
}

export function convertString(numStr, fromLanguage, toLanguage) {
    numStr = String(numStr);
    return [...numStr].map(char => convertCharacter(char, fromLanguage, toLanguage))
                      .join('');
}

function convertCharacter(char, fromLanguage, toLanguage) {
    const digitIndex = digitsByLanguage[fromLanguage].indexOf(char);
    if (digitIndex < 0) return char;
    return digitsByLanguage[toLanguage][digitIndex];
}

export function convertIndex(index, language) {
    const digitAsInt = Number(index);

    if (!isDigit(digitAsInt)) throw new Error('Must be a digit [0,9]');

    return digitsByLanguage[language][index];
}

function isDigit(digitAsInt) {
    return 0 <= digitAsInt && digitAsInt <= 9;
}