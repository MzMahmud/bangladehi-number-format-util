from bd_number_in_word import convert_to_word
from bd_number_formatter import get_formatted_number
from char_processors import convert_string
from constants import BANGLA, ENGLISH


def test_input(num_str: str) -> None:
    print('>>> ', num_str)

    formatted_number = get_formatted_number(num_str)
    print('formatted number >>> ', formatted_number)

    number_in_word = convert_to_word(num_str)
    print('number in word >>>', number_in_word)
    print('-------------------------------------')


inputs = [
    '.061407',
    '9',
    '11',
    '50',
    '69',
    '100',
    '103',
    '150',
    '200',
    '404',
    '420',
    '85154646464',
    '-64679793.14155623',
    '-9790106'
]

bangla_inputs = map(lambda num: convert_string(num, ENGLISH, BANGLA), inputs)

for num_str in inputs:
    test_input(num_str)

for num_str in bangla_inputs:
    test_input(num_str)
