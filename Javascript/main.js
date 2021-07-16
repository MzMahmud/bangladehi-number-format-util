import convertToWord from "./src/bd-number-in-word.js"
import getFormattedNumber from "./src/bd-number-formatter.js"
import {convertString} from "./src/char-processors.js"
import {BANGLA, ENGLISH} from "./src/constants.js"

function testInput(input) {
    console.log('>>> ', input);
    let formattedNumber = getFormattedNumber(input);
    console.log('formatted number >>> ', formattedNumber);
    const numberInWord = convertToWord(input);
    console.log('number in word >>>', numberInWord);
    console.log('-------------------------------------');
}

const inputs = [
    '.061407',
    9,
    11,
    50,
    69,
    100,
    103,
    150,
    200,
    404,
    420,
    '85154646464',
    -64679793.14155623,
    -9790106
];

const banglaInputs = inputs.map(num => convertString(num, ENGLISH, BANGLA));

inputs.forEach(testInput)
banglaInputs.forEach(testInput)
