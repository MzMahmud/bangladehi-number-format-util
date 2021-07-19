from constants import BANGLA, ENGLISH

digits_by_language = {
    BANGLA: ['০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'],
    ENGLISH: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
}


def find_out_language(num_str):
    num_str_digit_set = set(num_str)
    num_str_digit_set.discard('.')
    num_str_digit_set.discard('+')
    num_str_digit_set.discard('-')

    for language, digits in digits_by_language.items():
        digit_set = set(digits)
        if num_str_digit_set.issubset(digit_set):
            return language

    raise Exception(f'supported languages {digits_by_language.keys()}')


def convert_string(num_str: str, from_language: str, to_language: str) -> str:
    return ''.join(map(lambda char: convert_character(char, from_language, to_language), num_str))


def convert_character(char: str, from_language: str, to_language: str) -> str:
    try:
        digit_index = digits_by_language[from_language].index(char)
    except ValueError:
        return char

    return digits_by_language[to_language][digit_index]


def convert_index(index: int, language: str) -> str:
    if not is_digit(index):
        raise Exception('Must be a digit [0,9]')

    return digits_by_language[language][index]


def is_digit(digit: int) -> bool:
    return 0 <= digit <= 9
